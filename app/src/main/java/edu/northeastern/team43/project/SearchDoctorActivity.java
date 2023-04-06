package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import edu.northeastern.team43.R;

public class SearchDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchDoctorAdapter adapter;
    private ArrayList<String> doctorNamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        recyclerView=findViewById(R.id.recyclerViewdoctor);
        doctorNamesList=new ArrayList<>();
        doctorNamesList.add("pragya");
        doctorNamesList.add("amit");
        doctorNamesList.add("shambhavi");
        setAdapter();
    }

    private void setAdapter(){
        adapter=new SearchDoctorAdapter(doctorNamesList, this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}