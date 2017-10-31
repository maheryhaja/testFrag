package fr.hdb.artibip.presentation.widget.webview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtils {
    WebView mWebView;
    Activity activity;
    WebViewListener pageListener;

    public WebViewUtils(WebView mwView, Activity activity, WebViewListener pageListener) {
        this.mWebView = mwView;
        this.activity = activity;
        this.pageListener = pageListener;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public void loadUrl(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        mWebView.setWebViewClient(new Callback());
        mWebView.loadUrl(url);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public void loadHtml(String html) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        mWebView.setWebViewClient(new Callback());
        mWebView.loadData(html,"text/html", "UTF-8");

    }

    public WebViewListener getPageListener() {
        return pageListener;
    }

    public void setPageListener(WebViewListener pageListener) {
        this.pageListener = pageListener;
    }

    private class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(pageListener != null)
                pageListener.onPageLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(pageListener != null)
                pageListener.onPageFinished();
        }
    }
}
