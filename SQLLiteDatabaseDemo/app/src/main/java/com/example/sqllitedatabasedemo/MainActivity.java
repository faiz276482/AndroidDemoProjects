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
            SQLiteDatabase sqLiteDatabase=this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("Create table If not exists  newUsers (name varchar, year int(4),id Integer Primary Key)");
            sqLiteDatabase.execSQL("Insert into newUsers (name,age) values('Zaid',21)");
            sqLiteDatabase.execSQL("Insert into newUsers (name,age) values('Damini',22)");
            sqLiteDatabase.execSQL("Insert into newUsers (name,age) values('Faiz',22)");
            sqLiteDatabase.execSQL("Insert into newUsers (name,age) values('Anjali',21)");

            Cursor c=sqLiteDatabase.rawQuery("Select * from newUsers where name like '%a%'",null);
            int nameIndex=c.getColumnIndex("name");
            int ageIndex=c.getColumnIndex("age");
            int idIndex=c.getColumnIndex("id");
            c.moveToFirst();
            while (c!=null)
            {
                Log.i("User",c.getString(nameIndex));
                Log.i("age",c.getString(ageIndex));
                Log.i("ID",c.getString(idIndex));
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
