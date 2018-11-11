package com.example.ragui.nextstation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.activities.LigneActivity;
import com.example.ragui.nextstation.activities.MainActivity;

import java.util.List;

public class MainCardViewAdapter extends RecyclerView.Adapter<MainCardViewAdapter.LigneViewHolder>
{
    private Context mcontext;
    private List<String> lignes;
    private List<Integer> imageResources;

    public MainCardViewAdapter(Context context, List<String> lignes, List<Integer> imageResources)
    {
        this.mcontext = context;
        this.lignes = lignes;
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public LigneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_main,parent,false);
        return new LigneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigneViewHolder holder, final int position)
    {
        holder.image.setImageResource(imageResources.get(position));
        holder.textView.setText(lignes.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                MainActivity mainActivity = (MainActivity) mcontext;
                Intent ligneActivity = new Intent(mainActivity, LigneActivity.class);

                if (position == 0)
                    ligneActivity.putExtra("typeTransport","CHRONO");
                if (position == 1)
                    ligneActivity.putExtra("typeTransport","PROXIMO");
                if (position == 2)
                    ligneActivity.putExtra("typeTransport","TRAM");
                if (position == 3)
                    ligneActivity.putExtra("typeTransport","FLEXO");


                mainActivity.startActivity(ligneActivity);
                mainActivity.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return lignes.size();
    }

    public static class LigneViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView textView;
        CardView cardView;

        public LigneViewHolder(View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.ivImage);
            textView = itemView.findViewById(R.id.tvTitle);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
