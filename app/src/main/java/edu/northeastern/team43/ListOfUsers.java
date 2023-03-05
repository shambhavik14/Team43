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
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofusers);

        recyclerView=findViewById(R.id.listofusersrecyclerview);
        setAdapter();
        /*
        1. adapter
        2. list of usernames
        3.

         */
    }

    private void setAdapter(){
//        ArrayList<String> users = new ArrayList<>();
//        users.add("pragya");
//        users.add("shambhavi");
//        users.add("amit");
        ArrayList<UserModel> userModelFromDb = getUserModelFromDb();
        ListOfUsersRecyclerViewAdapter adapter = new ListOfUsersRecyclerViewAdapter(userModelFromDb,this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<UserModel> getUserModelFromDb(){
        ArrayList<UserModel> userModels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {

                    DataSnapshot dataSnapshot=task.getResult();
                    Object object = dataSnapshot.getValue();

                    Log.d("firebase1",  object.toString());



//                    Map<String, UserModel> map = ((Map<String, UserModel>) task.getResult().getValue());
//                    Log.d("firebase1",  map.toString());
//                    Log.d("firebase3",  map.values().toString());
//                    for (UserModel u : map.values()){
//                        userModels.add(u);
//                    }
//                    Log.d("firebase",  values.toString());
//                    Iterable<DataSnapshot> children = task.getResult().getChildren();
//                    Log.d("", children.toString());
//                    Iterator<DataSnapshot> iterator = children.iterator();
//                    while (iterator.hasNext()){
//                        UserModel userModel =iterator.next().getValue(UserModel.class);
//                        userModels.add(userModel);
//                    }
                }
            }
        });
        Log.d("firebase2", userModels.toString());
        return userModels;
    }
}