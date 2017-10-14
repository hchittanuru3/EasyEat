package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class homeScreen extends AppCompatActivity {

    Button login = (Button) findViewById(R.id.loginb);
    Button register = (Button) findViewById(R.id.registerb);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
}
