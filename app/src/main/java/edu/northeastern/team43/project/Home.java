package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import edu.northeastern.team43.R;

public class Home extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ConstraintLayout button;

    DatabaseReference databaseReference;

    boolean isDoctor = false;
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.listofdoc);
        TextView welcomeMsg= findViewById(R.id.welcome_msg);
        button.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),SearchDoctorActivity.class);
            startActivity(intent);
        });


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        welcomeMsg.setText("Welcome "+doctorModel.getName());
                        welcomeMsg.setTypeface(null, Typeface.BOLD);
                        welcomeMsg.setTextColor(Color.rgb(0,0,0));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator=snapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    PatientModel patientModel=iterator.next().getValue(PatientModel.class);
                    if(patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        welcomeMsg.setText("Welcome "+patientModel.getName());
                        welcomeMsg.setTypeface(null,Typeface.BOLD);
                        welcomeMsg.setTextColor(Color.rgb(0,0,0));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ImageView logoutButton = findViewById(R.id.logout_button);
        if (firebaseAuth.getCurrentUser()==null){
            Intent intent = new Intent(getApplicationContext(),Companion.class);
            startActivity(intent);
            finish();
        }else {

            welcomeMsg.setText("Welcome "+firebaseAuth.getCurrentUser().getDisplayName());
            logoutButton.setOnClickListener(v->{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Companion.class);
                startActivity(intent);
                finish();
            });
        }
        ConstraintLayout activity = (ConstraintLayout) findViewById(R.id.activities_button);
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Activity_Recommendation.class);
                startActivity(intent);

            }
        });
        ImageView myProfileButton = findViewById(R.id.profile);
        myProfileButton.setOnClickListener(v->{
            databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                        if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                            isDoctor = true;
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if (isDoctor){
                Intent intent = new Intent(Home.this, DoctorEditProfile.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Home.this, PatientEditProfile.class);
                startActivity(intent);
            }



        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Home.this,R.color.darkgreen));
    }
}