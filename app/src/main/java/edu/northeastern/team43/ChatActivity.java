package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
        ImageView mickey = findViewById(R.id.mickey);
        ImageView spongebob = findViewById(R.id.spongebob);
        UserModel selectedUser = (UserModel) getIntent().getSerializableExtra("selected_user");
        selectedUserTextView.setText(selectedUser.getUserName());
        Log.d("USER",selectedUser.toString());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        UserModel loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
        oswald.setOnClickListener(v->{
             sentSticker = new SentSticker("oswald",selectedUser.getUserName(), LocalDateTime.now().toString());
             receivedSticker = new ReceivedSticker("oswald",loggedInUser.getUserName(), LocalDateTime.now().toString());


        });
        spongebob.setOnClickListener(v->{
            sentSticker = new SentSticker("spongebob",selectedUser.getUserName(), LocalDateTime.now().toString());
            receivedSticker = new ReceivedSticker("spongebob",loggedInUser.getUserName(), LocalDateTime.now().toString());


        });
        mickey.setOnClickListener(v->{
            sentSticker = new SentSticker("mickey",selectedUser.getUserName(), LocalDateTime.now().toString());
            receivedSticker = new ReceivedSticker("mickey",loggedInUser.getUserName(), LocalDateTime.now().toString());


        });
        sendButton.setOnClickListener(v->{
            if (sentSticker!=null && receivedSticker!=null){

                databaseReference.orderByChild("userName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        Log.d("", children.toString());
                        Iterator<DataSnapshot> iterator = children.iterator();
                        int  count = 0;
                        while (iterator.hasNext()){
                            UserModel userModel =iterator.next().getValue(UserModel.class);
                            if (userModel.getUserName().equalsIgnoreCase(loggedInUser.getUserName())){
                                ArrayList<SentSticker> sentStickers = userModel.getSentStickers();
                                sentStickers.add(sentSticker);
                                loggedInUser.setSentStickers(sentStickers);
                                databaseReference.child(loggedInUser.getUserId()).setValue(loggedInUser);
                                count+=1;

                            }else if(userModel.getUserName().equalsIgnoreCase(selectedUser.getUserName())){

                                ArrayList<ReceivedSticker> receivedStickers = userModel.getReceivedStickers();
                                receivedStickers.add(receivedSticker);
                                selectedUser.setReceivedStickers(receivedStickers);
                                databaseReference.child(selectedUser.getUserId()).setValue(selectedUser);
                                count+=1;

                            }
                            if (count==2){
                                return;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("", error.getMessage());
                    }
                });
                finish();
            }

        });
    }
}