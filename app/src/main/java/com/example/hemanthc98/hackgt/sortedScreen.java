package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class sortedScreen extends AppCompatActivity {

    ExpandableListView list = (ExpandableListView) findViewById(R.id.list);
    Spinner spinner = (Spinner) findViewById(R.id.spinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_screen);
        spinner.setPrompt("Sort By:");
    }
}
