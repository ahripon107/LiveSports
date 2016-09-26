package com.androidfragmant.cricket.allabout.cricbuzz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidfragmant.cricket.allabout.R;
import com.androidfragmant.cricket.allabout.activity.CricketNewsListActivity;
import com.androidfragmant.cricket.allabout.activity.NewsDetailsActivity;
import com.androidfragmant.cricket.allabout.adapter.BasicListAdapter;
import com.androidfragmant.cricket.allabout.model.CricketNews;
import com.androidfragmant.cricket.allabout.util.CircleImageView;
import com.androidfragmant.cricket.allabout.util.Constants;
import com.androidfragmant.cricket.allabout.util.FetchFromWeb;
import com.androidfragmant.cricket.allabout.util.RecyclerItemClickListener;
import com.androidfragmant.cricket.allabout.util.RoboAppCompatActivity;
import com.androidfragmant.cricket.allabout.util.ViewHolder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author Ripon
 */
@ContentView(R.layout.news)
public class CricbuzzNewsListActivity extends RoboAppCompatActivity{

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.adViewNews)
    AdView adView;

    @Inject
    ArrayList<CricketNews> cricketNewses;

    @Inject
    Gson gson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Latest News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(new BasicListAdapter<CricketNews,CricbuzzNewsListActivity.NewsViewHolder>(cricketNewses) {
            @Override
            public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenews, parent, false);
                return new NewsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(NewsViewHolder holder, final int position) {

                holder.headline.setText(cricketNewses.get(position).getTitle());
                holder.author.setText(cricketNewses.get(position).getPubDate());

                holder.time.setVisibility(View.GONE);
                Picasso.with(CricbuzzNewsListActivity.this)
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
                        Intent intent = new Intent(CricbuzzNewsListActivity.this, NewsDetailsActivity.class);
                        intent.putExtra(NewsDetailsActivity.EXTRA_NEWS_OBJECT, cricketNewses.get(position));
                        CricbuzzNewsListActivity.this.startActivity(intent);
                    }
                })
        );

        String url = "http://mapps.cricbuzz.com/cricbuzz-android/news/index";
        final String newsUrlFirstPart = "http://mapps.cricbuzz.com/cricbuzz-android/news/details-html/";

        final AlertDialog progressDialog = new SpotsDialog(CricbuzzNewsListActivity.this, R.style.Custom);
        progressDialog.show();
        progressDialog.setCancelable(true);
        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("stories");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CricketNews cricketNews = new CricketNews(jsonObject.getJSONObject("header").getString("author_name"),newsUrlFirstPart+jsonObject.getString("id"),
                                Constants.getTimeAgo(Long.parseLong(jsonObject.getJSONObject("header").getString("date"))),jsonObject.getString("hline"),jsonObject.getString("intro"),
                                jsonObject.getString("id"),jsonObject.getJSONObject("img").getString("ipath"));
                        cricketNewses.add(cricketNews);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(CricbuzzNewsListActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
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
