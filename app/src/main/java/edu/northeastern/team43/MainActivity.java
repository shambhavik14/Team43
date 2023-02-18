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
    private ProgressBar progressBar;
    private TextView textView;
    private Button covidNewsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        covidNewsApi = (Button) findViewById(id.btnYourService);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressText);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        covidNewsApi.setVisibility(View.INVISIBLE);
        progressAnimation();
        covidNewsApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), States.class);
                startActivity(intent);
            }
        });
    }

    private void progressAnimation() {
        ProgressBarAnimation animation = new ProgressBarAnimation(getApplicationContext(), progressBar, textView, 0f, 100f, covidNewsApi);
        animation.setDuration(5000);
        progressBar.setAnimation(animation);
    }
}


