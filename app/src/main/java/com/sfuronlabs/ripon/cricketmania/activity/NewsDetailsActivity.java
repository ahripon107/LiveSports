package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.CricketNews;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.RoboAppCompatActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author ripon
 */
@ContentView(R.layout.newsdetails)
public class NewsDetailsActivity extends RoboAppCompatActivity {

    public static final String EXTRA_NEWS_OBJECT = "newsobject";

    @InjectView(R.id.btn_details_news)
    Button detailsNews;

    @InjectView(R.id.text_view_headline)
    TextView headline;

    @InjectView(R.id.text_view_date)
    TextView date;

    @InjectView(R.id.text_view_author)
    TextView author;

    @InjectView(R.id.text_view_details)
    TextView details;

    @InjectView(R.id.adViewNewsDetails)
    AdView adView;

    CricketNews cricketNews;

    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cricketNews = (CricketNews) getIntent().getSerializableExtra(EXTRA_NEWS_OBJECT);

        typeface = Typeface.createFromAsset(getAssets(),Constants.TIMES_NEW_ROMAN_FONT);
        headline.setTypeface(typeface);
        date.setTypeface(typeface);
        author.setTypeface(typeface);
        details.setTypeface(typeface);
        headline.setText(cricketNews.getTitle());
        String arr[] = cricketNews.getPubDate().split("T");
        date.setText(arr[0]+" "+arr[1]);
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
