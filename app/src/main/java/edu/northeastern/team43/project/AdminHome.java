package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.team43.AboutActivity;
import edu.northeastern.team43.R;

public class AdminHome extends AppCompatActivity {

    private Button pat_gender;
    private Button pat_state;
    private Button pat_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        pat_gender = (Button) findViewById(R.id.pat_gender_stats);

        pat_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkbluelatest)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHome.this,R.color.darkbluelatest));
    }
}