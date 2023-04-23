package edu.northeastern.team43.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


import edu.northeastern.team43.R;

public class SearchDoctorAdapter extends RecyclerView.Adapter<SearchDoctorAdapter.MyViewHolder> {
    ArrayList<DoctorModel> doctorObject;
    static Context context;

    public SearchDoctorAdapter(ArrayList<DoctorModel> doctorObj, Context context){
        this.doctorObject =doctorObj;
        this.context=context;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView profilePic;
        private TextView name;
        private ConstraintLayout chatButton;
    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        name=itemView.findViewById(R.id.doctorName);
        chatButton=itemView.findViewById(R.id.chatButton);
        profilePic = itemView.findViewById(R.id.chat_profile_pic);





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

        DoctorModel docObj= doctorObject.get(position);
        Log.println(Log.DEBUG, "", docObj.getName());
        Glide.with(context).load(docObj.getProfilePicture()).circleCrop().into(holder.profilePic);
        holder.name.setText("Dr. "+docObj.getName());
        holder.chatButton.setOnClickListener(v->{
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("chatwithuser",docObj);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return doctorObject.size();
    }
}
