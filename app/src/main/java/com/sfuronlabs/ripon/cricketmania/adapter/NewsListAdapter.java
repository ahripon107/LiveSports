package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.CricketNews;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ripon on 10/30/15.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    Context context;
    ArrayList<CricketNews> cricketNewses;
    LayoutInflater layoutInflater;

    public NewsListAdapter(Context context, ArrayList<CricketNews> cricketNewses) {
        this.context = context;
        this.cricketNewses = cricketNewses;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlenews,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.headline.setText(cricketNewses.get(position).getTitle());
        holder.author.setText(cricketNewses.get(position).getAuthor());
        holder.time.setText(cricketNewses.get(position).getPubDate());
        Picasso.with(context)
                .load(cricketNewses.get(position).getThumburl())
                .placeholder(R.drawable.bpl)
                .into(holder.circleImageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return cricketNewses.size();
    }


    static class NewsViewHolder extends RecyclerView.ViewHolder {
        protected TextView headline;
        protected TextView author;
        protected TextView time;
        protected CircleImageView circleImageView;
        protected LinearLayout linearLayout;

        public NewsViewHolder(View itemView) {
            super(itemView);

            headline = ViewHolder.get(itemView,R.id.tv_headline);
            author = ViewHolder.get(itemView,R.id.tv_author);
            time = ViewHolder.get(itemView,R.id.tv_times_ago);
            circleImageView = ViewHolder.get(itemView,R.id.civ_news_thumb);
            linearLayout = ViewHolder.get(itemView,R.id.news_item_container);
        }
    }
}
