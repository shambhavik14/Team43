package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class SearchPatientActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView loggedInUserName;
    private ImageView loggedInUserImage;
    private SearchPatientAdapter adapter;
    private ArrayList<PatientModel> patientNamesList;
    private HashSet<String> visited;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    RecyclerView.LayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_patient);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recyclerViewdoctor);
        loggedInUserName=findViewById(R.id.loginUser);
        loggedInUserImage=findViewById(R.id.loginuserimg);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        loggedInUserName.setText("Dr. "+doctorModel.getName());
                        Glide.with(getApplicationContext()).load(doctorModel.getProfilePicture()).circleCrop().into(loggedInUserImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        patientNamesList =new ArrayList<>();
        visited=new HashSet<>();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                patientNamesList.clear();
                visited.clear();
                while (iterator.hasNext()){
                    ChatModel chatModel = iterator.next().getValue(ChatModel.class);
                    if (firebaseAuth.getCurrentUser()!=null
                            && firebaseAuth.getCurrentUser().getEmail().equalsIgnoreCase(chatModel.getReceiverEmail())){
                        databaseReference.child("patients").orderByChild("patientId").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                while (iterator.hasNext()){
                                    PatientModel patientModel = iterator.next().getValue(PatientModel.class);
                                    Log.println(Log.DEBUG, "", patientModel.getName());
                                    if (!visited.contains(patientModel.getEmail()) &&patientModel.getEmail().equalsIgnoreCase(chatModel.getSenderEmail())){
                                        patientNamesList.add(patientModel);
                                        visited.add(patientModel.getEmail());
                                    }
                                }

                                setAdapter();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchPatientActivity.this,R.color.darkgreen));
    }

    private void setAdapter(){
        Log.println(Log.DEBUG, "","this is adapter");
        patientNamesList.sort(new Comparator<PatientModel>() {
            @Override
            public int compare(PatientModel o1, PatientModel o2) {
                if (o1.getMostRecentMsgDate() ==null || o2.getMostRecentMsgDate()==null){
                    return 0;
                }

                LocalDateTime date1 =  convertToLocalDateTime(o1.getMostRecentMsgDate());
                LocalDateTime date2 =  convertToLocalDateTime(o2.getMostRecentMsgDate());

                if( date1.isAfter(date2)){
                    return -1;
                }else if(date1.isBefore(date2)){
                    return 1;
                }
                return 0;
            }
        });
        adapter=new SearchPatientAdapter(patientNamesList, this);
        recyclerView.setAdapter(adapter);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    private LocalDateTime convertToLocalDateTime(String dateToConvert){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd h:mm a");
        LocalDateTime localDateTime = null;
        try {
            localDateTime = sdf.parse(dateToConvert).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return localDateTime;
    }

}