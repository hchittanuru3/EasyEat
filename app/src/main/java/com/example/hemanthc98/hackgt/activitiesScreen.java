package com.example.hemanthc98.hackgt;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class activitiesScreen extends AppCompatActivity {
    final Context context = this;
    Button add = (Button) findViewById(R.id.add);
    EditText date = (EditText) findViewById(R.id.date);
    ListView list = (ListView) findViewById(R.id.list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_screen);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finDate = date.getText().toString();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activities_popup);
                dialog.setTitle("Add New Activity");
                final EditText name = (EditText) findViewById(R.id.name);
                final EditText location = (EditText) findViewById(R.id.location);
                final Spinner firstChoice = (Spinner) findViewById(R.id.time1);
                final Spinner secChoice = (Spinner) findViewById(R.id.time2);
                Button btn = (Button) findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String act = name.getText().toString();
                        String loc = location.getText().toString();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

}
