package com.sfuronlabs.ripon.cricketmania.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sfuronlabs.ripon.cricketmania.R;
import com.sfuronlabs.ripon.cricketmania.model.PlayerProfile;

/**
 * Created by Ripon on 8/13/16.
 */
public class PlayerProfileActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);
        textView = (TextView) findViewById(R.id.tv_player_profile);

        PlayerProfile playerProfile = (PlayerProfile) getIntent().getSerializableExtra("profile");
        textView.setText(playerProfile.toString());
    }
}
