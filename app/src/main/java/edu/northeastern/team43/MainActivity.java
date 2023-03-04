package edu.northeastern.team43;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.northeastern.team43.R.id;

public class MainActivity extends AppCompatActivity {
    private Button covidNewsApi;
    private Button about;
    private Button chat;
   UserModel userModel;
   UserModel userModel1;
   UserModel userModel2;
    DatabaseReference userDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


             userModel = new UserModel();
             userModel.setUserName("amit");
             userModel.setPassword("123");

             userModel1 = new UserModel();
             userModel1.setUserName("pragya");
             userModel1.setPassword("123");

             userModel2 = new UserModel();
             userModel2.setUserName("shambhavi");
             userModel2.setPassword("123");


             userDataRef = FirebaseDatabase.getInstance().getReference().child("Users");
             userDataRef.push().setValue(userModel);
             userDataRef.push().setValue(userModel1);
             userDataRef.push().setValue(userModel2);


        covidNewsApi = (Button) findViewById(id.btnYourService);
        about = (Button) findViewById(R.id.aboutbttn);
        chat = (Button) findViewById(R.id.chatbttn);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }



}


