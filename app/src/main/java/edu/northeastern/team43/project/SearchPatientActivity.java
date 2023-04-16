package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class SearchPatientActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView loggedInUserName;
    private ImageView loggedInUserImage;
    private SearchPatientAdapter adapter;
    private ArrayList<PatientModel> patientNamesList;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_patient);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recyclerViewdoctor);
        loggedInUserName=findViewById(R.id.loginUser);
        loggedInUserImage=findViewById(R.id.loginuserimg);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        loggedInUserName.setText(doctorModel.getName());
                        Glide.with(getApplicationContext()).load(doctorModel.getProfilePicture()).circleCrop().into(loggedInUserImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterator<DataSnapshot> iterator=snapshot.getChildren().iterator();
//                while(iterator.hasNext()){
//                    PatientModel patientModel=iterator.next().getValue(PatientModel.class);
//                    if(patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
//                        loggedInUserName.setText(patientModel.getName());
//                        Glide.with(getApplicationContext()).load(patientModel.getProfilePicture()).circleCrop().into(loggedInUserImage);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        patientNamesList =new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    PatientModel patientModel = iterator.next().getValue(PatientModel.class);
                    Log.println(Log.DEBUG, "", patientModel.getName());
                    if (!patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        patientNamesList.add(patientModel);
                    }
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchPatientActivity.this,R.color.darkgreen));
    }

    private void setAdapter(){
        Log.println(Log.DEBUG, "","this is adapter");
        adapter=new SearchPatientAdapter(patientNamesList, this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}