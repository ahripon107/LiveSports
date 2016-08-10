package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.model.Bowler;
import com.sfuronlabs.ripon.cricketmania.R;

import java.util.ArrayList;

/**
 * Created by Ripon on 3/25/16.
 */
public class BowlerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Bowler> bowlers;
    LayoutInflater layoutInflater;

    public BowlerAdapter(Context context, ArrayList<Bowler> bowlers)
    {
        this.context = context;
        this.bowlers = bowlers;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bowlers.size();
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
        if(convertView == null)
        {
            view = layoutInflater.inflate(R.layout.singlebowler,null);
        }

        TextView name = (TextView) view.findViewById(R.id.bowl_Name);
        TextView overs = (TextView) view.findViewById(R.id.bowl_overs);
        TextView maidens = (TextView) view.findViewById(R.id.bowl_maiden);
        TextView runs = (TextView) view.findViewById(R.id.bowl_runs);
        TextView wickets = (TextView) view.findViewById(R.id.bowl_wickets);
        TextView economy = (TextView) view.findViewById(R.id.bowl_economy);

        name.setText(bowlers.get(position).getName());
        overs.setText(bowlers.get(position).getOver());
        maidens.setText(bowlers.get(position).getMaiden());
        runs.setText(bowlers.get(position).getRun());
        wickets.setText(bowlers.get(position).getWicket());
        economy.setText(bowlers.get(position).getEconomy());

        return  view;
    }
}
