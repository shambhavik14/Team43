package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
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

public class Companion extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    FirebaseAuth firebaseAuth;

    Intent homeIntent;

    DatabaseReference databaseReference;
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companion);
        Button loginButton = findViewById(R.id.login_button);
        TextView registerButton = findViewById(R.id.signupText);
        loginButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_companion);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseAuth.getInstance().signOut();
        }
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Button submit = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.username);
        passwordEditText=findViewById(R.id.password);
        submit.setOnClickListener(v->{
            String emailId = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            try {

                firebaseAuth.signInWithEmailAndPassword(emailId,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                if(firebaseAuth.getCurrentUser().getEmail().equalsIgnoreCase("admin@gmail.com")) {
                                    homeIntent = new Intent(getApplicationContext(),AdminHome.class);
                                    startActivity(homeIntent);
                                }
                                databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                        while (iterator.hasNext()){
                                            DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                                            if (doctorModel.getEmail().equalsIgnoreCase(emailId)){
                                                homeIntent = new Intent(getApplicationContext(),DoctorHome.class);
                                                startActivity(homeIntent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Iterator<DataSnapshot> iterator=snapshot.getChildren().iterator();
                                        while(iterator.hasNext()){
                                            PatientModel patientModel=iterator.next().getValue(PatientModel.class);
                                            if(patientModel.getEmail().equalsIgnoreCase(emailId)){
                                                homeIntent = new Intent(getApplicationContext(),Home.class);
                                                startActivity(homeIntent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showErrorDialog();
                            }
                        });
            }catch (Exception e){
                showErrorDialog();
            }
        });
        registerButton = (Button) findViewById(R.id.signupText);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Companion.this,R.color.blue));
    }

    private void showErrorDialog() {
        Dialog dialog = new Dialog(Companion.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.error_dialog);

        dialog.show();
        Button closeButton = dialog.findViewById(R.id.cancel_button);
        closeButton.setOnClickListener(v1->{
            dialog.dismiss();
        });
    }
}