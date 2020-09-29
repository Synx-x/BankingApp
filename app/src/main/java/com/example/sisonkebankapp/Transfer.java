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

        String fAccount = uAcocunt[Integer.parseInt(newInt)]+"\n"+"\n"+uAcocunt1[Integer.parseInt(newInt)];

        tCurrent.append(fAccount);

/*

FIX, CAUSES CRASHING

        String conCatCtS = CtS();
        String conCatStC = StC();

        newCurrent = conCatCtS.split(",");
        newSavings = conCatStC.split(",");

        fCurrent = newCurrent[0];
        fSavings = newCurrent[1];
*/


        tTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input of transfer amount
                uInput = tInput.getText().toString();

                if(validate(fCurrent, fSavings)){
                    db.open();
                    db.updateBalance(fCurrent, fSavings);
                    db.close();

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

    public String CtS(){
        String current =uAcocunt[Integer.parseInt(newInt)];
        String savings =uAcocunt1[Integer.parseInt(newInt)];
        int newCurrent = Integer.parseInt(current) - Integer.parseInt(uInput);
        int newSavings = Integer.parseInt(savings) + Integer.parseInt(uInput);

        return newCurrent+","+newSavings;
    }
    public String StC(){
        String current =uAcocunt[Integer.parseInt(newInt)];
        String savings =uAcocunt1[Integer.parseInt(newInt)];
        int newCurrent = Integer.parseInt(current) + Integer.parseInt(uInput);
        int newSavings = Integer.parseInt(savings) - Integer.parseInt(uInput);

        return newCurrent+","+newSavings;

    }
    //validates user has sufficient funds before allowing a transaction
    private boolean validate(String current, String savings){
        if(Integer.parseInt(current) > Integer.parseInt(uInput) || Integer.parseInt(savings) > Integer.parseInt(uInput)){
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
             //   CtS();
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