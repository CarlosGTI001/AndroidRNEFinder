package com.carlosgti001.rnegen;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        TextView paraTextView = findViewById(R.id.por);
        TextView entyTextView = findViewById(R.id.enty);
        ImageView logoImageView = findViewById(R.id.logo);
        paraTextView.setAnimation(animacion2);
        entyTextView.setAnimation(animacion2);
        logoImageView.setAnimation(animacion1);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, info1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                verdadero();
                finish();
            }
        };
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        TimerTask listo = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, formulario.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        };
        if(leer()){
            Timer timer = new Timer();
            timer.schedule(listo, 4000);
        }else{
            Timer timer = new Timer();
            timer.schedule(task, 4000);
        }

    }
    @SuppressLint("ApplySharedPref")
    void verdadero(){
        SharedPreferences preferences = getSharedPreferences("primeravez", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("valor", true);
        editor.commit();
    }
    boolean leer(){
        SharedPreferences preferences = getSharedPreferences("primeravez", Context.MODE_PRIVATE);
        return preferences.getBoolean("valor", false);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("Configuracion","true");
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                Toast.makeText(this, "Modo no oscuro", Toast.LENGTH_LONG).show();
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                Toast.makeText(this, "Modo oscuro", Toast.LENGTH_LONG).show();
                break;
        }
    }
}