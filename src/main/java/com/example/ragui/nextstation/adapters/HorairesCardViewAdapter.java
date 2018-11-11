package com.example.ragui.nextstation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ragui.nextstation.R;
import com.example.ragui.nextstation.model.Time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HorairesCardViewAdapter extends RecyclerView.Adapter<HorairesCardViewAdapter.HorairesViewHolder>
{
    private Context context;
    private List<String> destinationList;
    private HashMap<String, List<Time>> horairesArrivee;
    private Date horaires;
    private String nomArret;

    public HorairesCardViewAdapter(Context context, List<String> destinationList, HashMap<String, List<Time>> horairesArrivee, String nomArret)
    {
        this.context = context;
        this.destinationList = destinationList;
        this.horairesArrivee = horairesArrivee;
        this.nomArret = nomArret;
    }

    @Override
    public HorairesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_horaires,parent,false);
        return new HorairesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorairesViewHolder holder, int position)
    {
        /*
            Defining Animation for timeLeft textView if it equals to 0 min
         */
        final Animation in = new AlphaAnimation(0.0f,1.0f);
        in.setDuration(500);
        in.setRepeatCount(Animation.INFINITE);

        final Animation out = new AlphaAnimation(1.0f,0.0f);
        out.setDuration(500);
        out.setRepeatCount(Animation.INFINITE);

        AnimationSet as = new AnimationSet(true);
        as.addAnimation(out);
        in.setStartOffset(500);
        as.addAnimation(in);


        holder.stopName.setText(nomArret);
        holder.destination.setText(destinationList.get(position));

        List<Time> times = horairesArrivee.get(destinationList.get(position));
        horaires = convertSecondsToHoursAndMinutes(times.get(0).getRealtimeArrival());
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        int minutesDiff = Integer.parseInt(timeDifference(horaires));

        if(minutesDiff <= 30)
        {
            if(minutesDiff == 0)
            {
                holder.cardViewItem.startAnimation(as);
            }
            holder.timeLeft.setText(Integer.toString(minutesDiff));
        }



        else if(minutesDiff > 30)
            holder.timeLeft.setText(dateFormat.format(horaires));

        if(times.size() > 1)
        {
            horaires  = convertSecondsToHoursAndMinutes(times.get(1).getRealtimeArrival());
            holder.passagesHoraires_1.setText(dateFormat.format(horaires).toString());
        }

        if(times.size() > 2)
        {
            horaires = convertSecondsToHoursAndMinutes(times.get(2).getRealtimeArrival());
            holder.passagesHoraires_2.setText(dateFormat.format(horaires).toString());
        }

        if(times.size() > 3)
        {
            horaires = convertSecondsToHoursAndMinutes(times.get(3).getRealtimeArrival());
            holder.passagesHoraires_3.setText(dateFormat.format(horaires).toString());
        }
    }

    @Override
    public int getItemCount()
    {
        return destinationList.size();
    }

    public static class HorairesViewHolder extends RecyclerView.ViewHolder
    {
        TextView timeLeft,timeMin,stopName,destination,passagesHoraires_1,passagesHoraires_2,passagesHoraires_3;
        LinearLayout cardViewItem;

        public HorairesViewHolder(View itemView)
        {
            super(itemView);

            timeLeft = itemView.findViewById(R.id.timeLeft);
            timeMin = itemView.findViewById(R.id.timeMin);
            stopName = itemView.findViewById(R.id.stopName);
            destination = itemView.findViewById(R.id.destination);
            passagesHoraires_1 = itemView.findViewById(R.id.passagesHoraires_1);
            passagesHoraires_2 = itemView.findViewById(R.id.passagesHoraires_2);
            passagesHoraires_3 = itemView.findViewById(R.id.passagesHoraires_3);
            cardViewItem = itemView.findViewById(R.id.cardViewItem1);

        }
    }

//    private int[] convertSecondsToHoursAndMinutes(int seconds)
//    {
//        final int MINUTES_IN_AN_HOUR = 60;
//        final int SECONDS_IN_A_MINUTE = 60;
//        int convertedSecondsArray[] = new int[2];
//
//        int minutes = seconds / SECONDS_IN_A_MINUTE;
//
//        int hours = minutes / MINUTES_IN_AN_HOUR;
//        minutes -= hours * MINUTES_IN_AN_HOUR;
//
//        convertedSecondsArray[0] = hours;
//        convertedSecondsArray[1] = minutes;
//
//        return convertedSecondsArray;
//    }

    private Date convertSecondsToHoursAndMinutes(int seconds)
    {
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = seconds / SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hours);
        cal.set(Calendar.MINUTE,minutes);

        return cal.getTime();
    }

    private String timeDifference(Date arrivalTime)
    {
        Calendar currentTime = Calendar.getInstance();

        int androidHours = currentTime.get(Calendar.HOUR_OF_DAY);
        int androidminutes = currentTime.get(Calendar.MINUTE);

        int hours = arrivalTime.getHours();
        int minutes = arrivalTime.getMinutes();

        String androidTime = String.valueOf(androidHours+":"+androidminutes);
        String arrTime = String.valueOf(hours+":"+minutes);


        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
        long min = 0;
        try
        {
            Date sysTime = parseFormat.parse(androidTime);
            Date arriTime = parseFormat.parse(arrTime);

            long mills = arriTime.getTime() - sysTime.getTime();
            min = mills / (1000 * 60);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return String.valueOf(min);
    }

    private void setTimeLeftAnimation()
    {

    }

}
