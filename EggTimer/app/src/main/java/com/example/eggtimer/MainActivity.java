package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button goButton;
    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean flag=true;
    CountDownTimer countDownTimer;
    void format(int i)
    {
        int sec,min;
        String minText="",secText="";
        min =i/60;
        sec=i-(60*min);
        if(min>=0&&min<=9){
            minText="0"+min;
        }
        else
            minText=""+min;
        if(sec>=0&&sec<=9)
        {
            secText="0"+sec;
        }
        else
            secText=""+sec;
        timerTextView.setText(minText+":"+secText);
    }

    void startTimer(View view){



        if(flag==true)
        {
            goButton.setText("STOP");
            flag=false;
            timerSeekBar.setEnabled(false);
            countDownTimer=new CountDownTimer((long)timerSeekBar.getProgress()*1000+100,1000) {
                @Override
                public void onTick(long l) {
                    int i =(int)l/1000;
                    format(i);

                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mediaPlayer.start();
                    goButton.setText("GO");
                    flag=true;
                    countDownTimer.cancel();
                    timerSeekBar.setEnabled(true);
                    timerSeekBar.setProgress(30);
                    Toast.makeText(getApplicationContext(), "Yay! Timer Finished", Toast.LENGTH_SHORT).show();
                }
            }.start();
        }
        else
        {
            goButton.setText("GO");
            flag=true;
            countDownTimer.cancel();
            timerSeekBar.setEnabled(true);
            timerSeekBar.setProgress(30);


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar=findViewById(R.id.timerSeekBar);
        goButton=findViewById(R.id.goButton);
        timerTextView=findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerTextView.setText("00:30");

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                format(i);
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
