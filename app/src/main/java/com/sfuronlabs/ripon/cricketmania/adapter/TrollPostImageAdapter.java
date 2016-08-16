package com.sfuronlabs.ripon.cricketmania.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.TrollPost;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ripon on 8/12/16.
 */
public class TrollPostImageAdapter extends RecyclerView.Adapter<TrollPostImageAdapter.TrollPostViewHolder> {

    Context context;
    ArrayList<TrollPost> trollPosts;

    public TrollPostImageAdapter(Context context, ArrayList<TrollPost> trollPosts) {
        this.context= context;
        this.trollPosts = trollPosts;
    }

    @Override
    public TrollPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_troll_posts,parent,false);
        return new TrollPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrollPostViewHolder holder, int position) {
        holder.courtesy.setText(trollPosts.get(position).getCourtesy());
        holder.title.setText(trollPosts.get(position).getImagetext());
        Picasso.with(context)
                .load((trollPosts.get(position).getImageurl()))
                .placeholder(R.drawable.bpl)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return trollPosts.size();
    }

    static class TrollPostViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView courtesy;

        public TrollPostViewHolder(View itemView) {
            super(itemView);
            imageView = ViewHolder.get(itemView, R.id.img_troll_post);
            title = ViewHolder.get(itemView,R.id.tv_troll_post_title);
            courtesy = ViewHolder.get(itemView,R.id.tv_troll_post_courtesy);
        }
    }
}
