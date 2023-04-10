package edu.northeastern.team43.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


import edu.northeastern.team43.ChatActivity;
import edu.northeastern.team43.R;

public class SearchDoctorAdapter extends RecyclerView.Adapter<SearchDoctorAdapter.MyViewHolder> {
    ArrayList<String> doctorNames;
    static Context context;

    public SearchDoctorAdapter(ArrayList<String> doctorNames, Context context){
        this.doctorNames=doctorNames;
        this.context=context;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private Button chatButton;
    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        name=itemView.findViewById(R.id.doctorName);
        chatButton=itemView.findViewById(R.id.chatButton);





    }

    }

    @NonNull
    @Override
    public SearchDoctorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.listofdoctors,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDoctorAdapter.MyViewHolder holder, int position) {

        String name=doctorNames.get(position);
        Log.println(Log.DEBUG, "", name);
        holder.name.setText(name);
        holder.chatButton.setOnClickListener(v->{
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("chatwithuser",name);
            Toast.makeText(context, "" + name, Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return doctorNames.size();
    }
}
