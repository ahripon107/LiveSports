package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.model.Bowler;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;

import java.util.ArrayList;

/**
 * Created by Ripon on 3/25/16.
 */
public class BowlerAdapter extends RecyclerView.Adapter<BowlerAdapter.BowlerViewHolder> {

    Context context;
    ArrayList<Bowler> bowlers;
    LayoutInflater layoutInflater;

    public BowlerAdapter(Context context, ArrayList<Bowler> bowlers)
    {
        this.context = context;
        this.bowlers = bowlers;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public BowlerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BowlerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singlebowler,parent,false));
    }

    @Override
    public void onBindViewHolder(BowlerViewHolder holder, int position) {
        holder.name.setText(bowlers.get(position).getName());
        holder.overs.setText(bowlers.get(position).getOver());
        holder.maidens.setText(bowlers.get(position).getMaiden());
        holder.runs.setText(bowlers.get(position).getRun());
        holder.wickets.setText(bowlers.get(position).getWicket());
        holder.economy.setText(bowlers.get(position).getEconomy());
    }


    @Override
    public int getItemCount() {
        return bowlers.size();
    }

    static class BowlerViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView overs;
        protected TextView maidens;
        protected TextView runs;
        protected TextView wickets;
        protected TextView economy;

        public BowlerViewHolder(View itemView) {
            super(itemView);

            name = ViewHolder.get(itemView,R.id.bowl_Name);
            overs = ViewHolder.get(itemView,R.id.bowl_Name);
            maidens = ViewHolder.get(itemView,R.id.bowl_Name);
            runs = ViewHolder.get(itemView,R.id.bowl_Name);
            wickets =ViewHolder.get(itemView,R.id.bowl_Name);
            economy = ViewHolder.get(itemView,R.id.bowl_Name);
        }
    }
}
