package com.androidfragmant.cricket.allabout.videoplayers;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.androidfragmant.cricket.allabout.R;
import com.androidfragmant.cricket.allabout.util.Constants;

/**
 * @author Ripon
 */
public class LiveStreamView extends AppCompatActivity {

    VideoView videoView1;
    String address;
    private MediaController mediaController;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livestreamview);

        videoView1 = (VideoView) findViewById(R.id.videoView);
        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(LiveStreamView.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView1);


            // Set MediaController for VideoView
            videoView1.setMediaController(mediaController);
        }
        Intent intent = getIntent();
        address = intent.getStringExtra("url");


        videoView1.setVideoURI(Uri.parse(address));
        videoView1.setMediaController (new MediaController(this));
        videoView1.requestFocus();
        videoView1.start();

        adView = (AdView) findViewById(R.id.adViewStreamM3U8);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest);
        Toast.makeText(getApplicationContext(),"Please wait while video is loading",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView1.pause();
    }

    @Override
    protected void onStop() {
        videoView1.stopPlayback();
        super.onStop();
    }
}
