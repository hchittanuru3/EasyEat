package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TimesScreen extends AppCompatActivity {

    TextView breakfastclick = (TextView) findViewById(R.id.breakfastclick);
    TextView lunchclick = (TextView) findViewById(R.id.lunchclick);
    TextView dinnerclick = (TextView) findViewById(R.id.dinnerclick);
    Button plan = (Button) findViewById(R.id.plan);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_screen);
    }
}
