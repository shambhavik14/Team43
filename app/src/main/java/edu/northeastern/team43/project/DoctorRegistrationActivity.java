package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.northeastern.team43.R;

public class DoctorRegistrationActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;

    TextView dateText;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
        emailEditText = findViewById(R.id.reg_doc_email);
        passwordEditText=findViewById(R.id.reg_doc_pass);
        Button submit = findViewById(R.id.reg_submit_button);
        dateText=findViewById(R.id.reg_doc_date);
        dateText.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(DoctorRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateText.setText(String.valueOf(month +1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year));
                }
            },2023,0,1);
            datePickerDialog.show();
        });
        firebaseAuth= FirebaseAuth.getInstance();
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


        Spinner genderSpinner = findViewById(R.id.reg_gender_drop);
        Spinner stateSpinner = findViewById(R.id.reg_state_drop);
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderArray);
        genderSpinner.setAdapter(genderSpinnerAdapter);

        ArrayAdapter<String> stateSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statesArray);
        stateSpinner.setAdapter(stateSpinnerAdapter);

        submit.setOnClickListener(v->{
            String emailId = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(emailId,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(getApplicationContext(),"REGISTRATION SUCCESSFUL",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"REGISTRATION FAILURE",Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}