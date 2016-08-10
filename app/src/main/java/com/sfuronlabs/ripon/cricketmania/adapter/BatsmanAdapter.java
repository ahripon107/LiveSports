package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.model.Batsman;
import com.sfuronlabs.ripon.cricketmania.R;

import java.util.ArrayList;

/**
 * Created by Ripon on 3/25/16.
 */
public class BatsmanAdapter extends BaseAdapter {

    Context context;
    ArrayList<Batsman> batsmans;
    LayoutInflater layoutInflater;

    public BatsmanAdapter(Context context, ArrayList<Batsman> batsmans)
    {
        this.context = context;
        this.batsmans = batsmans;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return batsmans.size();
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
            view = layoutInflater.inflate(R.layout.singlebatsman,null);
        }

        TextView name = (TextView) view.findViewById(R.id.batsmenName);
        TextView runs = (TextView) view.findViewById(R.id.runs);
        TextView ball = (TextView) view.findViewById(R.id.balls);
        TextView fours = (TextView) view.findViewById(R.id.fours);
        TextView six = (TextView) view.findViewById(R.id.six);
        TextView out = (TextView) view.findViewById(R.id.desc_out);

        name.setText(batsmans.get(position).getName());
        runs.setText(batsmans.get(position).getRuns());
        ball.setText(batsmans.get(position).getBalls());
        fours.setText(batsmans.get(position).getFours());
        six.setText(batsmans.get(position).getSixes());
        out.setText(batsmans.get(position).getOut());



        return  view;
    }
}
