package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.FixtureAdapter;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ripon on 11/23/15.
 */
public class LiveScoreList extends AppCompatActivity {

    ListView listView;
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
        listView = (ListView) findViewById(R.id.lvFixture);

        data = new ArrayList<>();
        urls = new ArrayList<>();
        fixtureAdapter = new FixtureAdapter(this,data);
        listView.setAdapter(fixtureAdapter);
        listView.getBackground().setAlpha(50);
        String url = "http://americadecides.xyz/cricket/livescore-api.php?key=bl905577";

        new LoadLiveScoreLinks().execute(url);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("18D9D4FB40DF048C506091E42E0FDAFD").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(LiveScoreList.this,LiveScore.class);
                Intent intent = new Intent(LiveScoreList.this,LiveScoreFromTotalCricinfo.class);

                //intent.putExtra("url",urls.get(position));
                startActivity(intent);
            }
        });
    }

    private class LoadLiveScoreLinks extends AsyncTask<String, Long, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog= ProgressDialog.show(LiveScoreList.this, "",
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

                try {
                    JSONObject jsonObject = new JSONObject(req);

                    JSONArray jsonArray = jsonObject.getJSONArray("fixtures");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        data.add(new Match(obj.getString("team1"),obj.getString("team2"),
                                obj.getString("venue"),obj.getString("time")));
                        urls.add(obj.getString("url"));



                    }



                    fixtureAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.d("MyApp", "Download failed");
                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();
            }

        }
    }
}
