package com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;


public class AboutActivity extends Activity {
    public static final String URL = "http://www.xiaoquwuyou.com/yhxy.html";
    public static final String NAME = "name";

    private WebView web;
    private View load_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        web = (WebView) findViewById(R.id.webview_about);
        load_view =findViewById(R.id.load_view);

        findOrBuildView();


        web.loadUrl(URL);
    }

    protected void findOrBuildView() {
        WebSettings webSettings = web.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        // webSettings.setPluginsEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSaveFormData(true);
        // webSettings.setUserAgent(0);
        webSettings.setAllowFileAccess(true);
        webSettings.setBlockNetworkImage(false);

        web.addJavascriptInterface(new LocalObject(), "xqwy");
        web.setWebViewClient(wvc);
        web.setWebChromeClient(wcc);

        setSize(webSettings);
    }

    @SuppressWarnings("deprecation")
    private void setSize(WebSettings settings) {
        settings.setTextSize(WebSettings.TextSize.NORMAL);
    }

    public class LocalObject {
        public void myOpen(final String name, final String url) {
            Intent i = new Intent(AboutActivity.this, AboutActivity.class);
            i.putExtra(URL, url);
            i.putExtra(NAME, name);
            startActivity(i);
        }
    }

    private WebChromeClient wcc = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            return true;
        }

    };

    private WebViewClient wvc = new WebViewClient() {

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //load_view.setVisibility(View.GONE);

            super.onPageFinished(view, url);
            load_view.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            load_view.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                web.loadUrl(url);
            }
            return true;
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                finish();
            }
        }
        return true;
    }
}
