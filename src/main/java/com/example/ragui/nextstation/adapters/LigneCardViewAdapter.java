package com.example.ragui.nextstation.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.activities.LigneActivity;
import com.example.ragui.nextstation.activities.RouteActivity;

import java.util.HashMap;
import java.util.List;

public class LigneCardViewAdapter extends RecyclerView.Adapter<LigneCardViewAdapter.LigneViewHolder>
{
    private Context context;
    private List<String> lignesList,lignesType;
    private int transpIconSrc;
    private HashMap<String,String> transpIconColor,stations1,stations2,idShortName;
    private String typeTransport;

    public LigneCardViewAdapter(Context context, List<String> lignesList, List<String> lignesType, int transpIconSrc, HashMap<String,
            String> transpIconColor, HashMap<String, String> stations1, HashMap<String, String> stations2, HashMap<String, String> idShortName, String typeTransport)
    {
        this.context = context;
        this.lignesList = lignesList;
        this.lignesType = lignesType;
        this.transpIconSrc = transpIconSrc;
        this.transpIconColor = transpIconColor;
        this.stations1 = stations1;
        this.stations2 = stations2;
        this.idShortName = idShortName;
        this.typeTransport = typeTransport;
    }

    @Override
    public LigneViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_ligne,parent,false);
        return new LigneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LigneViewHolder holder, int position)
    {
        holder.transpIcone.setImageResource(transpIconSrc);
        holder.transpName.setText(lignesList.get(position));
        holder.transpName.setBackgroundColor(Color.parseColor("#"+transpIconColor.get(holder.transpName.getText())));
        holder.transpIcone.setColorFilter(Color.parseColor("#"+transpIconColor.get(holder.transpName.getText())));
        holder.transpType.setText(lignesType.get(position));
        holder.station1.setText(stations1.get(holder.transpName.getText()));
        holder.station2.setText(stations2.get(holder.transpName.getText()));



        holder.ligneCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TextView shortName = view.findViewById(R.id.transpName);
                LigneActivity ligneActivity = (LigneActivity)context;
                Intent routeActivity = new Intent(ligneActivity, RouteActivity.class);
                routeActivity.putExtra("codeTransport",idShortName.get(shortName.getText()));
                routeActivity.putExtra("typeTransport",typeTransport);

                ligneActivity.startActivity(routeActivity);
                ligneActivity.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return lignesList.size();
    }

    public static class LigneViewHolder extends RecyclerView.ViewHolder
    {
        ImageView transpIcone;
        TextView transpType,transpName,station1,station2;
        CardView ligneCardView;

        public LigneViewHolder(View itemView)
        {
            super(itemView);

            transpIcone = itemView.findViewById(R.id.transpIcone);
            transpType = itemView.findViewById(R.id.typeTransp);
            transpName = itemView.findViewById(R.id.transpName);
            station1 = itemView.findViewById(R.id.stationTextView_1);
            station2 = itemView.findViewById(R.id.stationTextView_2);
            ligneCardView = itemView.findViewById(R.id.ligneCardView);
        }
    }
}
