package com.sportsworld.cricket.everything.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sportsworld.cricket.everything.R;
import com.sportsworld.cricket.everything.activity.RecordDetailsActivity;
import com.sportsworld.cricket.everything.adapter.BasicListAdapter;
import com.sportsworld.cricket.everything.model.RecordModel;
import com.sportsworld.cricket.everything.util.ViewHolder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author Ripon
 */
public class RecordsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<RecordModel> recordModels = new ArrayList<>();
    Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    public void populateFragment(JSONArray jsonArray) throws JSONException {
        for (int i=0;i<jsonArray.length();i++)
            recordModels.add(gson.fromJson(String.valueOf(jsonArray.getJSONObject(i)),RecordModel.class));
        recyclerView.setAdapter(new BasicListAdapter<RecordModel,RecordsViewHolder>(recordModels) {
            @Override
            public RecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_records,parent,false);
                return new RecordsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecordsViewHolder holder, final int position) {
                holder.textView.setText(recordModels.get(position).getHeader());
                holder.recordTypeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), RecordDetailsActivity.class);
                        intent.putExtra("title",recordModels.get(position).getHeader());
                        intent.putExtra("recordtype",getArguments().getString("recordtype"));
                        intent.putExtra("url",recordModels.get(position).getUrl());
                        startActivity(intent);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private static class RecordsViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        protected LinearLayout recordTypeLayout;

        public RecordsViewHolder(View itemView) {
            super(itemView);
            textView = ViewHolder.get(itemView,R.id.tv_record_type);
            recordTypeLayout = ViewHolder.get(itemView,R.id.record_type_layout);
        }
    }
}
