package com.example.sisonkebankapp;

public class credentials {
    //variables used to validate users input
    private String dbEmail;
    private String dbPassword;

    credentials(String email, String password){
        this.dbEmail = email;
        this.dbPassword = password;
    }

    public String getDbEmail() {
        return dbEmail;
    }

    public void setDbEmail(String dbEmail) {
        this.dbEmail = dbEmail;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
