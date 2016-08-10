package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.fragment.BasicInfoFragment;
import com.sfuronlabs.ripon.cricketmania.fragment.ODIRecordFragment;
import com.sfuronlabs.ripon.cricketmania.fragment.PlayersFragment;
import com.sfuronlabs.ripon.cricketmania.fragment.RecordFragment;

/**
 * Created by Ripon on 12/16/15.
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

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");


        mTabLayout = (TabLayout) findViewById(R.id.hoteltab_layout);
        mAdapter = new YourPagerAdapter(getSupportFragmentManager(), Titles, NoOfTabs,data);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);

        mTabLayout.setTabsFromPagerAdapter(mAdapter);


        mTabLayout.setupWithViewPager(mPager);
    }
}


class YourPagerAdapter extends FragmentStatePagerAdapter {
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
            BasicInfoFragment tab1 = BasicInfoFragment.newInstanceOfDescriptionFragment(data);
            return tab1;
        } else if (position == 1) {
            RecordFragment tab2 = RecordFragment.newInstanceOfRecordFragment(data,"Test");
            return tab2;
        } else if (position == 2) {
            ODIRecordFragment tab3 = ODIRecordFragment.newInstanceOfODIRecordFragment(data, "ODI");
            return tab3;
        } else if (position == 3) {
            RecordFragment tab4 = RecordFragment.newInstanceOfRecordFragment(data, "T20");
            return tab4;
        } else  {
            PlayersFragment tab5 = PlayersFragment.newInstanceOfPlayersFragment(data);
            return tab5;
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

