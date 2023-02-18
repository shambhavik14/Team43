package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {
    private ArrayList<String> districtNames;
    private DistrictRecycleAdapter adapter;
    private RecyclerView recyclerView;
    private JSONObject jsonObject;
    private String stateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        recyclerView=findViewById(R.id.district_recycle);

        ArrayList<String> districts = (ArrayList<String>) getIntent().getExtras().get("districts");
        String jsonString = (String) getIntent().getExtras().get("data");
        stateName = (String) getIntent().getExtras().get("statename");
        try {
             jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        districtNames = new ArrayList<>();
        setAdapter();
        districtNames.addAll(districts);
        adapter.notifyDataSetChanged();
    }
    private void setAdapter(){
        adapter = new DistrictRecycleAdapter(districtNames,this,jsonObject,stateName);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}