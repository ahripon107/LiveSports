package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.util.CircleImageView;
import com.sfuronlabs.ripon.cricketmania.model.Match;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.util.Constants;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ripon on 10/29/15.
 */
public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder> {

    Context context;
    ArrayList<Match> data;
    String source;

    public FixtureAdapter(Context context, ArrayList<Match> data,String source) {
        this.context = context;
        this.data = data;
        this.source = source;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match, parent, false);
        return new FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FixtureViewHolder holder, int position) {
        Picasso.with(context)
                .load(resolveLogo(data.get(position).getTeam1()))
                .placeholder(R.drawable.bpl)
                .into(holder.imgteam1);

        Picasso.with(context)
                .load(resolveLogo(data.get(position).getTeam2()))
                .placeholder(R.drawable.bpl)
                .into(holder.imgteam2);
        holder.textteam1.setText(data.get(position).getTeam1());
        holder.textteam2.setText(data.get(position).getTeam2());
        holder.venue.setText(data.get(position).getVenue());
        if (source.equals("fixture")) {
            String timeparts[] = data.get(position).getTime().split("T");
            holder.time.setText(timeparts[0]+"  "+timeparts[1]);
        } else {
            holder.time.setText(data.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class FixtureViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView imgteam1;
        protected CircleImageView imgteam2;
        protected TextView textteam1;
        protected TextView textteam2;
        protected TextView venue;
        protected TextView time;

        public FixtureViewHolder(View itemView) {
            super(itemView);

            imgteam1 = ViewHolder.get(itemView, R.id.civTeam1);
            imgteam2 = ViewHolder.get(itemView, R.id.civTeam2);
            textteam1 = ViewHolder.get(itemView, R.id.tvTeam1);
            textteam2 = ViewHolder.get(itemView, R.id.tvTeam2);
            venue = ViewHolder.get(itemView, R.id.tvVenue);
            time = ViewHolder.get(itemView, R.id.tvTime);
        }
    }

    public String resolveLogo(String teamName) {
        if (teamName.contains("Australia"))
            return Constants.AUS_TEAM_LOGO_URL;
        else if (teamName.contains("Bangladesh"))
            return Constants.BD_TEAM_LOGO_URL;
        else if (teamName.contains("England"))
            return Constants.ENG_TEAM_LOGO_URL;
        else if (teamName.contains("India"))
            return Constants.IND_TEAm_LOGO_URL;
        else if (teamName.contains("New Zealand"))
            return Constants.NZ_TEAM_LOGO_URL;
        else if (teamName.contains("Pakistan"))
            return Constants.PAK_TEAM_LOGO_URL;
        else if (teamName.contains("South Africa"))
            return Constants.SA_TEAM_LOGO_URL;
        else if (teamName.contains("Sri Lanka"))
            return Constants.SL_TEAM_LOGO_URL;
        else if (teamName.contains("West Indies"))
            return Constants.WI_TEAM_LOGO_URL;
        else if (teamName.contains("Zimbabwe"))
            return Constants.ZIM_TEAM_LOGO_URL;
        else if (teamName.contains("Bermuda"))
            return Constants.BER_TEAM_LOGO_URL;
        else if (teamName.contains("Canada"))
            return Constants.CAN_TEAM_LOGO_URL;
        else if (teamName.contains("Ireland"))
            return Constants.IRE_TEAM_LOGO_URL;
        else if (teamName.contains("Kenya"))
            return Constants.KEN_TEAM_LOGO_URL;
        else if (teamName.contains("Netherland"))
            return Constants.NED_TEAM_LOGO_URL;
        else if (teamName.contains("Scotland"))
            return Constants.SCO_TEAM_LOGO_URL;
        else return "n";
    }
}
