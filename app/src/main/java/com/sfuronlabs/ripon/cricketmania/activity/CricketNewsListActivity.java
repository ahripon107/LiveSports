package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.adapter.BasicListAdapter;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.CricketNews;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;
import com.sfuronlabs.ripon.cricketmania.util.RecyclerItemClickListener;
import com.sfuronlabs.ripon.cricketmania.util.RoboAppCompatActivity;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author ripon
 */
@ContentView(R.layout.news)
public class CricketNewsListActivity extends RoboAppCompatActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.adViewNews)
    AdView adView;

    @Inject
    ArrayList<CricketNews> cricketNewses;

    @Inject
    Gson gson;

    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Cricket News");
        typeface = Typeface.createFromAsset(getAssets(),Constants.TIMES_NEW_ROMAN_FONT);

        recyclerView.setAdapter(new BasicListAdapter<CricketNews,NewsViewHolder>(cricketNewses) {
            @Override
            public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenews, parent, false);
                return new NewsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(NewsViewHolder holder, final int position) {
                holder.headline.setTypeface(typeface);
                holder.author.setTypeface(typeface);
                holder.time.setTypeface(typeface);

                holder.headline.setText(cricketNewses.get(position).getTitle());
                holder.author.setText(cricketNewses.get(position).getAuthor());

                String dateAndTime = cricketNewses.get(position).getPubDate();
                String arr[] = dateAndTime.split("T");
                holder.time.setText(arr[0] +" "+arr[1]);
                Picasso.with(CricketNewsListActivity.this)
                        .load(cricketNewses.get(position).getThumburl())
                        .placeholder(R.drawable.default_image)
                        .into(holder.circleImageView);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(CricketNewsListActivity.this, NewsDetailsActivity.class);
                        intent.putExtra(NewsDetailsActivity.EXTRA_NEWS_OBJECT, cricketNewses.get(position));
                        CricketNewsListActivity.this.startActivity(intent);
                    }
                })
        );

        String url = Constants.NEWS_URL;
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
                        CricketNews cricketNews = gson.fromJson(String.valueOf(jsonObject),CricketNews.class);
                        cricketNewses.add(cricketNews);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(CricketNewsListActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        protected TextView headline;
        protected TextView author;
        protected TextView time;
        protected CircleImageView circleImageView;

        public NewsViewHolder(View itemView) {
            super(itemView);

            headline = ViewHolder.get(itemView, R.id.tv_headline);
            author = ViewHolder.get(itemView, R.id.tv_author);
            time = ViewHolder.get(itemView, R.id.tv_times_ago);
            circleImageView = ViewHolder.get(itemView, R.id.civ_news_thumb);
        }
    }
}
