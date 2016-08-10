package com.sfuronlabs.ripon.cricketmania.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.adapter.PlayerListAdapter;
import com.sfuronlabs.ripon.cricketmania.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ripon on 12/16/15.
 */
public class PlayersFragment extends Fragment {
    ListView playerslist;
    ArrayList<Player> pl;
    PlayerListAdapter playerListAdapter;

    public PlayersFragment()
    {

    }

    public static PlayersFragment newInstanceOfPlayersFragment(String text) {
        PlayersFragment myFragment = new PlayersFragment();
        Bundle arguments = new Bundle();
        arguments.putString("details", text);

        myFragment.setArguments(arguments);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.playersfragment,null,false);
        playerslist = (ListView) v.findViewById(R.id.lvPlayers);
        pl = new ArrayList<>();
        playerListAdapter = new PlayerListAdapter(getContext(),pl);
        playerslist.setAdapter(playerListAdapter);


        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            pl.clear();
            String string = getArguments().getString("details");
            try {
                JSONObject jsonObject = new JSONObject(string);

                jsonObject = jsonObject.getJSONObject("query");
                jsonObject = jsonObject.getJSONObject("results");
                jsonObject = jsonObject.getJSONObject("TeamProfile");
                jsonObject = jsonObject.getJSONObject("Players");
                JSONArray jsonArray = jsonObject.getJSONArray("Player");
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String personid = obj.getString("personid");
                    String name = obj.getString("FirstName")+" "+obj.getString("LastName");
                    Player player = new Player(name,null,personid);
                    pl.add(player);

                }
                playerListAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {

        }
    }
}
