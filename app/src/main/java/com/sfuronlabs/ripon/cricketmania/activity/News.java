package com.sfuronlabs.ripon.cricketmania.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sfuronlabs.ripon.cricketmania.adapter.NewsAdapter;
import com.sfuronlabs.ripon.cricketmania.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ripon on 10/30/15.
 */
public class News extends AppCompatActivity {

    ListView allNews;
    NewsAdapter newsAdapter;
    ArrayList<String> headlines,dates,details;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        setTitle("BPL NEWS");
        adView = (AdView) findViewById(R.id.adViewNews);
        headlines = new ArrayList<>();
        dates = new ArrayList<>();
        details = new ArrayList<>();
        allNews = (ListView) findViewById(R.id.lvNews);
        allNews.getBackground().setAlpha(50);
        newsAdapter = new NewsAdapter(this,headlines,dates);
        allNews.setAdapter(newsAdapter);

        ParseQuery<ParseObject> parseObjectParseQuery = ParseQuery.getQuery("News");
        final ProgressDialog progressDialog= ProgressDialog.show(News.this, "",
                "Loading. Please wait...", true);
        progressDialog.setCancelable(true);
        parseObjectParseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                progressDialog.dismiss();
                if (e == null)
                {
                    for (int i=0;i<list.size();i++)
                    {
                        headlines.add((String)list.get(i).get("headline"));
                        dates.add((String)list.get(i).get("date"));
                        details.add((String)list.get(i).get("details"));
                    }
                    Collections.reverse(headlines);
                    Collections.reverse(dates);
                    Collections.reverse(details);
                    newsAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();
                }
            }
        });


        allNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(News.this,NewsDetails.class);
                intent.putExtra("headline",headlines.get(position));
                intent.putExtra("date",dates.get(position));
                intent.putExtra("details",details.get(position));
                startActivity(intent);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D3FA0144AD5EA91460638306E4CB0FB2").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
