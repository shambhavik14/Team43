package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainmenuActivity extends AppCompatActivity {
    Button chat;
    Button receivedSticker;
    Button sentStickerCount;
    DatabaseReference databaseReference;
    DataSnapshot previousSnapshot;
    ArrayList<ReceivedSticker> previousList;
    UserModel loggedInUser;
    @Override
    public void onBackPressed() {
        loggedInUser = null;
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        chat = (Button) findViewById(R.id.chat);
        sentStickerCount= (Button) findViewById(R.id.sentstickercount);
        receivedSticker = (Button) findViewById(R.id.receivedStickerButton);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListOfUsers.class);

                intent.putExtra("LOGGED_IN_USER", loggedInUser);
                startActivity(intent);
            }
        });


        sentStickerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),SentstickerActivity.class);
//                UserModel loggedInUser=(UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
                intent.putExtra("LOGGED_IN_USER",loggedInUser);
                startActivity(intent);
            }
        });

        
        receivedSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReceivestickerActivity.class);
//                UserModel loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
                intent.putExtra("LOGGED_IN_USER", loggedInUser);
                startActivity(intent);
            }
        });
        if (loggedInUser==null){
            return;
        }
        databaseReference.orderByChild("userName").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (loggedInUser==null){
                    return;
                }
//                UserModel loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
                Log.d("LOGGEDIN_USER",loggedInUser.toString());
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                ArrayList<ReceivedSticker> currentList = new ArrayList<>();
                while (iterator.hasNext()){
                    DataSnapshot next = iterator.next();
//                    Log.d("TAGGGG",next.toString());
                    UserModel value = next.getValue(UserModel.class);
                    if (value!=null && value.getUserName().equalsIgnoreCase(loggedInUser.getUserName())){
                        currentList.addAll(value.getReceivedStickers());
                        break;
                    }
                }
                if (previousList!=null){
                    Log.d("PREVIOUS_LIST BEFORE",previousList.toString());
                }
                Log.d("CURRENT_LIST",currentList.toString());
                if (previousList!=null && previousList.size()!=currentList.size()){
                    String latestStickerId = currentList.get(currentList.size() - 1).getStickerId();
                    notifyUser(latestStickerId);
                    previousList.clear();
                    Log.d("PREVIOUS_LIST CLEARED",previousList.toString());
                    previousList.addAll(currentList);
                    Log.d("PREVIOUS_LIST AFTER SIZE MISMATCH",previousList.toString());
                }else if (previousList==null){
                    Iterator<DataSnapshot> iterator2 = snapshot.getChildren().iterator();
                    while (iterator2.hasNext()){
                        UserModel value = iterator2.next().getValue(UserModel.class);
                        if (value.getUserName().equalsIgnoreCase(loggedInUser.getUserName())){
                            previousList = new ArrayList<>();
                            previousList.addAll(value.getReceivedStickers());
                            Log.d("PREVIOUS_LIST AFTER",previousList.toString());

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void notifyUser(String stickerId){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("my notification","my notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainmenuActivity.this,"my notification");
        builder.setContentTitle("New Sticker Received");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.bob);
        builder.setContentText("Please check the sticker that you have received");
        Bitmap largeIcon = null;
        if (stickerId.equalsIgnoreCase("oswald")){
            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.oswald);
        }else if(stickerId.equalsIgnoreCase("spongebob")){
            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.spongebob);
        }else if(stickerId.equalsIgnoreCase("mickey")){
            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.mickey);
        }

        builder.setLargeIcon(largeIcon);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainmenuActivity.this);
        managerCompat.notify(1,builder.build());
    }
}