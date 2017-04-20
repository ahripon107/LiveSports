package com.sportsworld.cricket.everything.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sportsworld.cricket.everything.R;
import com.sportsworld.cricket.everything.util.Constants;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author ripon
 */
@ContentView(R.layout.livescore)
public class LiveScore extends CommonAppCompatActivity {

    public static final String EXTRA_URL = "url";

    @InjectView(R.id.webView)
    private WebView mWebview;
    @InjectView(R.id.adViewLivescore)
    AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("News Details");
        String url = getIntent().getStringExtra(EXTRA_URL);

        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(LiveScore.this, description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebview.loadUrl(url);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest);

    }
}
