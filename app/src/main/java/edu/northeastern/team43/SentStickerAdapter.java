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
import java.util.stream.Stream;

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
        long oswald = sentStickerArrayList.stream().filter(sticker -> sticker.getStickerId().equalsIgnoreCase("oswald")).count();

        long mickey=sentStickerArrayList.stream().filter(sticker -> sticker.getStickerId().equalsIgnoreCase("mickey")).count();
        long spongebob=sentStickerArrayList.stream().filter(sticker -> sticker.getStickerId().equalsIgnoreCase("spongebob")).count();
//        String stickerId=sentStickerArrayList.get(position).getStickerId();
        if(oswald!=0){
            holder.stickerImage.setImageResource(R.drawable.oswald);
            holder.stickerCount.setText(String.valueOf(oswald));
        }
        if(mickey!=0){
            holder.stickerImage.setImageResource(R.drawable.mickey);
            holder.stickerCount.setText(String.valueOf(mickey));
        }
         if(spongebob!=0){
            holder.stickerImage.setImageResource(R.drawable.spongebob);
            holder.stickerCount.setText(String.valueOf(spongebob));
        }
        holder.stickerImage.getLayoutParams().width=400;
        holder.stickerImage.getLayoutParams().height=400;
        holder.stickerImage.requestLayout();
    }

    @Override
    public int getItemCount() {
        return sentStickerArrayList.size();
    }
}
