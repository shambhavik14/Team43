package edu.northeastern.team43.project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class Chat extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ChatAdapter messageAdapter;
    DatabaseReference databaseReference;
    TextView chatUser;
    CardView sendButton;
    EditText chatMessage;
    RecyclerView recyclerView;
    ArrayList<ChatModel> chatModelArrayList;
    PatientModel patientModel = null;
    DoctorModel doctorModel = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView=findViewById(R.id.chatadapter);
        sendButton=findViewById(R.id.sendbttn);
        chatMessage=findViewById(R.id.chatexchanged);
        chatUser = findViewById(R.id.userchattingwith);
        ImageView receiverPic = findViewById(R.id.receiverpic);
//        PatientModel patientModel = null;
//        DoctorModel doctorModel = null;
        try {

            doctorModel= (DoctorModel) getIntent().getSerializableExtra("chatwithuser");
        }catch (Exception e){
            patientModel= (PatientModel) getIntent().getSerializableExtra("chatwithuser");
        }
        if (doctorModel!=null){
            Glide.with(getApplicationContext()).load(doctorModel.getProfilePicture()).circleCrop().into(receiverPic);
            chatUser.setText("Dr " + doctorModel.getName());
        }else {
            Glide.with(getApplicationContext()).load(patientModel.getProfilePicture()).circleCrop().into(receiverPic);
            chatUser.setText(patientModel.getName());
        }


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

//        DoctorModel finalDoctorModel = doctorModel;
//        PatientModel finalPatientModel = patientModel;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=chatMessage.getText().toString();
                chatMessage.setText("");
                if(msg.isEmpty()){
                    Toast.makeText(Chat.this,"Please enter a message", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference().child("chats");
                databaseReference.orderByChild("chatId").addListenerForSingleValueEvent(new ValueEventListener(){
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        String key1=databaseReference.push().getKey();
                        String currentDate = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now());
                        ChatModel chatModel=new ChatModel.Builder()
                                .chatId(key1)
                                .senderEmail(firebaseAuth.getCurrentUser().getEmail())
                                .receiverEmail(doctorModel!=null ?doctorModel.getEmail():patientModel.getEmail())
                                .message(msg)
                                .date(currentDate)
                                .build();
                        databaseReference.child(key1).setValue(chatModel);
                        if (doctorModel!=null){

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                    while (iterator.hasNext()){
                                        PatientModel pm = iterator.next().getValue(PatientModel.class);
                                        if (pm.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
//                                            pm.setMostRecentMsgDate(currentDate);
                                            databaseReference.child("patients").child(pm.getPatientId()).child("mostRecentMsgDate").setValue(currentDate);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            updateUIForDoctor(doctorModel);
                        }else {

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                    while (iterator.hasNext()){
                                        DoctorModel dm = iterator.next().getValue(DoctorModel.class);
                                        if (dm.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                                            dm.setMostRecentMsgDate(currentDate);
                                            databaseReference.child("doctors").child(dm.getDoctorId()).child("mostRecentMsgDate").setValue(currentDate);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            updateUIForPatient(patientModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        if (doctorModel!=null){

            databaseReference.child("doctors").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    updateUIForDoctor(doctorModel);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {

            databaseReference.child("patients").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    updateUIForPatient(patientModel);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Chat.this,R.color.darkgreen));

    }

    private void updateUIForDoctor(DoctorModel doctorModel) {
        chatModelArrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("chats").orderByChild("chatId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    ChatModel chatModel = iterator.next().getValue(ChatModel.class);
                    if(chatModel.getSenderEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())
                            && chatModel.getReceiverEmail().equalsIgnoreCase(doctorModel.getEmail())
                            || chatModel.getSenderEmail().equalsIgnoreCase(doctorModel.getEmail())
                            && chatModel.getReceiverEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        chatModelArrayList.add(chatModel);
                    }
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateUIForPatient(PatientModel patientModel) {
        chatModelArrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("chats").orderByChild("chatId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    ChatModel chatModel = iterator.next().getValue(ChatModel.class);
                    if(chatModel.getSenderEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())
                            && chatModel.getReceiverEmail().equalsIgnoreCase(patientModel.getEmail())
                            || chatModel.getSenderEmail().equalsIgnoreCase(patientModel.getEmail())
                            && chatModel.getReceiverEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        chatModelArrayList.add(chatModel);
                    }
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setAdapter(){
        if (doctorModel!=null){
            messageAdapter=new ChatAdapter(this,chatModelArrayList,true);
        }else {
            messageAdapter=new ChatAdapter(this,chatModelArrayList,false);
        }

        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }
}