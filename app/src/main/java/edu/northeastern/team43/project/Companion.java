package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import edu.northeastern.team43.R;

public class Companion extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    FirebaseAuth firebaseAuth;
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
        setContentView(R.layout.activity_companion);
        firebaseAuth= FirebaseAuth.getInstance();
        Button submit = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.username);
        passwordEditText=findViewById(R.id.password);
        submit.setOnClickListener(v->{
            String emailId = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            firebaseAuth.signInWithEmailAndPassword(emailId,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getApplicationContext(),"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"LOGIN FAILURE",Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        registerButton = (Button) findViewById(R.id.signupText);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
    }
}