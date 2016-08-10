package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.RecordVsOthers;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/16/15.
 */
public class TeamRecordAdapter extends BaseAdapter {

    Context context;
    ArrayList<RecordVsOthers> players;
    LayoutInflater layoutInflater;

    public TeamRecordAdapter(Context context, ArrayList<RecordVsOthers> players)
    {
        this.context = context;
        this.players = players;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = convertView;
        if (convertView == null)
        {
            view = layoutInflater.inflate(R.layout.singleteamrecord,null);
        }

        TextView vsWhom = (TextView) view.findViewById(R.id.tvAgainst);
        TextView played = (TextView) view.findViewById(R.id.tvPlayed);
        TextView wins = (TextView) view.findViewById(R.id.tvWins);
        TextView loss = (TextView) view.findViewById(R.id.tvLoss);
        TextView draw = (TextView) view.findViewById(R.id.tvDraw);
        TextView highestInnings = (TextView) view.findViewById(R.id.tvHighestInnings);
        TextView BBI = (TextView) view.findViewById(R.id.tvBBI);
        TextView bestIndividual = (TextView) view.findViewById(R.id.tvBestIndividual);
        TextView mostWkts = (TextView) view.findViewById(R.id.tvMostWickets);
        TextView mostRuns = (TextView) view.findViewById(R.id.tvMostRuns);


        vsWhom.setText("Against "+players.get(position).getAgainst());
        played.setText("Played: "+players.get(position).getPlayed());
        wins.setText("Wins: "+players.get(position).getWins());
        loss.setText("Losses: "+players.get(position).getLoss());
        draw.setText("Tie/NR: "+players.get(position).getDraw());
        highestInnings.setText("Highest Innings: "+players.get(position).getHighestInnings());
        BBI.setText("Best Bowling: "+players.get(position).getBestBBI());
        bestIndividual.setText("Best Innings: "+players.get(position).getBestInning());
        mostWkts.setText("Most Wickets: "+players.get(position).getMaxWkts());
        mostRuns.setText("Most Runs: "+players.get(position).getMaxRuns());
        return view;
    }
}