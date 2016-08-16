package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.TrollPostImageAdapter;
import com.sfuronlabs.ripon.cricketmania.model.TrollPost;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Ripon on 8/12/16.
 */
public class TrollPostListActivity extends AppCompatActivity {

    AdView adView;
    RecyclerView recyclerView;
    ArrayList<TrollPost> trollPosts;
    TrollPostImageAdapter trollPostImageAdapter;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gson = new Gson();
        adView = (AdView) findViewById(R.id.adViewFixture);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        trollPosts = new ArrayList<>();
        trollPostImageAdapter = new TrollPostImageAdapter(this,trollPosts);
        recyclerView.setAdapter(trollPostImageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://apisea.xyz/Cricket/apis/v1/FetchFunPosts.php?key=bl905577";
        Log.d(Constants.TAG, url);

        final ProgressDialog progressDialog;
        progressDialog= ProgressDialog.show(TrollPostListActivity.this, "", "Loading. Please wait...", true);
        progressDialog.setCancelable(true);

        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        TrollPost trollPost = gson.fromJson(String.valueOf(obj),TrollPost.class);
                        trollPosts.add(trollPost);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                trollPostImageAdapter.notifyDataSetChanged();
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(TrollPostListActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
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
