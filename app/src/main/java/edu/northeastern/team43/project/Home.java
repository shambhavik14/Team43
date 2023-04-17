package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class Home extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    boolean isDoctor = false;
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageButton connectWithExpertsBtn = findViewById(R.id.connect_expert_button);
        TextView welcomeMsg= findViewById(R.id.welcome_msg);
        connectWithExpertsBtn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),SearchDoctorActivity.class);
            startActivity(intent);
        });


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
//        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
//                while (iterator.hasNext()){
//                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
//                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
//                        welcomeMsg.setText("Welcome "+doctorModel.getName());
//                        welcomeMsg.setTypeface(null, Typeface.BOLD);
//                        welcomeMsg.setTextColor(Color.rgb(0,0,0));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator=snapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    PatientModel patientModel=iterator.next().getValue(PatientModel.class);
                    if(patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        welcomeMsg.setText("Welcome "+patientModel.getName());
                        welcomeMsg.setTypeface(null,Typeface.BOLD);
                        welcomeMsg.setTextColor(Color.rgb(0,0,0));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ImageView logoutButton = findViewById(R.id.logout_button);
        if (firebaseAuth.getCurrentUser()==null){
            Intent intent = new Intent(getApplicationContext(),Companion.class);
            startActivity(intent);
            finish();
        }else {

//            welcomeMsg.setText("Welcome "+firebaseAuth.getCurrentUser().getDisplayName());
            logoutButton.setOnClickListener(v->{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Companion.class);
                startActivity(intent);
                finish();
            });
        }
        ImageButton getActiveButton =  findViewById(R.id.get_active_button);
        getActiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Activity_Recommendation.class);
                startActivity(intent);

            }
        });
        ImageView myProfileButton = findViewById(R.id.profile);
        myProfileButton.setOnClickListener(v->{
//            databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
//                    while (iterator.hasNext()){
//                        DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
//                        if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
//                            isDoctor = true;
//                            break;
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            if (isDoctor){
//                Intent intent = new Intent(Home.this, DoctorEditProfile.class);
//                startActivity(intent);
//            }
//            else{
                Intent intent = new Intent(Home.this, PatientEditProfile.class);
                startActivity(intent);
//            }



        });
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                ChatModel prev = null;
                while (iterator.hasNext()){
                    prev = iterator.next().getValue(ChatModel.class);
                }
                ChatModel finalPrev = prev;
                if (firebaseAuth.getCurrentUser()!=null && prev.getReceiverEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){

                    databaseReference.child("doctors").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                            while (iterator.hasNext()){
                                DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);

                                if (doctorModel.getEmail().equalsIgnoreCase(finalPrev.getSenderEmail())){
                                    notifyUser(doctorModel.getName(),doctorModel.getProfilePicture(),finalPrev.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkbluelatest)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Home.this,R.color.darkbluelatest));
    }
    private void notifyUser(String name,String profilePicture, String msg){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("my notification","my notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Home.this,"my notification");
        builder.setContentTitle(name);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.bob);
        builder.setContentText(msg);
//        Bitmap largeIcon = null;
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(profilePicture);
                    Bitmap largeIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    builder.setLargeIcon(largeIcon);
                } catch(IOException e) {
                    System.out.println(e);
                }
            }
        });

        thread.start();



        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Home.this);
        managerCompat.notify(1,builder.build());
    }
}