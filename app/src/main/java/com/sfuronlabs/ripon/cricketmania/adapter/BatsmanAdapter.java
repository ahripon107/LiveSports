package com.sfuronlabs.ripon.cricketmania.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sfuronlabs.ripon.cricketmania.activity.PlayerProfileActivity;
import com.sfuronlabs.ripon.cricketmania.model.Batsman;
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
    public void onBindViewHolder(BatsmanViewHolder holder, final int position) {
        holder.name.setText(batsmans.get(position).getName());
        holder.runs.setText(batsmans.get(position).getRuns());
        holder.ball.setText(batsmans.get(position).getBalls());
        holder.fours.setText(batsmans.get(position).getFours());
        holder.six.setText(batsmans.get(position).getSixes());
        holder.out.setText(Html.fromHtml(batsmans.get(position).getOut()));
        holder.sr.setText(batsmans.get(position).getSr());
        if (batsmans.get(position).getOut().equals("not out")) {
            holder.name.setTextColor(context.getResources().getColor(R.color.Blue));
            holder.out.setTextColor(context.getResources().getColor(R.color.ForestGreen));
            holder.name.setTypeface(null, Typeface.BOLD);
            holder.out.setTypeface(null,Typeface.BOLD);
        }

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://apisea.xyz/Cricket/apis/v1/CricinfoToCricAPI.php?key=bl905577&cricinfo="+batsmans.get(position).getPlayerId();
                final ProgressDialog progressDialog;
                progressDialog = ProgressDialog.show(context, "", "Loading. Please wait...", true);
                progressDialog.setCancelable(true);

                FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.getString("msg").equals("Successful")) {
                                String playerID = (response.getJSONArray("content").getJSONObject(0).getString("cricapiID"));
                                Intent intent = new Intent(context, PlayerProfileActivity.class);
                                intent.putExtra("playerID",playerID);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context,"Profile Not Found",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(Constants.TAG, response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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
        protected TextView sr;

        public BatsmanViewHolder(View itemView) {
            super(itemView);

            name = ViewHolder.get(itemView, R.id.batsmenName);
            runs = ViewHolder.get(itemView, R.id.runs);
            ball = ViewHolder.get(itemView, R.id.balls);
            fours = ViewHolder.get(itemView, R.id.fours);
            six = ViewHolder.get(itemView, R.id.six);
            out = ViewHolder.get(itemView, R.id.desc_out);
            sr = ViewHolder.get(itemView,R.id.sr);
        }
    }
}
