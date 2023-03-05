package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatActivity extends AppCompatActivity {
    SentSticker sentSticker = null;
    ReceivedSticker receivedSticker = null;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        TextView selectedUserTextView = findViewById(R.id.selected_user);
        Button sendButton = findViewById(R.id.sendButton);
        ImageView oswald = findViewById(R.id.oswald);
        ImageView bob = findViewById(R.id.bob);
        ImageView mickey = findViewById(R.id.mickey);
        ImageView spongebob = findViewById(R.id.spongebob);
        UserModel selectedUser = (UserModel) getIntent().getSerializableExtra("selected_user");
        selectedUserTextView.setText(selectedUser.getUserName());
        Log.d("USER",selectedUser.toString());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        oswald.setOnClickListener(v->{
             sentSticker = new SentSticker("oswald",selectedUser.getUserName(), LocalDateTime.now().toString());
             receivedSticker = new ReceivedSticker("oswald",selectedUser.getUserName(), LocalDateTime.now().toString());


        });
        sendButton.setOnClickListener(v->{
            if (sentSticker!=null && receivedSticker!=null){
                UserModel loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
                ArrayList<SentSticker> sentStickers = loggedInUser.getSentStickers();
                sentStickers.add(sentSticker);
                selectedUser.getReceivedStickers().add(receivedSticker);
                databaseReference.orderByChild("userName").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        Log.d("", children.toString());
                        Iterator<DataSnapshot> iterator = children.iterator();
                        while (iterator.hasNext()){
                            UserModel userModel =iterator.next().getValue(UserModel.class);
                            if (userModel.getUserName().equalsIgnoreCase(loggedInUser.getUserName())){

//                                databaseReference.child(loggedInUser.getUserName()).setValue(loggedInUser,UserModel.class);

//                                Intent intent=new Intent(getApplicationContext(),MainmenuActivity.class);
//                                intent.putExtra("LOGGED_IN_USER",userModel);
//                                startActivity(intent);
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("", error.getMessage());
                    }
                });
            }

        });
    }
}