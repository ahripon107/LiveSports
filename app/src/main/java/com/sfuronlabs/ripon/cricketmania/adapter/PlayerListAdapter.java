package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.Player;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/16/15.
 */
public class PlayerListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Player> players;

    LayoutInflater layoutInflater;


    public PlayerListAdapter(Context context, ArrayList<Player> players)
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
        View view = convertView;
        if (convertView == null)
        {
            view = layoutInflater.inflate(R.layout.singleplayeroflist,null);
        }
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.civSinglePlayer);

        TextView pname = (TextView) view.findViewById(R.id.tvPlayerName);

        pname.setText(players.get(position).getName());
        return view;
    }
}
