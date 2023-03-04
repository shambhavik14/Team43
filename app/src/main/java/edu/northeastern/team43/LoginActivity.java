package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button loginBttn;
    EditText userNameText;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
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
        databaseReference.orderByChild("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Log.d("", children.toString());
                Iterator<DataSnapshot> iterator = children.iterator();
                while (iterator.hasNext()){
                    UserModel userModel =iterator.next().getValue(UserModel.class);
                    if (userModel.getUserName().equalsIgnoreCase(username)){
                        Intent intent=new Intent(getApplicationContext(),MainmenuActivity.class);
                        intent.putExtra("currentUser",userModel);
                        startActivity(intent);
                        return;
                    }
                }
                Dialog authFailureDialog = new Dialog(LoginActivity.this);
                authFailureDialog.setContentView(R.layout.auth_failure);
                authFailureDialog.show();
                Button closeButton = authFailureDialog.findViewById(R.id.close_button);
                closeButton.setOnClickListener(v->{
                    authFailureDialog.dismiss();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("", error.getMessage());
            }
        });

    }
}