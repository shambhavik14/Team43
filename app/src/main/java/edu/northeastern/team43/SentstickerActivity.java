package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SentstickerActivity extends AppCompatActivity {
    private UserModel loggedInUser;
    private SentStickerAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<SentSticker> sentStickerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentsticker);
        recyclerView=findViewById(R.id.sentstickerrecyclerview);
        sentStickerArrayList=new ArrayList<>();
        loggedInUser=(UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");
        getUserModelDb();

    }

    private void setAdapter(){
        adapter=new SentStickerAdapter(this,sentStickerArrayList);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getUserModelDb(){

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children=snapshot.getChildren();
                for(DataSnapshot ds:children){
                    UserModel value=ds.getValue(UserModel.class);
                    if(loggedInUser.getUserName().equalsIgnoreCase(value.getUserName())){
                        sentStickerArrayList.addAll(value.getSentStickers());
                    }
//                    adapter.notifyDataSetChanged();

                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}