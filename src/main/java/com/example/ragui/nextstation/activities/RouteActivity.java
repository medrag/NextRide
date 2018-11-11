package com.example.ragui.nextstation.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.adapters.RouteCardViewAdapter;
import com.example.ragui.nextstation.model.Route;
import com.example.ragui.nextstation.remoteServices.RetrofitRequests;
import com.example.ragui.nextstation.remoteServices.RouteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    RouteCardViewAdapter routeCardViewAdapter;
    List<String> routeList;
    HashMap<String,String> codeNameArret,cityNameArret;
    String typeTransport;
    ProgressBar progressBar;
    RouteService routeService;
    String[] transp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.routeRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        routeService = RetrofitRequests.getRouteByCode();

        routeList = new ArrayList<String>();
        codeNameArret = new HashMap<String,String>();
        cityNameArret = new HashMap<String,String>();

        typeTransport = getIntent().getExtras().getString("typeTransport");
        transp = getIntent().getExtras().getString("codeTransport").split(":");
        getSupportActionBar().setTitle("Ligne "+transp[1]);
        getSupportActionBar().setSubtitle("Arrêts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getRouteByCode(transp[0]+":"+transp[1]);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_options_menu, menu);

        //Associate searchable config with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                routeCardViewAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                routeCardViewAdapter.filter(newText);
                return true;
            }
        });
        return true;
    }

    //Get Arrets
    public void getRouteByCode(final String code)
    {
        routeService.getRouteByCode(code).enqueue(new Callback<List<Route>>()
        {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response)
            {
                routeList.clear();
                codeNameArret.clear();
                cityNameArret.clear();

                for(Route route : response.body())
                {
                    routeList.add(route.getName());
                    codeNameArret.put(route.getName(),route.getCode());
                    cityNameArret.put(route.getName(),route.getCity());
                }

                routeCardViewAdapter = new RouteCardViewAdapter(RouteActivity.this,routeList,cityNameArret,codeNameArret,transp[1]);
                recyclerView.setAdapter(routeCardViewAdapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t)
            {
                Log.e("ERROR_2",t.getMessage());
            }
        });
    }
}
