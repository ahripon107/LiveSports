package com.sportsworld.cricket.everything.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sportsworld.cricket.everything.R;
import com.sportsworld.cricket.everything.fragment.BasicInfoFragment;
import com.sportsworld.cricket.everything.fragment.PlayersFragment;
import com.sportsworld.cricket.everything.fragment.RecordFragment;
import com.sportsworld.cricket.everything.util.Constants;

/**
 * @author Ripon
 */
public class TeamDetailsActivity extends CommonAppCompatActivity {

    private ViewPager mPager;
    private TeamDetailsPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private CharSequence Titles[] = {"BASIC INFO", "TEST RECORD", "ODI RECORD", "T20 RECORD", "PLAYERS"};
    private int NoOfTabs = 5;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamdetailsactivity);
        adView = (AdView) findViewById(R.id.adViewTeamProfieDetails);

        String data = getIntent().getStringExtra("data");
        mTabLayout = (TabLayout) findViewById(R.id.hoteltab_layout);
        mAdapter = new TeamDetailsPagerAdapter(getSupportFragmentManager(), Titles, NoOfTabs, data);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mPager);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }

    private class TeamDetailsPagerAdapter extends FragmentPagerAdapter {
        CharSequence[] Titles;
        int NoOfTabs;
        String data;


        public TeamDetailsPagerAdapter(FragmentManager fm, CharSequence[] Titles, int NoOfTabs, String data) {
            super(fm);
            this.Titles = Titles;
            this.NoOfTabs = NoOfTabs;
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return BasicInfoFragment.newInstanceOfDescriptionFragment(data);
            } else if (position == 1) {
                return RecordFragment.newInstanceOfRecordFragment(data, "Test");
            } else if (position == 2) {
                return RecordFragment.newInstanceOfRecordFragment(data, "ODI");
            } else if (position == 3) {
                return RecordFragment.newInstanceOfRecordFragment(data, "T20");
            } else {
                return PlayersFragment.newInstanceOfPlayersFragment(data);
            }

        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }
    }
}



