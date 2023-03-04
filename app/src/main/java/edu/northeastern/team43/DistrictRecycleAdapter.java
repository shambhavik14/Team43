package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DistrictRecycleAdapter extends RecyclerView.Adapter<DistrictRecycleAdapter.MyViewHolder> {
    ArrayList<String> districtNames;
    static Context context;
    static JSONObject jsonObject;
    static String stateName;
    public DistrictRecycleAdapter(ArrayList<String> districtNames,Context context,JSONObject jsonObject,String stateName){
        this.districtNames = districtNames;
        this.context= context;
        this.jsonObject = jsonObject;
        this.stateName = stateName;
    }
    public DistrictRecycleAdapter(){}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.district, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String districtName = districtNames.get(position);
        holder.districtName.setText(districtName);
        holder.btnStats.setOnClickListener(v -> {
            Intent statsIntent = new Intent(context, CovidStats.class);
            ArrayList<String> covidStats = new ArrayList<>();
            try {
                Log.println(Log.DEBUG, "", districtName);

                JSONObject statItems = (JSONObject)((JSONObject)((JSONObject)((JSONObject) jsonObject.get(stateName)).get("districts")).get(districtName)).get("delta7");
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
        return districtNames.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView districtName;
        Button btnStats;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            districtName = itemView.findViewById(R.id.districtName);
            btnStats = itemView.findViewById(R.id.button3);
        }
    }
}