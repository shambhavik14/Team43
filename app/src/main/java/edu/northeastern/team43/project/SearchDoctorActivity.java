package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import edu.northeastern.team43.R;

public class SearchDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView loggedInUserName;
    private ImageView loggedInUserImage;
    private SearchDoctorAdapter adapter;
    private ArrayList<DoctorModel> doctorNamesList;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_doctor);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recyclerViewdoctor);
        loggedInUserName=findViewById(R.id.loginUser);
        loggedInUserImage=findViewById(R.id.loginuserimg);


        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("patients").orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator=snapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    PatientModel patientModel=iterator.next().getValue(PatientModel.class);
                    if(patientModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        loggedInUserName.setText(patientModel.getName());
                        Glide.with(getApplicationContext()).load(patientModel.getProfilePicture()).circleCrop().into(loggedInUserImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctorNamesList=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    Log.println(Log.DEBUG, "", doctorModel.getName());
                    if (!doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        doctorNamesList.add(doctorModel);
                    }
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchDoctorActivity.this,R.color.darkgreen));
    }

    private void setAdapter(){
        Log.println(Log.DEBUG, "","this is adapter");
        doctorNamesList.sort(new Comparator<DoctorModel>() {
            @Override
            public int compare(DoctorModel o1, DoctorModel o2) {
                LocalDateTime date1 =  LocalDateTime.parse(o1.getMostRecentMsgDate(), DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
                LocalDateTime date2 =  LocalDateTime.parse(o2.getMostRecentMsgDate(),DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));

                if( date1.isAfter(date2)){
                    return -1;
                }else if(date1.isBefore(date2)){
                    return 1;
                }
                return 0;
            }
        });
        adapter=new SearchDoctorAdapter(doctorNamesList, this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}