package edu.northeastern.team43.project;

import static edu.northeastern.team43.project.SearchDoctorAdapter.context;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.northeastern.team43.R;

public class ChatAdapter extends RecyclerView.Adapter {
    Context contex;
    ArrayList<ChatModel> messagesArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECEIVE = 2;
    FirebaseAuth firebaseAuth;

    public ChatAdapter(Context contex, ArrayList<ChatModel> messagesArrayList) {
        this.contex = contex;
        this.messagesArrayList = messagesArrayList;
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.senderlayout,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.receiverlayout,parent,false);
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
            viewHolder.txtsenderMessage.setText(chatModel.getMessage());
        }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.txtreceiverMessage.setText(chatModel.getMessage());
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
