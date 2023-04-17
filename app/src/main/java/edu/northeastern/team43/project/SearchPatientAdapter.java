package edu.northeastern.team43.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.northeastern.team43.R;

public class SearchPatientAdapter extends RecyclerView.Adapter<SearchPatientAdapter.MyViewHolder>{
    ArrayList<PatientModel> patientObject;
    static Context context;

    public SearchPatientAdapter(ArrayList<PatientModel> patientObj, Context context){
        this.patientObject =patientObj;
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
    public SearchPatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.listofdoctors,null);
        return new SearchPatientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPatientAdapter.MyViewHolder holder, int position) {

        PatientModel patObj= patientObject.get(position);
        Log.println(Log.DEBUG, "", patObj.getName());
        Glide.with(context).load(patObj.getProfilePicture()).circleCrop().into(holder.profilePic);
        holder.name.setText(patObj.getName());
        holder.chatButton.setOnClickListener(v->{
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("chatwithuser",patObj);
//            Toast.makeText(context, "" + patObj.getName(), Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return patientObject.size();
    }
}
