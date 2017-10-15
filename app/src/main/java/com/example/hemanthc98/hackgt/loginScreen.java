package com.example.hemanthc98.hackgt;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class loginScreen extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = MainActivity.makePassword(password.getText().toString());
                if (APIInterfacer.authenticateUser(user, pass)) {
                    SessionInfo.getInstance().setUsername(user);
                    Intent intent = new Intent(loginScreen.this, activitiesScreen.class);
                    startActivity(intent);
                } else {
                    String title = "Error";
                    String message = "Login credentials are incorrect.";
                    new AlertDialog.Builder(loginScreen.this)
                            .setMessage(message)
                            .setTitle(title)
                            .setPositiveButton("OK", null);
                }
            }
        });
    }
}
