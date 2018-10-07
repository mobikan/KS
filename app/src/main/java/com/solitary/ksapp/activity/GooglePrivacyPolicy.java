package com.solitary.ksapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;


import com.solitary.ksapp.R;


public class GooglePrivacyPolicy extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle( "Google Play Policy");
        WebView wv = (WebView) findViewById(R.id.webview);
        wv.loadUrl("file:///android_asset/privacy_policy.html");   // now it will not fail here
    }
}
