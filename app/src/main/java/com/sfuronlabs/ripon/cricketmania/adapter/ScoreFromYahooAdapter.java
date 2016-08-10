package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/16/15.
 */
public class ScoreFromYahooAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> itemName;

    LayoutInflater layoutInflater;


    public ScoreFromYahooAdapter(Context context, ArrayList<String> itemName)
    {
        this.context = context;
        this.itemName = itemName;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return itemName.size();
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
            view = layoutInflater.inflate(R.layout.singleyahoo,null);


        }

        TextView textView = (TextView) view.findViewById(R.id.tvYahoo);


        textView.setText(itemName.get(position));
        return view;
    }
}

