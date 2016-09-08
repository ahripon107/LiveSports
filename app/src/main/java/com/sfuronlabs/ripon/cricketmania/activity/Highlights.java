package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.videoplayers.HighlightsVids;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;
import com.sfuronlabs.ripon.cricketmania.videoplayers.LiveStreamView;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.FulSquadAdapter;
import com.sfuronlabs.ripon.cricketmania.videoplayers.FrameStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author ripon
 */
public class Highlights extends AppCompatActivity {

    ListView highlights;
    AdView adView;
    ArrayList<String> title,img,urls,source;
    FulSquadAdapter adapter;
    String cause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highlights);
        title = new ArrayList<>();
        img = new ArrayList<>();
        urls = new ArrayList<>();
        source = new ArrayList<>();

        Intent intent = getIntent();
        cause = intent.getStringExtra("cause");

        highlights = (ListView) findViewById(R.id.lvHighlights);
        highlights.getBackground().setAlpha(50);
        adView = (AdView) findViewById(R.id.adViewHighlights);

        adapter = new FulSquadAdapter(this,title,img,"name");
        highlights.setAdapter(adapter);

        if (cause.equals("livestream"))
        {
            String url = "http://americadecides.xyz/cricket/home-api.php?key=bl905577";

            new LoadLiveStreamLinks().execute(url);

            setTitle("LIVE STREAMING");
        }
        else
        {
            String url = "http://americadecides.xyz/cricket/highlights-api.php?key=bl905577";

            new LoadHighlightsLinks().execute(url);
            setTitle("HIGHLIGHTS");
        }





        highlights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (cause.equals("livestream"))
                {
                    if (source.get(position).equals("youtube"))
                    {
                        Intent intent = new Intent(Highlights.this,HighlightsVids.class);
                        intent.putExtra("url",urls.get(position));
                        startActivity(intent);
                    }
                    else if (source.get(position).equals("other"))
                    {
                        Intent intent = new Intent(Highlights.this,FrameStream.class);
                        intent.putExtra("url",urls.get(position));
                        startActivity(intent);
                    }
                    else if (source.get(position).equals("m3u8"))
                    {
                        Intent intent = new Intent(Highlights.this,LiveStreamView.class);
                        intent.putExtra("url",urls.get(position));
                        startActivity(intent);
                    }
                }
                else
                {
                    Intent intent = new Intent(Highlights.this,HighlightsVids.class);
                    intent.putExtra("url",urls.get(position));
                    startActivity(intent);
                }

            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE).build();
        adView.loadAd(adRequest);


    }

    private class LoadLiveStreamLinks extends AsyncTask<String, Long, String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog= ProgressDialog.show(Highlights.this, "",
                    "Loading. Please wait...", true);
            progressDialog.setCancelable(true);
        }

        protected String doInBackground(String... urls) {
            try {
                String request =  HttpRequest.get(urls[0]).body();

                return request;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }



        protected void onPostExecute(String req) {
            progressDialog.dismiss();
            if (req != null)
            {

                try {
                    JSONObject jsonObject = new JSONObject(req);

                    JSONArray jsonArray = jsonObject.getJSONArray("fixtures");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        title.add(obj.getString("title"));
                        img.add(obj.getString("url"));
                        urls.add(obj.getString("url"));
                        source.add(obj.getString("type"));


                    }


                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.d("MyApp", "Download failed");
                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();
            }

        }
    }


    private class LoadHighlightsLinks extends AsyncTask<String, Long, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog= ProgressDialog.show(Highlights.this, "",
                    "Loading. Please wait...", true);
            progressDialog.setCancelable(true);
        }

        protected String doInBackground(String... urls) {
            try {
                String request =  HttpRequest.get(urls[0]).body();

                return request;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }



        protected void onPostExecute(String req) {
            progressDialog.dismiss();
            if (req != null)
            {

                try {
                    JSONObject jsonObject = new JSONObject(req);

                    JSONArray jsonArray = jsonObject.getJSONArray("fixtures");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);


                        title.add(obj.getString("title"));
                        img.add(obj.getString("url"));
                        urls.add(obj.getString("url"));

                    }



                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.d("MyApp", "Download failed");
                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();
            }

        }
    }
}
