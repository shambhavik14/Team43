package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.northeastern.team43.LoginActivity;
import edu.northeastern.team43.R;

public class Companion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companion);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);
        loginButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
        registerButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        });
    }
}