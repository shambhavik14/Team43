package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.team43.R;

public class Register extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button button;
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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();



        firebaseAuth=FirebaseAuth.getInstance();
        Button regAsDoctor = findViewById(R.id.cancel_button);
        Button regAsPatient = findViewById(R.id.reg_pat_button);
        button=findViewById(R.id.loginregisterUser);

        button.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),Companion.class);
            startActivity(intent);
        });

        regAsDoctor.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),DoctorRegistrationActivity.class);
            startActivity(intent);
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkbluelatest)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Register.this,R.color.darkbluelatest));
        regAsPatient.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(), PatientRegistrationActivity.class);
            startActivity(intent);
        });
    }
}