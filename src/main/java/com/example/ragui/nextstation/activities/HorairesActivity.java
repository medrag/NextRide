package com.example.ragui.nextstation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.adapters.HorairesCardViewAdapter;
import com.example.ragui.nextstation.model.StopTime;
import com.example.ragui.nextstation.model.Time;
import com.example.ragui.nextstation.remoteServices.HorairesService;
import com.example.ragui.nextstation.remoteServices.RetrofitRequests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorairesActivity extends AppCompatActivity
{
    String nomArret,codeArret, codeTransport;
    HorairesService horairesService;
    List<String> destinationList;
    HashMap<String, List<Time>> horaireArrivee;
    RecyclerView recyclerView;
    HorairesCardViewAdapter horairesCardViewAdapter;
    ProgressBar progressBar;
    Handler mhandler;
    Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaires);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        mhandler = new Handler();


        horairesService = RetrofitRequests.getStopTime();
        recyclerView = findViewById(R.id.horairesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        destinationList = new ArrayList<String>();
        horaireArrivee = new HashMap<>();


        nomArret = getIntent().getExtras().getString("nomArret");
        codeArret = getIntent().getExtras().getString("codeArret");
        codeTransport = getIntent().getExtras().getString("codeTransport");

        getSupportActionBar().setTitle("Ligne "+codeTransport);
        getSupportActionBar().setSubtitle(nomArret);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getStopTimes(codeArret);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refreshStopTimes();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Stop the handler when destroying the fragment
        mhandler.removeCallbacks(runnable);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        //stop the handler when the app is on Pause
        mhandler.removeCallbacks(runnable);
    }

    /*
            call getStopTimes every 30 sec to refresh stopTimes in the UI automatically
         */
    private void refreshStopTimes()
    {
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                getStopTimes(codeArret);
                Toast.makeText(HorairesActivity.this,"Horaires actualisés",Toast.LENGTH_LONG).show();
                mhandler.postDelayed(this,30000);
            }
        };
        mhandler.postDelayed(runnable,30000);
    }


    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.horaires_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.accueil:
                    Intent mainActivity = new Intent(HorairesActivity.this,MainActivity.class);
                    startActivity(mainActivity);
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                break;

            case R.id.favoris:

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    //get Horaires de passage
    public void getStopTimes(String codeArret)
    {
        horairesService.getStopTime(codeArret).enqueue(new Callback<List<StopTime>>()
        {
            @Override
            public void onResponse(Call<List<StopTime>> call, Response<List<StopTime>> response)
            {
                destinationList.clear();
                horaireArrivee.clear();

                for(StopTime stopTime : response.body())
                {
                    if(stopTime.getPattern().getId().contains(codeTransport))
                    {
                        destinationList.add(stopTime.getPattern().getDesc());
                        horaireArrivee.put(stopTime.getPattern().getDesc(),stopTime.getTimes());
                    }
                }

                horairesCardViewAdapter = new HorairesCardViewAdapter(HorairesActivity.this,destinationList,horaireArrivee,nomArret);
                recyclerView.setAdapter(horairesCardViewAdapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<StopTime>> call, Throwable t)
            {
                Log.e("getStopTimes",t.getMessage());
            }
        });
    }

}
