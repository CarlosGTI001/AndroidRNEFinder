package com.carlosgti001.rnegen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class lectorweb extends AppCompatActivity {

    public String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectorweb);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView mAdView = findViewById(R.id.adView2);
        mAdView.loadAd(adRequest);
        WebView web = (WebView) findViewById(R.id.vistaweb);
        url = getIntent().getStringExtra("url");
        web.loadUrl(url);

    }

    public void cerrar (){
        finishActivity(0);
    }
}