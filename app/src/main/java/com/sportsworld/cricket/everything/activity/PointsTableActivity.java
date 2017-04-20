package com.sportsworld.cricket.everything.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sportsworld.cricket.everything.R;
import com.sportsworld.cricket.everything.adapter.BasicListAdapter;
import com.sportsworld.cricket.everything.model.PointsTable;
import com.sportsworld.cricket.everything.model.PointsTableElement;
import com.sportsworld.cricket.everything.util.Constants;
import com.sportsworld.cricket.everything.util.FetchFromWeb;
import com.sportsworld.cricket.everything.util.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

/**
 * @author Ripon
 */
public class PointsTableActivity extends CommonAppCompatActivity {

    AdView adView;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<PointsTable> pointTables;
    ArrayList<PointsTableElement> pointTableElements;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);
        pointTables = new ArrayList<>();
        pointTableElements = new ArrayList<>();
        gson = new Gson();

        adView = (AdView) findViewById(R.id.adViewpointsTable);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        spinner = (Spinner) findViewById(R.id.spn_tournaments);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest);

        final List<String> categories = new ArrayList<String>();
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog progressDialog = new SpotsDialog(PointsTableActivity.this, R.style.Custom);
                progressDialog.show();
                progressDialog.setCancelable(true);
                FetchFromWeb.get(pointTables.get(position).getUrl(), null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        progressDialog.dismiss();
                        processData(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String url = Constants.POINT_TABLE_URL;
        Log.d(Constants.TAG, url);

        final AlertDialog progressDialog = new SpotsDialog(PointsTableActivity.this, R.style.Custom);
        progressDialog.show();
        progressDialog.setCancelable(true);
        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("pointsTable");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        pointTables.add(gson.fromJson(String.valueOf(jsonArray.get(i)), PointsTable.class));
                        categories.add(pointTables.get(i).getSeriesName());
                    }
                    dataAdapter.notifyDataSetChanged();

                    if (pointTables.size() > 0) {
                        final AlertDialog progressDialog = new SpotsDialog(PointsTableActivity.this, R.style.Custom);
                        progressDialog.show();
                        progressDialog.setCancelable(true);

                        FetchFromWeb.get(pointTables.get(0).getUrl(), null, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                progressDialog.dismiss();
                                processData(response);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                                progressDialog.dismiss();
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(Constants.TAG, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                progressDialog.dismiss();
            }
        });

    }

    public void processData(JSONObject jsonObject) {

        pointTableElements.clear();
        pointTableElements.add(new PointsTableElement("Team", "Played", "Wins", "Loss", "NR", "Pts", "NRR"));
        try {
            jsonObject = jsonObject.getJSONObject("group");
            Iterator<?> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                for (int i = 0; i < jsonArray.length(); i++) {
                    pointTableElements.add(gson.fromJson(String.valueOf(jsonArray.get(i)), PointsTableElement.class));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(new BasicListAdapter<PointsTableElement, PointsTableViewHolder>(pointTableElements) {
            @Override
            public PointsTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_points_table, parent, false);
                return new PointsTableViewHolder(view);
            }

            @Override
            public void onBindViewHolder(PointsTableViewHolder holder, int position) {
                holder.teamName.setText(pointTableElements.get(position).getTeamName());
                holder.played.setText(pointTableElements.get(position).getPlayed());
                holder.wins.setText(pointTableElements.get(position).getWon());
                holder.losses.setText(pointTableElements.get(position).getLost());
                holder.NR.setText(pointTableElements.get(position).getNoresults());
                holder.points.setText(pointTableElements.get(position).getPointsscored());
                holder.nrr.setText(pointTableElements.get(position).getRunrate());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class PointsTableViewHolder extends RecyclerView.ViewHolder {
        protected TextView teamName;
        protected TextView played;
        protected TextView wins;
        protected TextView losses;
        protected TextView NR;
        protected TextView points;
        protected TextView nrr;

        public PointsTableViewHolder(View itemView) {
            super(itemView);
            teamName = ViewHolder.get(itemView, R.id.tv_team_name);
            played = ViewHolder.get(itemView, R.id.tv_played);
            wins = ViewHolder.get(itemView, R.id.tv_wins);
            losses = ViewHolder.get(itemView, R.id.tv_loss);
            NR = ViewHolder.get(itemView, R.id.tv_nr);
            points = ViewHolder.get(itemView, R.id.tv_points);
            nrr = ViewHolder.get(itemView, R.id.tv_nrr);
        }
    }
}
