package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.inject.Inject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.adapter.BasicListAdapter;
import com.sfuronlabs.ripon.cricketmania.model.LivestreamAndHighlights;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;
import com.sfuronlabs.ripon.cricketmania.util.RoboAppCompatActivity;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;
import com.sfuronlabs.ripon.cricketmania.videoplayers.DMPlayerActivity;
import com.sfuronlabs.ripon.cricketmania.videoplayers.HighlightsVids;
import com.sfuronlabs.ripon.cricketmania.videoplayers.LiveStreamView;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.videoplayers.FrameStream;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author ripon
 */
@ContentView(R.layout.highlights)
public class Highlights extends RoboAppCompatActivity {

    @InjectView(R.id.lvHighlights)
    RecyclerView recyclerView;

    @InjectView(R.id.adViewHighlights)
    AdView adView;

    @Inject
    ArrayList<LivestreamAndHighlights> objects;

    String cause,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cause = getIntent().getStringExtra("cause");

        recyclerView.setAdapter(new BasicListAdapter<LivestreamAndHighlights,HighlightsViewHolder>(objects) {
            @Override
            public HighlightsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleteam,parent,false);
                return new HighlightsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(HighlightsViewHolder holder, final int position) {
                holder.teamName.setText(objects.get(position).getTitle());
                Picasso.with(Highlights.this)
                        .load("http://img.youtube.com/vi/" + objects.get(position).getUrl() + "/0.jpg")
                        .placeholder(R.drawable.default_image)
                        .into(holder.circleImageView);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (objects.get(position).getSource().equals("youtube")) {
                            Intent intent = new Intent(Highlights.this, HighlightsVids.class);
                            intent.putExtra("url", objects.get(position).getUrl());
                            startActivity(intent);
                        } else if (objects.get(position).getSource().equals("other")) {
                            Intent intent = new Intent(Highlights.this, FrameStream.class);
                            intent.putExtra("url", objects.get(position).getUrl());
                            startActivity(intent);
                        } else if (objects.get(position).getSource().equals("m3u8")) {
                            Intent intent = new Intent(Highlights.this, LiveStreamView.class);
                            intent.putExtra("url", objects.get(position).getUrl());
                            startActivity(intent);
                        } else if (objects.get(position).getSource().equals("dmplayer")) {
                            Intent intent = new Intent(Highlights.this, DMPlayerActivity.class);
                            intent.putExtra("url", objects.get(position).getUrl());
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (cause.equals("livestream")) {
            url = "http://americadecides.xyz/cricket/home-api.php?key=bl905577";
            fetchFromWeb(url);
            setTitle("LIVE STREAMING");
        } else {
            url = "http://americadecides.xyz/cricket/highlights-api.php?key=bl905577";
            fetchFromWeb(url);
            setTitle("HIGHLIGHTS");
        }
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }

    private static class HighlightsViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView circleImageView;
        protected TextView teamName;
        protected LinearLayout linearLayout;
        public HighlightsViewHolder(View itemView) {
            super(itemView);
            circleImageView = ViewHolder.get(itemView, R.id.civTeams);
            teamName = ViewHolder.get(itemView, R.id.tvTeamName);
            linearLayout = ViewHolder.get(itemView,R.id.linear_layout);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_load:
                fetchFromWeb(url);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchFromWeb(String url) {

        final ProgressDialog progressDialog = new ProgressDialog(Highlights.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                objects.clear();

                //parse json object here

                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(Highlights.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
