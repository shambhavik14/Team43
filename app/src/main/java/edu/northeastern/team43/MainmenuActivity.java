package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainmenuActivity extends AppCompatActivity {
    Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
    chat=(Button) findViewById(R.id.chat);
    chat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(), ListOfUsers.class);
            UserModel loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
            intent.putExtra("LOGGED_IN_USER",loggedInUser);
            startActivity(intent);
        }
    });
    }
}