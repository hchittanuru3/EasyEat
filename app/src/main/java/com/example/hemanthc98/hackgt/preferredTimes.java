package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Button;

public class preferredTimes extends AppCompatActivity {

    Spinner Breakfasttime1 = (Spinner) findViewById(R.id.breakfasttime1);
    Spinner Breakfasttime2 = (Spinner) findViewById(R.id.breakfasttime2);
    Spinner Lunchtime1 = (Spinner) findViewById(R.id.lunchtime1);
    Spinner Lunchtime2 = (Spinner) findViewById(R.id.lunchtime2);
    Spinner Dinnertime1 = (Spinner) findViewById(R.id.dinnertime1);
    Spinner Dinnertime2 = (Spinner) findViewById(R.id.dinnertime2);
    Button Submit = (Button) findViewById(R.id.submit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_times);
    }
}
