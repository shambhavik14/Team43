package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button loginBttn;
    EditText userNameText;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        loginBttn=(Button)findViewById(R.id.login);
        userNameText=(EditText)findViewById(R.id.username);
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        String username = userNameText.getText().toString();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(username.trim().concat("@gmail.com"),"123456")
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            databaseReference.push()
                                    .setValue(new UserModel(FirebaseAuth.getInstance().getUid(),username));
                            Intent intent=new Intent(getApplicationContext(),MainmenuActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
        }else{
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(username,"123456")
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent=new Intent(getApplicationContext(),MainmenuActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Dialog authFailureDialog = new Dialog(LoginActivity.this);
                            authFailureDialog.setContentView(R.layout.auth_failure);
                            authFailureDialog.show();
                            Button closeButton = authFailureDialog.findViewById(R.id.close_button);
                            closeButton.setOnClickListener(v->{
                                authFailureDialog.dismiss();
                            });
                        }
                    });
        }

    }
}