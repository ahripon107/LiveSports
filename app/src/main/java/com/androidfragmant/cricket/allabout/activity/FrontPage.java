package com.androidfragmant.cricket.allabout.activity;

import android.content.Context;
import android.content.Intent;;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.batch.android.Batch;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.androidfragmant.cricket.allabout.R;
import com.androidfragmant.cricket.allabout.adapter.SlideShowViewPagerAdapter;
import com.androidfragmant.cricket.allabout.util.Constants;
import com.androidfragmant.cricket.allabout.util.FetchFromWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * @author ripon
 */
public class FrontPage extends AppCompatActivity {

    Button cricketLive,cricketLiveScore,cricketHighlights,cricketFixture,cricketNews,trollPosts,teamProfile,pastMatches,rate,ranking,records,pointsTable;

    TextView[] dots1;

    ArrayList<String> imageUrls,texts;
    ViewPager viewPager;

    SlideShowViewPagerAdapter viewPagerAdapter;

    LinearLayout placeImageDotsLayout,cardContainer;

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
        pastMatches = (Button) findViewById(R.id.button_cricket_past_matches);
        rate = (Button) findViewById(R.id.button_rate);
        ranking = (Button) findViewById(R.id.button_ranking);
        records = (Button) findViewById(R.id.button_records);
        pointsTable = (Button) findViewById(R.id.button_points_table);

        viewPager = (ViewPager) findViewById(R.id.placeViewPagerImageSlideShow);
        placeImageDotsLayout = (LinearLayout) findViewById(R.id.placeImageDots);
        cardContainer = (LinearLayout) findViewById(R.id.placecardcontainer);

        imageUrls = new ArrayList<>();
        texts = new ArrayList<>();

        viewPagerAdapter = new SlideShowViewPagerAdapter(this,imageUrls,texts);
        viewPager.setAdapter(viewPagerAdapter);

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

        pastMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, PastMatchesActivity.class);
                startActivity(intent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, RankingActivity.class);
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, RecordsActivity.class);
                startActivity(intent);
            }
        });

        pointsTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPage.this, PointsTableActivity.class);
                startActivity(intent);
            }
        });

        String url = "https://skysportsapi.herokuapp.com/sky/getnews/cricket/v1.0/";
        Log.d(Constants.TAG, url);

        if (isNetworkAvailable()) {
            cardContainer.setVisibility(View.VISIBLE);
            FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    try {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            imageUrls.add(jsonObject.getString("imgsrc"));
                            texts.add(jsonObject.getString("title"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    viewPagerAdapter.notifyDataSetChanged();
                    addBottomDots(0);
                    Log.d(Constants.TAG, response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                }
            });
        } else {
            cardContainer.setVisibility(View.GONE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position1) {
                addBottomDots(position1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots1 = new TextView[imageUrls.size()];

        int colorsActive = getResources().getColor(R.color.DarkGreen);
        int colorsInactive = getResources().getColor(R.color.MediumSpringGreen);

        placeImageDotsLayout.removeAllViews();
        for (int i = 0; i < dots1.length; i++) {
            dots1[i] = new TextView(this);
            dots1[i].setText(Html.fromHtml("&#8226;"));
            dots1[i].setTextSize(35);
            dots1[i].setTextColor(colorsInactive);
            placeImageDotsLayout.addView(dots1[i]);
        }
        if (dots1.length > 0)
            dots1[currentPage].setTextColor(colorsActive);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Batch.onStart(this);
    }

    @Override
    protected void onStop()
    {
        Batch.onStop(this);

        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Batch.onDestroy(this);

        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }
}
