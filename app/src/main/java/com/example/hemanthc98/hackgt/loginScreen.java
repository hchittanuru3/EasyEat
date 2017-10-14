package com.example.hemanthc98.hackgt;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class loginScreen extends AppCompatActivity {

    EditText username = (EditText) findViewById(R.id.username);
    EditText password = (EditText) findViewById(R.id.password);
    Button login = (Button) findViewById(R.id.login);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = MainActivity.makePassword(password.getText().toString());
                Intent intent = new Intent(loginScreen.this, activitiesScreen.class);
                startActivity(intent);
            }
        });
    }
}
