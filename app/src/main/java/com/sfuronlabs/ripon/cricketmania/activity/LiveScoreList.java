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
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.FixtureAdapter;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Ripon on 11/23/15.
 */
public class LiveScoreList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Match> data;
    FixtureAdapter fixtureAdapter;
    AdView adView;
    ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixture);
        setTitle("LIVE SCORE");
        adView = (AdView) findViewById(R.id.adViewFixture);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        data = new ArrayList<>();
        urls = new ArrayList<>();
        fixtureAdapter = new FixtureAdapter(this, data,"livescore");
        recyclerView.setAdapter(fixtureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String url = "http://cricinfo-mukki.rhcloud.com/api/match/live";
        Log.d("ripon", url);

        final ProgressDialog progressDialog = new ProgressDialog(LiveScoreList.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.d("ripon", response.toString());
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        data.add(new Match(obj.getJSONObject("team1").getString("teamName"), obj.getJSONObject("team2").getString("teamName"),
                                obj.getString("matchDescription"), "","",""));
                    }
                    fixtureAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(LiveScoreList.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);

    }

}
