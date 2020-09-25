package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText uiEmail;
    private EditText uiPassword;
    private Button uiLogin;
    private TextView uiRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking xml layout to variables
        uiEmail = findViewById(R.id.loginEmail);
        uiPassword = findViewById(R.id.loginPassword);
        uiLogin = findViewById(R.id.login);
        uiRegister = findViewById(R.id.register);

        //button redirects to the registration activity if user doesnt have account
        uiRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
}