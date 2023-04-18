package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import edu.northeastern.team43.R;
import edu.northeastern.team43.UserModel;

public class DoctorRegistrationActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;

    TextView dateText;
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    String profilePictureFirebasePath = "https://firebasestorage.googleapis.com/v0/b/team43-d5a15.appspot.com/o/images%2Ffda5ec56-55e6-47c3-a463-7f25acba0f1c?alt=media&token=d7f381a7-1628-4cf5-b4ae-aa4e6ed82fd5";

    ImageView profilePicture;
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK && data!=null){
            Uri imagePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
            }catch (Exception e){
                Log.println(Log.DEBUG,"",e.getMessage());
            }
            profilePicture.setImageBitmap(bitmap);
            ProgressDialog progressDialog = new ProgressDialog(DoctorRegistrationActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString())
                    .putFile(imagePath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()){
                                            profilePictureFirebasePath = task.getResult().toString();
                                            Glide.with(DoctorRegistrationActivity.this).load(profilePictureFirebasePath).circleCrop().into(profilePicture);


                                        }
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
        else if(requestCode==101){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
//            profilePicture.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString())
                    .putBytes(bytes)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()){
                                            profilePictureFirebasePath = task.getResult().toString();
                                            Glide.with(DoctorRegistrationActivity.this).load(profilePictureFirebasePath).circleCrop().into(profilePicture);


                                        }
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_doctor_registration);
        emailEditText = findViewById(R.id.reg_doc_email);
        passwordEditText=findViewById(R.id.reg_doc_pass);
        EditText nameEditText = findViewById(R.id.reg_doc_name);
        Button submit = findViewById(R.id.reg_submit_button);
        profilePicture = findViewById(R.id.profile_picture);
        profilePicture.setOnClickListener(v->{

            Dialog dialog=new Dialog(DoctorRegistrationActivity.this);
            dialog.setContentView(R.layout.camerdialog);
            dialog.show();

           Button cameraOption= dialog.findViewById(R.id.camera);
           if(ContextCompat.checkSelfPermission(DoctorRegistrationActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(DoctorRegistrationActivity.this, new String[]{Manifest.permission.CAMERA}, 101);

           }
           cameraOption.setOnClickListener(v1->{
               Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intent,101);
               dialog.dismiss();
           });
            Button galleryOption = dialog.findViewById(R.id.gallery);
            galleryOption.setOnClickListener(v2->{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
                dialog.dismiss();
            });


        });
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("doctors");
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
            String gender = genderSpinner.getSelectedItem().toString().trim();
            String state = stateSpinner.getSelectedItem().toString().trim();
            String dateOfBirth = dateText.getText().toString().trim();
            String name= nameEditText.getText().toString().trim();
            try {

                firebaseAuth.createUserWithEmailAndPassword(emailId,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(getApplicationContext(),Companion.class);
                                databaseReference.orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String key1 = databaseReference.push().getKey();
                                        DoctorModel doctor = new DoctorModel.Builder()
                                                .doctorId(key1)
                                                .name(name)
                                                .email(emailId)
                                                .password(password)
                                                .dob(dateOfBirth)
                                                .gender(gender)
                                                .state(state)
                                                .profilePicture(profilePictureFirebasePath)
                                                .build();
                                        databaseReference.child(key1).setValue(doctor);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.d("", error.getMessage());
                                    }
                                });
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showErrorDialog();
                            }
                        });
            }catch (Exception e){
                showErrorDialog();
            }



        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkbluelatest)));
        getWindow().setStatusBarColor(ContextCompat.getColor(DoctorRegistrationActivity.this,R.color.darkbluelatest));
    }
    private void showErrorDialog() {
        Dialog dialog = new Dialog(DoctorRegistrationActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.error_dialog);

        dialog.show();
        Button closeButton = dialog.findViewById(R.id.cancel_button);
        closeButton.setOnClickListener(v1->{
            dialog.dismiss();
        });
    }
}