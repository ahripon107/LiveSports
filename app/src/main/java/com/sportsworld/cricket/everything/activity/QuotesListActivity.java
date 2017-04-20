package com.sportsworld.cricket.everything.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sportsworld.cricket.everything.R;
import com.sportsworld.cricket.everything.adapter.BasicListAdapter;
import com.sportsworld.cricket.everything.model.CricketNews;
import com.sportsworld.cricket.everything.util.Constants;
import com.sportsworld.cricket.everything.util.FetchFromWeb;
import com.sportsworld.cricket.everything.util.ViewHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

/**
 * @author Ripon
 */

public class QuotesListActivity extends CommonAppCompatActivity {

    RecyclerView recyclerView;
    AdView adView;
    ArrayList<CricketNews> quotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adView = (AdView) findViewById(R.id.adViewNews);
        quotes = new ArrayList<>();

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest);

        recyclerView.setAdapter(new BasicListAdapter<CricketNews, QuotesViewHolder>(quotes) {
            @Override
            public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_quotes, parent, false);
                return new QuotesViewHolder(view);
            }

            @Override
            public void onBindViewHolder(QuotesViewHolder holder, int position) {
                holder.headline.setText(quotes.get(position).getTitle());
                holder.author.setText(Html.fromHtml(quotes.get(position).getThumburl()));

                holder.time.setVisibility(View.GONE);
                Picasso.with(QuotesListActivity.this)
                        .load(quotes.get(position).getLink())
                        .placeholder(R.drawable.default_image)
                        .into(holder.circleImageView);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://m.cricbuzz.com/cricbuzz-android/quotes";
        Log.d(Constants.TAG, url);

        final AlertDialog progressDialog = new SpotsDialog(QuotesListActivity.this, R.style.Custom);
        progressDialog.show();
        progressDialog.setCancelable(true);
        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("quotes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CricketNews cricketNews = new CricketNews("", jsonObject.getString("image"),
                                "", jsonObject.getString("comment"),
                                "", "banglanews", jsonObject.getString("context"));
                        quotes.add(cricketNews);
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
                Toast.makeText(QuotesListActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(QuotesListActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private static class QuotesViewHolder extends RecyclerView.ViewHolder {
        protected TextView headline;
        protected TextView author;
        protected TextView time;
        protected ImageView circleImageView;

        public QuotesViewHolder(View itemView) {
            super(itemView);

            headline = ViewHolder.get(itemView, R.id.tv_headline);
            author = ViewHolder.get(itemView, R.id.tv_author);
            time = ViewHolder.get(itemView, R.id.tv_times_ago);
            circleImageView = ViewHolder.get(itemView, R.id.civ_news_thumb);
        }
    }
}