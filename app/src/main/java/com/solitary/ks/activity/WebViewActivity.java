package com.solitary.ks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.solitary.ks.R;

import java.util.Objects;

import static com.solitary.ks.utils.Constants.WebView.INTENT_URL;


public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        Toolbar toolbar = findViewById(R.id.toolbar );
        setSupportActionBar(toolbar);
        setTitle("Love Tips");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setPadding(0, 0, 0, 0);
        myWebView.setInitialScale(30);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
// remove a weird white line on the right size
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        String url =  getIntent().getStringExtra(INTENT_URL);
        myWebView.loadUrl(url);
    }


}
