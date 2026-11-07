package com.quinonescreative.street2homev2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;

public class MainActivity extends Activity {
    private WebView webView;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleExternal(request.getUrl());
            }
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleExternal(Uri.parse(url));
            }
        });
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }
    private boolean handleExternal(Uri uri) {
        String scheme = uri.getScheme();
        if ("tel".equalsIgnoreCase(scheme)) {
            try { startActivity(new Intent(Intent.ACTION_DIAL, uri)); }
            catch (Exception e) { android.widget.Toast.makeText(this, "No phone dialer available", android.widget.Toast.LENGTH_LONG).show(); }
            return true;
        }
        if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
            try { startActivity(new Intent(Intent.ACTION_VIEW, uri)); }
            catch (Exception e) { android.widget.Toast.makeText(this, "No browser available", android.widget.Toast.LENGTH_LONG).show(); }
            return true;
        }
        return !("file".equalsIgnoreCase(scheme));
    }
    @Override public void onBackPressed() {
        webView.evaluateJavascript("if(document.getElementById('detail').style.display==='block'){back();}else{history.back();}", null);
    }
}
