package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Ripon on 10/29/15.
 */
public class SquadAdapter extends BaseAdapter {

    Context context;
    String[] teamName;
    String[] imageID;
    LayoutInflater layoutInflater;

    public SquadAdapter(Context context, String[] itemName, String[] imageID)
    {
        this.context = context;
        this.teamName = itemName;
        this.imageID = imageID;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageID.length;
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
        TextView teamname = (TextView) view.findViewById(R.id.tvTeamName);

        Picasso.with(context)
                .load("http://vpn.gd/bpl/"+imageID[position]+".jpg")
                .placeholder(R.drawable.bpl) // optional
                .into(circleImageView);

        teamname.setText(teamName[position]);
        return view;
    }
}
