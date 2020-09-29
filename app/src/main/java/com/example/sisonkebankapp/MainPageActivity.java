package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainPageActivity extends AppCompatActivity {

    private TextView uiUsername;
    private Button uiBalance;
    private Button uiTransfer;
    private Button uiLogout;
    private String[] user;
    String newInt;
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
        user = txt.split(",");

        FileInputStream fis = null;
        try {
            fis = openFileInput("increment.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null){
                 sb.append(text);
            }

          //  uiUsername.setText(sb.toString());
            newInt = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

         String namePlate = user[Integer.parseInt(newInt)];

           uiUsername.setText(namePlate);
      // Toast.makeText(MainPageActivity.this, newInt+"", Toast.LENGTH_LONG).show();

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
                Toast.makeText(MainPageActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }


}