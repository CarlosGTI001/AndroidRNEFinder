package com.carlosgti001.rnegen;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class info2 extends AppCompatActivity {

    int numero = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo_rapido);

        TextView texto = findViewById(R.id.descripcion);
        ImageView img = findViewById(R.id.icono);
        FloatingActionButton fbuton = findViewById(R.id.contiunar);

        texto.setAnimation(animacion2);
        img.setAnimation(animacion1);
        fbuton.setAnimation(animacion2);

        fbuton.setOnClickListener(view -> {
          AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


            alertDialog.setTitle("Politica de privacidad");
            alertDialog.setMessage(R.string.aviso2doApellidp);
            alertDialog.setNeutralButton("Leer Politicas de Privacidad", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent ir = new Intent(info2.this, lectorweb.class);
                    ir.putExtra("url", "http://rne.servercraftrd.xyz/politica.php");
                    startActivity(ir);
                }
            });
            alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(info2.this, formulario.class);
                    startActivity(intent);
                    finish();
                }
            });
            alertDialog.setNegativeButton("No aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences preferences = getSharedPreferences("primeravez", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("valor", false);
                    editor.commit();
                    finishActivity(0);
                }
            });
            alertDialog.create();
            alertDialog.show();

        });

        new CountDownTimer(30000, 2000) {
            @SuppressLint("ResourceType")
            @Override
            public void onTick(long l) {
                if (numero == 1) {
                    img.setImageResource(R.drawable.nombre);
                }
                if (numero == 2) {
                    img.setImageResource(R.drawable.fecha);
                }
                if (numero == 3) {
                    img.setImageResource(R.drawable.student);
                    numero = 0;
                }
                numero++;
            }

            @Override
            public void onFinish() {
                this.start();
            }
        }.start();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("Configuracion", "true");
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

    public AlertDialog create(Context context) {
        final TextView message = new TextView(context);
        // i.e.: R.string.dialog_message =>
        // "Test this dialog following the link to dtmilano.blogspot.com"
        final SpannableString s = new SpannableString(context.getText(R.string.aviso2doApellidp));
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        return new AlertDialog.Builder(context)
                .setTitle("Politicas de privacidad")
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Aceptar", (dialog, which) -> {

                })
                .setNegativeButton("Declinar", (dialog, which) -> {
                    SharedPreferences preferences = getSharedPreferences("primeravez", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("valor", false);
                    editor.commit();
                    finishActivity(0);
                })
                .setView(message)
                .create();
    }
}