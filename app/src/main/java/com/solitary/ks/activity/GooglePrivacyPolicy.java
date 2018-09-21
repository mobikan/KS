package com.solitary.ks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.solitary.ks.R;


public class GooglePrivacyPolicy extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        WebView wv = (WebView) findViewById(R.id.webview);
        wv.loadUrl("file:///android_asset/privacy_policy.html");   // now it will not fail here
    }
}
