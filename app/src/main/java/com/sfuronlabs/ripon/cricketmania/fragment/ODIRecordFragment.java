package com.sfuronlabs.ripon.cricketmania.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.RecordVsOthers;
import com.sfuronlabs.ripon.cricketmania.adapter.TeamRecordAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ripon on 3/8/16.
 */
public class ODIRecordFragment extends Fragment {
    ListView playerslist;
    ArrayList<RecordVsOthers> pl;
    TeamRecordAdapter teamRecordAdapter;

    public ODIRecordFragment()
    {
        //pl = new ArrayList<>();
    }

    public static ODIRecordFragment newInstanceOfODIRecordFragment(String text,String what)
    {
        ODIRecordFragment myFragment = new ODIRecordFragment();
        Bundle arguments = new Bundle();

        arguments.putString("details", text);
        arguments.putString("what", what);

        myFragment.setArguments(arguments);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.playersfragment,container,false);
        playerslist = (ListView) v.findViewById(R.id.lvPlayers);
        pl = new ArrayList<>();
        teamRecordAdapter = new TeamRecordAdapter(this.getActivity(),pl);
        playerslist.setAdapter(teamRecordAdapter);

        String string = getArguments().getString("details");
        String what = getArguments().getString("what");


        try {
            JSONObject jsonObject = new JSONObject(string);

            jsonObject = jsonObject.getJSONObject("query");
            jsonObject = jsonObject.getJSONObject("results");
            jsonObject = jsonObject.getJSONObject("TeamProfile");

            JSONArray jsonArray = jsonObject.getJSONArray("Tally");

            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("mtype").equals("ODI"))
                {
                    String against = obj.getString("Name");
                    String wins = obj.getString("Wins");
                    String lass = obj.getString("Losses");
                    String draw = obj.getString("NoResults");
                    String played = obj.getString("Played");
                    JSONObject obj1 = obj.getJSONObject("HighestInningSummary");
                    obj1 = obj1.getJSONObject("Score");
                    String highestInnings = obj1.getString("runs")+" / "+obj1.getString("wickets");

                    obj1 = obj.getJSONObject("Tops").getJSONObject("Bowling").getJSONObject("Player");
                    String as = obj1.getString("FirstName")+" "+obj1.getString("LastName")+" - "+obj.getJSONObject("Tops").getJSONObject("Bowling").getString("RunsGiven")+" / "+obj.getJSONObject("Tops").getJSONObject("Bowling").getString("WicketsTaken");

                    obj1 = obj.getJSONObject("Tops").getJSONObject("Batting").getJSONObject("Player");
                    String bs = obj1.getString("FirstName")+" "+obj1.getString("LastName")+" - "+obj.getJSONObject("Tops").getJSONObject("Batting").getJSONObject("RunsScored").getString("content")+" ("+obj.getJSONObject("Tops").getJSONObject("Batting").getString("BallsFaced")+")";

                    obj1 = obj.getJSONObject("Prolific").getJSONObject("Bowling").getJSONObject("Player");
                    String cs = obj1.getString("FirstName")+" "+obj1.getString("LastName")+" - "+obj.getJSONObject("Prolific").getJSONObject("Bowling").getString("WicketsTaken")+" wickets";

                    obj1 = obj.getJSONObject("Prolific").getJSONObject("Batting").getJSONObject("Player");
                    String ds = obj1.getString("FirstName")+" "+obj1.getString("LastName")+" - "+obj.getJSONObject("Prolific").getJSONObject("Batting").getString("RunsScored")+" runs";

                    RecordVsOthers recordVsOthers = new RecordVsOthers(against,played,wins,lass,draw,highestInnings,as,bs,cs,ds);
                    pl.add(recordVsOthers);
                }
            }
            Toast.makeText(getActivity(), "length " + jsonArray.length(), Toast.LENGTH_LONG).show();
            teamRecordAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }
}