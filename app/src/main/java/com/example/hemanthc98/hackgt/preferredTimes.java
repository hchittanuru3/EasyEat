package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class preferredTimes extends AppCompatActivity {

    Spinner Breakfasttime1;
    Spinner Breakfasttime2;
    Spinner Lunchtime1;
    Spinner Lunchtime2;
    Spinner Dinnertime1;
    Spinner Dinnertime2;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_times);
        Breakfasttime1 = (Spinner) findViewById(R.id.breakfasttime1);
        Breakfasttime2 = (Spinner) findViewById(R.id.breakfasttime2);
        Lunchtime1 = (Spinner) findViewById(R.id.lunchtime1);
        Lunchtime2 = (Spinner) findViewById(R.id.lunchtime2);
        Dinnertime1 = (Spinner) findViewById(R.id.dinnertime1);
        Dinnertime2 = (Spinner) findViewById(R.id.dinnertime2);
        Submit = (Button) findViewById(R.id.submit);
        Breakfasttime1.setAdapter(getTimes());
        Breakfasttime2.setAdapter(getTimes());
        Lunchtime1.setAdapter(getTimes());
        Lunchtime2.setAdapter(getTimes());
        Dinnertime1.setAdapter(getTimes());
        Dinnertime2.setAdapter(getTimes());
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bsTime = Breakfasttime1.getSelectedItem().toString();
                String beTime = Breakfasttime2.getSelectedItem().toString();
                String lsTime = Lunchtime1.getSelectedItem().toString();
                String leTime = Lunchtime2.getSelectedItem().toString();
                String dsTime = Dinnertime1.getSelectedItem().toString();
                String deTime = Dinnertime2.getSelectedItem().toString();
                String user = SessionInfo.getInstance().getUsername();
                APIInterfacer.updatePreferences(user, bsTime, beTime, lsTime, leTime, dsTime, deTime);
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
