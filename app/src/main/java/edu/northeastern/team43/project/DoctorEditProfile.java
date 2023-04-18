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
import android.view.View;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.northeastern.team43.R;

public class DoctorEditProfile extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;

    TextView dateText;
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    ImageView profilePicture;

    String profilePictureFirebasePath = "https://firebasestorage.googleapis.com/v0/b/team43-d5a15.appspot.com/o/images%2Ffda5ec56-55e6-47c3-a463-7f25acba0f1c?alt=media&token=d7f381a7-1628-4cf5-b4ae-aa4e6ed82fd5";

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
            ProgressDialog progressDialog = new ProgressDialog(DoctorEditProfile.this);
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
                                            Glide.with(DoctorEditProfile.this).load(profilePictureFirebasePath).circleCrop().into(profilePicture);

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
            profilePicture.setImageBitmap(bitmap);
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
                                            Glide.with(DoctorEditProfile.this).load(profilePictureFirebasePath).circleCrop().into(profilePicture);


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
        TextView titleTextView = findViewById(R.id.signuptitle);
        titleTextView.setText("Update Account");
        emailEditText.setEnabled(false);
        emailEditText.setFocusable(false);
        passwordEditText=findViewById(R.id.reg_doc_pass);
        EditText nameEditText = findViewById(R.id.reg_doc_name);
        Button submit = findViewById(R.id.reg_submit_button);
        submit.setText("Update");
        profilePicture = findViewById(R.id.profile_picture);
        dateText=findViewById(R.id.reg_doc_date);
        profilePicture.setOnClickListener(v->{
            Dialog dialog=new Dialog(DoctorEditProfile.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.camerdialog);
            dialog.show();

            Button cameraOption= dialog.findViewById(R.id.camera);
            if(ContextCompat.checkSelfPermission(DoctorEditProfile.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DoctorEditProfile.this, new String[]{Manifest.permission.CAMERA}, 101);

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
        dateText.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(DoctorEditProfile.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateText.setText(String.valueOf(month +1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year));
                }
            },2023,0,1);
            datePickerDialog.show();
        });
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
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

        databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                    if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                        nameEditText.setText(doctorModel.getName());
                        emailEditText.setText(doctorModel.getEmail());
                        passwordEditText.setText(doctorModel.getPassword());
                        dateText.setText(doctorModel.getDob());
                        int genderPosition = genderSpinnerAdapter.getPosition(doctorModel.getGender());
                        genderSpinner.setSelection(genderPosition);
                        int statesPosition = stateSpinnerAdapter.getPosition(doctorModel.getState());
                        stateSpinner.setSelection(statesPosition);
                        if (doctorModel.getProfilePicture()!=null && !doctorModel.getProfilePicture().isEmpty()){
                            profilePicture.setImageURI(Uri.parse(doctorModel.getProfilePicture()));
                            Glide.with(DoctorEditProfile.this).load(doctorModel.getProfilePicture()).circleCrop().into(profilePicture);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(v->{
            String emailId = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (!validateEmailAndPassword(emailId,password)){
                return;
            }
            String gender = genderSpinner.getSelectedItem().toString().trim();
            String state = stateSpinner.getSelectedItem().toString().trim();
            String dateOfBirth = dateText.getText().toString().trim();
            String name= nameEditText.getText().toString().trim();
                databaseReference.child("doctors").orderByChild("doctorId").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                        while (iterator.hasNext()){
                            DoctorModel doctorModel = iterator.next().getValue(DoctorModel.class);
                            if (doctorModel.getEmail().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail())){
                                databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("dob").setValue(dateOfBirth);
                                databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("gender").setValue(gender);
                                databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("name").setValue(name);
                                databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("state").setValue(state);
                                databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("profilePicture").setValue(profilePictureFirebasePath);
                                if(emailId.equalsIgnoreCase(doctorModel.getEmail()) && password.equalsIgnoreCase(doctorModel.getPassword())){
                                    databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("email").setValue(emailId);
                                    databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("password").setValue(password);
                                }
                                else{
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    AuthCredential credential = EmailAuthProvider.getCredential(emailId, password);
                                    user.reauthenticate(credential)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.d("RE-AUTHENTICATED", "User re-authenticated.");
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    user.updateEmail(emailId)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("email").setValue(emailId);
                                                                    }
                                                                }
                                                            });
                                                    user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                databaseReference.child("doctors").child(doctorModel.getDoctorId()).child("password").setValue(password);
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                }


                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            finish();

        });


    }
    private boolean validateEmailAndPassword(String email, String password){
        boolean isValid = true;

        if (password==null || password.isEmpty() || password.length()<6){
            isValid = false;
            Dialog dialog = new Dialog(DoctorEditProfile.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.error_dialog);
            TextView errorTextView = dialog.findViewById(R.id.error_msg);
            errorTextView.setText("Password is incorrect.\n\n Password should be atleast 8 characters");
            errorTextView.setTextSize(20);
            errorTextView.setHeight(190);
            dialog.show();
            Button closeButton = dialog.findViewById(R.id.cancel_button);
            closeButton.setOnClickListener(v1->{
                dialog.dismiss();
            });
        }
        return isValid;

    }
}