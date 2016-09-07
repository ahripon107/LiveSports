package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.adapter.FixtureAdapter;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Ripon on 10/29/15.
 */
public class Fixture extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Match> data;
    FixtureAdapter fixtureAdapter;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixture);
        setTitle("Fixture");
        adView = (AdView) findViewById(R.id.adViewFixture);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        data = new ArrayList<>();
        fixtureAdapter = new FixtureAdapter(this,data,"fixture");
        recyclerView.setAdapter(fixtureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = Constants.FIXTURE_URL;
        Log.d(Constants.TAG, url);


        final ProgressDialog progressDialog;
        progressDialog= ProgressDialog.show(Fixture.this, "", "Loading. Please wait...", true);
        progressDialog.setCancelable(true);

        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    String team1,team2,venue,time,seriesName,matcNo;
                    response = response.getJSONObject("query").getJSONObject("results");
                    JSONArray jsonArray = response.getJSONArray("Match");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONArray array = obj.getJSONArray("Team");
                        seriesName = obj.getString("series_name");
                        matcNo = obj.getString("MatchNo");

                        team1 = array.getJSONObject(0).getString("Team");
                        team2 = array.getJSONObject(1).getString("Team");

                        venue = obj.getJSONObject("Venue").getString("content");
                        time = obj.getString("StartDate");
                        Match match = new Match(team1,team2,venue,time,seriesName,matcNo,"");
                        data.add(match);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fixtureAdapter.notifyDataSetChanged();
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(Fixture.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }
}
