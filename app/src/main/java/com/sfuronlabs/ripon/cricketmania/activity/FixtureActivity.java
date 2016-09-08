package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;
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
@ContentView(R.layout.fixture)
public class FixtureActivity extends RoboAppCompatActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    ArrayList<Match> data;

    @InjectView(R.id.adViewFixture)
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setAdapter(new BasicListAdapter<Match, FixtureViewHolder>(data) {
            @Override
            public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match, parent, false);
                return new FixtureViewHolder(view);
            }

            @Override
            public void onBindViewHolder(FixtureViewHolder holder, int position) {
                Picasso.with(FixtureActivity.this)
                        .load(Constants.resolveLogo(data.get(position).getTeam1()))
                        .placeholder(R.drawable.bpl)
                        .into(holder.imgteam1);

                Picasso.with(FixtureActivity.this)
                        .load(Constants.resolveLogo(data.get(position).getTeam2()))
                        .placeholder(R.drawable.bpl)
                        .into(holder.imgteam2);

                holder.textteam1.setText(data.get(position).getTeam1());
                holder.textteam2.setText(data.get(position).getTeam2());
                holder.venue.setText(data.get(position).getVenue());

                String timeparts[] = data.get(position).getTime().split("T");
                holder.time.setText(timeparts[0] + "  " + timeparts[1]);
                holder.seriesName.setText(data.get(position).getSeriesName());
                holder.matchNo.setText(data.get(position).getMatchNo());

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = Constants.FIXTURE_URL;
        Log.d(Constants.TAG, url);


        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(FixtureActivity.this, "", "Loading. Please wait...", true);
        progressDialog.setCancelable(true);

        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    String team1, team2, venue, time, seriesName, matcNo;
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
                        Match match = new Match(team1, team2, venue, time, seriesName, matcNo, "");
                        data.add(match);
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
                Toast.makeText(FixtureActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);
    }

    private static class FixtureViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView imgteam1;
        protected CircleImageView imgteam2;
        protected TextView textteam1;
        protected TextView textteam2;
        protected TextView venue;
        protected TextView time;
        protected TextView seriesName;
        protected TextView matchNo;
        protected LinearLayout linearLayout;

        public FixtureViewHolder(View itemView) {
            super(itemView);

            imgteam1 = ViewHolder.get(itemView, R.id.civTeam1);
            imgteam2 = ViewHolder.get(itemView, R.id.civTeam2);
            textteam1 = ViewHolder.get(itemView, R.id.tvTeam1);
            textteam2 = ViewHolder.get(itemView, R.id.tvTeam2);
            venue = ViewHolder.get(itemView, R.id.tvVenue);
            time = ViewHolder.get(itemView, R.id.tvTime);
            seriesName = ViewHolder.get(itemView, R.id.tvSeriesname);
            matchNo = ViewHolder.get(itemView, R.id.tvMatchNo);
            linearLayout = ViewHolder.get(itemView, R.id.match_layout);
        }
    }
}
