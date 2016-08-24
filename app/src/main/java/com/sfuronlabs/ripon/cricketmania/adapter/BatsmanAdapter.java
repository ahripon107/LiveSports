package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.model.Batsman;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;

import java.util.ArrayList;

/**
 * Created by Ripon on 3/25/16.
 */
public class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.BatsmanViewHolder> {

    Context context;
    ArrayList<Batsman> batsmans;

    public BatsmanAdapter(Context context, ArrayList<Batsman> batsmans) {
        this.context = context;
        this.batsmans = batsmans;
    }


    @Override
    public BatsmanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlebatsman, parent, false);
        return new BatsmanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BatsmanViewHolder holder, int position) {
        holder.name.setText(batsmans.get(position).getName());
        holder.runs.setText(batsmans.get(position).getRuns());
        holder.ball.setText(batsmans.get(position).getBalls());
        holder.fours.setText(batsmans.get(position).getFours());
        holder.six.setText(batsmans.get(position).getSixes());
        holder.out.setText(batsmans.get(position).getOut());
    }

    @Override
    public int getItemCount() {
        return batsmans.size();
    }

    static class BatsmanViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView runs;
        protected TextView ball;
        protected TextView fours;
        protected TextView six;
        protected TextView out;

        public BatsmanViewHolder(View itemView) {
            super(itemView);

            name = ViewHolder.get(itemView, R.id.batsmenName);
            runs = ViewHolder.get(itemView, R.id.runs);
            ball = ViewHolder.get(itemView, R.id.balls);
            fours = ViewHolder.get(itemView, R.id.fours);
            six = ViewHolder.get(itemView, R.id.six);
            out = ViewHolder.get(itemView, R.id.desc_out);
        }
    }
}
