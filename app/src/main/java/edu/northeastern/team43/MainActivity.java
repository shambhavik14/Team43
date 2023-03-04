package edu.northeastern.team43;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.team43.R.id;

public class MainActivity extends AppCompatActivity {
    private Button covidNewsApi;
    private Button about;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        covidNewsApi = (Button) findViewById(id.btnYourService);
        about=(Button) findViewById(R.id.aboutbttn);
        chat=(Button) findViewById(R.id.chatbttn);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),AboutActivity.class);
               startActivity(intent);
            }
        });



        covidNewsApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), States.class);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}


