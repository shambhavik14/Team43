package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class DoctorHome extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ImageView doctorProfile;

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


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_doctor_home);
        ImageButton connectWithExpertsBtn = findViewById(R.id.connect_expert_button);
        TextView welcomeMsg= findViewById(R.id.welcome_msg);
        doctorProfile=findViewById(R.id.docim);
        connectWithExpertsBtn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),SearchPatientActivity.class);
            startActivity(intent);
        });


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        welcomeMsg.setText("Welcome Dr. "+doctorModel.getName());
                        Glide.with(getApplicationContext()).load( doctorModel.getProfilePicture()).circleCrop().into(doctorProfile);
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

            logoutButton.setOnClickListener(v->{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Companion.class);
                startActivity(intent);
                finish();
            });
        }
        ImageView myProfileButton = findViewById(R.id.profile);
        myProfileButton.setOnClickListener(v->{
                Intent intent = new Intent(DoctorHome.this, DoctorEditProfile.class);
                startActivity(intent);
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
                if (prev!=null && firebaseAuth.getCurrentUser()!=null && prev.getReceiverEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){

                    databaseReference.child("patients").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                            while (iterator.hasNext()){
                                PatientModel patientModel = iterator.next().getValue(PatientModel.class);

                                if (patientModel.getEmail().equalsIgnoreCase(finalPrev.getSenderEmail())){
                                    notifyUser(patientModel.getName(),patientModel.getProfilePicture(),finalPrev.getMessage());
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
        getWindow().setStatusBarColor(ContextCompat.getColor(DoctorHome.this,R.color.darkbluelatest));
    }
    private void notifyUser(String name,String profilePicture, String msg){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("my notification","my notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(DoctorHome.this,"my notification");
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



        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(DoctorHome.this);
        managerCompat.notify(1,builder.build());
    }
}