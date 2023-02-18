package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CovidStats extends AppCompatActivity {
    private ArrayList<String> covidStats;
    private CovidStatsRecycleAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_stats);
        recyclerView=findViewById(R.id.stats_recycle);

        ArrayList<String> stats = (ArrayList<String>) getIntent().getExtras().get("delta7");
        covidStats = new ArrayList<>();
        setAdapter();
        covidStats.addAll(stats);
        adapter.notifyDataSetChanged();
    }
    private void setAdapter(){
        adapter = new CovidStatsRecycleAdapter(covidStats,this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}