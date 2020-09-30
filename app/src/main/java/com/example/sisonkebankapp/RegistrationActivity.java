package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    //java variables for xml layout
    private EditText rFname;
    private EditText rLname;
    private EditText rPassword;
    private EditText rEmail;
    private EditText rMobile;
    //private RadioButton rMale;
    //private RadioButton rFemale;
    private Button rCreate;
    private TextView rLoginRedirect;

    String dbEmail;

    database sql;

    //regular expression to check password
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z0-9])" +      //any letter and digit
                    //  "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //   "(?=\\S+$)" +           //no white spaces
                    ".{5,20}" +               //at least 5 characters
                    "$");



    public static BankUser credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //associate xml layout variables with java variables
        rFname = findViewById(R.id.regFname);
        rLname = findViewById(R.id.regLname);
        rPassword = findViewById(R.id.regPwd);
        rEmail = findViewById(R.id.regEmail);
        rMobile = findViewById(R.id.regMob);
        //rMale = findViewById(R.id.male);
        //rFemale = findViewById(R.id.female);
        rCreate = findViewById(R.id.regCreate);
        rLoginRedirect = findViewById(R.id.redirect);

        //sql object
        sql=new database(RegistrationActivity.this);

          dbEmail = sql.getUserEmail();

        //button redirects to the login activity if user does have account
        rLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        rCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user input of BankUser
                String regFname = rFname.getText().toString().trim();
                String regLname = rLname.getText().toString().trim();
                String regEmail = rEmail.getText().toString().trim();
                String regPassword = rPassword.getText().toString().trim();
                String regMobile = rMobile.getText().toString().trim();
                String current = "5000";
                String savings = "8000";
               // String regFemale = rFemale.getText().toString();
               // String regmale = rMale.getText().toString();

                if(validate(regFname,regLname,regEmail,regPassword,regMobile)){
                    sql.open();
                    sql.addUser(regFname, regLname,  regEmail, regPassword, regMobile, current, savings);
                    sql.close();

                    //loads new BankUser into constructor
                    credentials = new BankUser(regEmail, regPassword);
                    credentials.setDbEmail(sql.getUserName());
                    credentials.setDbPassword(sql.getUserName());

                    //once BankUser have been registered, this will redirect the user to the login page
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();
                    //message saying registration was successful
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //validates BankUser against the required information in order to register an account
    private boolean validate(String name, String lname, String email, String password, String mobile){
        if(name.isEmpty() || lname.isEmpty()){
            Toast.makeText(this, "First Name or Last Name is Empty.", Toast.LENGTH_LONG).show();
            return false;
        }else if(email.isEmpty()){
            Toast.makeText(this, "Email is Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(RegistrationActivity.this, "Please Enter a Valid Email Address.", Toast.LENGTH_SHORT).show();
           return false;
        }else if(sql.checkIfExists(email)){
            Toast.makeText(this, "Email Already Exists. Enter a Different Email.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(mobile.isEmpty()){
            Toast.makeText(this, "Please Enter a Phone Number.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(mobile.length() <10 || mobile.length() >10){
            Toast.makeText(this, "Phone Number Must Be 10 Digits Long.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            Toast.makeText(RegistrationActivity.this, "Please Enter a Password With at Least 5 Characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}