package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ListOfUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<UserModel> userModelFromDb;
    private  ListOfUsersRecyclerViewAdapter adapter;
    private UserModel loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofusers);

        recyclerView=findViewById(R.id.listofusersrecyclerview);
        userModelFromDb = new ArrayList<>();
        loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
        getUserModelFromDb();

    }

    private void setAdapter(){


        adapter = new ListOfUsersRecyclerViewAdapter(userModelFromDb,this,loggedInUser);

        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<UserModel> getUserModelFromDb(){
        ArrayList<UserModel> userModels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot ds : children){
                    UserModel value = ds.getValue(UserModel.class);
                    if (!loggedInUser.getUserName().equalsIgnoreCase(value.getUserName())){

                        userModelFromDb.add(value);
                    }
//                    adapter.notifyDataSetChanged();

                }
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userModels;
    }
}