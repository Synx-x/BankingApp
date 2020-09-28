package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Transfer extends AppCompatActivity {

    private TextView tCurrent;
   // private TextView tSavings;
    private TextView tInput;
    private Button tTransfer;
    String[] uAcocunt;
    String[] uAcocunt1;
    String uInput;
    String newInt;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //button to return to previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tCurrent = findViewById(R.id.tCurrent);
      //  tSavings = findViewById(R.id.transfer);
        tInput = findViewById(R.id.tInput);
        tTransfer = findViewById(R.id.tTransfer);

        db = new database(this);

        db.getUserTransfer();
        String account = db.getUserTransfer();
        String account2 = db.getUserTransfer1();
        uAcocunt = account.split(",");
        uAcocunt1 = account2.split(",");

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

        String fAccount = uAcocunt[Integer.parseInt(newInt)]+"\n"+"\n"+uAcocunt1[Integer.parseInt(newInt)];

        tCurrent.append(fAccount);


        tInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input of transfer amount
                uInput = tInput.getText().toString();
            }
        });



    }

    public String CtS(){
        String current =uAcocunt[Integer.parseInt(newInt)];
        String savings =uAcocunt1[Integer.parseInt(newInt)];
        int newCurrent = Integer.parseInt(current) - Integer.parseInt(uInput);
        int newSavings = Integer.parseInt(savings) + Integer.parseInt(uInput);

        return newCurrent+"\n"+"\n"+newSavings;
    }
    public String StC(){
        String current =uAcocunt[Integer.parseInt(newInt)];
        String savings =uAcocunt1[Integer.parseInt(newInt)];
        int newCurrent = Integer.parseInt(current) + Integer.parseInt(uInput);
        int newSavings = Integer.parseInt(savings) - Integer.parseInt(uInput);

        return newCurrent+"\n"+"\n"+newSavings;

    }

}