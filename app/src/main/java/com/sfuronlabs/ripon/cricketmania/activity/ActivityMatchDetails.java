package com.sfuronlabs.ripon.cricketmania.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.MatchDetailsViewPagerAdapter;
import com.sfuronlabs.ripon.cricketmania.fragment.FragmentMatchSummary;
import com.sfuronlabs.ripon.cricketmania.model.LiveMatch;
import com.sfuronlabs.ripon.cricketmania.model.Match;

import java.util.HashMap;

import javax.xml.transform.ErrorListener;

/**
 * Created by amin on 8/24/16.
 */
public class ActivityMatchDetails extends AppCompatActivity {
    private Match liveMatch;
    private MatchDetailsViewPagerAdapter matchDetailsViewPagerAdapter;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.liveMatch = (Match) getIntent().getSerializableExtra("match");
        setContentView((int) R.layout.activity_match_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.viewPager.setOffscreenPageLimit(3);
        setupViewPage(this.viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(this.viewPager);
        sendRequestForLiveMatchDetails();
    }


    public final void setupViewPage(ViewPager viewPager) {
        this.matchDetailsViewPagerAdapter = new MatchDetailsViewPagerAdapter(getSupportFragmentManager());
        this.matchDetailsViewPagerAdapter.addFragment(new FragmentMatchSummary(), "Summary");
        this.matchDetailsViewPagerAdapter.addFragment(new FragmentScoreBoard(), "Score Board");
        viewPager.setAdapter(this.matchDetailsViewPagerAdapter);
    }

    private void sendRequestForLiveMatchDetails() {
        Volley.newRequestQueue(getApplicationContext()).add(new CustomStringRequest(0, "http://cricinfo-mukki.rhcloud.com/api/match/" + this.liveMatch.getMatchId(), new HashMap(), new Listener<String>() {
            public void onResponse(String response) {
                Log.d(ActivityMatchDetails.this.getResources().getString(R.string.app_name), "live match details: " + response);
                try {
                    LiveMatchDetailsApi liveMatchDetailsApi = (LiveMatchDetailsApi) new GsonBuilder().create().fromJson(response, LiveMatchDetailsApi.class);
                    ((FragmentMatchSummary) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(0)).setMatchSummary(liveMatchDetailsApi.getSummary());
                    if (liveMatchDetailsApi.getFirstInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFirstInningsBattingList(liveMatchDetailsApi.getFirstInnings().getBatting());
                    }
                    if (liveMatchDetailsApi.getSecondInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setSecondInningsBattingList(liveMatchDetailsApi.getSecondInnings().getBatting());
                    }
                    if (liveMatchDetailsApi.getThirdInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setThirdInningsBattingList(liveMatchDetailsApi.getThirdInnings().getBatting());
                    }
                    if (liveMatchDetailsApi.getFourthInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFourthInningsBattingList(liveMatchDetailsApi.getFourthInnings().getBatting());
                    }
                    if (liveMatchDetailsApi.getFirstInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFirstInningsBowlingList(liveMatchDetailsApi.getFirstInnings().getBowling());
                    }
                    if (liveMatchDetailsApi.getSecondInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setSecondInningsBowlingList(liveMatchDetailsApi.getSecondInnings().getBowling());
                    }
                    if (liveMatchDetailsApi.getThirdInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setThirdInningsBowlingList(liveMatchDetailsApi.getThirdInnings().getBowling());
                    }
                    if (liveMatchDetailsApi.getFourthInnings() != null) {
                        ((FragmentScoreBoard) ActivityMatchDetails.this.matchDetailsViewPagerAdapter.getItem(1)).setFourthInningsBowlingList(liveMatchDetailsApi.getFourthInnings().getBowling());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d(ActivityMatchDetails.this.getResources().getString(R.string.app_name), "live: " + error.toString());
            }
        }));
    }
}