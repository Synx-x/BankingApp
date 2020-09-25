package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
    database sql;



    public static credentials credentials;

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






        //button redirects to the login activity if user does have account
        rLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

        rCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user input of credentials
                String regFname = rFname.getText().toString();
                String regLname = rLname.getText().toString();
                String regEmail = rEmail.getText().toString();
                String regPassword = rPassword.getText().toString();
                String regMobile = rMobile.getText().toString();
             //   String regFemale = rFemale.getText().toString();
               // String regmale = rMale.getText().toString();

                if(validate(regFname,regLname,regEmail,regPassword,regMobile)){
                    sql.open();
                    sql.addUser(regFname, regLname,  regEmail, regPassword, regMobile);
                    sql.close();
                    //loads new credentials into constructor
                    credentials = new credentials(regEmail, regPassword);
                    //once credentials have been registered, this will redirect the user to the login page
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    //message saying registration was successful
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    //validates credentials against the required information in order to register an account
    private boolean validate(String name, String lname, String email, String password, String mobile){
        if(name.isEmpty() || lname.isEmpty() || email.isEmpty() || password.length() < 5 || mobile.length() < 10 ){
            Toast.makeText(this, "Enter your details correctly/n Password must be at least 8 characters /n Mobile Number must be 10 digits", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}