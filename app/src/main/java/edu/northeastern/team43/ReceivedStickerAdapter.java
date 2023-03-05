package edu.northeastern.team43;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceivedStickerAdapter extends RecyclerView.Adapter<ReceivedStickerAdapter.MyViewHolder> {
    private static Context context;
    ArrayList<ReceivedSticker> receivedStickerArrayList;
    public ReceivedStickerAdapter(Context context, ArrayList<ReceivedSticker> receivedStickerArrayList){
        this.receivedStickerArrayList=receivedStickerArrayList;
        this.context=context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView senderUsername;
        private TextView sentTime;
        private ImageView sentSticker;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            senderUsername=itemView.findViewById(R.id.senderusername);
            sentTime=itemView.findViewById(R.id.senttime);
            sentSticker=itemView.findViewById(R.id.sentStickerImg);
        }
    }


    @NonNull
    @Override
    public ReceivedStickerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.receivedstickerlayout,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedStickerAdapter.MyViewHolder holder, int position) {
       holder.senderUsername.setText(receivedStickerArrayList.get(position).getReceivedFromUsername());
       holder.sentTime.setText(receivedStickerArrayList.get(position).getTime());
       String stickerId=receivedStickerArrayList.get(position).getStickerId();
       if(stickerId.equalsIgnoreCase("oswald")){
          holder.sentSticker.setImageResource(R.drawable.oswald);
       }
       else if(stickerId.equalsIgnoreCase("mickey")){
           holder.sentSticker.setImageResource(R.drawable.mickey);
       }
       else if(stickerId.equalsIgnoreCase("spongebob")){
           holder.sentSticker.setImageResource(R.drawable.spongebob);
       }
    }

    @Override
    public int getItemCount() {
        return receivedStickerArrayList.size();
    }
}
