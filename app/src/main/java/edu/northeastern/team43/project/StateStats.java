package edu.northeastern.team43.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.team43.R;

public class StateStats extends AppCompatActivity {

    PieChart pieChart_pat;
    PieChart pieChart_doc;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_stats);

        pieChart_pat = findViewById(R.id.pie_pat_state);
        pieChart_doc = findViewById(R.id.pie_doc_state);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Integer> stateCountMap = new HashMap<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String state = userSnapshot.child("state").getValue(String.class);
                    if (stateCountMap.containsKey(state)) {
                        stateCountMap.put(state, stateCountMap.get(state) + 1);
                    } else {
                        stateCountMap.put(state, 1);
                    }
                }
                List<PieEntry> entries = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : stateCountMap.entrySet()) {
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                }
                PieDataSet dataSet = new PieDataSet(entries, "State Distribution");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart_pat.setDrawEntryLabels(true);
                pieChart_pat.setUsePercentValues(true);
                pieChart_doc.setCenterText("Patient State Data");

                // Create a PieData object from the PieDataSet object and set it to the PieChart
                PieData data = new PieData(dataSet);
                pieChart_pat.setData(data);

                // Customize the appearance of the PieChart
                pieChart_pat.setUsePercentValues(true);
                pieChart_pat.getDescription().setEnabled(false);
                pieChart_pat.setExtraOffsets(5, 10, 5, 5);
                pieChart_pat.setDragDecelerationFrictionCoef(0.95f);
                pieChart_pat.setDrawHoleEnabled(true);
                pieChart_pat.setHoleColor(Color.WHITE);
                pieChart_pat.setTransparentCircleRadius(61f);
                pieChart_pat.setEntryLabelColor(Color.BLACK);
                pieChart_pat.setEntryLabelTextSize(12f);
                pieChart_pat.setDrawEntryLabels(true);
                pieChart_pat.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}