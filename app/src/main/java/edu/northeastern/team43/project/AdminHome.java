package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.team43.AboutActivity;
import edu.northeastern.team43.R;

public class AdminHome extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        TextView welcomeMsg= findViewById(R.id.welcome_msg);
        welcomeMsg.setText("Welcome Admin!");
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        ImageView logoutButton = findViewById(R.id.logout_button);
        if (firebaseAuth.getCurrentUser()==null){
            Intent intent = new Intent(getApplicationContext(),Companion.class);
            startActivity(intent);
            finish();
        }else {

            logoutButton.setOnClickListener(v->{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Companion.class);
                startActivity(intent);
                finish();
            });
        }
        ImageView myProfileButton = findViewById(R.id.profile);
        myProfileButton.setOnClickListener(v->{
            Intent intent = new Intent(AdminHome.this, DoctorEditProfile.class);
            startActivity(intent);
        });


        Button pat_gender = (Button) findViewById(R.id.pat_gender_stats);
        Button age = (Button) findViewById(R.id.pat_age_stats);
        Button states = (Button) findViewById(R.id.pat_state_stats);

        pat_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientGenderStats.class);
                startActivity(intent);
            }
        });

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AgeStats.class);
                startActivity(intent);
            }
        });

        states.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StateStats.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkbluelatest)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHome.this,R.color.darkbluelatest));
    }
}