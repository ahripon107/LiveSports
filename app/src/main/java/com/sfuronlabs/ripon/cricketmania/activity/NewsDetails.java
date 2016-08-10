package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.R;

/**
 * Created by Ripon on 10/30/15.
 */
public class NewsDetails extends AppCompatActivity {
    TextView headline, date, details;
    Typeface banglafont;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetails);

        adView = (AdView) findViewById(R.id.adViewNewsDetails);
        headline = (TextView) findViewById(R.id.tvHL);
        date = (TextView) findViewById(R.id.tvDT);
        details = (TextView) findViewById(R.id.tvDTLS);

        banglafont = Typeface.createFromAsset(getApplicationContext()
                .getAssets(), "fonts/solaimanlipi.ttf");

        headline.setTypeface(banglafont);
        date.setTypeface(banglafont);
        details.setTypeface(banglafont);

        Intent intent = getIntent();
        String hl = intent.getStringExtra("headline");
        String dt = intent.getStringExtra("date");
        String dtls = intent.getStringExtra("details");

        headline.setText(hl);
        date.setText(dt);
        details.setText(dtls);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D3FA0144AD5EA91460638306E4CB0FB2").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
