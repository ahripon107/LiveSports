package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.adapter.FixtureAdapter;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ripon on 10/29/15.
 */
public class Fixture extends AppCompatActivity {

    ListView listView;
    ArrayList<Match> data;
    FixtureAdapter fixtureAdapter;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixture);
        setTitle("FIXTURE");
        adView = (AdView) findViewById(R.id.adViewFixture);
        listView = (ListView) findViewById(R.id.lvFixture);

        data = new ArrayList<>();
        fixtureAdapter = new FixtureAdapter(this,data);
        listView.setAdapter(fixtureAdapter);
        listView.getBackground().setAlpha(50);

        String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20cricket.upcoming_matches(0,25)&format=json&diagnostics=true&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0&callback=";

        new DownloadTask().execute(url);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("18D9D4FB40DF048C506091E42E0FDAFD").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    private class DownloadTask extends AsyncTask<String, Long, String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog= ProgressDialog.show(Fixture.this, "",
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
                String team1,team2,venue,time;
                try {
                    JSONObject jsonObject = new JSONObject(req);
                    jsonObject = jsonObject.getJSONObject("query").getJSONObject("results");
                    JSONArray jsonArray = jsonObject.getJSONArray("Match");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        JSONArray array = obj.getJSONArray("Team");

                        team1 = array.getJSONObject(0).getString("Team");
                        team2 = array.getJSONObject(1).getString("Team");

                        venue = obj.getJSONObject("Venue").getString("content");
                        time = obj.getString("StartDate");
                        Match match = new Match(team1,team2,venue,time);
                        data.add(match);

                    }
                    fixtureAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Log.d("MyApp", "Download failed");
        }
    }
}
