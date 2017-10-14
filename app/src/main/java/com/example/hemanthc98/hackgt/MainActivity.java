package com.example.hemanthc98.hackgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Button register = (Button) findViewById(R.id.register);
    CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
    EditText name = (EditText) findViewById(R.id.name);
    EditText email = (EditText) findViewById(R.id.email);
    EditText password = (EditText) findViewById(R.id.password);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = name.getText().toString();
                String mail = email.getText().toString();
            }
        });
    }

    private String makePassword(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
