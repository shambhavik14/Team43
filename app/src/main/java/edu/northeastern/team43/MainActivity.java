package edu.northeastern.team43;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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


             userModel = new UserModel("0", "amit");


             userModel1 = new UserModel("1","pragya");


             userModel2 = new UserModel("2","shambhavi");



             userDataRef = FirebaseDatabase.getInstance().getReference().child("Users");
            Query q= userDataRef.orderByChild("userName");
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        String key1 = userDataRef.push().getKey();
                        userDataRef.child(key1).setValue(new UserModel( key1, "amit"));
                        String key2 = userDataRef.push().getKey();
                        userDataRef.child(key2).setValue(new UserModel( key2, "pragya"));
                        String key3 = userDataRef.push().getKey();
                        userDataRef.child(key3).setValue(new UserModel( key3, "shambhavi"));
//                        userDataRef.push().setValue(userModel1);
//                        userDataRef.push().setValue(userModel2);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("", error.getMessage());

                }
            });






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


