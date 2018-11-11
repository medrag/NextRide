package com.example.ragui.nextstation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.activities.HorairesActivity;
import com.example.ragui.nextstation.activities.RouteActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RouteCardViewAdapter extends RecyclerView.Adapter<RouteCardViewAdapter.RouteViewHolder>
{
    private Context context;
    private List<String> routeList;
    private HashMap<String,String> cityNameArret,cityNameArretCopy,codeNameArret;
    private String codeTransport;


    public RouteCardViewAdapter(Context context, List<String> routeList, HashMap<String,String> cityNameArret, HashMap<String,String> codeNameArret, String codeTransport)
    {
        this.context = context;
        this.routeList = routeList;
        this.cityNameArret = cityNameArret;
        this.codeNameArret = codeNameArret;
        this.cityNameArretCopy = new HashMap<>(cityNameArret);
        this.codeTransport = codeTransport;
    }


    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_route,parent,false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RouteViewHolder holder, int position)
    {
        holder.stopName.setText(routeList.get(position));
        holder.cityTextView.setText(cityNameArret.get(routeList.get(position)));


        holder.routeCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //call Horaires Activity
                Intent horaireActivity = new Intent(context,HorairesActivity.class);
                horaireActivity.putExtra("codeArret",codeNameArret.get(holder.stopName.getText()));
                horaireActivity.putExtra("nomArret", holder.stopName.getText());
                horaireActivity.putExtra("codeTransport", codeTransport);

                RouteActivity routeActivity = (RouteActivity) context;
                routeActivity.startActivity(horaireActivity);
                routeActivity.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });



        holder.buttonViewOptions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //creating popup Menu here
                PopupMenu popupMenu = new PopupMenu(context, holder.buttonViewOptions);

                //inflating menu from xml ressource;
                popupMenu.inflate(R.menu.route_options_menu);

                //adding click Listener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        switch(menuItem.getItemId())
                        {
                            case R.id.menu1:
                                break;
                            case R.id.menu2:
                                break;
                            case R.id.menu3:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return routeList.size();
    }

    public void filter(String query)
    {
        routeList.clear();

        if(query.isEmpty())
        {
            routeList.addAll(cityNameArretCopy.keySet());
        }
        else
        {
            query = query.toLowerCase();

            Iterator it = cityNameArretCopy.entrySet().iterator();

            while(it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();

                //search by stop Name or City Name
                if(pair.getKey().toString().toLowerCase().contains(query) || pair.getValue().toString().toLowerCase().contains(query))
                {
                    routeList.add(pair.getKey().toString());
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder
    {
        TextView stopName,cityTextView;
        ImageButton buttonViewOptions;
        CardView routeCardView;

        public RouteViewHolder(View itemView)
        {
            super(itemView);

            stopName = itemView.findViewById(R.id.stopName);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            buttonViewOptions = itemView.findViewById(R.id.buttonViewOptions);
            routeCardView = itemView.findViewById(R.id.routeCardView);
        }
    }


}
