package com.sfuronlabs.ripon.cricketmania.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.TeamProfileAdapter;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/17/15.
 */
public class TeamProfile extends AppCompatActivity {

    ArrayList<String> teams;
    TeamProfileAdapter teamProfileAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playersfragment);

        teams = new ArrayList<>();
        teams.add("Australia");
        teams.add("Bangladesh");
        teams.add("England");
        teams.add("India");
        teams.add("New Zealand");
        teams.add("Pakistan");
        teams.add("South Africa");
        teams.add("Sri Lanka");
        teams.add("West Indies");
        teams.add("Zimbabwe");
        teams.add("Bermuda");
        teams.add("Canada");
        teams.add("Ireland");
        teams.add("Kenya");
        teams.add("Netherlands");
        teams.add("Scotland");

        listView = (ListView) findViewById(R.id.lvPlayers);
        teamProfileAdapter = new TeamProfileAdapter(this,teams);
        listView.setAdapter(teamProfileAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20cricket.team.profile%20where%20team_id="+(position+1)+"&format=json&diagnostics=true&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0&callback=";
                new DownloadTask().execute(url);
            }
        });

    }

    private class DownloadTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                String request =  HttpRequest.get(urls[0]).body();

                return request;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }



        protected void onPostExecute(String req) {
            if (req != null)
            {
                Intent intent = new Intent(TeamProfile.this,TeamDetailsActivity.class);
                intent.putExtra("data",req);
                startActivity(intent);
            }
            else
                Log.d("MyApp", "Download failed");
        }
    }
}
