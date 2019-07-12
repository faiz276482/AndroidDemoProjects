package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);

        /*ArrayList<String> friends=new ArrayList<String>();

        friends.add("Tony");
        friends.add("Steve");
        friends.add("Thor");
        friends.add("Bruce");

        try {
            sharedPreferences.edit().putString("friends",ObjectSerializer.serialize(friends)).apply();
            Log.i("friends",ObjectSerializer.serialize(friends));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        */
        ArrayList<String> newFriends=new ArrayList<>();

        try{
            newFriends=(ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("friends",ObjectSerializer.serialize(new ArrayList<>())));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i("Nefriends", String.valueOf(newFriends));

        //sharedPreferences.edit().putString("username","faiz").apply();
        //String username=sharedPreferences.getString("username","");
        //Log.i("Username:",username);
    }
}
