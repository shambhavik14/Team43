package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.northeastern.team43.R;

public class Activity_Recommendation extends AppCompatActivity {

    /*RelativeLayout musicbtn;
    RelativeLayout meditatebtn;
    RelativeLayout todobtn;
    RelativeLayout chatbtn;*/

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        /*musicbtn = findViewById(R.id.music_button);
        meditatebtn = findViewById(R.id.meditate_button);
        todobtn = findViewById(R.id.todo_button);
        chatbtn = findViewById(R.id.chat_button);*/
    }

    public void openMusicApp(View view) {
                //Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
                //startActivity(intent);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.spotify.music");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            Toast.makeText(Activity_Recommendation.this, "There is no package", Toast.LENGTH_LONG).show();
        }
    }

    public void openMediApp(View view) {
        //Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
        //startActivity(intent);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.getsomeheadspace.android");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            Toast.makeText(Activity_Recommendation.this, "There is no package", Toast.LENGTH_LONG).show();
        }
    }

    public void openListApp(View view) {
        //Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
        //startActivity(intent);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.todoist");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            Toast.makeText(Activity_Recommendation.this, "There is no package", Toast.LENGTH_LONG).show();
        }
    }

    public void openChatApp(View view) {
        //Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
        //startActivity(intent);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.spotify.music");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            Toast.makeText(Activity_Recommendation.this, "There is no package", Toast.LENGTH_LONG).show();
        }
    }

        /*meditatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
                startActivity(intent);
            }
        });
        todobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
                startActivity(intent);
            }
        });
        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Recommendation.this, Music_Activity.class);
                startActivity(intent);
            }
        });*/
}