package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends AppCompatActivity {

    private TextView uiUsername;
    database sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //sql object
        sql=new database(MainPageActivity.this);

        uiUsername = findViewById(R.id.username);
        RegistrationActivity reg;
        reg = new RegistrationActivity();
        String txt = sql.getUserName();
        uiUsername.setText(txt);
        Toast.makeText(MainPageActivity.this, txt+"", Toast.LENGTH_LONG).show();




    }
}