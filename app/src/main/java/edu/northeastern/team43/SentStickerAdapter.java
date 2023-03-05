package edu.northeastern.team43;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SentStickerAdapter extends RecyclerView.Adapter<SentStickerAdapter.MyViewHolder> {
    private static Context context;
    ArrayList<SentSticker> sentStickerArrayList;

    public SentStickerAdapter(Context context, ArrayList<SentSticker> sentStickerArrayList) {
        this.sentStickerArrayList = sentStickerArrayList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView stickerImage;
        private TextView stickerCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stickerImage = itemView.findViewById(R.id.stickersentimg);
            stickerCount = itemView.findViewById(R.id.stickercounttracker);
        }
    }


    @NonNull
    @Override
    public SentStickerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sent_sticker_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentStickerAdapter.MyViewHolder holder, int position) {
        int oswald=0;
        int mickey=0;
        int spongebob=0;
        String stickerId=sentStickerArrayList.get(position).getStickerId();
        if(stickerId.equalsIgnoreCase("oswald")){
            holder.stickerImage.setImageResource(R.drawable.oswald);
            oswald++;
            holder.stickerCount.setText(String.valueOf(oswald));
        }
        else if(stickerId.equalsIgnoreCase("mickey")){
            holder.stickerImage.setImageResource(R.drawable.mickey);
            mickey++;
            holder.stickerCount.setText(String.valueOf(mickey));
        }
        if(stickerId.equalsIgnoreCase("spongebob")){
            holder.stickerImage.setImageResource(R.drawable.spongebob);
            spongebob++;
            holder.stickerCount.setText(String.valueOf(spongebob));
        }
    }

    @Override
    public int getItemCount() {
        return sentStickerArrayList.size();
    }
}
