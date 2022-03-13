package com.carlosgti001.rnegen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosgti001.rnegen.database.database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class info1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo_rapido);
        TextView texto = findViewById(R.id.descripcion);
        ImageView img = findViewById(R.id.icono);
        FloatingActionButton fbuton = findViewById(R.id.contiunar);

        texto.setAnimation(animacion2);
        img.setAnimation(animacion1);
        fbuton.setAnimation(animacion2);



        fbuton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                database data = new database(info1.this);
                SQLiteDatabase db = data.getWritableDatabase();
                if(db != null){
                    Intent intent = new Intent(info1.this, info2.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(info1.this, info2.class);
                    startActivity(intent);
                }

                finish();
            }
        });
    }
}