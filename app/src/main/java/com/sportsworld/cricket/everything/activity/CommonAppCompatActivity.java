package com.sportsworld.cricket.everything.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.sportsworld.cricket.everything.util.RoboAppCompatActivity;

/**
 * @author Ripon
 */

public class CommonAppCompatActivity extends RoboAppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
