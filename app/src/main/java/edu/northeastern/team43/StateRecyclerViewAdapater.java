package edu.northeastern.team43;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StateRecyclerViewAdapater extends RecyclerView.Adapter<StateRecyclerViewAdapater.MyViewHolder> {

    ArrayList<String> stateNameList;
    static Context context;
    static JSONObject jsonObject;
    StateRecyclerViewAdapater(ArrayList<String> stateNameList, Context context, JSONObject jsonObject){
        this.stateNameList=stateNameList;
        this.context=context;
        this.jsonObject = jsonObject;
    }

    @NonNull
    @Override
    public StateRecyclerViewAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_states_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateRecyclerViewAdapater.MyViewHolder holder, int position) {
        String stateName = stateNameList.get(position);
        holder.stateName.setText(stateName);
        holder.stateName.setOnClickListener(v->{
            Intent districtIntent = new Intent(context,DistrictActivity.class);
            ArrayList<String> districtNames = new ArrayList<>();
            try {
                JSONObject districtItems =(JSONObject) ((JSONObject) jsonObject.get(stateName)).get("districts");

                Iterator<String> districtIterator =districtItems.keys();
                while (districtIterator.hasNext()){
                    districtNames.add(districtIterator.next());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            districtIntent.putExtra("districts",districtNames);
            context.startActivity(districtIntent);
        });

        holder.btnStats.setOnClickListener(v -> {
            Intent statsIntent = new Intent(context, CovidStats.class);
            ArrayList<String> covidStats = new ArrayList<>();
            try {
                JSONObject statItems = (JSONObject) ((JSONObject) jsonObject.get(stateName)).get("delta7");
                //Set<String> keyset = statItems.keySet();
                Iterator<String> statsIterator = statItems.keys();
                System.out.println(statsIterator);
                while (statsIterator.hasNext()) {
                    String key = statsIterator.next();
                    Object value = statItems.get(key);
                    //covidStats.add(key);
                    covidStats.add(key + ": " + value);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            statsIntent.putExtra("delta7", covidStats);
            context.startActivity(statsIntent);
        });
    }

    @Override
    public int getItemCount() {
        return stateNameList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView stateName;
        Button btnStats;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName=itemView.findViewById(R.id.stateName);
            btnStats = itemView.findViewById(R.id.button2);

        }
    }
}