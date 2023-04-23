package edu.northeastern.team43.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.northeastern.team43.R;

public class Activity_Recommendation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recommendation);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Recommendation.this,R.color.darkgreen));
    }

    public void openMusicApp(View view) {

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.spotify.music");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music&hl=en_US")));
        }
    }

    public void openMediApp(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.getsomeheadspace.android");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.getsomeheadspace.android&hl=en_US")));
        }
    }

    public void openListApp(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.todoist");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.todoist&hl=en_US")));
        }
    }

    public void openJournalApp(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.northstar.gratitude");

        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.northstar.gratitude&hl=en_US")));
        }
    }
}