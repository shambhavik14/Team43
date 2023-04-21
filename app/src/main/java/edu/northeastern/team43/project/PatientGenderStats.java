package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.northeastern.team43.R;

public class PatientGenderStats extends AppCompatActivity {

    PieChart pieChart_pat;
    PieChart pieChart_doc;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_gender_stats);

        // Retrieve a reference to the PieChart object
        pieChart_pat = findViewById(R.id.pie_pat_gen);
        pieChart_doc = findViewById(R.id.pie_doc_gen);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("patients").addValueEventListener(new ValueEventListener() {
            int males = 0;
            int females = 0;
            int other = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String gender = userSnapshot.child("gender").getValue(String.class);
                    if (gender.equals("Male")) {
                        males++;
                    } else if (gender.equals("Female")) {
                        females++;
                    } else {
                        other++;
                    }
                }
                List<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(males, "Male"));
                entries.add(new PieEntry(females, "Female"));
                entries.add(new PieEntry(other, "other"));

                // Create a PieDataSet object from the list of PieEntry objects
                PieDataSet dataSet = new PieDataSet(entries, "Pie Chart");
                pieChart_pat.setDrawEntryLabels(true);
                pieChart_pat.setUsePercentValues(true);
                pieChart_pat.setCenterText("Patient Gender Data");

                // Set the colors of the slices in the chart
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);


                // Create a PieData object from the PieDataSet object
                PieData data = new PieData(dataSet);

                // Set the data of the PieChart object to the PieData object
                pieChart_pat.setData(data);

                pieChart_pat.setEntryLabelColor(Color.WHITE);

                // Refresh the chart
                pieChart_pat.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("doctors").addValueEventListener(new ValueEventListener() {
            int males = 0;
            int females = 0;
            int other = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String gender = userSnapshot.child("gender").getValue(String.class);
                    if (gender.equals("Male")) {
                        males++;
                    } else if (gender.equals("Female")) {
                        females++;
                    } else {
                        other++;
                    }
                }
                List<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(males, "Male"));
                entries.add(new PieEntry(females, "Female"));
                entries.add(new PieEntry(other, "other"));

                // Create a PieDataSet object from the list of PieEntry objects
                PieDataSet dataSet = new PieDataSet(entries,"");
                pieChart_doc.setDrawEntryLabels(true);
                pieChart_doc.setUsePercentValues(true);
                pieChart_doc.setCenterText("Doctor Gender Data");

                // Set the colors of the slices in the chart
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart_doc.getLegend().setEnabled(false);


                // Create a PieData object from the PieDataSet object
                PieData data = new PieData(dataSet);

                // Set the data of the PieChart object to the PieData object
                pieChart_doc.setData(data);

                pieChart_doc.setEntryLabelColor(Color.WHITE);

                // Refresh the chart
                pieChart_doc.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}