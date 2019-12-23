package com.nerdytech.ttscannerandadcatcherforgpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

public class TwentyTwenty extends AppCompatActivity {
    Random random=new Random();

    private AdView mAdView;
    String[] fonts={"font/almendra_sc.ttf","font/alex_brush.ttf","font/abril_fatface.ttf","font/eagle_lake.ttf","font/arapey_italic.ttf","font/bilbo_swash_caps.ttf","font/chelsea_market.ttf","font/redressed.ttf","font/qwigley.ttf","font/creepster.ttf"};
    String[] colors={"#FFAD05","#044389","#2D3047","#1B998B","#8E5572","#E34A6F","#053225","#FC814A","#004FFF","#E0A458"};
    TextView t20TextView;

    public void another(View view)
    {


        String choice=fonts[random.nextInt(10)];
        //Log.i("font",choice);
        try {
            t20TextView.setTextColor(Color.parseColor(colors[random.nextInt(10)]));
            Typeface face = Typeface.createFromAsset(getAssets(), choice);
            t20TextView.setTypeface(face);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            Log.i("font",choice);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_twenty);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent intent=getIntent();
        int choice=intent.getIntExtra("choice",0);
        //int choice=random.nextInt(11);
        t20TextView=findViewById(R.id.t20TextView);
        try {
            t20TextView.setTextColor(Color.parseColor(colors[random.nextInt(10)]));




            Typeface face = Typeface.createFromAsset(getAssets(), fonts[choice]);
            t20TextView.setTypeface(face);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            Log.i("font", fonts[choice]);
        }



    }


}
