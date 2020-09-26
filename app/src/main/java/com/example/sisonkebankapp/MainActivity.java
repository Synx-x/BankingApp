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

public class MainActivity extends AppCompatActivity {
    private EditText uiEmail;
    private EditText uiPassword;
    private Button uiLogin;
    private TextView uiRegister;
    boolean isValid = false;
    String fEmail;
    String fPwd;
    String inputEmail;
    String inputPassword;

    //sql object
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


    credentials credentials = new credentials("admin", "12345");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking xml layout to variables
        uiEmail = findViewById(R.id.loginEmail);
        uiPassword = findViewById(R.id.loginPassword);
        uiLogin = findViewById(R.id.login);
        uiRegister = findViewById(R.id.register);

        //sql database link
        sql=new database(MainActivity.this);

       fEmail = sql.getUserEmail();
       fPwd = sql.getUserPwd();

        //button redirects to the registration activity if user doesn't have account
        uiRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        uiLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting user input from the 2 input fields
                 inputEmail = uiEmail.getText().toString();
                 inputPassword = uiPassword.getText().toString();

                //validating inputs provided by a user
                if(inputEmail.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter all your details correctly.", Toast.LENGTH_SHORT).show();
                    //validates email is an actual email using regular expressions
                }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
                    Toast.makeText(MainActivity.this, "Please Enter a Valid Email Address.", Toast.LENGTH_SHORT).show();
                    //validates password with a regular expression that checks if its at least 5 characters long
                }else if(!PASSWORD_PATTERN.matcher(inputPassword).matches()){
                    Toast.makeText(MainActivity.this, "Please Enter a Password with at least 5 characters.", Toast.LENGTH_SHORT).show();
                }
                else {

                    isValid = validateLogin(inputEmail, inputPassword);
                    if(!isValid){
                       Toast.makeText(MainActivity.this, "Incorrect Login Details", Toast.LENGTH_SHORT).show();
                   //   Toast.makeText(MainActivity.this, fEmail+"1", Toast.LENGTH_SHORT).show();
                    //   Toast.makeText(MainActivity.this, fPwd+"2", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        //once login is successful, this intent redirects to the main page activity

                        //create intent
                        startActivity(new Intent(MainActivity.this, MainPageActivity.class));

                    }

                }
            }
        });
    }
    //validate credentials on database and checks if you have credentials
    private boolean validateLogin(String email, String password){

            if(email.equalsIgnoreCase(fEmail) && password.equals(fPwd)){
                return true;
            }

        return false;
    }

}