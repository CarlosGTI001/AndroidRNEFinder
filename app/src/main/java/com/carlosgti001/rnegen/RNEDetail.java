package com.carlosgti001.rnegen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosgti001.rnegen.database.CRUD;
import com.carlosgti001.rnegen.databinding.ActivityRnedetailBinding;
import com.carlosgti001.rnegen.list.CodeStudiant;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RNEDetail extends AppCompatActivity {
    private Boolean testMode = true;
    private String adUnitId = "Interstitial_Android";
    String unityGameID = "4056667";

    private TextView Nombre;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout principal
        ActivityRnedetailBinding binding = ActivityRnedetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener referencia al NestedScrollView incluido
        NestedScrollView nestedScrollView = findViewById(R.id.nel);

        // Obtener referencias de vistas dentro del NestedScrollView
        TextView random = nestedScrollView.findViewById(R.id.textView3);
        TextView Nombre = nestedScrollView.findViewById(R.id.nombreTextView);
        TextView RNECode = nestedScrollView.findViewById(R.id.rneTextView);
        TextView Date = nestedScrollView.findViewById(R.id.fechaRneTextView);
        TextView FechaNac = nestedScrollView.findViewById(R.id.fechaTextView);
        Button Eliminar = nestedScrollView.findViewById(R.id.eliminarBtn);
        // Obtener datos pasados por el intent
        Intent intent = getIntent();
        String RNE = intent.getStringExtra("rne");

        // Actualizar las vistas con los datos obtenidos
        RNECode.setText(RNE);
        CRUD db = new CRUD(this);
        CodeStudiant contacto = db.leerRNE(RNE);
        String name = contacto.getNombre();
        Nombre.setText(name);
        String Rne = contacto.getRne();
        RNECode.setText(Rne);
        String Fecha = contacto.getFecha();
        Date.setText(convertirFecha(Fecha));
        Log.d("FECHA", obtenerFechaConCodigo(Rne));
        FechaNac.setText(convertirFecha(obtenerFechaConCodigo(Rne)));
        Eliminar.setOnClickListener(View ->{
            AlertDialog.Builder alertDialogo = new AlertDialog.Builder(RNEDetail.this);
            alertDialogo.setTitle("Aviso");
            alertDialogo.setMessage(Html.fromHtml("En realidad desea eliminar el RNE?"));
            alertDialogo.setPositiveButton("Aceptar", (dialogInterface, i) -> {
                boolean resultado = db.eliminarRNE(Rne);
                if(resultado){
                    Toast toast = new Toast(this);
                    toast.setText("RNE Eliminado con exito");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                    Intent ide = new Intent();
                    ide.putExtra("key", "xDDD"); // Aquí puedes agregar los datos que desees pasar de vuelta a la actividad padre
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            alertDialogo.create();
            alertDialogo.show();
        });

        ImageView Copiar = findViewById(R.id.copiarImg);

        Copiar.setOnClickListener(View ->{
            // Obtén una referencia al servicio ClipboardManager
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            // Crea un ClipData para almacenar los datos que deseas copiar
            ClipData clip = ClipData.newPlainText("RNE", Rne);

            // Asigna los datos al portapapeles
            clipboard.setPrimaryClip(clip);

            // Notifica al usuario que el texto ha sido copiado
            Toast.makeText(getApplicationContext(), "RNE copiado al portapapeles", Toast.LENGTH_SHORT).show();

        });

        Button compartir = findViewById(R.id.compartirBtn);

        compartir.setOnClickListener(View->{
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, RNE);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        Button resultados = findViewById(R.id.resultadosBtn);

        resultados.setOnClickListener(View->{
            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("RNE", RNE);
            clipboard.setPrimaryClip(clip);
            Snackbar.make(View, R.string.copiado, Snackbar.LENGTH_SHORT).show();
            Intent ir = new Intent("android.intent.action.VIEW", Uri.parse("https://certificado.ministeriodeeducacion.gob.do/"));

            startActivity(ir);
        });

        FloatingActionButton fab = binding.fab;
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Informacion de RNE");

        Nombre.setText(name);

        fab.setOnClickListener(view -> {
            finish();
        });
    }

//    public void DisplayInterstitialAd () {
//        UnityAds.load(adUnitId, loadListener);
//    }



        private static final SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");



        public static String obtenerFechaConCodigo(String codigoFecha) {
            String yy = codigoFecha.substring(3, 5);
            String mm = codigoFecha.substring(5, 7);
            String dd = codigoFecha.substring(7, 9);

            String fechaString = dd + "/" + mm + "/" + "20" + yy;
            try {
                Date fecha = sdfFecha.parse(fechaString);
                return sdfFecha.format(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

    public static String convertirFecha(String fechaOriginal) {
        try {
            // Parsear la fecha original
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = sdf.parse(fechaOriginal);

            // Obtener el día, mes y año
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int año = calendar.get(Calendar.YEAR);

            // Obtener el nombre del mes en formato completo
            String[] nombreMeses = new SimpleDateFormat("MMMM", Locale.getDefault()).getDateFormatSymbols().getMonths();
            String nombreMes = nombreMeses[mes];

            // Construir la fecha en el formato deseado
            return dia + " de " + nombreMes + " de " + año;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
