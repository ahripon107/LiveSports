package com.sfuronlabs.ripon.cricketmania.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfuronlabs.ripon.cricketmania.R;

import org.json.JSONArray;

/**
 * Created by amin on 8/24/16.
 */
public class FragmentScoreBoard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_board,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setFirstInningsBattingList(JSONArray jsonArray) {

    }

    public void setSecondInningsBattingList(JSONArray jsonArray) {

    }

    public void setThirdInningsBattingList(JSONArray jsonArray) {

    }

    public void setFourthInningsBattingList(JSONArray jsonArray) {

    }

    public void setFirstInningsBowlingList(JSONArray jsonArray) {

    }

    public void setSecondInningsBowlingList(JSONArray jsonArray) {

    }

    public void setThirdInningsBowlingList(JSONArray jsonArray) {

    }

    public void setFourthInningsBowlingList(JSONArray jsonArray) {

    }
}
