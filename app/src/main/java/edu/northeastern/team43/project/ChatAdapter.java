package edu.northeastern.team43.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class ChatAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<ChatModel> messagesArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECEIVE = 2;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userProfilePic = "";

    public ChatAdapter(Context context, ArrayList<ChatModel> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_SEND){
            View view= LayoutInflater.from(SearchDoctorAdapter.context).inflate(R.layout.senderlayout,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(SearchDoctorAdapter.context).inflate(R.layout.receiverlayout,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ChatModel chatModel=messagesArrayList.get(position);
//        if(holder.getClass()==SenderViewHolder.class){
//            SenderViewHolder viewHolder= (SenderViewHolder) holder;
//            viewHolder.txtsenderMessage.setText(chatModel.getMessage());
//        }
//        ReceiverViewHolder viewHolder= (ReceiverViewHolder) holder;
//        viewHolder.txtreceiverMessage.setText(chatModel.getMessage());

        ChatModel chatModel=messagesArrayList.get(position);
        if(chatModel.getSenderEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
            SenderViewHolder viewHolder=(SenderViewHolder) holder;
            String text = "<font color=#FFFFFF>"+chatModel.getMessage()+"</font> "+"<br/>"+"<font color=#877F7F>"+chatModel.getDate()+"</font>";
            viewHolder.txtsenderMessage.setText(Html.fromHtml(text));


            databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                        if (doctorModel.getEmail().equalsIgnoreCase(chatModel.getSenderEmail())){
                            userProfilePic = doctorModel.getProfilePicture();
                            Glide.with(context).load( doctorModel.getProfilePicture()).circleCrop().into(viewHolder.senderImage);
                            break;

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
                        if(patientModel.getEmail().equalsIgnoreCase(chatModel.getReceiverEmail())){
                            Glide.with(context).load( patientModel.getProfilePicture()).circleCrop().into(viewHolder.senderImage);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            String text = "<font color=#FFFFFF>"+chatModel.getMessage()+"</font> "+"<br/>"+"<font color=#877F7F>"+chatModel.getDate()+"</font>";
            viewHolder.txtreceiverMessage.setText(Html.fromHtml(text));





            databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                    while (iterator.hasNext()){
                        DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                        if (doctorModel.getEmail().equalsIgnoreCase(chatModel.getSenderEmail())){
                            userProfilePic = doctorModel.getProfilePicture();
                            Glide.with(context).load(doctorModel.getProfilePicture()).circleCrop().into(viewHolder.receiverImage);
                            break;

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
                        if(patientModel.getEmail().equalsIgnoreCase(chatModel.getReceiverEmail())){
                            Glide.with(context).load(patientModel.getProfilePicture()).circleCrop().into(viewHolder.receiverImage);
                            break;
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
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel chatModel=messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(chatModel.getSenderEmail())){
            return ITEM_SEND;
        }else{
            return ITEM_RECEIVE;
        }

    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView txtsenderMessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderImage = itemView.findViewById(R.id.senderimg);
            txtsenderMessage = itemView.findViewById(R.id.sendermessage);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder {
        ImageView receiverImage;
        TextView txtreceiverMessage;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverImage = itemView.findViewById(R.id.receiverimg);
            txtreceiverMessage = itemView.findViewById(R.id.receivermessage);
        }
    }

}
