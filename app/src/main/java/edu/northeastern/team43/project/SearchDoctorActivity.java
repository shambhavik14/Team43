package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
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
        doctorNamesList.add("Dr. Pragya Prashar");
        doctorNamesList.add("Dr. Amit Kulkarni");
        doctorNamesList.add("Dr. Shambhavi Kulkarni");
        doctorNamesList.add("Dr. Apoorva Chaudhary");
        doctorNamesList.add("Dr. Ranjan Singh");
        doctorNamesList.add("Dr. Ananya Kumar");
        setAdapter();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
    }

    private void setAdapter(){
        adapter=new SearchDoctorAdapter(doctorNamesList, this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}