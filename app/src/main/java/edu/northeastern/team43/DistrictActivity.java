package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {
    private ArrayList<String> districtNames;
    private DistrictRecycleAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        recyclerView=findViewById(R.id.district_recycle);

        ArrayList<String> districts = (ArrayList<String>) getIntent().getExtras().get("districts");
        districtNames = new ArrayList<>();
        setAdapter();
        districtNames.addAll(districts);
        adapter.notifyDataSetChanged();
    }
    private void setAdapter(){
        adapter = new DistrictRecycleAdapter(districtNames,this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}