package edu.northeastern.team43;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class States extends AppCompatActivity {
    static JSONObject jsonObject;
    RecyclerView recyclerView;
    StateRecyclerViewAdapater stateRecyclerViewAdapater;
    ArrayList<String> stateNameList;
    static String data;

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);
        recyclerView = findViewById(R.id.recyclerView);
        Thread thread = new Thread(() -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            progressBar = findViewById(R.id.progressBar);
            textView = findViewById(R.id.progressText);
            progressBar.setMax(100);
            progressBar.setScaleY(3f);
            progressAnimation();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String api = api();
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            data = api;
            try {
                jsonObject = new JSONObject(api);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.println(Log.DEBUG, "", api);
            stateNameList = new ArrayList<>();
            if (jsonObject != null) {
                Iterator<String> keysIterator = jsonObject.keys();
                while (keysIterator.hasNext()) {
                    String key = keysIterator.next();
                    stateNameList.add(key);
                }
            }
                runOnUiThread(() -> {
                    setAdapter();

                    stateRecyclerViewAdapater.notifyDataSetChanged();
                });
        });
        thread.start();
    }
    private void progressAnimation() {
        ProgressBarAnimation animation = new ProgressBarAnimation(getApplicationContext(), progressBar, textView, 0f, 100f);
        animation.setDuration(3000);
        progressBar.setAnimation(animation);
    }

    private String api() {
        String data = "";
        try {
            URL url = new URL("https://data.covid19india.org/v4/min/data.min.json");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data = data + line;
            }
        } catch (Exception e) {

        }
        return data;
    }

    void setAdapter() {
        stateRecyclerViewAdapater = new StateRecyclerViewAdapater(stateNameList, this, jsonObject,data);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(stateRecyclerViewAdapater);
    }
}