package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Transfer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tCurrent;
    private TextView tInput;
    private Button tTransfer;

    String[] uAcocunt;
    String[] uAcocunt1;

    int trCurrent;
    int trSavings;

    int intCurrent;
    int intSavings;
    int intInput;

    String stringCurrent;
    String stringSavings;

    String newsC="";
    String newsS="";

    String uInput;

    static String newInt;

    String sC;
    String sS;

    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //creates the spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Transaction, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //button to return to previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tCurrent = findViewById(R.id.tCurrent);
        tInput = findViewById(R.id.tInput);
        tTransfer = findViewById(R.id.tTransfer);

        db = new database(this);

        db.getUserTransfer();
        String account = db.getUserTransfer();
        String account2 = db.getUserTransfer1();
        uAcocunt = account.split(",");
        uAcocunt1 = account2.split(",");

        //reads user id from secure file that's created to track the users id in the database
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
             sC = uAcocunt[Integer.parseInt(newInt)];
             sS = uAcocunt1[Integer.parseInt(newInt)];

        String str = sC;
        for(int i=1; i<str.length();i++) {
            char ch = str.charAt(i);

           newsC+=ch;

        }
        String str1 = sS;
        for(int i=1; i<str1.length();i++) {
            char ch = str1.charAt(i);

            newsS+=ch;

        }
         intCurrent =  Integer.parseInt(sC);
         intSavings = Integer.parseInt(sS);

        String fAccount = sC+"\n"+"\n"+sS;

        tCurrent.append(fAccount);

         stringCurrent = Integer.toString(intCurrent);
         stringSavings = Integer.toString(intSavings);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        switch (selectedItem){
            case"Current to Savings":
                //item 1 of spinner, performs current to savings transaction
                tTransfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tInput.getText().toString().equals("")) {
                            Toast.makeText(Transfer.this, "Cannot Perform a Transfer. \nEnter a Transfer Amount of at least 1.", Toast.LENGTH_LONG).show();
                        }else{

                            //get user input of transfer amount
                            uInput = tInput.getText().toString();
                            intInput = Integer.parseInt(uInput);

                            if(validate(stringCurrent, stringSavings)) {
                                trSavings = 0;
                                trCurrent = 0;
                                //deducts input amount from current
                                trCurrent = getNewCurrent(intCurrent, intInput);

                                //adds deducted amount to saving
                                trSavings = transferToSavings(intSavings, intInput);

                                String aCurrent = String.valueOf(trCurrent);
                                String aSavings = String.valueOf(trSavings);

                                db.open();
                                db.updateBalance(aCurrent, aSavings);
                                db.close();
                                Toast.makeText(Transfer.this, "Transfer Completed Successfully", Toast.LENGTH_LONG).show();
                                //refreshes the page once the transaction is complete and loads the new balances
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(Transfer.this, Transfer.class));
                                overridePendingTransition(0, 0);

                            }
                        }
                    }
                });
                break;
            case "Savings to Current":

                //item 2 of spinner, performs savings to current transaction
                tTransfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tInput.getText().toString().equals("")) {
                            Toast.makeText(Transfer.this, "Cannot Perform a Transfer. \nEnter a Transfer Amount of at least 1.", Toast.LENGTH_LONG).show();
                        }else {

                            //get user input of transfer amount
                            uInput = tInput.getText().toString();
                            intInput = Integer.parseInt(uInput);

                            if (validate(stringCurrent, stringSavings)) {

                                //deducts input amount from savings account
                                trSavings = 0;
                                trCurrent = 0;
                                trSavings = getNewSavings(intSavings, intInput);
                                //adds deducted amount to current account
                                trCurrent = transferToCurrent(intCurrent, intInput);

                                String aCurrent = String.valueOf(trCurrent);
                                String aSavings = String.valueOf(trSavings);

                                db.open();
                                db.updateBalance(aCurrent, aSavings);
                                db.close();

                                Toast.makeText(Transfer.this, "Transfer Completed Successfully", Toast.LENGTH_LONG).show();
                                //refreshes the page once the transaction is complete and loads the new balances
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(Transfer.this, Transfer.class));
                                overridePendingTransition(0, 0);
                            }
                        }
                    }
                });
                break;
        }
    }

    //4 methods for performing calculations for the transactions
    public int getNewCurrent(int current, int input){
                int sum=current-input;
        return sum;
    }
    public int getNewSavings(int savings, int input){
                int sum=savings-input;
        return sum;
    }
    public int transferToCurrent(int current, int input){
                int sum = current+input;
        return sum;
    }
    public  int transferToSavings(int savings, int input){
                int sum =savings+input;
        return  sum;
    }


    //validates user has sufficient funds before allowing a transaction
    private boolean validate(String current, String savings){
        if(Integer.parseInt(savings) < Integer.parseInt(uInput) && Integer.parseInt(current) < Integer.parseInt(uInput)){
            Toast.makeText(this, "Amount Specified Exceeds Amount Currently Available in Account.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}