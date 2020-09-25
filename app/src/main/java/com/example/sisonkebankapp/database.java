package com.example.sisonkebankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    helper h;
    Context c;

    //stores all sqlite queries
    SQLiteDatabase s;

    public database(RegistrationActivity registrationActivity) {
        c = registrationActivity;

    }

    public database(MainPageActivity mainPageActivity) {
        c= mainPageActivity;
    }

    public void addUser(String regFname, String regLname, String regEmail, String regPassword, String regMobile) {
        ContentValues cv = new ContentValues();
        //maps data from registration activity to database
        cv.put(fName, regFname);
        cv.put(lName, regLname);
        cv.put(email, regEmail);
        cv.put(password, regPassword);
        cv.put(mobile, regMobile);

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

    public String get(){
        h = new helper(c);
        s = h.getReadableDatabase();
        String txt ="";
        String[] col={fName,lName};
        //fetches all data
        Cursor c = s.query(table, col, null, null, null, null, null);
        c.moveToFirst();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            txt = txt+c.getString(0)+" "+c.getString(1)+"\n";
        }

        return txt;
    }

    public class helper extends SQLiteOpenHelper{

        public helper( Context context) {
            super(context, database, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //creates the table for the registration page
            String query = "CREATE TABLE "+table+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+fName+" TEXT NOT NULL, "+lName+" TEXT NOT NULL, "+email+" TEXT NOT NULL, "+password+" TEXT NOT NULL, "+mobile+" INTEGER NOT NULL );";
            //executes the query
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
