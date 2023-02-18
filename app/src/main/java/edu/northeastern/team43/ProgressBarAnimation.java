package edu.northeastern.team43;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from;
    private float to;
    private Button covidNewsApi;
    public ProgressBarAnimation(Context context,ProgressBar progressBar,TextView textView,float from,float to,Button covidNewsApi){
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
        this.covidNewsApi = covidNewsApi;
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation){
        super.applyTransformation(interpolatedTime, transformation);
        float value = from + (to-from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int)value + " %");
        if (value==to){
            covidNewsApi.setVisibility(View.VISIBLE);

        }
    }
}
