package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;

import java.util.ArrayList;

/**
 * Created by Ripon on 3/25/16.
 */
public class CommentaryAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> commentaries;
    LayoutInflater layoutInflater;

    public CommentaryAdapter(Context context, ArrayList<String> commentaries)
    {
        this.context = context;
        this.commentaries = commentaries;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return commentaries.size();
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
            view = layoutInflater.inflate(R.layout.singlecommentary,null);
        }

        TextView c = (TextView) view.findViewById(R.id.live_commentary);
        c.setText(commentaries.get(position));

        return view;
    }
}
