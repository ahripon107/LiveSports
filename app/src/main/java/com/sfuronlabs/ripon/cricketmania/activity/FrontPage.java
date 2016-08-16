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
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;
import com.sfuronlabs.ripon.cricketmania.adapter.OptionsAdapter;
import com.sfuronlabs.ripon.cricketmania.R;

/**
 * Created by Ripon on 11/5/15.
 */
public class FrontPage extends AppCompatActivity {

    AdView adView;

    String[] itemName = {"Live Streaming", "Live Score", "Highlights", "Fixture", "News", "Funny Cricket Trolls", "Team Profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        adView = (AdView) findViewById(R.id.adViewFPage);

        GridView gridView = (GridView) findViewById(R.id.gvFirstPage);
        OptionsAdapter optionsAdapter = new OptionsAdapter(this, itemName);
        gridView.setAdapter(optionsAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intent = new Intent(FrontPage.this, Highlights.class);
                    intent.putExtra("cause", "livestream");
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(FrontPage.this, LiveScoreList.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(FrontPage.this, Highlights.class);
                    intent.putExtra("cause", "highlights");
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(FrontPage.this, Fixture.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(FrontPage.this, CricketNewsListActivity.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(FrontPage.this,TrollPostListActivity.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(FrontPage.this, TeamProfile.class);
                    startActivity(intent);
                }
            }
        });
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }
}
