package edu.northeastern.team43;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);
        recyclerView = findViewById(R.id.recyclerView);
        Thread thread = new Thread(() -> {

            String api = api();
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