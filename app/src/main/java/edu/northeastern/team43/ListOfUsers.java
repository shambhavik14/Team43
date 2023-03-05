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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofusers);

        recyclerView=findViewById(R.id.listofusersrecyclerview);
        userModelFromDb = new ArrayList<>();

        getUserModelFromDb();

    }

    private void setAdapter(){

        UserModel loggedInUser = (UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
        adapter = new ListOfUsersRecyclerViewAdapter(userModelFromDb,this,loggedInUser);

        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<UserModel> getUserModelFromDb(){
        ArrayList<UserModel> userModels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot ds : children){
                    UserModel value = ds.getValue(UserModel.class);
                    userModelFromDb.add(value);
//                    adapter.notifyDataSetChanged();

                }
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Iterable<DataSnapshot> children = task.getResult().getChildren();
//                    for (DataSnapshot ds : children){
//                        UserModel value = ds.getValue(UserModel.class);
//                        userModels.add(value);
//                    }
//                    Iterator<DataSnapshot> iterator = children.iterator();
//                    while (iterator!=null && iterator.hasNext()){
//                        DataSnapshot next = iterator.next();
//                        if (next !=null){
//
//
//                            UserModel userModel = next.getValue(UserModel.class);
//                            Log.d("usersssss",userModel.toString());
//                            userModels.add(userModel);
//                        }
//                    }
//                }
//            }
//        });
        return userModels;
    }
}