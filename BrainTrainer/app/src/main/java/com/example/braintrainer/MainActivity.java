package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    ConstraintLayout addContraintLayout;

    public void start(View view)
    {
        goButton.setVisibility(View.INVISIBLE);
        addContraintLayout.setVisibility(View.VISIBLE);
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

        addContraintLayout=findViewById(R.id.addConstraintLayout);
        addContraintLayout.setVisibility(View.INVISIBLE);
    }
}
