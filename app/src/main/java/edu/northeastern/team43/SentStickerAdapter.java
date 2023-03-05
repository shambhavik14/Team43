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
    private ArrayList<StickerDetail> sentStickerArrayList;
    private int displayCount  =0;

    public SentStickerAdapter(Context context, ArrayList<StickerDetail> sentStickerArrayList) {
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
        StickerDetail stickerDetail = sentStickerArrayList.get(position);
        if (stickerDetail.getStickerId().equalsIgnoreCase("oswald")){
            holder.stickerImage.setImageResource(R.drawable.oswald);

        }else if(stickerDetail.getStickerId().equalsIgnoreCase("spongebob")){
            holder.stickerImage.setImageResource(R.drawable.spongebob);
        }else if(stickerDetail.getStickerId().equalsIgnoreCase("mickey")){
            holder.stickerImage.setImageResource(R.drawable.mickey);
        }
        holder.stickerCount.setText(String.valueOf(stickerDetail.getCount()));
        holder.stickerImage.getLayoutParams().width=400;
        holder.stickerImage.getLayoutParams().height=400;
        holder.stickerImage.requestLayout();
    }

    @Override
    public int getItemCount() {
        return sentStickerArrayList.size();
    }
}
