package com.example.sqllitedatabasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            SQLiteDatabase sqLiteDatabase=this.openOrCreateDatabase("Events",MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("Create table If not exists  events (name varchar, year int(4))");
            sqLiteDatabase.execSQL("Insert into events (name,year) values('Milenium',2000)");
            sqLiteDatabase.execSQL("Insert into events (name,year) values('I started college',2017)");

            Cursor c=sqLiteDatabase.rawQuery("Select * from events",null);
            int eventIndex=c.getColumnIndex("name");
            int yearIndex=c.getColumnIndex("year");
            c.moveToFirst();
            while (c!=null)
            {
                Log.i("event",c.getString(eventIndex));
                Log.i("year",c.getString(yearIndex));
                c.moveToNext();
            }
            c.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
