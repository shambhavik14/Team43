package edu.northeastern.team43;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class AtYourService extends AppCompatActivity {

    private static final String TAG = "AtYourService";

    private EditText mURLEditText;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        mURLEditText = (EditText) findViewById(R.id.URL_editText);
        mTitleTextView = (TextView) findViewById(R.id.result_textview);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebserviceButtonHandler(view);
            }
        });
    }

    public void callWebserviceButtonHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask();
        task.execute(mURLEditText.getText().toString());
    }


    private class PingWebServiceTask extends AsyncTask<String, Integer, String[]> {

        @Override
        protected void onProgressUpdate(Integer... values) { Log.i(TAG, "Making progress..."); }

        @Override
        protected String[] doInBackground(String... params) {

            String[] results = new String[2];
            URL url = null;
            try {

                //url = new URL("https://jsonplaceholder.typicode.com/posts/1");
                url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);

                JSONObject jObject = new JSONObject(resp);
                String jTitle = jObject.getString("title");
                String jBody = jObject.getString("body");
                results[0] = jTitle;
                results[1] = jBody;
                return results;

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }

            results[0] = "Something went wrong";
            return(results);
        }

        @Override
        protected void onPostExecute(String... s) {
            super.onPostExecute(s);
            TextView result_view = (TextView)findViewById(R.id.result_textview);
            result_view.setText(s[0]);
        }

    }

    private String convertStreamToString(InputStream is) {

        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}