package com.sfuronlabs.ripon.cricketmania.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.Summary;

/**
 * Created by amin on 8/24/16.
 */
public class FragmentMatchSummary extends Fragment {
    private TextView labelGround;
    private TextView labelInfo;
    private TextView labelMatchStatus;
    private TextView labelTeam1;
    private TextView labelTeam2;
    private TextView labelTournament;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_summary, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.labelTournament = (TextView) view.findViewById(R.id.labelTournament);
        this.labelTeam1 = (TextView) view.findViewById(R.id.labelTeam1);
        this.labelTeam2 = (TextView) view.findViewById(R.id.labelTeam2);
        this.labelGround = (TextView) view.findViewById(R.id.labelGround);
        this.labelInfo = (TextView) view.findViewById(R.id.labelInfo);
        this.labelMatchStatus = (TextView) view.findViewById(R.id.labelMatchStatus);
    }

    public void setMatchSummary(Summary matchSummary) {
        if (isAdded()) {
            this.labelTournament.setText(matchSummary.getTournament());
            this.labelTeam1.setText(matchSummary.getTeam1());
            this.labelTeam2.setText(matchSummary.getTeam2());
            this.labelGround.setText(matchSummary.getGround());
            this.labelInfo.setText(matchSummary.getInfo());
            this.labelMatchStatus.setText(matchSummary.getMatchStatus());
        }
    }
}