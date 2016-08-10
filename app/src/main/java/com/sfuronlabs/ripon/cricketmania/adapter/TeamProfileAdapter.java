package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/17/15.
 */
public class TeamProfileAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> teamname;
    LayoutInflater layoutInflater;

    public TeamProfileAdapter(Context context, ArrayList<String> teamName)
    {
        this.context = context;
        this.teamname = teamName;

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return teamname.size();
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

        View view = null;
        view = convertView;
        if (convertView == null)
        {

            view = layoutInflater.inflate(R.layout.singleteam,null);

        }

        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.civTeams);
        TextView tname = (TextView) view.findViewById(R.id.tvTeamName);



        tname.setText(teamname.get(position));
        return view;
    }
}
