package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;
import com.sfuronlabs.ripon.cricketmania.R;

/**
 * @author ripon
 */
public class FrontPage extends AppCompatActivity {

    Button cricketLive,cricketLiveScore,cricketHighlights,cricketFixture,cricketNews,trollPosts,teamProfile,testbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        cricketLive = (Button) findViewById(R.id.button_cricket_live);
        cricketLiveScore = (Button) findViewById(R.id.button_cricket_live_score);
        cricketHighlights = (Button) findViewById(R.id.button_cricket_highlights);
        cricketFixture = (Button) findViewById(R.id.button_cricket_fixture);
        cricketNews = (Button) findViewById(R.id.button_cricket_news);
        trollPosts = (Button) findViewById(R.id.button_troll_posts);
        teamProfile = (Button) findViewById(R.id.button_team_profile);
        testbtn = (Button) findViewById(R.id.button_test);

        cricketLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, Highlights.class);
                intent.putExtra("cause", "livestream");
                startActivity(intent);
            }
        });

        cricketLiveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, LiveScoreListActivity.class);
                startActivity(intent);
            }
        });

        cricketHighlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, Highlights.class);
                intent.putExtra("cause", "highlights");
                startActivity(intent);
            }
        });

        cricketFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, FixtureActivity.class);
                startActivity(intent);
            }
        });

        cricketNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, CricketNewsListActivity.class);
                startActivity(intent);
            }
        });

        trollPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,TrollPostListActivity.class);
                startActivity(intent);
            }
        });

        teamProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, TeamProfile.class);
                startActivity(intent);
            }
        });

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this,TestActivity.class);
                startActivity(intent);
            }
        });
    }
}
