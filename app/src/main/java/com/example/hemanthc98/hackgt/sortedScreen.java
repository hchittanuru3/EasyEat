package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sortedScreen extends AppCompatActivity {

    ExpandableListView list = (ExpandableListView) findViewById(R.id.list);
    Spinner spinner = (Spinner) findViewById(R.id.spinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_screen);
        spinner.setPrompt("Sort By:");
        spinner.setAdapter(getCriteria());

    }

    private ArrayAdapter<String> getCriteria() {
        List<String> list = new ArrayList<>(Arrays.asList("Rating", "Est. Arrival", "Price"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
