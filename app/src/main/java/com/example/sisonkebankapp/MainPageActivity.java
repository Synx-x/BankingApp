package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends AppCompatActivity {

    private TextView uiUsername;
    private Button uiBalance;
    private Button uiTransfer;
    private Button uiLogout;
    database sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //sql object
        sql=new database(MainPageActivity.this);


        uiUsername = findViewById(R.id.username);
        uiBalance = findViewById(R.id.balance);
        uiTransfer = findViewById(R.id.transfer);
        uiLogout = findViewById(R.id.logout);

        RegistrationActivity reg;
        reg = new RegistrationActivity();
        String txt = sql.getUserName();
        uiUsername.setText(txt);
      //  Toast.makeText(MainPageActivity.this, txt+"", Toast.LENGTH_LONG).show();

        uiBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this, ViewAccountBalanceActivity.class));
            }
        });


        uiTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this, Transfer.class));
            }
        });


        uiLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this, MainActivity.class));
                finish();
            }
        });


    }


}