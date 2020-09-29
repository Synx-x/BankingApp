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
   // private TextView tSavings;
    private TextView tInput;
    private Button tTransfer;
    String[] uAcocunt;
    String[] uAcocunt1;
    String[] newCurrent;
    String[] newSavings;
    String fCurrent;
    String fSavings;
    int trCurrent;
    int trSavings;
    String newsC="";
    String newsS="";
    String uInput;
    String newInt;
    String sC;
    String sS;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Transaction, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
             sC = uAcocunt[Integer.parseInt(newInt)];
             sS = uAcocunt1[Integer.parseInt(newInt)];

        String str = sC;
        for(int i=1; i<str.length();i++) {
            char ch = str.charAt(i);
            // System.out.println("Character at "+i+" Position: "+ch);
          //  Toast.makeText(this, ch + "New Int", Toast.LENGTH_SHORT).show();
           newsC+=ch;

        }
        String str1 = sS;
        for(int i=1; i<str1.length();i++) {
            char ch = str1.charAt(i);
            // System.out.println("Character at "+i+" Position: "+ch);
            //  Toast.makeText(this, ch + "New Int", Toast.LENGTH_SHORT).show();
            newsS+=ch;

        }
        int intCurrent = Integer.parseInt(newsC);
        int intSavings = Integer.parseInt(newsS);
        Toast.makeText(this, intCurrent+ "Current"+intSavings+"Savings", Toast.LENGTH_SHORT).show();
        String fAccount = sC+"\n"+"\n"+sS;

        tCurrent.append(fAccount);

       final String stringCurrent = Integer.toString(intCurrent);
       final String stringSavings = Integer.toString(intSavings);


        tTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input of transfer amount
                uInput = tInput.getText().toString();

                if(validate(stringCurrent, stringSavings)){
                    db.open();
                 //   db.updateBalance(String.valueOf(trCurrent), String.valueOf(trSavings));
                  //  db.close();

                    Toast.makeText(Transfer.this, "Transfer completed Successfully", Toast.LENGTH_LONG).show();
                    //refreshes the page once the transaction is complete and loads the new balances
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);

                }
            }
        });



    }


    public int getNewCurrent(int current, int input){
        return current - input;
    }
    public int getNewSavings(int savings, int input){
        return savings - input;
    }
    public int transferToCurrent(int current, int input){
        return current + input;
    }
    public  int transferToSavings(int savings, int input){
        return  savings + input;
    }

    //validates user has sufficient funds before allowing a transaction
    private boolean validate(String current, String savings){
        if(Integer.parseInt(current) < Integer.parseInt(uInput) || Integer.parseInt(savings) < Integer.parseInt(uInput)){
            Toast.makeText(this, "Amount Specified Exceeds Amount Currently Available in Account.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        switch (selectedItem){
            case"Current to Savings":
             //deducts input amount from current
         //      int adjustedCurrent = getNewCurrent(Integer.parseInt(sC), Integer.parseInt(uInput));
          //     trCurrent = adjustedCurrent;
               //adds deducted amount to saving
          //     int adjustedSavings = transferToSavings(Integer.parseInt(sS), Integer.parseInt(uInput));
           //    trSavings = adjustedSavings;
                break;
            case "Savings to Current":
          //      StC();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}