package com.carlosgti001.rnegen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;
import com.unity3d.services.banners.UnityBanners;


public class lectorweb extends AppCompatActivity implements IUnityAdsInitializationListener {


        String unityGameID = "4056667";
        Boolean testMode = true;
        String topAdUnitId = "Banner_Android";
        String bottomAdUnitId = "Banner_Android";

        // Listener for banner events:
        private BannerView.IListener bannerListener = new BannerView.IListener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                // Called when the banner is loaded.
                Log.v("UnityAdsExample", "onBannerLoaded: " + bannerAdView.getPlacementId());
                ((ViewGroup)findViewById(R.id.banner)).removeView(bannerAdView);
                ((ViewGroup)findViewById(R.id.banner)).addView(bannerAdView);
            }


            @Override
            public void onBannerClick(BannerView bannerAdView) {
                // Called when a banner is clicked.
                Log.v("UnityAdsExample", "onBannerClick: " + bannerAdView.getPlacementId());
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {

            }

            @Override
            public void onBannerLeftApplication(BannerView bannerAdView) {
                // Called when the banner links out of the application.
                Log.v("UnityAdsExample", "onBannerLeftApplication: " + bannerAdView.getPlacementId());
            }
        };

        // This banner view object will be placed at the top of the screen:
        BannerView topBanner;
        // This banner view object will be placed at the bottom of the screen:
        BannerView bottomBanner;
        // View objects to display banners:
        RelativeLayout topBannerView;
        RelativeLayout bottomBannerView;
        // Buttons to show the banners:



    public Button salir;
    public String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectorweb);
        UnityAds.initialize(getApplicationContext(), unityGameID, testMode, this);
        salir = findViewById(R.id.cerrar);
        salir.setOnClickListener(v -> {
            finish();
        });

        WebView web = (WebView) findViewById(R.id.vistaweb);
        url = getIntent().getStringExtra("url");
        web.loadUrl(url);

    }

    @Override
    public void onInitializationComplete() {
        Toast t = Toast.makeText(this, "LISTO", Toast.LENGTH_LONG);
        t.show();

        topBanner = new BannerView(this, "Banner_Android", new UnityBannerSize(320, 50));
        topBanner.setListener(bannerListener);
        topBannerView = findViewById(R.id.banner);
        LoadBannerAd(topBanner, topBannerView);

    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {
        Toast t = Toast.makeText(this, "MALO", Toast.LENGTH_LONG);
        t.show();
    }

    public void LoadBannerAd(BannerView bannerView, RelativeLayout bannerLayout) {
        // Request a banner ad:
        bannerView.load();
        // Associate the banner view object with the banner view:
        bannerLayout.addView(bannerView);
    }
}