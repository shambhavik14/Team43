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
import java.util.List;
import java.util.stream.Collectors;

public class SentstickerActivity extends AppCompatActivity {
    private UserModel loggedInUser;
    private SentStickerAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<StickerDetail> sentStickerArrayList;
    private int oswaldCount = 0;
    private int spongeBobCount = 0;
    private int mickeyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentsticker);
        recyclerView=findViewById(R.id.sentstickerrecyclerview);
        sentStickerArrayList=new ArrayList<>();
        getUserModelDb();


        loggedInUser=(UserModel) getIntent().getSerializableExtra("LOGGED_IN_USER");


    }

    private void setAdapter(){
        adapter=new SentStickerAdapter(this,sentStickerArrayList);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getUserModelDb(){
        ArrayList<SentSticker> tmp = new ArrayList<>();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children=snapshot.getChildren();
                for(DataSnapshot ds:children){
                    UserModel value=ds.getValue(UserModel.class);
                    if(loggedInUser.getUserName().equalsIgnoreCase(value.getUserName())){
                        tmp.addAll(value.getSentStickers());
                    }
//                    adapter.notifyDataSetChanged();

                }
                getCount(tmp);
                if (oswaldCount!=0){
                    sentStickerArrayList.add(new StickerDetail("oswald",oswaldCount));
                }
                if (spongeBobCount!=0){
                    sentStickerArrayList.add(new StickerDetail("spongebob",spongeBobCount));
                }
                if (mickeyCount!=0){
                    sentStickerArrayList.add(new StickerDetail("mickey",mickeyCount));
                }
//                List<SentSticker> oswaldList = tmp.stream().filter(s -> s.getStickerId().equalsIgnoreCase("oswald")).collect(Collectors.toList());
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getCount(ArrayList<SentSticker> tmp){
        for (int i=0;i<tmp.size();i++){
            String stickerId = tmp.get(i).getStickerId();
            if (stickerId.equalsIgnoreCase("oswald")){
                oswaldCount+=1;
            }else if(stickerId.equalsIgnoreCase("spongebob")){
                spongeBobCount+=1;
            }else if(stickerId.equalsIgnoreCase("mickey")){
                mickeyCount+=1;
            }
        }
    }
}