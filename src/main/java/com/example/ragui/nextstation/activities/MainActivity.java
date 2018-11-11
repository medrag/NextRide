package com.example.ragui.nextstation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.adapters.MainCardViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    List<String> lignes;
    List<Integer> imagesRessources;
    MainCardViewAdapter mainCardViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        lignes = new ArrayList<>();
        lignes.add("7  lignes");
        lignes.add("12 lignes");
        lignes.add("5  lignes");
        lignes.add("28 lignes");

        imagesRessources = new ArrayList<>();
        imagesRessources.add(R.drawable.chrono);
        imagesRessources.add(R.drawable.proximo);
        imagesRessources.add(R.drawable.tram);
        imagesRessources.add(R.drawable.flexo);

        mainCardViewAdapter = new MainCardViewAdapter(this,lignes,imagesRessources);
        recyclerView.setAdapter(mainCardViewAdapter);
    }


}
