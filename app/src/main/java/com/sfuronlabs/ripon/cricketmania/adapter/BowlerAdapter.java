package com.sfuronlabs.ripon.cricketmania.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.activity.PlayerProfileActivity;
import com.sfuronlabs.ripon.cricketmania.model.Bowler;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.FetchFromWeb;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * @author Ripon
 */
public class BowlerAdapter extends RecyclerView.Adapter<BowlerAdapter.BowlerViewHolder> {

    Context context;
    ArrayList<Bowler> bowlers;
    LayoutInflater layoutInflater;

    public BowlerAdapter(Context context, ArrayList<Bowler> bowlers) {
        this.context = context;
        this.bowlers = bowlers;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public BowlerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BowlerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singlebowler, parent, false));
    }

    @Override
    public void onBindViewHolder(BowlerViewHolder holder, final int position) {
        holder.name.setText(bowlers.get(position).getName());
        holder.overs.setText(bowlers.get(position).getOver());
        holder.maidens.setText(bowlers.get(position).getMaiden());
        holder.runs.setText(bowlers.get(position).getRun());
        holder.wickets.setText(bowlers.get(position).getWicket());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerProfileActivity.class);
                intent.putExtra("playerID", bowlers.get(position).getPlayerId());
                context.startActivity(intent);
            }
        });
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

        public BowlerViewHolder(View itemView) {
            super(itemView);

            name = ViewHolder.get(itemView, R.id.bowl_Name);
            overs = ViewHolder.get(itemView, R.id.bowl_overs);
            maidens = ViewHolder.get(itemView, R.id.bowl_maiden);
            runs = ViewHolder.get(itemView, R.id.bowl_runs);
            wickets = ViewHolder.get(itemView, R.id.bowl_wickets);
        }
    }
}
