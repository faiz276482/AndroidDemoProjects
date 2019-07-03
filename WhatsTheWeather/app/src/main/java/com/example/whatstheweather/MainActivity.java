package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {



    public class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try{
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();

                while (data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.i("JSON",s);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo=jsonObject.getString("weather");
                System.out.println("Weather content: "+weatherInfo);
                JSONArray arr=new JSONArray(weatherInfo);
                String a="";
                for (int i=0;i<arr.length();i++)
                {
                    JSONObject jsonPart=arr.getJSONObject(i);
                    a+=jsonPart.getString("main")+"\n";
                    a+=jsonPart.getString("description")+"\r\n";

                }
                if(!a.equals("")) {
                    resultTextView.setText(a);
                }
                else
                    resultTextView.setText("");

            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "City not recognized", Toast.LENGTH_SHORT).show();
                resultTextView.setText("");
            }

        }
    }

    TextView resultTextView;
    EditText cityEditText;


    public void getWeather(View view)
    {

        DownloadTask task=new DownloadTask();
        task.execute("https://openweathermap.org/data/2.5/weather?q="+cityEditText.getText().toString()+"&appid=b6907d289e10d714a6e88b30761fae22");
        InputMethodManager mgr=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityEditText.getWindowToken(),0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView=findViewById(R.id.resultTextView);
        cityEditText=findViewById(R.id.cityEditText);


    }
}