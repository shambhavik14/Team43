package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.northeastern.team43.R;

public class PatientRegistrationActivity extends AppCompatActivity {
    EditText patientName;
    EditText patientEmailId;
    EditText patientPassword;
    TextView patientDOB;
    Spinner  patientGender;
    Spinner patientState;
    Button submit;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        patientName=findViewById(R.id.reg_pat_name);
        patientEmailId=findViewById(R.id.reg_pat_email);
        patientPassword=findViewById(R.id.reg_pat_password);
        patientDOB=findViewById(R.id.reg_pat_dob);
        patientGender=findViewById(R.id.reg_pat_gender);
        patientState=findViewById(R.id.reg_pat_state);
        submit=findViewById(R.id.red_pat_submit);
        patientDOB.setOnClickListener(v->{
            DatePickerDialog datePickerDialog=new DatePickerDialog(PatientRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    patientDOB.setText(String.valueOf(month+1)+"/"+String.valueOf(year));
                }
            },2023,0,1);
            datePickerDialog.show();
        });
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("patients");


        ArrayList<String> genderArray = new ArrayList<String>();
        genderArray.add("Male");
        genderArray.add("Female");
        genderArray.add("Other");
        ArrayList<String> statesArray = new ArrayList<>();
        statesArray.add("Alabama");
        statesArray.add("Alaska");
        statesArray.add("Arizona");
        statesArray.add("Arkansas");
        statesArray.add("California");
        statesArray.add("Colorado");
        statesArray.add("Connecticut");
        statesArray.add("Delaware");
        statesArray.add("Florida");
        statesArray.add("Georgia");
        statesArray.add("Hawaii");
        statesArray.add("Idaho");
        statesArray.add("Illinois");
        statesArray.add("Indiana");
        statesArray.add("Iowa");
        statesArray.add("Kansas");
        statesArray.add("Kentucky");
        statesArray.add("Louisiana");
        statesArray.add("Maine");
        statesArray.add("Maryland");
        statesArray.add("Massachusetts");
        statesArray.add("Michigan");
        statesArray.add("Minnesota");
        statesArray.add("Mississippi");
        statesArray.add("Missouri");
        statesArray.add("Montana");
        statesArray.add("Nebraska");
        statesArray.add("Nevada");
        statesArray.add("New Hampshire");
        statesArray.add("New Jersey");
        statesArray.add("New Mexico");
        statesArray.add("New York");
        statesArray.add("North Carolina");
        statesArray.add("North Dakota");
        statesArray.add("Ohio");
        statesArray.add("Oklahoma");
        statesArray.add("Oregon");
        statesArray.add("Rhode Island");
        statesArray.add("South Carolina");
        statesArray.add("South Dakota");
        statesArray.add("Tennessee");
        statesArray.add("Texas");
        statesArray.add("Utah");
        statesArray.add("Vermont");
        statesArray.add("Virginia");
        statesArray.add("Washington");
        statesArray.add("West Virginia");
        statesArray.add("Wisconsin");
        statesArray.add("Wyoming");

        ArrayAdapter<String> genderSpinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,genderArray);
        patientGender.setAdapter(genderSpinnerAdapter);
        ArrayAdapter<String> stateSpinnerAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,statesArray);
        patientState.setAdapter(stateSpinnerAdapter);

        submit.setOnClickListener(v->{
            String name=patientName.getText().toString().trim();

            String email=patientEmailId.getText().toString().trim();
            String password=patientPassword.getText().toString().trim();
            String dateOfBirth=patientDOB.getText().toString().trim();
            Log.d("dob",dateOfBirth);
            String gender=patientGender.getSelectedItem().toString().trim();
            String state=patientState.getSelectedItem().toString().trim();
           firebaseAuth.createUserWithEmailAndPassword(email,password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           Toast.makeText(getApplicationContext(),"REGISTRATION SUCCESSFUL",Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(getApplicationContext(), Welcome.class);
                           databaseReference.orderByChild("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   String key1=databaseReference.push().getKey();
                                   PatientModel patient=new PatientModel.Builder()
                                           .patientId(key1)
                                           .name(name)
                                           .email(email)
                                           .password(password)
                                           .dob(dateOfBirth)
                                           .gender(gender)
                                           .state(state)
                                           .build();
                                   databaseReference.child(key1).setValue(patient);
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {
                                   Log.d("",error.getMessage());
                               }
                           });
                       startActivity(intent);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getApplicationContext(),"REGISTRATION FAILURE",Toast.LENGTH_SHORT).show();
                       }
                   });

        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(PatientRegistrationActivity.this,R.color.purple));

    }
}