package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class SearchDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchDoctorAdapter adapter;
    private ArrayList<DoctorModel> doctorNamesList;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        recyclerView=findViewById(R.id.recyclerViewdoctor);

        doctorNamesList=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    Log.println(Log.DEBUG, "", doctorModel.getName());
                    doctorNamesList.add(doctorModel);
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        doctorNamesList.add("Dr. Pragya Prashar");
//        doctorNamesList.add("Dr. Amit Kulkarni");
//        doctorNamesList.add("Dr. Shambhavi Kulkarni");
//        doctorNamesList.add("Dr. Apoorva Chaudhary");
//        doctorNamesList.add("Dr. Ranjan Singh");
//        doctorNamesList.add("Dr. Ananya Kumar");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchDoctorActivity.this,R.color.darkgreen));
    }

    private void setAdapter(){
        Log.println(Log.DEBUG, "","this is adapter");
        adapter=new SearchDoctorAdapter(doctorNamesList, this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}