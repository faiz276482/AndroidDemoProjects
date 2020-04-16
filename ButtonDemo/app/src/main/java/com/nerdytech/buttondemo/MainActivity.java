package com.nerdytech.buttondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //widgets
    Button LogBtn,LogBtn2;

    public void LogBtnOnPressed(View view){
        Log.i("LogBtn","Pressed!");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogBtn=findViewById(R.id.LogBtn);
        LogBtn2=findViewById(R.id.LogBtn2);
        LogBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LogBtn2","Pressed!");
            }
        });
    }
}
