package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout goButton;
    ConstraintLayout addContraintLayout;
    TextView sumTextView;
    Button optionButton1;
    Button optionButton2;
    Button optionButton3;
    Button optionButton4;
    int locationOfCorrectAnswer;
    TextView correctTextView;
    ArrayList<Integer> answers;
    TextView scoreTextView;
    TextView timerTextView;
    Button mainMenuButton;
    Button playAgainButton;
    int score=0;
    int total=0;
    int tagOption=0;

    public void start(View view)
    {
        goButton.setVisibility(View.INVISIBLE);
        addContraintLayout.setVisibility(View.VISIBLE);
        tagOption=Integer.parseInt(view.getTag().toString());
        newQs(tagOption);
        playAgain(view);
    }

    public void playAgain(View view){
        score=0;
        total=0;
        timerTextView.setText("30 s");
        scoreTextView.setText("0/0");
        mainMenuButton.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        correctTextView.setText("");
        optionButton1.setEnabled(true);
        optionButton2.setEnabled(true);
        optionButton3.setEnabled(true);
        optionButton4.setEnabled(true);

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText((l/1000)+" s");
            }

            @Override
            public void onFinish() {
                correctTextView.setText("DONE!");
                mainMenuButton.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                optionButton1.setEnabled(false);
                optionButton2.setEnabled(false);
                optionButton3.setEnabled(false);
                optionButton4.setEnabled(false);
            }
        }.start();

    }

    public void newQs(int b)
    {
        if(b==0)
        {
            newAddQs();

        }
        else if(b==1)
        {
            newSubQs();
        }
        else if(b==2)
        {
            newMulQs();

        }
        else if(b==3)
        {
            newDivQs();
        }

    }

    public void newAddQs()
    {
        Random rand=new Random();
        int a=rand.nextInt(101);
        int b=rand.nextInt(101);
        sumTextView.setText(a+"+"+b+"=?");
        locationOfCorrectAnswer=rand.nextInt(4);
        answers=new ArrayList<Integer>();

        for(int i=0; i<4; i++)
        {
            if(i == locationOfCorrectAnswer)
            {
                answers.add((a+b));
            }
            else{
                int wrongAnswer=rand.nextInt(201);
                while(wrongAnswer==(a+b))
                {
                    wrongAnswer=rand.nextInt(201);
                }
                answers.add(wrongAnswer);
            }
        }
        optionButton1.setText(Integer.toString(answers.get(0)));
        optionButton2.setText(Integer.toString(answers.get(1)));
        optionButton3.setText(Integer.toString(answers.get(2)));
        optionButton4.setText(Integer.toString(answers.get(3)));

    }

    public void newSubQs()
    {
        Random rand=new Random();
        int a=rand.nextInt(101);
        int b=rand.nextInt(101);
        while(a<=b)
        {
            a=rand.nextInt(101);
            b=rand.nextInt(101);
        }
        sumTextView.setText(a+"-"+b+"=?");
        locationOfCorrectAnswer=rand.nextInt(4);
        answers=new ArrayList<Integer>();

        for(int i=0;i<4;i++)
        {
            if(i==locationOfCorrectAnswer)
            {
                answers.add((a-b));
            }
            else{
                int wrongAnswer=rand.nextInt(101);
                while(wrongAnswer==(a-b))
                {
                    wrongAnswer=rand.nextInt(101);
                }
                answers.add(wrongAnswer);
            }
        }
        optionButton1.setText(Integer.toString(answers.get(0)));
        optionButton2.setText(Integer.toString(answers.get(1)));
        optionButton3.setText(Integer.toString(answers.get(2)));
        optionButton4.setText(Integer.toString(answers.get(3)));

    }

    public void newMulQs()
    {
        Random rand=new Random();
        int a=rand.nextInt(21);
        int b=rand.nextInt(21);
        sumTextView.setText(a+"*"+b+"=?");
        locationOfCorrectAnswer=rand.nextInt(4);
        answers=new ArrayList<Integer>();

        for(int i=0;i<4;i++)
        {
            if(i==locationOfCorrectAnswer)
            {
                answers.add((a*b));
            }
            else{
                int wrongAnswer=rand.nextInt(401);
                while(wrongAnswer==(a*b))
                {
                    wrongAnswer=rand.nextInt(401);
                }
                answers.add(wrongAnswer);
            }
        }
        optionButton1.setText(Integer.toString(answers.get(0)));
        optionButton2.setText(Integer.toString(answers.get(1)));
        optionButton3.setText(Integer.toString(answers.get(2)));
        optionButton4.setText(Integer.toString(answers.get(3)));

    }

    public void newDivQs()
    {
        Random rand=new Random();
        int a=rand.nextInt(101);
        int b=rand.nextInt(101);
        while(true)
        {
            a=rand.nextInt(101);
            b=rand.nextInt(101);
            if(a>=b && a%b==0)
            {
                break;
            }

        }
        sumTextView.setText(a+"/"+b+"=?");
        locationOfCorrectAnswer=rand.nextInt(4);
        answers=new ArrayList<Integer>();

        for(int i=0;i<4;i++)
        {
            if(i==locationOfCorrectAnswer)
            {
                answers.add((a/b));
            }
            else{
                int wrongAnswer=rand.nextInt(101);
                while(wrongAnswer==(a/b))
                {
                    wrongAnswer=rand.nextInt(101);
                }
                answers.add(wrongAnswer);
            }
        }
        optionButton1.setText(Integer.toString(answers.get(0)));
        optionButton2.setText(Integer.toString(answers.get(1)));
        optionButton3.setText(Integer.toString(answers.get(2)));
        optionButton4.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view)
    {
        //Log.i("Button pressed",view.getTag().toString());
        if(Integer.parseInt(view.getTag().toString()) == locationOfCorrectAnswer)
        {
         correctTextView.setText("Correct!");
         score++;

        }
        else
        {
            correctTextView.setText("Incorrect");
        }
        total++;
        scoreTextView.setText(score+"/"+total);
        newQs(tagOption);

    }

    public void mainMenu(View view)
    {
        goButton.setVisibility(View.VISIBLE);
        addContraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton=findViewById(R.id.goButton);
        goButton.setVisibility(View.VISIBLE);
        sumTextView=findViewById(R.id.sumTextView);
        correctTextView=findViewById(R.id.correctTextView);
        scoreTextView=findViewById(R.id.scoreTextView);
        timerTextView=findViewById(R.id.timerTextView);
        mainMenuButton=findViewById(R.id.mainMenuButton);
        playAgainButton=findViewById(R.id.playAgainButton);

        optionButton1=findViewById(R.id.optionButton1);
        optionButton2=findViewById(R.id.optionButton2);
        optionButton3=findViewById(R.id.optionButton3);
        optionButton4=findViewById(R.id.optionButton4);

        addContraintLayout=findViewById(R.id.addConstraintLayout);
        addContraintLayout.setVisibility(View.INVISIBLE);
        scoreTextView.setText(score+"/"+total);
        correctTextView.setText("");




    }
}
