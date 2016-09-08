package com.sfuronlabs.ripon.cricketmania.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.MatchDetailsViewPagerAdapter;
import com.sfuronlabs.ripon.cricketmania.fragment.FragmentMatchSummary;
import com.sfuronlabs.ripon.cricketmania.fragment.FragmentScoreBoard;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.model.Summary;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * @author ripon
 */
public class ActivityMatchDetails extends AppCompatActivity {
    private Match liveMatch;
    private MatchDetailsViewPagerAdapter matchDetailsViewPagerAdapter;
    private ViewPager viewPager;
    private Gson gson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        this.liveMatch = (Match) getIntent().getSerializableExtra("match");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.viewPager.setOffscreenPageLimit(3);
        setupViewPage(this.viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(this.viewPager);
        gson = new Gson();
        sendRequestForLiveMatchDetails();

    }


    public final void setupViewPage(ViewPager viewPager) {
        this.matchDetailsViewPagerAdapter = new MatchDetailsViewPagerAdapter(getSupportFragmentManager());
        this.matchDetailsViewPagerAdapter.addFragment(new FragmentMatchSummary(), "Summary");
        this.matchDetailsViewPagerAdapter.addFragment(new FragmentScoreBoard(), "Score Board");
        viewPager.setAdapter(this.matchDetailsViewPagerAdapter);
    }

    private void sendRequestForLiveMatchDetails() {
        String url = "http://cricinfo-mukki.rhcloud.com/api/match/" + this.liveMatch.getMatchId();
        Log.d(Constants.TAG, url);

        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONObject sumry = response.getJSONObject("summary");
                    Summary summary = gson.fromJson(String.valueOf(sumry),Summary.class);
                    ((FragmentMatchSummary) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(0)).setMatchSummary(summary);

                    if (response.has("innings1")) {
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFirstInningsBattingList(response.getJSONObject("innings1").getJSONArray("batting"));
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFirstInningsBowlingList(response.getJSONObject("innings1").getJSONArray("bowling"));
                    }
                    if (response.has("innings2")) {
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setSecondInningsBattingList(response.getJSONObject("innings2").getJSONArray("batting"));
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setSecondInningsBowlingList(response.getJSONObject("innings2").getJSONArray("bowling"));
                    }
                    if (response.has("innings3")) {
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setThirdInningsBattingList(response.getJSONObject("innings3").getJSONArray("batting"));
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setThirdInningsBowlingList(response.getJSONObject("innings3").getJSONArray("bowling"));
                    }
                    if (response.has("innings4")) {
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFourthInningsBattingList(response.getJSONObject("innings4").getJSONArray("batting"));
                        ((FragmentScoreBoard)ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFourthInningsBowlingList(response.getJSONObject("innings4").getJSONArray("bowling"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ActivityMatchDetails.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}