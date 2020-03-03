package com.app.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class webSitePage extends AppCompatActivity {

    private WebView WebSiteWebview;
    private WebSettings WebSiteSettings;
    private ConnectivityManager cm;
    private String BaseUrl = "https://easilymart.com/";
    private ProgressBar RoundProgressBar, HorizontalProgressBar;
    private int BackPressedTime;

    protected Handler LoadWebClientHandler =  new Handler();
    protected Handler LoadWebChromeClientHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean HasInternetAccess = CheckForInternet();

        if(HasInternetAccess){
            InitialiseID();
            SetWebSettings();
            Thread LoadWebClientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    LoadWebClientHandler.post(new Runnable() {
                        @Override
                        public void run() { LoadWebClient();
                        }
                    });
                }
            });
            Thread LoadWebChromeClientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    LoadWebChromeClientHandler.post(new Runnable() {
                        @Override
                        public void run() { LoadChromeClient(); }
                    });
                }
            });
            LoadWebClientThread.start(); LoadWebChromeClientThread.start();
        }
        else {
            ShowToast("No Internet Access");
        }
    }

    @Override
    public void onBackPressed() {
        if(WebSiteWebview.canGoBack()) {
            WebSiteWebview.goBack();
        }
        else if (BackPressedTime + 2000 >= System.currentTimeMillis() && BackPressedTime + 3000 <= System.currentTimeMillis()){
            super.onBackPressed();
        }
        else {
            ShowToast("Press Again TO Exit");
        }

    }

    private void InitialiseID() {
        WebSiteWebview = findViewById(R.id.WebsiteWebviewID);
        RoundProgressBar = findViewById(R.id.RoundedProgressID);
        HorizontalProgressBar = findViewById(R.id.HorizontalProgressID);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void SetWebSettings() {
        WebSiteSettings = WebSiteWebview.getSettings();
        WebSiteSettings.setJavaScriptEnabled(true);
        WebSiteSettings.setAppCacheEnabled(true);
    }

    private void LoadChromeClient() {
        WebSiteWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                HorizontalProgressBar.setVisibility(View.VISIBLE);
                HorizontalProgressBar.setProgress(WebSiteWebview.getProgress());
            }
        });
    }

    private void LoadWebClient() {
        WebSiteWebview.loadUrl(BaseUrl);
        WebSiteWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                RoundProgressBar.setVisibility(View.VISIBLE);

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                RoundProgressBar.setVisibility(View.GONE);
                HorizontalProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }

    private boolean CheckForInternet() {
        cm = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo i = cm.getActiveNetworkInfo();
        return (i != null && i.isConnected() && i.isAvailable());
    }

    private void ShowToast(String massage) {
        Toast.makeText(this, massage,Toast.LENGTH_LONG).show();
    }
}
