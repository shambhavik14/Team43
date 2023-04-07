package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.team43.R;

public class Register extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    public void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        Button regAsDoctor = findViewById(R.id.reg_doc_button);
        Button regAsPatient = findViewById(R.id.reg_pat_button);
        regAsDoctor.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),DoctorRegistrationActivity.class);
            startActivity(intent);
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Register.this,R.color.purple));
        regAsPatient.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(), PatientRegistrationActivity.class);
            startActivity(intent);
        });
    }
}