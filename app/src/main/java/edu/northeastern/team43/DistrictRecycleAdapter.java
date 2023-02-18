package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DistrictRecycleAdapter extends RecyclerView.Adapter<DistrictRecycleAdapter.MyViewHolder> {
    ArrayList<String> districtNames;
    static Context context;
    public DistrictRecycleAdapter(ArrayList<String> districtNames,Context context){
        this.districtNames = districtNames;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.district, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = districtNames.get(position);
        holder.districtName.setText(name);
    }

    @Override
    public int getItemCount() {
        return districtNames.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView districtName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            districtName = itemView.findViewById(R.id.districtName);
        }
    }
}