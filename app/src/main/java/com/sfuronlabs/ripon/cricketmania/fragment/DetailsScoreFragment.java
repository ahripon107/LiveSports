package com.sfuronlabs.ripon.cricketmania.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfuronlabs.ripon.cricketmania.R;

/**
 * Created by Ripon on 3/26/16.
 */
public class DetailsScoreFragment extends Fragment {

    public DetailsScoreFragment()
    {

    }

    public static DetailsScoreFragment newInstanceofDetailsScoreFragment(String text, String teamno)
    {
        DetailsScoreFragment detailsScoreFragment = new DetailsScoreFragment();
        Bundle arguments = new Bundle();
        arguments.putString("details", text);
        arguments.putString("teamno",teamno);
        detailsScoreFragment.setArguments(arguments);
        return detailsScoreFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);


        View v = inflater.inflate(R.layout.team1fragment,null,false);



        return v;
    }
}
