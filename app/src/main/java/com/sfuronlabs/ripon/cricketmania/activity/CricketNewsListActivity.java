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
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.adapter.NewsListAdapter;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.CricketNews;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Ripon on 11/6/15.
 */
public class CricketNewsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NewsListAdapter newsListAdapter;
    AdView adView;
    ArrayList<CricketNews> cricketNewses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        setTitle("CRICKET NEWS");
        adView = (AdView) findViewById(R.id.adViewNews);
        cricketNewses = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.rv_news);
        //allNews.getBackground().setAlpha(50);
        newsListAdapter = new NewsListAdapter(this,cricketNewses);
        recyclerView.setAdapter(newsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20cricket.news(0,50)%20where%20region=%22IN%22&format=json&diagnostics=true&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0&callback=";
        Log.d(Constants.TAG, url);

        final ProgressDialog progressDialog = new ProgressDialog(CricketNewsListActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    response = response.getJSONObject("query").getJSONObject("results");
                    JSONArray jsonArray = response.getJSONArray("item");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        CricketNews cricketNews = gson.fromJson(String.valueOf(jsonObject),CricketNews.class);
                        cricketNewses.add(cricketNews);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newsListAdapter.notifyDataSetChanged();
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(CricketNewsListActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D3FA0144AD5EA91460638306E4CB0FB2").build();
        adView.loadAd(adRequest);
    }
}
