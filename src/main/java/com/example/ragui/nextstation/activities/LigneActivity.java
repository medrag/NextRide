package com.example.ragui.nextstation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.adapters.LigneCardViewAdapter;
import com.example.ragui.nextstation.model.Ligne;
import com.example.ragui.nextstation.model.Route;
import com.example.ragui.nextstation.remoteServices.LigneService;
import com.example.ragui.nextstation.remoteServices.RetrofitRequests;
import com.example.ragui.nextstation.remoteServices.RouteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LigneActivity extends AppCompatActivity
{
    LigneService ligneService;
    RouteService routeService;
    List<String> routesNames, bgColors,arretsNames,transpType;
    HashMap<String,String> idShortName,codeNameArret,shortNameBgColor,stations1,stations2,cityNameArret;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LigneCardViewAdapter ligneCardViewAdapter;
    int transpIconSrc;
    String typeTransport;
    TextView titleTxtView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne);

        routesNames = new ArrayList<String>();
        bgColors = new ArrayList<String>();
        arretsNames = new ArrayList<String>();
        transpType = new ArrayList<String>();
        idShortName = new HashMap<String,String>();
        codeNameArret = new HashMap<String, String>();
        shortNameBgColor = new HashMap<String,String>();
        stations1 = new HashMap<String, String>();
        stations2 =  new HashMap<String, String>();
        cityNameArret = new HashMap<String, String>();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        titleTxtView = findViewById(R.id.textGrid);

        ligneService = RetrofitRequests.getTAGLignes();
        routeService = RetrofitRequests.getRouteByCode();
        //get TAG lignes
        typeTransport = getIntent().getExtras().getString("typeTransport");
        titleTxtView.setText(typeTransport);
        getLignes(typeTransport);

        recyclerView = findViewById(R.id.ligneRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    //Get Tag Lignes
    private void getLignes(final String typeTransport)
    {
        ligneService.getTAGLignes().enqueue(new Callback<List<Ligne>>()
        {
            @Override
            public void onResponse(Call<List<Ligne>> call, Response<List<Ligne>> response)
            {

                for(Ligne ligne : response.body())
                {
                    if(ligne.getId().contains("SEM") && ligne.getType().equals(typeTransport))
                    {
                        if(!ligne.getId().equals("SEM:11"))
                        {
                            routesNames.add(ligne.getShortName());
                            idShortName.put(ligne.getShortName(), ligne.getId());
                            shortNameBgColor.put(ligne.getShortName(),ligne.getColor());

                            String[] stations = new String[2];
                            stations = ligne.getLongName().split("/");

                            stations1.put(ligne.getShortName(),stations[0]);
                            stations2.put(ligne.getShortName(),stations[1]);

                            if(!ligne.getType().equals("TRAM"))
                            {
                                transpType.add("BUS");
                                transpIconSrc = R.drawable.ic_bus;
                            }

                            if(ligne.getType().equals("TRAM"))
                            {
                                transpType.add("TRAM");
                                transpIconSrc = R.drawable.ic_tram;
                            }
                        }
                    }
                }

                //call ligneFragmentAdapter
                ligneCardViewAdapter = new LigneCardViewAdapter(LigneActivity.this,routesNames,transpType,transpIconSrc,shortNameBgColor,stations1,stations2,idShortName,typeTransport);
                recyclerView.setAdapter(ligneCardViewAdapter);

                sortList(routesNames);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Ligne>> call, Throwable t)
            {
                Log.e("ERROR_1",t.getMessage());
            }
        });
    }

    private void sortList(List<String> routesNames)
    {
        Collections.sort(routesNames, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                if(o1.length() > o2.length())
                    return 1;
                else if (o1.length() < o2.length())
                    return -1;

                return o1.compareTo(o2);
            }
        });
    }
}
