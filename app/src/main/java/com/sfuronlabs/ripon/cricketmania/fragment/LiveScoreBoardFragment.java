package com.sfuronlabs.ripon.cricketmania.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.BatsmanAdapter;
import com.sfuronlabs.ripon.cricketmania.adapter.BowlerAdapter;
import com.sfuronlabs.ripon.cricketmania.adapter.CommentaryAdapter;
import com.sfuronlabs.ripon.cricketmania.model.Batsman;
import com.sfuronlabs.ripon.cricketmania.model.Bowler;
import com.sfuronlabs.ripon.cricketmania.util.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Ripon
 */
public class LiveScoreBoardFragment extends Fragment {
    ArrayList<Batsman> batsmans;
    ArrayList<Bowler> bowlers;
    ArrayList<String> commentary;
    BatsmanAdapter batsmanAdapter;
    BowlerAdapter bowlerAdapter;
    CommentaryAdapter commentaryAdapter;
    public LiveScoreBoardFragment()
    {

    }

    public static LiveScoreBoardFragment newInstanceofLiveScoreBoardFragment(String text)
    {
        LiveScoreBoardFragment liveScoreBoardFragment = new LiveScoreBoardFragment();
        Bundle arguments = new Bundle();
        arguments.putString("details", text);

        liveScoreBoardFragment.setArguments(arguments);
        return liveScoreBoardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.livescoreboard,container,false);

        batsmans = new ArrayList<>();
        batsmanAdapter = new BatsmanAdapter(this.getActivity(),batsmans);

        bowlers = new ArrayList<>();
        bowlerAdapter = new BowlerAdapter(this.getActivity(),bowlers);

        commentary = new ArrayList<>();
        commentaryAdapter = new CommentaryAdapter(this.getActivity(),commentary);

        TextView summaryMatchresult = (TextView) v.findViewById(R.id.summary_matchresult);
        ImageView team1Icon = (ImageView) v.findViewById(R.id.liveteamone_icon);
        TextView team1Score = (TextView) v.findViewById(R.id.team1sore);
        TextView currentRunRate = (TextView) v.findViewById(R.id.crunrate);
        ListView batsmanDetails = (ListView) v.findViewById(R.id.list_batsmandetails);

        //batsmanDetails.setAdapter(batsmanAdapter);

        ImageView team2Icon = (ImageView) v.findViewById(R.id.liveteamtwo_icon);
        TextView team2Score = (TextView) v.findViewById(R.id.team2sore);
        ListView bowlerDetails = (ListView) v.findViewById(R.id.list_bowlerdetails);

        //bowlerDetails.setAdapter(bowlerAdapter);


        ListView commentaryList = (ListView)v.findViewById(R.id.livecommentarylist);

        commentaryList.setAdapter(commentaryAdapter);
        String string = getArguments().getString("details");

        String commentaryUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20cricket.commentary%20where%20match_id=193316%20limit%201&format=json&diagnostics=true&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0&callback=";
        new LoadCommentary().execute(commentaryUrl);


        try
        {
            JSONObject jsonObject = new JSONObject(string);
            summaryMatchresult.setText(jsonObject.getJSONObject("match").getString("result"));

            JSONObject scr = jsonObject.getJSONArray("teams").getJSONObject(0);
            team1Score.setText(scr.getString("teamname")+" "+ scr.getString("score")+"/"+scr.getString("wickets")+"("+scr.getString("overs")+")");
            currentRunRate.setText("Run rate: "+scr.getString("runrate"));

            JSONObject obj = jsonObject.getJSONArray("batsman").getJSONObject(0);
            Batsman batsman = new Batsman("",obj.getString("name"),"",obj.getString("runs"),obj.getString("balls"),obj.getString("fours"),obj.getString("sixes"),"");
            batsmans.add(batsman);
            obj = jsonObject.getJSONArray("batsman").getJSONObject(1);
            batsman = new Batsman("",obj.getString("name"),"",obj.getString("runs"),obj.getString("balls"),obj.getString("fours"),obj.getString("sixes"),"");
            batsmans.add(batsman);

            batsmanAdapter.notifyDataSetChanged();

            obj = jsonObject.getJSONArray("bowler").getJSONObject(0);
            Bowler bowler = new Bowler("",obj.getString("name"),obj.getString("overs"),obj.getString("maiden"),obj.getString("runs"),obj.getString("wickets"));
            bowlers.add(bowler);
            obj = jsonObject.getJSONArray("bowler").getJSONObject(1);
            bowler = new Bowler("",obj.getString("name"),obj.getString("overs"),obj.getString("maiden"),obj.getString("runs"),obj.getString("wickets"));
            bowlers.add(bowler);



            bowlerAdapter.notifyDataSetChanged();


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
         return  v;
    }


    private class LoadCommentary extends AsyncTask<String, Long, String> {



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
                try {
                    JSONObject jsonObject = new JSONObject(req);
                    JSONArray jsonArray = jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("Over").getJSONArray("Ball");
                    Toast.makeText(getActivity(),""+jsonArray.length(),Toast.LENGTH_LONG).show();
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        commentary.add(obj.getString("c"));
                    }
                    Toast.makeText(getActivity(),""+commentary.size(),Toast.LENGTH_LONG).show();
                    commentaryAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Log.d("MyApp", "Download failed");
                Toast.makeText(LiveScoreBoardFragment.this.getActivity(), "Error occured", Toast.LENGTH_LONG).show();
            }

        }
    }


}
