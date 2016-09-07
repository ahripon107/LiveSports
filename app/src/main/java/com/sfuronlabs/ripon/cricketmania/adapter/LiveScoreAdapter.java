package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.activity.ActivityMatchDetails;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amin on 9/7/16.
 */
public class LiveScoreAdapter extends RecyclerView.Adapter<LiveScoreAdapter.LiveScoreViewHolder> {

    Context context;
    ArrayList<Match> data;
    String source;

    public LiveScoreAdapter(Context context, ArrayList<Match> data,String source) {
        this.context = context;
        this.data = data;
        this.source = source;
    }

    @Override
    public LiveScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_match_item, parent, false);
        return new LiveScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveScoreViewHolder holder, final int position) {

        holder.textView.setText(data.get(position).getVenue());

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityMatchDetails.class);
                    intent.putExtra("match", data.get(position));
                }
            });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class LiveScoreViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public LiveScoreViewHolder(View itemView) {
            super(itemView);

            textView = ViewHolder.get(itemView, R.id.textView);
        }
    }

}
