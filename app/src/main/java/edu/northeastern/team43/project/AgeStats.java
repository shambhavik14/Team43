package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.northeastern.team43.R;

public class AgeStats extends AppCompatActivity {

    PieChart pieChart_pat;
    PieChart pieChart_doc;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_stats);

        pieChart_pat = findViewById(R.id.pie_pat_age);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Integer> ages = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String dob = userSnapshot.child("dob").getValue(String.class);
                    System.out.println("dob: "+dob);
                    if (!dob.equals("")) {
                        int age = getAge(dob);
                        System.out.println("Age:" + age);
                        ages.add(age);
                    }
                }
                System.out.println("ages:" + ages);
                int[] ageRanges = new int[10];
                for (int age : ages) {
                    if (age >= 18 && age <= 20) {
                        ageRanges[0]++;
                    } else if (age >= 21 && age <= 30) {
                        ageRanges[1]++;
                    } else if (age >= 31 && age <= 40) {
                        ageRanges[2]++;
                    } else if (age >= 41 && age <= 50) {
                        ageRanges[3]++;
                    } else if (age >= 51 && age <= 60) {
                        ageRanges[4]++;
                    } else if (age >= 61 && age <= 70) {
                        ageRanges[5]++;
                    }
                }
                List<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(ageRanges[0], "18-20"));
                entries.add(new PieEntry(ageRanges[1], "21-30"));
                entries.add(new PieEntry(ageRanges[2], "31-40"));
                entries.add(new PieEntry(ageRanges[3], "41-50"));
                entries.add(new PieEntry(ageRanges[4], "51-60"));
                entries.add(new PieEntry(ageRanges[5], "61-70"));

                // Create a PieDataSet object from the list of PieEntry objects
                PieDataSet dataSet = new PieDataSet(entries, "Pie Chart");
                pieChart_pat.setDrawEntryLabels(true);
                pieChart_pat.setUsePercentValues(true);
                pieChart_pat.setCenterText("Patient Age Data");

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

    }

    public static int getAge(String dob) {
        int year = Integer.parseInt(dob.substring(dob.length() - 4));
        return 2023 - year;
    }

}