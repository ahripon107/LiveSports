package com.sportsworld.cricket.everything.cricbuzz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.sportsworld.cricket.everything.R;
import com.sportsworld.cricket.everything.activity.CommonAppCompatActivity;
import com.sportsworld.cricket.everything.model.CricketNews;
import com.sportsworld.cricket.everything.util.Constants;
import com.sportsworld.cricket.everything.util.FetchFromWeb;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

/**
 * @author Ripon
 */

public class CricbuzzNewsDetailsActivity extends CommonAppCompatActivity {

    WebView webView;
    String html;
    AdView adView;
    public static final String EXTRA_NEWS = "newsobject";

    CricketNews cricketNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framestream);


        adView = (AdView) findViewById(R.id.adViewFrameStream);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constants.ONE_PLUS_TEST_DEVICE)
                .addTestDevice(Constants.XIAOMI_TEST_DEVICE).build();
        adView.loadAd(adRequest);
        webView = (WebView) findViewById(R.id.wvframe);
        webView.getSettings().setJavaScriptEnabled(true);

        cricketNews = (CricketNews) getIntent().getSerializableExtra(EXTRA_NEWS);

        String url = cricketNews.getLink();
        Log.d(Constants.TAG, url);
        final AlertDialog progressDialog = new SpotsDialog(CricbuzzNewsDetailsActivity.this, R.style.Custom);
        progressDialog.show();
        progressDialog.setCancelable(true);
        FetchFromWeb.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    html = response.getJSONArray("story").getString(0);
                    Log.d(Constants.TAG, html);

                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.getSettings().setDisplayZoomControls(false);

                    webView.setWebChromeClient(new WebChromeClient());
                    webView.loadDataWithBaseURL("", html , "text/html",  "UTF-8", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(CricbuzzNewsDetailsActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPause() {
        webView.onPause();
        webView.pauseTimers();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
        webView.onResume();
    }


    @Override
    protected void onDestroy() {
        webView.destroy();
        webView = null;
        super.onDestroy();
    }
}
