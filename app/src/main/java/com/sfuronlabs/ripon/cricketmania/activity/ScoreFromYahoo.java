package com.sfuronlabs.ripon.cricketmania.activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;


import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.ScoreFromYahooAdapter;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/16/15.
 */
public class ScoreFromYahoo extends AppCompatActivity  {

    ListView score;

    ScoreFromYahooAdapter scoreFromYahooAdapter;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorefromyahoo);
        data = new ArrayList<>();
        data.add("kisu nai");
        scoreFromYahooAdapter = new ScoreFromYahooAdapter(this, data);


        score = (ListView) findViewById(R.id.lvYahoo);
        score.setAdapter(scoreFromYahooAdapter);



        String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20cricket.upcoming_matches&format=json&diagnostics=true&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0&callback=";

        new DownloadTask().execute(url);


    }


    private class DownloadTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                String request =  HttpRequest.get(urls[0]).body();
                /*File file = null;
                if (request.ok()) {
                    file = File.createTempFile("download", ".tmp");
                    request.receive(file);
                    publishProgress(file.length());
                }*/
                return request;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }



        protected void onPostExecute(String req) {
            if (req != null)
            {



                try {
                    JSONObject jsonObject = new JSONObject(req);
                    //jsonObject = jsonObject.getJSONObject("JSON");
                    jsonObject = jsonObject.getJSONObject("query");
                    jsonObject = jsonObject.getJSONObject("results");
                    JSONArray jArray = jsonObject.getJSONArray("Match");
                    for (int i=0;i<jArray.length();i++)
                    {
                        JSONObject json_data = jArray.getJSONObject(i);
                        String seriesName = json_data.getString("series_name");
                        String matchNoOfSeries = json_data.getString("MatchNo");
                        JSONObject object = json_data.getJSONObject("Venue");
                        String venueName = object.getString("content");
                        JSONArray array = json_data.getJSONArray("Team");
                        JSONObject obj = array.getJSONObject(0);
                        String team1 = obj.getString("Team");
                        obj = array.getJSONObject(1);
                        String team2 = obj.getString("Team");
                        String startTime = json_data.getString("date_match_start");
                        data.add(seriesName+" "+matchNoOfSeries+" "+venueName+" "+team1+" "+team2+" "+startTime);
                    }

                    scoreFromYahooAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Log.d("MyApp", "Download failed");
        }
    }

}
