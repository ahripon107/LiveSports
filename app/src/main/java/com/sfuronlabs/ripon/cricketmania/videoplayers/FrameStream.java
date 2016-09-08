package com.sfuronlabs.ripon.cricketmania.videoplayers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sfuronlabs.ripon.cricketmania.R;

/**
 * Created by Ripon on 2/13/16.
 */
public class FrameStream extends AppCompatActivity {

    WebView webView;
    String html;
    AdView adView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.framestream);


        adView = (AdView) findViewById(R.id.adViewFrameStream);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("18D9D4FB40DF048C506091E42E0FDAFD").build();
        adView.loadAd(adRequest);
        Intent intent = getIntent();
        String str = intent.getStringExtra("url");

        html = "<html>\n" +
                "    <head>\n" +
                "     <meta name=\"viewport\" content=\"width=device-width, target-densitydpi=device-dpi, initial-scale=0, maximum-scale=1, user-scalable=yes\" />\n"+
                "        <script type=\"text/javascript\">\n" +
                "            function changeIframeSize(height, width){\n" +
                "                var iframe = document.getElementById(\"iframe1\");\n" +
                "                iframe.height = height;\n" +
                "                iframe.width = width;\n" +
                "            }\n" +
                "        </script>\n" +
                "    </head>\n" +
                "    <body style=\"margin: 0; padding: 0\"> \n" +
                "        \n" +
                "       "+ str + "  \n" +
                "    </body>\n" +
                "</html>";
        webView = (WebView) findViewById(R.id.wvframe);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);


        final Activity activity = this;



        webView.setWebChromeClient(new WebChromeClient());
        webView.loadDataWithBaseURL("", html , "text/html",  "UTF-8", "");

        Toast.makeText(getApplicationContext(),"Zoom in or out to adjust with device screen",Toast.LENGTH_LONG).show();


    }
}
