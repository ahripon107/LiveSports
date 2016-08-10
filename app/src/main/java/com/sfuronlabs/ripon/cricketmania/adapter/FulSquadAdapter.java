package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ripon on 10/29/15.
 */
public class FulSquadAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> players;
    ArrayList<String> pics;
    LayoutInflater layoutInflater;
    String name;

    public FulSquadAdapter(Context context, ArrayList<String> players,ArrayList<String> pics,String name)
    {
        this.context = context;
        this.players = players;
        this.pics = pics;
        this.name = name;
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
            view = layoutInflater.inflate(R.layout.singleteam,null);
        }
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.civTeams);
        TextView textView = (TextView) view.findViewById(R.id.tvTeamName);


        Picasso.with(context)
                .load("http://img.youtube.com/vi/"+pics.get(position)+"/0.jpg")
                .placeholder(R.drawable.bpl) // optional
                .into(circleImageView);
        textView.setText(players.get(position));
        return view;
    }
}
