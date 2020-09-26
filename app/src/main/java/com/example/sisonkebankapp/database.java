package com.example.sisonkebankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Scanner;

public class database {

    //database name
    String database="db";
    //table name
    String table = "users";
    //database version
    int version = 1;

    //columns
    String id="id";
    String fName="firstName";
    String lName="lastName";
    String email="email";
    String password="password";
    String mobile="mobile";
    String current="current";
    String savings="savings";
    public String gEmail;
    public String gPassword;
    public String namePlate;
    String gCurrent;
    String gSavings;
    String gBalance;
    helper h;
    Context c;

    //stores all sqlite queries
    SQLiteDatabase s;

    //obj
    MainActivity main = new MainActivity();

    public database(RegistrationActivity registrationActivity) {
        c = registrationActivity;

    }

    public database(MainPageActivity mainPageActivity) {
        c= mainPageActivity;
    }

    public database() {

    }

    public database(MainActivity mainActivity) {
        c= mainActivity;
    }

    public database(Transfer transfer) {
        c = transfer;
    }

    public database(ViewAccountBalanceActivity viewAccountBalanceActivity) {
        c = viewAccountBalanceActivity;
    }


    public void addUser(String regFname, String regLname, String regEmail, String regPassword, String regMobile, String regCurrent, String regSavings) {
        ContentValues cv = new ContentValues();
        //maps data from registration activity to database
        cv.put(fName, regFname);
        cv.put(lName, regLname);
        cv.put(email, regEmail);
        cv.put(password, regPassword);
        cv.put(mobile, regMobile);
        cv.put(current, regCurrent);
        cv.put(savings, regSavings);

        //puts data to table
        s.insert(table, null, cv);

    }

    public void open() {
        h = new helper(c);
        s=h.getWritableDatabase();
    }

    public void close() {
        s.close();
    }

    public String getUserName(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt ="";
        String[] col={fName,lName};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            txt = txt+c.getString(0)+" ";

            Scanner fromStr = new Scanner(txt);
            namePlate = fromStr.next();

        }

        return namePlate;
    }

    public String getUserEmail(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String[] col ={email, password};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            txt1 = txt1+c.getString(0)+" ";


            Scanner fromStr = new Scanner(txt1);

            gEmail = fromStr.next();

        }

        return gEmail;
    }

    public String getUserPwd(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String[] col ={email, password};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            txt2 = txt2+c.getString(1)+" ";

            Scanner fromStr = new Scanner(txt2);


            gPassword = fromStr.next();
        }

        return gPassword;
    }

    public String getUserTransfer(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String[] col ={current, savings};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            txt1 = txt1+c.getString(0)+" ";
            txt2 = txt2+c.getString(1)+" ";

            Scanner fromStr = new Scanner(txt1);
            Scanner fromStr2 = new Scanner(txt2);


            gBalance = "R"+fromStr.next()+"\n\n"+
                    "R"+fromStr2.next();
        }

        return gBalance;
    }

    public String getUserCurrent1(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String[] col ={savings, current};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            txt1 = txt1+c.getString(0)+" ";
            txt2 = txt2+c.getString(1)+" ";

            Scanner fromStr = new Scanner(txt2);
            Scanner fromStr2 = new Scanner(txt1);

            gCurrent = fromStr.next();
            gSavings = fromStr2.next();
        }

        return gSavings;
    }

    public String getUserBalance(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String txt3 ="";
        String txt4 ="";
        String[] col ={fName, lName, current, savings};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            txt1 = txt1+c.getString(0)+" ";
            txt2 = txt2+c.getString(1)+" ";
            txt3 = txt3+c.getString(2)+" ";
            txt4 = txt4+c.getString(3)+" ";

            Scanner fromStr = new Scanner(txt1);
            Scanner fromStr2 = new Scanner(txt2);
            Scanner fromStr3 = new Scanner(txt3);
            Scanner fromStr4 = new Scanner(txt4);

            gBalance = "Account Holder Name: "+fromStr.next()+"\n"+
                    "Account Holder Surname: "+fromStr2.next()+"\n"+
                    "Current Account Balance: R"+fromStr3.next()+"\n"+
                    "Savings Account Balance: R"+fromStr4.next();
        }

        return gBalance;
    }








    public class helper extends SQLiteOpenHelper{

        public helper( Context context) {
            super(context, database, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //creates the table for the registration page
            String query = "CREATE TABLE "+table+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+fName+" TEXT NOT NULL, "+lName+" TEXT NOT NULL, "+email+" TEXT NOT NULL, "+password+" TEXT NOT NULL, "+mobile+" INTEGER NOT NULL, "+current+" LONG NOT NULL, "+savings+" LONG NOT NULL );";
            //executes the query
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+table);
        }

    }


}
