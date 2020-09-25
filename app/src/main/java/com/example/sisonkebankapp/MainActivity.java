package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText uiEmail;
    private EditText uiPassword;
    private Button uiLogin;
    private TextView uiRegister;
    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking xml layout to variables
        uiEmail = findViewById(R.id.loginEmail);
        uiPassword = findViewById(R.id.loginPassword);
        uiLogin = findViewById(R.id.login);
        uiRegister = findViewById(R.id.register);

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
                String inputName = uiEmail.getText().toString();
                String inputPassword = uiPassword.getText().toString();

                //validating inputs provided by a user
                if(inputName.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter all your details correctly.", Toast.LENGTH_SHORT).show();
                }else {

                    isValid = validateLogin(inputName, inputPassword);
                    if(!isValid){
                        Toast.makeText(MainActivity.this, "Incorrect Login Details", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        //once login is successful, this intent redirects to the main page activity

                        //create intent
                        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);

                        //start intent
                        startActivity(intent);

                    }

                }
            }
        });
    }
    //validate credentials on database
    private boolean validateLogin(String email, String password){
        if(email.equalsIgnoreCase(RegistrationActivity.credentials.getDbEmail()) && password.equalsIgnoreCase(RegistrationActivity.credentials.getDbPassword())){
            return true;
        }
        return false;
    }

}