package com.example.hemanthc98.hackgt;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

    public class MainActivity extends AppCompatActivity {

    private Button register;
    private CheckBox checkBox;
    private EditText name;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.register);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBox.isChecked()) {
                    String title = "Error";
                    String message = "You must agree to terms and conditions before proceeding.";
                    new AlertDialog.Builder(MainActivity.this)
                                   .setMessage(message)
                                   .setTitle(title)
                                   .setPositiveButton("OK", null);
                }
                String str = name.getText().toString();
                String mail = email.getText().toString();
                String pass = makePassword(password.getText().toString());
                APIInterfacer.addUser(mail, pass);
                Intent intent = new Intent(MainActivity.this, loginScreen.class);
                startActivity(intent);
            }
        });
    }

    public static String makePassword(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}