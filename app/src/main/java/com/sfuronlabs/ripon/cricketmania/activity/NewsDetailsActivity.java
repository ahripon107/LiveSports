package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.CricketNews;
import com.sfuronlabs.ripon.cricketmania.util.Constants;

/**
 * Created by Ripon on 10/30/15.
 */
public class NewsDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_NEWS_OBJECT = "newsobject";

    Button detailsNews;
    TextView headline, date,author, details;
    AdView adView;
    CricketNews cricketNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetails);

        adView = (AdView) findViewById(R.id.adViewNewsDetails);
        headline = (TextView) findViewById(R.id.text_view_headline);
        date = (TextView) findViewById(R.id.text_view_date);
        author = (TextView) findViewById(R.id.text_view_author);
        details = (TextView) findViewById(R.id.text_view_details);
        detailsNews = (Button) findViewById(R.id.btn_details_news);

        cricketNews = (CricketNews) getIntent().getSerializableExtra(EXTRA_NEWS_OBJECT);

        headline.setText(cricketNews.getTitle());
        date.setText(Constants.timestamp(cricketNews.getPubDate()));
        author.setText(cricketNews.getAuthor());
        details.setText(cricketNews.getDescription());

        detailsNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDetailsActivity.this,LiveScore.class);
                intent.putExtra(LiveScore.EXTRA_URL,cricketNews.getLink());
                startActivity(intent);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }
}
