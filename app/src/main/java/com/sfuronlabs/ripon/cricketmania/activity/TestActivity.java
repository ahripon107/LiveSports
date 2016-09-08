package com.sfuronlabs.ripon.cricketmania.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.BasicListAdapter;
import com.sfuronlabs.ripon.cricketmania.model.TrollPost;
import com.sfuronlabs.ripon.cricketmania.util.RoboAppCompatActivity;
import com.sfuronlabs.ripon.cricketmania.util.ViewHolder;

import java.util.ArrayList;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author ripon
 */
@ContentView(R.layout.news)
public class TestActivity extends RoboAppCompatActivity{

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    ArrayList<String> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        values = new ArrayList<>();
        values.add("dasdsa");
        values.add("fdsffs");
        values.add("rteer");
        recyclerView.setAdapter(new BasicListAdapter<String,TestViewHolder>(values) {
            @Override
            public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testlayout,parent,false);
                return new TestViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final TestViewHolder holder, int position) {
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),Integer.toBinaryString(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;
        public TestViewHolder(View itemView) {
            super(itemView);
            textView = ViewHolder.get(itemView,R.id.te);
        }
    }
}
