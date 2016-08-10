package com.sfuronlabs.ripon.cricketmania.videoplayers;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.R;

/**
 * Created by Ripon on 3/16/16.
 */
public class LiveStreamView extends Activity {

    VideoView videoView1;
    String address;
    MediaPlayer mediaPlayer;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.livestreamview);

        mediaPlayer = new MediaPlayer();
        Intent intent = getIntent();
        address = intent.getStringExtra("url");

        videoView1 = (VideoView) findViewById(R.id.videoView);


        videoView1.setVideoURI(Uri.parse(address));
        videoView1.setMediaController (new MediaController(this));
        videoView1.requestFocus();
        videoView1.start();

        adView = (AdView) findViewById(R.id.adViewStreamM3U8);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("18D9D4FB40DF048C506091E42E0FDAFD").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        Toast.makeText(getApplicationContext(),"Please wait while video is loading",Toast.LENGTH_LONG).show();
    }
}
