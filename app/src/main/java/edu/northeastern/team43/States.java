package edu.northeastern.team43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class States extends AppCompatActivity {
    RecyclerView recyclerView;
    StateRecyclerViewAdapater stateRecyclerViewAdapater;
    ArrayList<String> stateNameList;
    static JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);
        recyclerView=findViewById(R.id.recyclerView);
        Thread thread = new Thread(()->{

            String api = api();
            try {
                jsonObject = new JSONObject(api);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.println(Log.DEBUG,"",api);
            stateNameList =new ArrayList<>();
            if (jsonObject!=null){
                Iterator<String> keysIterator = jsonObject.keys();
                while (keysIterator.hasNext()){
                    String key = keysIterator.next();
                    stateNameList.add(key);
                }
            }
            runOnUiThread(()->{
                setAdapter();

                stateRecyclerViewAdapater.notifyDataSetChanged();
            });

        });
        thread.start();
    }

    private String api() {
        String data = "";
        try {
            URL url=new URL("https://data.covid19india.org/v4/min/data.min.json");
            HttpsURLConnection httpsURLConnection=(HttpsURLConnection) url.openConnection();
            InputStream inputStream=httpsURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while((line=bufferedReader.readLine())!=null){
                data=data+line;
            }

        } catch (Exception e) {

//            try {
//                List<String> strings = Files.readAllLines(Paths.get("/Users/amitsk/AndroidStudioProjects/Team43/app/src/main/java/edu/northeastern/team43/data.min.json"));
//                for (String s : strings){
//                    data+=s;
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }

        }

        return data;
    }

    void setAdapter(){
        stateRecyclerViewAdapater=new StateRecyclerViewAdapater(stateNameList, this,jsonObject);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(stateRecyclerViewAdapater);


    }

}