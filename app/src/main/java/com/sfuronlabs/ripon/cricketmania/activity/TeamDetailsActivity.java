package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.fragment.BasicInfoFragment;
import com.sfuronlabs.ripon.cricketmania.fragment.PlayersFragment;
import com.sfuronlabs.ripon.cricketmania.fragment.RecordFragment;

/**
 * @author Ripon
 */
public class TeamDetailsActivity extends AppCompatActivity{

    private ViewPager mPager;
    private YourPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private CharSequence Titles[] = {"BASIC INFO", "TEST RECORD", "ODI RECORD", "T20 RECORD", "PLAYERS"};
    private int NoOfTabs = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamdetailsactivity);

        String data = getIntent().getStringExtra("data");
        mTabLayout = (TabLayout) findViewById(R.id.hoteltab_layout);
        mAdapter = new YourPagerAdapter(getSupportFragmentManager(), Titles, NoOfTabs,data);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mPager);
    }
}


class YourPagerAdapter extends FragmentPagerAdapter {
    CharSequence[] Titles;
    int NoOfTabs;
    String data;



    public YourPagerAdapter(FragmentManager fm, CharSequence[] Titles, int NoOfTabs, String data) {
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
            return RecordFragment.newInstanceOfRecordFragment(data,"Test");
        } else if (position == 2) {
            return RecordFragment.newInstanceOfRecordFragment(data, "ODI");
        } else if (position == 3) {
            return RecordFragment.newInstanceOfRecordFragment(data, "T20");
        } else  {
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

