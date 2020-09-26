package com.example.sisonkebankapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ViewAccountBalanceActivity extends AppCompatActivity {
    database db;
    private TextView uiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_balance);

        //button to return to previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new database(this);


        uiText = findViewById(R.id.accBalance);

        String balance =db.getUserBalance();

        //displays user information
        uiText.append(balance);
    }

}
