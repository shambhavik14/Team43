package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class States extends AppCompatActivity {
    RecyclerView recyclerView;
    StateRecyclerViewAdapater stateRecyclerViewAdapater;
    ArrayList<String> stateNameList;
    JSONObject jsonObject;

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
            Iterator<String> keysIterator = jsonObject.keys();
            while (keysIterator.hasNext()){
                String key = keysIterator.next();
                stateNameList.add(key);
            }
            runOnUiThread(()->{
                setAdapter();

                stateRecyclerViewAdapater.notifyDataSetChanged();
            });

        });
        thread.start();

//        stateNameList.add("Pragya");
//        stateNameList.add("Prashar");
//        stateNameList.add("I am the best");

    }

    String api() {
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    void setAdapter(){
        stateRecyclerViewAdapater=new StateRecyclerViewAdapater(stateNameList, this);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(stateRecyclerViewAdapater);


    }

}