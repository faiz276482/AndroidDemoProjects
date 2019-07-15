package com.example.newsshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles=new ArrayList<>();
    ArrayList<String> content=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    SQLiteDatabase articleDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        articleDb=this.openOrCreateDatabase("Articles",MODE_PRIVATE,null);

        articleDb.execSQL("Create table if not exists articles (id Integer Primary Key,articleId Integer,title varchar,content varchar)");



        DownloadTask task=new DownloadTask();
        try{
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");


        }
        catch (Exception e){
            e.printStackTrace();
        }

        arrayAdapter=new ArrayAdapter<String >(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(arrayAdapter);
        updateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                intent.putExtra("content",content.get(i));
                startActivity(intent);
            }
        });

    }

    public void updateListView()
    {
        Cursor c=articleDb.rawQuery("Select * from articles", null);
        int indexTitle=c.getColumnIndex("title");
        int indexContent=c.getColumnIndex("content");
        if(c.moveToFirst())
        {
            titles.clear();;
            //content.clear();
            do {
                titles.add(c.getString(indexTitle));
                //content.add(c.getString(indexContent));

            }while(c.moveToNext());
        }
        arrayAdapter.notifyDataSetChanged();
        if(c.moveToFirst())
        {

            content.clear();
            do {

                content.add(c.getString(indexContent));

            }while(c.moveToNext());
        }
    }

    public class DownloadTask extends AsyncTask<String,Void ,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try {
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                int data =inputStreamReader.read();
                while(data!=-1)
                {
                    char currentt=(char)data;
                    result+=currentt;
                    data=inputStreamReader.read();
                }
                Log.i("url content:",result);

                JSONArray jsonArray=new JSONArray(result);
                int numberOfItems=20;
                if(jsonArray.length()<20)
                {
                    numberOfItems=jsonArray.length();
                }
                articleDb.execSQL("delete from articles");

                for(int i =0;i<numberOfItems;i++)
                {
                    String articleId=jsonArray.getString(i);
                    //System.out.println(articleId);
                    url=new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty");
                    System.out.println(url);
                    urlConnection=(HttpURLConnection) url.openConnection();
                    inputStream=urlConnection.getInputStream();
                    inputStreamReader=new InputStreamReader(inputStream);
                    data=inputStreamReader.read();
                    String articleInfo="";
                    while(data !=-1)
                    {
                        char current=(char)data;
                        //System.out.println("current"+data1);
                        articleInfo +=current;
                        data=inputStreamReader.read();
                    }
                    //Log.i("Article Info:",articleInfo);

                    JSONObject jsonObject=new JSONObject(articleInfo);
                    String title="";
                    String urlA="";
                    if(!jsonObject.isNull("title")&& !jsonObject.isNull("url"))
                    {
                        title=jsonObject.getString("title");
                        urlA=jsonObject.getString("url");
                        Log.i("title anf url",title+"   "+urlA);

                        url=new URL(urlA);
                        urlConnection=(HttpURLConnection) url.openConnection();
                        inputStream=urlConnection.getInputStream();
                        inputStreamReader=new InputStreamReader(inputStream);
                        data=inputStreamReader.read();
                        String articleContent="";
                        while (data!=-1)
                        {
                            char current =(char) data;
                            articleContent=articleContent+current;
                            data=inputStreamReader.read();

                        }
                        Log.i("Article Content:",articleContent);
                        String sql="Insert into articles (articleId,title,content) values(?,?,?)";
                        SQLiteStatement statement=articleDb.compileStatement(sql);
                        statement.bindString(1,articleId);
                        statement.bindString(2,title);
                        statement.bindString(3,articleContent);
                        statement.execute();


                    }
                }

                Log.i("url content:",result);
                return result;

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }
}
