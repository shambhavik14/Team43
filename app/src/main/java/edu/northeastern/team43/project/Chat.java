package edu.northeastern.team43.project;

import static edu.northeastern.team43.project.SearchDoctorAdapter.context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class Chat extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ChatAdapter messageAdapter;
    DatabaseReference databaseReference;
//    TextView textView;
    TextView chatUser;
    CardView sendButton;
    EditText chatMessage;
    RecyclerView recyclerView;
    ArrayList<ChatModel> chatModelArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView=findViewById(R.id.chatadapter);
//        textView = findViewById(R.id.lgu);
        sendButton=findViewById(R.id.sendbttn);
        chatMessage=findViewById(R.id.chatexchanged);
        chatUser = findViewById(R.id.userchattingwith);
        DoctorModel doctorModel = (DoctorModel) getIntent().getSerializableExtra("chatwithuser");
        Toast.makeText(context, "" + doctorModel.getName(), Toast.LENGTH_SHORT).show();
        chatUser.setText("Dr " + doctorModel.getName());
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    PatientModel patientModel = iterator.next().getValue(PatientModel.class);
                    if (patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())) {
//                        textView.setText("Welcome " + doctorModel.getName());
//                        textView.setTypeface(null, Typeface.BOLD);
//                        textView.setTextColor(Color.rgb(0, 0, 0));

                        Toast.makeText(context, "" + patientModel.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=chatMessage.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(Chat.this,"Please enter a message", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference().child("chats");
                databaseReference.orderByChild("chatId").addListenerForSingleValueEvent(new ValueEventListener(){
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        String key1=databaseReference.push().getKey();
                        ChatModel chatModel=new ChatModel.Builder()
                                .chatId(key1)
                                .senderEmail(firebaseAuth.getCurrentUser().getEmail())
                                .receiverEmail(doctorModel.getEmail())
                                .message(msg)
                                .build();
                        databaseReference.child(key1).setValue(chatModel);
                        Toast.makeText(Chat.this,"Message Successfully Sent", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        chatModelArrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("chats").orderByChild("chatId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    ChatModel chatModel = iterator.next().getValue(ChatModel.class);
                    if(chatModel.getSenderEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())&&chatModel.getReceiverEmail().equalsIgnoreCase(doctorModel.getEmail()) ||chatModel.getSenderEmail().equalsIgnoreCase(doctorModel.getEmail()) &&chatModel.getReceiverEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        chatModelArrayList.add(chatModel);
                    }
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//        textView.setText("Welcome" + firebaseAuth.getCurrentUser().getDisplayName());
//        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterator<DataSnapshot> iterator=snapshot.getChildren().iterator();
//                while(iterator.hasNext()){
//                    PatientModel patientModel=iterator.next().getValue(PatientModel.class);
//                    if(patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
//                        textView.setText("Welcome"+patientModel.getName());
//                        textView.setTypeface(null,Typeface.BOLD);
//                        textView.setTextColor(Color.rgb(0,0,0));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Chat.this,R.color.darkgreen));

    }


    private void setAdapter(){
        Log.println(Log.DEBUG, "","this is adapter");
        messageAdapter=new ChatAdapter(this,chatModelArrayList);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();

        Toast.makeText(Chat.this,"Call to recycler view", Toast.LENGTH_SHORT).show();
    }
}