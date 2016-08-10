package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;

import java.util.ArrayList;

/**
 * Created by Ripon on 10/30/15.
 */
public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> headlines;
    ArrayList<String> dates;
    LayoutInflater layoutInflater;
    Typeface banglafont;

    public NewsAdapter(Context context, ArrayList<String> headlines, ArrayList<String> dates)
    {
        this.context = context;
        this.headlines = headlines;
        this.dates = dates;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        banglafont = Typeface.createFromAsset(context.getAssets(), "fonts/solaimanlipi.ttf");
    }


    @Override
    public int getCount() {
        return headlines.size();
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
            view = layoutInflater.inflate(R.layout.singlenews,null);
        }
        TextView headline = (TextView) view.findViewById(R.id.tvHeadline);
        TextView date = (TextView) view.findViewById(R.id.tvDate);
        headline.setTypeface(banglafont);
        date.setTypeface(banglafont);
        headline.setText(headlines.get(position));
        date.setText(dates.get(position));
        return view;
    }
}
