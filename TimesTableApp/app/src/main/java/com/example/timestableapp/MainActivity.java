package com.example.timestableapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView timesTableListView;
    TextView timesTableTextView;
    SeekBar timesTableSeekBar;

    void table(int i)
    {
        ArrayList<String> timesTable=new ArrayList<String>();
        for(int j=0;j<11;j++)
        {
            timesTable.add(i+"x"+j+"="+(i*j));

        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,timesTable);
        timesTableListView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timesTableTextView= (TextView)findViewById(R.id.timesTableTextView);
        timesTableListView = findViewById(R.id.timesTableListView);
        timesTableSeekBar=findViewById((R.id.timesTableSeekBar));

        timesTableSeekBar.setMax(20);
        int prog=10;
        timesTableSeekBar.setProgress(prog);
        table(prog);

        timesTableSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int s=i;
                if(s==0)
                {
                    s=1;
                }
                timesTableTextView.setText(s+" Times Table");
                table(i);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
