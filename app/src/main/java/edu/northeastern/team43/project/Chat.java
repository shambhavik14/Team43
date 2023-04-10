package edu.northeastern.team43.project;

import static edu.northeastern.team43.project.SearchDoctorAdapter.context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import edu.northeastern.team43.R;

public class Chat extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView textView;
    TextView chatUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textView=findViewById(R.id.lgu);
        chatUser=findViewById(R.id.chatu);
        String chatwithUser=  getIntent().getStringExtra("chatwithuser");
        Toast.makeText(context, "" + chatwithUser, Toast.LENGTH_SHORT).show();
        chatUser.setText(chatwithUser);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        textView.setText("Welcome "+doctorModel.getName());
                        textView.setTypeface(null, Typeface.BOLD);
                        textView.setTextColor(Color.rgb(0,0,0));
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
                        textView.setText("Welcome"+patientModel.getName());
                        textView.setTypeface(null,Typeface.BOLD);
                        textView.setTextColor(Color.rgb(0,0,0));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}