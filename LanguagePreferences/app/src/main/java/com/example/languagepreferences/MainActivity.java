package com.example.languagepreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SharedPreferences sharedPreferences;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.english:
                setLanguage("English");
                return true;
            case R.id.spanish:
                setLanguage("Spanish");
                return true;
            default:
                return false;
        }
    }

    public void setLanguage(String language)
    {

        sharedPreferences.edit().putString("language",language).apply();
        textView.setText(language);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences("com.example.languagepreferences", Context.MODE_PRIVATE);
        String language=sharedPreferences.getString("language","Error");
        textView = findViewById(R.id.textView);

        if(language.equals("Error")) {


            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a language")
                    .setMessage("Which Language would you like to choose?")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("English");
                        }
                    })
                    .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("Spanish");
                        }
                    })
                    .show();
        }
        else{
            textView.setText(language);
        }
    }
}
