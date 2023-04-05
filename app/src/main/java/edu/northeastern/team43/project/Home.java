package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.team43.R;

public class Home extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button button;
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button=findViewById(R.id.listofdoctor);

        button.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),SearchDoctorActivity.class);
            startActivity(intent);
        });


        firebaseAuth=FirebaseAuth.getInstance();
        Button logoutButton = findViewById(R.id.logout_button);
        if (firebaseAuth.getCurrentUser()==null){
            Intent intent = new Intent(getApplicationContext(),Companion.class);
            startActivity(intent);
            finish();
        }else {
            logoutButton.setOnClickListener(v->{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            });
        }
    }
}