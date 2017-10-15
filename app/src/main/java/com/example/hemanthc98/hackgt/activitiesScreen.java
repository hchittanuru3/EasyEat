package com.example.hemanthc98.hackgt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activitiesScreen extends AppCompatActivity {
    final Context context = this;
    private Button add;
    private EditText date;
    private ListView list;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_screen);
        add = (Button) findViewById(R.id.add);
        date = (EditText) findViewById(R.id.date);
        list = (ListView) findViewById(R.id.list);
        bar = getSupportActionBar();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String finDate = date.getText().toString();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activities_popup);
                dialog.setTitle("Add New Activity");
                final EditText name = (EditText) findViewById(R.id.name);
                final EditText location = (EditText) findViewById(R.id.location);
                final Spinner firstChoice = (Spinner) findViewById(R.id.time1);
                final Spinner secChoice = (Spinner) findViewById(R.id.time2);
                firstChoice.setAdapter(getTimes());
                secChoice.setAdapter(getTimes());
                Button btn = (Button) findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String act = name.getText().toString();
                        String loc = location.getText().toString();
                        String user = SessionInfo.getInstance().getUsername();
                        String sTime = firstChoice.getSelectedItem().toString();
                        String eTime = secChoice.getSelectedItem().toString();
                        APIInterfacer.addScheduleActivity(user, finDate, act, sTime, eTime, loc);
                        System.out.println("Success");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private ArrayAdapter<String> getTimes() {
        List<String> strings = new ArrayList<String>(Arrays.asList("0:00", "0:30", "1:00", "1:30", "2:00",
                "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", "6:30","7:00", "7:30",
                "8:00", "8:30","9:00", "9:30","10:00", "10:30","11:00", "11:30","12:00", "12:30",
                "13:00", "13:30", "14:00", "14:30", "15:00", "15:30","16:00", "16:30",
                "17:00", "17:30","18:00", "18:30","19:00", "19:30","20:00", "20:30","21:00", "21:30",
                "22:00", "22:30", "23:00", "23:30"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

}
