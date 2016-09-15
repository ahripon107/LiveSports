package com.androidfragmant.cricket.allabout.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.androidfragmant.cricket.allabout.R;
import com.androidfragmant.cricket.allabout.activity.TeamDetailsActivity;
import com.androidfragmant.cricket.allabout.util.CircleImageView;
import com.androidfragmant.cricket.allabout.util.Constants;
import com.androidfragmant.cricket.allabout.util.FetchFromWeb;
import com.androidfragmant.cricket.allabout.util.ViewHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

/**
 * @author Ripon
 */
public class TeamProfileAdapter extends RecyclerView.Adapter<TeamProfileAdapter.TeamProfileViewHolder> {

    Context context;
    ArrayList<String> teamname;
    ArrayList<String> teamImage;

    public TeamProfileAdapter(Context context, ArrayList<String> teamName, ArrayList<String> teamImage) {
        this.context = context;
        this.teamname = teamName;
        this.teamImage = teamImage;
    }

    @Override
    public TeamProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleteam, parent, false);
        return new TeamProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamProfileViewHolder holder, final int position) {
        holder.tname.setText(teamname.get(position));
        Picasso.with(context)
                .load(teamImage.get(position))
                .placeholder(R.drawable.default_image)
                .into(holder.circleImageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20cricket.team.profile%20where%20team_id=" + (position + 1) + "&format=json&diagnostics=true&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0&callback=";

                final AlertDialog progressDialog = new SpotsDialog(context, R.style.Custom);
                progressDialog.show();
                progressDialog.setCancelable(true);

                FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, TeamDetailsActivity.class);
                        intent.putExtra("data", response.toString());
                        context.startActivity(intent);
                        Log.d(Constants.TAG, response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return teamname.size();
    }

    static class TeamProfileViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView circleImageView;
        protected TextView tname;
        protected LinearLayout linearLayout;

        public TeamProfileViewHolder(View itemView) {
            super(itemView);
            circleImageView = ViewHolder.get(itemView, R.id.civTeams);
            tname = ViewHolder.get(itemView, R.id.tvTeamName);
            linearLayout = ViewHolder.get(itemView,R.id.team_name_flag_container);
        }
    }
}
