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

import java.util.ArrayList;

public class ListOfUsersRecyclerViewAdapter extends RecyclerView.Adapter<ListOfUsersRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<UserModel> users;
    private static Context context;
    private UserModel loggedInUser;

    public ListOfUsersRecyclerViewAdapter(ArrayList<UserModel> users, Context context,UserModel loggedInUser){
        this.context=context;
        this.users=users;
        this.loggedInUser=loggedInUser;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private Button chatButton;



        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.usernameTextView);
            chatButton=itemView.findViewById(R.id.sendsticker);
        }
    }

    @NonNull
    @Override
    public ListOfUsersRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.listofuser_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfUsersRecyclerViewAdapter.MyViewHolder holder, int position) {
        UserModel userModel = users.get(position);
        holder.name.setText(userModel.getUserName());
        holder.chatButton.setOnClickListener(v->{
            Intent intent = new Intent(context,ChatActivity.class);
            intent.putExtra("selected_user",userModel);
            intent.putExtra("LOGGED_IN_USER",loggedInUser);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
