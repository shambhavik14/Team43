package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CovidStatsRecycleAdapter extends RecyclerView.Adapter<CovidStatsRecycleAdapter.MyViewHolder> {
    ArrayList<String> covidStats;
    static Context context;
    public CovidStatsRecycleAdapter(ArrayList<String> covidStats,Context context){
        this.covidStats = covidStats;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_covid_stats_recycle_adapter, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = covidStats.get(position);
        holder.stats.setText(name);
    }

    @Override
    public int getItemCount() {
        return covidStats.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView stats;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stats = itemView.findViewById(R.id.stats);
        }
    }
}