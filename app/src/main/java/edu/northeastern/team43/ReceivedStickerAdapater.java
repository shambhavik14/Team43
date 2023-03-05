package edu.northeastern.team43;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ReceivedStickerAdapater extends RecyclerView.Adapter<ReceivedStickerAdapater.MyViewHolder> {
    private static Context context;
    ArrayList<ReceivedSticker> receivedStickerArrayList;
    public ReceivedStickerAdapater(Context context, ArrayList<ReceivedSticker> receivedStickerArrayList){
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
    public ReceivedStickerAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.receivedstickerlayout,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedStickerAdapater.MyViewHolder holder, int position) {
       holder.senderUsername.setText(receivedStickerArrayList.get(position).getReceivedFromUsername());
        String time = receivedStickerArrayList.get(position).getTime();
        LocalDateTime dateTime = LocalDateTime.parse(time);
        String formattedDate = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM).format(dateTime);
        holder.sentTime.setText(formattedDate);
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
        holder.sentSticker.getLayoutParams().width=400;
        holder.sentSticker.getLayoutParams().height=400;
        holder.sentSticker.requestLayout();
    }

    @Override
    public int getItemCount() {
        return receivedStickerArrayList.size();
    }
}
