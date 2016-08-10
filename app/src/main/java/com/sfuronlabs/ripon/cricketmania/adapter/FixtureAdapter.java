package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ripon on 10/29/15.
 */
public class FixtureAdapter extends BaseAdapter {

    Context context;
    ArrayList<Match> data;
    LayoutInflater layoutInflater;
    public FixtureAdapter(Context context, ArrayList<Match> data)
    {
        this.context = context;
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
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
            view = layoutInflater.inflate(R.layout.match,null);


        }
        CircleImageView imgteam1 = (CircleImageView) view.findViewById(R.id.civTeam1);
        CircleImageView imgteam2 = (CircleImageView) view.findViewById(R.id.civTeam2);
        TextView textteam1 = (TextView) view.findViewById(R.id.tvTeam1);
        TextView textteam2 = (TextView) view.findViewById(R.id.tvTeam2);
        TextView venue = (TextView)view.findViewById(R.id.tvVenue);
        TextView time = (TextView) view.findViewById(R.id.tvTime);
        /*TextView result = (TextView) view.findViewById(R.id.tvResult);*/

        Picasso.with(context)
                .load("http://americadecides.xyz/cricket/pics/"+data.get(position).getTeam1()+".jpg")
                .placeholder(R.drawable.bpl) // optional
                .into(imgteam1);

        Picasso.with(context)
                .load("http://americadecides.xyz/cricket/pics/"+data.get(position).getTeam2()+".jpg")
                .placeholder(R.drawable.bpl) // optional
                .into(imgteam2);
        textteam1.setText(data.get(position).getTeam1());
        textteam2.setText(data.get(position).getTeam2());
        venue.setText(data.get(position).getVenue());
        time.setText(data.get(position).getTime());
        //result.setText(data.get(position).getResult());
        return view;
    }
}
