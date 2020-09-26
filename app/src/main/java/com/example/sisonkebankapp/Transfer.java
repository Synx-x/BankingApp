package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Transfer extends AppCompatActivity {

    private TextView tCurrent;
    private TextView tSavings;
    private TextView tInput;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //button to return to previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tCurrent = findViewById(R.id.tCurrent);
        tSavings = findViewById(R.id.transfer);
        tInput = findViewById(R.id.tInput);

        db = new database(this);

        db.getUserCurrent();
        String account = db.getUserCurrent();
   //     String savings = db.getUserCurrent1();

        tCurrent.append(account);
     //   tSavings.append(savings);

    }
}