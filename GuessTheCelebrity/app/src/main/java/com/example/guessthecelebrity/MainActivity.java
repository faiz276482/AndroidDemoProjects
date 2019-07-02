package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button optionButton1;
    Button optionButton2;
    Button optionButton3;
    Button optionButton4;
    ImageView imageView;
    ArrayList<String> celebUrls=new ArrayList<String>();
    ArrayList<String> celebList=new ArrayList<String>();
    ArrayList<String> answers;
    int chosenCeleb=0;
    int locationOfCorrectAnswer=0;


    public void chooseAnswer(View view)
    {
       // Log.i("Button pressed:",(view.getTag().toString()));
        if((Integer.parseInt(view.getTag().toString())==locationOfCorrectAnswer)){
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Inorrect!", Toast.LENGTH_SHORT).show();
        }

        newQs();

    }
    public class ImageDownloader extends AsyncTask<String,Void ,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in=connection.getInputStream();
                Bitmap myBitmap=BitmapFactory.decodeStream(in);
                return myBitmap;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            return null;
        }
    }

    public class DownloadTask extends AsyncTask<String,Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String result=null;
            URL url;
            HttpURLConnection urlConnection=null;

            try {
                url=new URL((urls[0]));
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data = reader.read();
                while(data !=-1){
                    char current=(char) data;
                    result=result+current;
                    data=reader.read();
                }
                return result;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void newQs()
    {
        Random rand=new Random();
        chosenCeleb=rand.nextInt(celebUrls.size());
        ImageDownloader imageTask=new ImageDownloader();
        try {
            imageView.setImageBitmap(imageTask.execute(celebUrls.get(chosenCeleb)).get());
            locationOfCorrectAnswer=chosenCeleb;
            answers=new ArrayList<String>();
            int right=rand.nextInt(4);
            for(int i=0; i<4;i++)
            {
                int wrong=0;
                if(i!=right)
                {
                    wrong=rand.nextInt(celebUrls.size());
                    while(wrong==locationOfCorrectAnswer){
                        wrong=rand.nextInt(celebUrls.size());
                    }
                    answers.add(celebList.get(wrong));
                }
                else {
                    answers.add(celebList.get(locationOfCorrectAnswer));
                }
            }
            locationOfCorrectAnswer=right;
            optionButton1.setText(answers.get(0));
            optionButton2.setText(answers.get(1));
            optionButton3.setText(answers.get(2));
            optionButton4.setText(answers.get(3));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        optionButton1=findViewById(R.id.optionButton1);
        optionButton2=findViewById(R.id.optionButton2);
        optionButton3=findViewById(R.id.optionButton3);
        optionButton4=findViewById(R.id.optionButton4);

        DownloadTask task=new DownloadTask();
        String result=null;
        try{
            result=task.execute("http://www.posh24.se/kandisar").get();
            //Log.i("Contents of Url:",result);
            String array[]=result.split("<div class=\"listedArticles\">");
            Pattern p=Pattern.compile("img src=\"(.*?)\"");
            Matcher m=p.matcher(array[0]);

            while(m.find())
            {
               // System.out.println(m.group(1));
                celebUrls.add(m.group(1));
            }
            p=Pattern.compile("alt=\"(.*?)\"");
            m=p.matcher(array[0]);

            while(m.find())
            {
                //System.out.println(m.group(1));
                celebList.add(m.group(1));
            }
            newQs();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}
