package edu.northeastern.team43;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StateRecyclerViewAdapater extends RecyclerView.Adapter<StateRecyclerViewAdapater.MyViewHolder> {

    ArrayList<String> stateNameList;
    static Context context;
    StateRecyclerViewAdapater( ArrayList<String> stateNameList, Context context){
       this.stateNameList=stateNameList;
       this.context=context;
    }

    @NonNull
    @Override
    public StateRecyclerViewAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_states_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateRecyclerViewAdapater.MyViewHolder holder, int position) {
        String stateName=stateNameList.get(position);
        holder.stateName.setText(stateName);
    }

    @Override
    public int getItemCount() {
        return stateNameList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView stateName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName=itemView.findViewById(R.id.stateName);
        }
    }
}
