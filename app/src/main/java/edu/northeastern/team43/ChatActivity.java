package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v->{

        });
    }
}