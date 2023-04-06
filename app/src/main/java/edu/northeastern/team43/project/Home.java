package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.team43.AboutActivity;
import edu.northeastern.team43.R;

public class Home extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth=FirebaseAuth.getInstance();
        Button logoutButton = findViewById(R.id.logout_button);
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
        Button activity = (Button) findViewById(R.id.activities_button);
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Activity_Recommendation.class);
                startActivity(intent);
            }
        });
    }
}