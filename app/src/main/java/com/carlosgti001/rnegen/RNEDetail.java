package com.carlosgti001.rnegen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.carlosgti001.rnegen.databinding.ActivityFormularioBinding;
import com.carlosgti001.rnegen.databinding.ActivityRnedetailBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class RNEDetail extends AppCompatActivity {
    private Boolean testMode = true;
    private String adUnitId = "Interstitial_Android";
    String unityGameID = "4056667";

    public String RNE;

//    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
//        @Override
//        public void onUnityAdsAdLoaded(String placementId) {
//            UnityAds.show(RNEDetail.this, adUnitId, new UnityAdsShowOptions(), showListener);
//        }
//
//        @Override
//        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
//            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
//        }
//    };
//
//    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
//        @Override
//        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
//            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
//        }
//
//        @Override
//        public void onUnityAdsShowStart(String placementId) {
//            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
//        }
//
//        @Override
//        public void onUnityAdsShowClick(String placementId) {
//            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
//        }
//
//        @Override
//        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
//            Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
//        }
//    };

    @SuppressLint({"SourceLockedOrientationActivity", "SetTextI18n", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rnedetail);
        Intent intent = getIntent();
        String RNE = intent.getStringExtra("rne");
        ActivityRnedetailBinding binding = ActivityRnedetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        //UnityAds.initialize(getApplicationContext(), unityGameID, testMode, (IUnityAdsInitializationListener) this);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(RNE);
         //DisplayInterstitialAd();
    }

//    public void DisplayInterstitialAd () {
//        UnityAds.load(adUnitId, loadListener);
//    }
}
