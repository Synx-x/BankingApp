package com.example.sisonkebankapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database name
   public static final String database="db";
    //table name
   public static final String table = "users";


    //columns
    String id="id";
    String fName="firstName";
    String lName="lastName";
    String email="email";
    String password="password";
    String mobile="mobile";
    String current="current";
    String savings="savings";

    //constructor creates the database
    public DatabaseHelper(Context context) {
        super(context, database, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+table+"("+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+fName+" TEXT NOT NULL, "+lName+" TEXT NOT NULL, "+email+" TEXT NOT NULL, "+password+" TEXT NOT NULL, "+mobile+" INTEGER NOT NULL );");
    }

    public Cursor ViewData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select firstName, lastName, current, savings  from"+table, null);
        return res;
    }

    public String databaseToString(){
        String dbString="";
        SQLiteDatabase db = getWritableDatabase();

        String query = "select firstName, lastName, current, savings  from"+table;

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("firstName"))!=null){
                dbString+=c.getString(c.getColumnIndex("firstName"));
                dbString+="\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table);
        onCreate(db);
    }
}
