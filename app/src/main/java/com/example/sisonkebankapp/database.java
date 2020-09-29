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

    public void updateBalance(String regCurrent, String regSavings) {
        ContentValues cv = new ContentValues();
        //maps data from Transfer activity to database
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

            txt = txt+c.getString(0)+",";

            Scanner fromStr = new Scanner(txt);
            namePlate = fromStr.next();

        }

        return namePlate;
    }

    public String getUserEmail() {
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 = "";
        String txt2 = "";
        String[] col = {email, password};
        //fetches all data

        Cursor c = s.query(table, col, null, null, null, null, null);
        if (c.moveToFirst()) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                txt1 = txt1 + c.getString(0)+",";


                Scanner fromStr = new Scanner(txt1);

                gEmail = fromStr.next();

            }

            return gEmail;
        }else{
            gEmail ="";
            return gEmail;
        }
    }

    public boolean checkIfExists(String userEmail){



        String query = "select "+ email + " from " +table;
        Cursor cursor = s.rawQuery(query, null);
        String existEmail;

        if (cursor.moveToFirst()) {
            do {

                existEmail = cursor.getString(0);
                if (existEmail.equals(userEmail)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public String getUserPwd(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String[] col ={email, password};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        if(c.moveToFirst()) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {


                txt2 = txt2 + c.getString(1) + ",";

                Scanner fromStr = new Scanner(txt2);


                gPassword = fromStr.next();
            }

            return gPassword;
        }else{
            gPassword ="";
            return gPassword;
        }
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


            txt1 = txt1+c.getString(0)+",";


            Scanner fromStr = new Scanner(txt1);



            gBalance = "R"+fromStr.next();
        }

        return gBalance;
    }

    public String getUserTransfer1(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt1 ="";
        String txt2 ="";
        String[] col ={current, savings};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){


            txt1 = txt1+c.getString(1)+",";


            Scanner fromStr = new Scanner(txt1);



            gBalance = "R"+fromStr.next();
        }

        return gBalance;
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


            txt1 = txt1+c.getString(0)+",";


            Scanner fromStr = new Scanner(txt1);


            gBalance = fromStr.next()+"\n";
        }

        return gBalance;
    }
    public String getUserBalance1(){
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


            txt1 = txt1+c.getString(1)+",";


            Scanner fromStr = new Scanner(txt1);


            gBalance = fromStr.next()+"\n";
        }

        return gBalance;
    }
    public String getUserBalance2(){
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


            txt1 = txt1+c.getString(2)+",";


            Scanner fromStr = new Scanner(txt1);


            gBalance = fromStr.next()+"\n";
        }

        return gBalance;
    }
    public String getUserBalance3(){
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


            txt1 = txt1+c.getString(3)+",";


            Scanner fromStr = new Scanner(txt1);


            gBalance = fromStr.next()+"\n";
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
            String query = "CREATE TABLE "+table+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+fName+" TEXT NOT NULL, "+lName+" TEXT NOT NULL, "+email+" TEXT NOT NULL, "+password+" TEXT NOT NULL, "+mobile+" CHAR NOT NULL, "+current+" LONG NOT NULL, "+savings+" LONG NOT NULL );";
            //executes the query
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+table);
        }

    }


}
