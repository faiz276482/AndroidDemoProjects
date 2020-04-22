package com.nerdytech.textviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //widgets
    TextView textView;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lets connect them with the layout elements
        textView=findViewById(R.id.textView);
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);

        //let's add an on click listener on the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need to check if the user name entered is non empty
                if(TextUtils.isEmpty(editText.getText())){
                    textView.setText("Please enter your name");
                    //This line will edit the existing text in the text view with the string being passed in the setText function
                }
                else{
                    textView.setText("Hi "+editText.getText()+"!");
                    //This will take the username from the edittext and replace it with a greeting message for the user.
                }
            }
        });
    }
}
