package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.fragment.LiveScoreBoardFragment;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;

/**
 * Created by Ripon on 3/22/16.
 */
public class LiveScoreFromTotalCricinfo extends AppCompatActivity {

    private ViewPager mPager;
    private LiveScorePagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private CharSequence Titles[] = {"TEAM 1", "TEAM 2"};
    private int NoOfTabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.livescorefromtotalcricinfo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        //String url = intent.getStringExtra("url");
        String url = "http://www.totalcricinfo.com/cricket/getFeed?type=scorecard&matchid=2981&scorecardformat=small";
        new LoadLiveScore().execute(url);


    }

    private class LoadLiveScore extends AsyncTask<String, Long, String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog= ProgressDialog.show(LiveScoreFromTotalCricinfo.this, "",
                    "Loading. Please wait...", true);
            progressDialog.setCancelable(true);
        }

        protected String doInBackground(String... urls) {
            try {
                String request =  HttpRequest.get(urls[0]).body();

                return request;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }



        protected void onPostExecute(String req) {
            progressDialog.dismiss();
            if (req != null)
            {

                mTabLayout = (TabLayout) findViewById(R.id.teamstab);
                mAdapter = new LiveScorePagerAdapter(getSupportFragmentManager(), Titles, NoOfTabs,req);
                mPager = (ViewPager) findViewById(R.id.vplivescore);
                mPager.setAdapter(mAdapter);

                mTabLayout.setTabsFromPagerAdapter(mAdapter);


                mTabLayout.setupWithViewPager(mPager);
            }
            else
            {
                Log.d("MyApp", "Download failed");
                Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
            }

        }
    }

}

class LiveScorePagerAdapter extends FragmentStatePagerAdapter {
    CharSequence[] Titles;
    int NoOfTabs;
    String data;



    public LiveScorePagerAdapter(FragmentManager fm, CharSequence[] Titles, int NoOfTabs, String data) {
        super(fm);
        this.Titles = Titles;
        this.NoOfTabs = NoOfTabs;
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            LiveScoreBoardFragment tab1 = LiveScoreBoardFragment.newInstanceofLiveScoreBoardFragment(data);
            return tab1;
        } else  {
            LiveScoreBoardFragment tab2 = LiveScoreBoardFragment.newInstanceofLiveScoreBoardFragment(data);
            return tab2;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


}

