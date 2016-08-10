package com.sfuronlabs.ripon.cricketmania.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;
import com.sfuronlabs.ripon.cricketmania.adapter.OptionsAdapter;
import com.sfuronlabs.ripon.cricketmania.R;

/**
 * Created by Ripon on 11/5/15.
 */
public class FrontPage extends AppCompatActivity {

    private ViewPager mPager;
    AdView adView;

    String[] itemName = {"Live Streaming","Live Score","Highlights","Fixture","Download Flash Player"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        adView = (AdView) findViewById(R.id.adViewFPage);

        GridView gridView = (GridView) findViewById(R.id.gvFirstPage);
        OptionsAdapter optionsAdapter = new OptionsAdapter(this,itemName);
        gridView.setAdapter(optionsAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4)
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://download.macromedia.com/pub/flashplayer/installers/archive/android/11.1.115.81/install_flash_player_ics.apk"));
                    startActivity(intent);
                    //String url = "http://download.macromedia.com/pub/flashplayer/installers/archive/android/11.1.115.81/install_flash_player_ics.apk";

                    //new DownloadFlashPlayer().execute(url);
                }
                else if (position == 3)
                {
                    Intent intent = new Intent(FrontPage.this,Fixture.class);
                    startActivity(intent);

                }
                /*else if (position == 1)
                {
                    Intent intent = new Intent(FrontPage.this,CricketNews.class);
                    startActivity(intent);
                }*/
                else if (position == 0)
                {
                    Intent intent = new Intent(FrontPage.this,Highlights.class);
                    intent.putExtra("cause","livestream");
                    startActivity(intent);
                }
                else if (position == 1)
                {

                    Intent intent = new Intent(FrontPage.this,LiveScoreList.class);
                    startActivity(intent);
                }
                else if (position == 2)
                {

                    Intent intent = new Intent(FrontPage.this,Highlights.class);
                    intent.putExtra("cause","highlights");
                    startActivity(intent);
                }


            }
        });
                //AdRequest adRequest = new AdRequest.Builder().addTestDevice("C1617AE650A2E5D8E50608A3970D2F26").build();

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("18D9D4FB40DF048C506091E42E0FDAFD").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    private class DownloadFlashPlayer extends AsyncTask<String, Long, String> {
        ProgressDialog progressDialog;


        protected String doInBackground(String... urls) {
            try {
                String request =  HttpRequest.get(urls[0]).body();

                return request;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

    }
}
