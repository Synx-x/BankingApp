package com.example.sisonkebankapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ViewAccountBalanceActivity extends AppCompatActivity {
    database db;
    String[] nBalance;
    String[] nBalance1;
    String[] nBalance2;
    String[] nBalance3;
    String newInt;
    private TextView uiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_balance);

        //button to return to previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new database(this);


        uiText = findViewById(R.id.accBalance);

        //call user details from database
        String balance =db.getUserBalance();
        String balance1 =db.getUserBalance1();
        String balance2 =db.getUserBalance2();
        String balance3 =db.getUserBalance3();
        nBalance = balance.split(",");
        nBalance1 = balance1.split(",");
        nBalance2 = balance2.split(",");
        nBalance3 = balance3.split(",");

        //reads user id from secure file thats created to track the users id in the database
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

        //formats user information
        String balInfo = "Account Holder Name: "+nBalance[Integer.parseInt(newInt)]+"\n"+"Account Holder Surname: "+nBalance1[Integer.parseInt(newInt)]+"\n"+"Current Account Balance: R"+nBalance2[Integer.parseInt(newInt)]+"\n"+"Savings Account Balance: R"+nBalance3[Integer.parseInt(newInt)];



        //displays user information
        uiText.append(balInfo);
    }

}
