package com.nerdytech.edittextdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Widgets
    EditText emailEditText,passwordEditText,usernameEditText;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connecting Widgets with its resource file
        emailEditText=findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        usernameEditText=findViewById(R.id.usernameEditText);
        sendBtn=findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEditText.getText().toString();
                String username=usernameEditText.getText().toString();
                String password=passwordEditText.getText().toString();

                Log.i("Email",email);
                Log.i("Username",username);
                Log.i("Password",password);
            }
        });
    }
}
