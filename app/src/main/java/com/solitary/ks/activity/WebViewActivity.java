package com.solitary.ks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.solitary.ks.R;

import static com.solitary.ks.utils.Constants.WebView.INTENT_URL;


public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        String url =  getIntent().getStringExtra(INTENT_URL);
        myWebView.loadUrl(url);
    }
}
