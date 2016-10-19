package com.androidfragmant.cricket.allabout.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidfragmant.cricket.allabout.R;
import com.androidfragmant.cricket.allabout.adapter.MatchDetailsViewPagerAdapter;
import com.androidfragmant.cricket.allabout.fragment.ChattingFragment;
import com.androidfragmant.cricket.allabout.fragment.LiveScoreFragment;
import com.androidfragmant.cricket.allabout.fragment.OpinionFragment;
import com.androidfragmant.cricket.allabout.util.Constants;
import com.androidfragmant.cricket.allabout.util.FetchFromWeb;
import com.batch.android.Batch;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

/**
 * @author Ripon 
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MatchDetailsViewPagerAdapter matchDetailsViewPagerAdapter;
    ViewPager viewPager;
    AdView adView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        adView = (AdView) findViewById(R.id.adViewFontPage);
        setSupportActionBar(toolbar);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9201945236996508/2250606872");

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).addTestDevice(Constants.XIAOMI_TEST_DEVICE)
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        this.viewPager = (ViewPager) findViewById(R.id.viewPager);

        this.viewPager.setOffscreenPageLimit(3);
        setupViewPage(this.viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(this.viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.openDrawer(Gravity.LEFT);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest1);


    }

    public final void setupViewPage(ViewPager viewPager) {
        this.matchDetailsViewPagerAdapter = new MatchDetailsViewPagerAdapter(getSupportFragmentManager());
        this.matchDetailsViewPagerAdapter.addFragment(new LiveScoreFragment(), "Live Score");
        this.matchDetailsViewPagerAdapter.addFragment(new ChattingFragment(), "Chatting");
        this.matchDetailsViewPagerAdapter.addFragment(new OpinionFragment(),"Discussion");
        viewPager.setAdapter(this.matchDetailsViewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_live_streaming) {
            String isAllowedUrl = Constants.ACCESS_CHECKER_URL;
            Log.d(Constants.TAG, isAllowedUrl);

            final AlertDialog progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestParams params = new RequestParams();
            params.add("key", "bl905577");

            FetchFromWeb.get(isAllowedUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getString("msg").equals("Successful")) {
                            String source = response.getJSONArray("content").getJSONObject(0).getString("livestream");
                            if (source.equals("true")) {
                                Intent intent = new Intent(MainActivity.this, Highlights.class);
                                intent.putExtra("cause", "livestream");
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.d(Constants.TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });

        } else if (id == R.id.nav_sports_news) {
            String isAllowedUrl = Constants.ACCESS_CHECKER_URL;
            Log.d(Constants.TAG, isAllowedUrl);

            final AlertDialog progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestParams params = new RequestParams();
            params.add("key", "bl905577");

            FetchFromWeb.get(isAllowedUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getString("msg").equals("Successful")) {
                            String source = response.getJSONArray("content").getJSONObject(0).getString("news");
                            if (source.equals("true")) {
                                Intent intent = new Intent(MainActivity.this, CricketNewsListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.d(Constants.TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });
        } else if (id == R.id.nav_highlights) {
            String isAllowedUrl = Constants.ACCESS_CHECKER_URL;
            Log.d(Constants.TAG, isAllowedUrl);

            final AlertDialog progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestParams params = new RequestParams();
            params.add("key", "bl905577");

            FetchFromWeb.get(isAllowedUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getString("msg").equals("Successful")) {
                            String source = response.getJSONArray("content").getJSONObject(0).getString("highlights");
                            if (source.equals("true")) {
                                Intent intent = new Intent(MainActivity.this, Highlights.class);
                                intent.putExtra("cause", "highlights");
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.d(Constants.TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });

        } else if (id == R.id.nav_fixture) {
            Intent intent = new Intent(MainActivity.this, FixtureActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_past_matches) {
            Intent intent = new Intent(MainActivity.this, PastMatchesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            String isAllowedUrl = Constants.ACCESS_CHECKER_URL;
            Log.d(Constants.TAG, isAllowedUrl);

            final AlertDialog progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestParams params = new RequestParams();
            params.add("key", "bl905577");

            FetchFromWeb.get(isAllowedUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getString("msg").equals("Successful")) {
                            String source = response.getJSONArray("content").getJSONObject(0).getString("gallery");
                            if (source.equals("true")) {
                                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.d(Constants.TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });


        } else if (id == R.id.nav_series_stats) {
            Intent intent = new Intent(MainActivity.this, SeriesStatsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ranking) {
            Intent intent = new Intent(MainActivity.this, RankingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_records) {
            Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_points_table) {
            Intent intent = new Intent(MainActivity.this, PointsTableActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_quotes) {
            String isAllowedUrl = Constants.ACCESS_CHECKER_URL;
            Log.d(Constants.TAG, isAllowedUrl);

            final AlertDialog progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestParams params = new RequestParams();
            params.add("key", "bl905577");

            FetchFromWeb.get(isAllowedUrl, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getString("msg").equals("Successful")) {
                            String source = response.getJSONArray("content").getJSONObject(0).getString("quotes");
                            if (source.equals("true")) {
                                Intent intent = new Intent(MainActivity.this, QuotesListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.d(Constants.TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });

        } else if (id == R.id.nav_team_profile) {
            Intent intent = new Intent(MainActivity.this, TeamProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_update_app) {
            String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Batch.onStart(this);
    }

    @Override
    protected void onStop() {
        Batch.onStop(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Batch.onDestroy(this);

        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }


}
