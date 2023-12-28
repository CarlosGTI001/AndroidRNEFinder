package com.carlosgti001.rnegen;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.UiModeManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosgti001.rnegen.database.CRUD;
import com.carlosgti001.rnegen.database.database;
import com.carlosgti001.rnegen.databinding.ActivityFormularioBinding;
import com.carlosgti001.rnegen.list.contacto;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class formulario extends AppCompatActivity implements IUnityAdsInitializationListener {
    public EditText fecha1, nombreTxt, apellido1Txt, apellido2Txt;
    public DatePickerDialog.OnDateSetListener dataSetListener;
    private Boolean testMode = true;
    private String adUnitId = "Interstitial_Android";
    String unityGameID = "4056667";
    List<ListElement> elements;
    public RecyclerView rneLista;
    ArrayList<contacto> listArrayContacto;


    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {
            UnityAds.show(formulario.this, adUnitId, new UnityAdsShowOptions(), showListener);
        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
        }
    };

    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
        }

        @Override
        public void onUnityAdsShowStart(String placementId) {
            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {
            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
            Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
        }
    };

    @SuppressLint({"SourceLockedOrientationActivity", "SetTextI18n", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFormularioBinding binding = ActivityFormularioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        UnityAds.initialize(getApplicationContext(), unityGameID, testMode, this);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Formulario");
        CRUD dbRne = new CRUD(this);
        rneLista = findViewById(R.id.rneItems);
        rneLista.setLayoutManager(new LinearLayoutManager(this));
        Log.d("DB", "RDS");
        actualizar();

        /*mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadAd();*/
        fecha1 = findViewById(R.id.editFecha);
        nombreTxt = findViewById(R.id.editPrimerNombre);
        apellido1Txt = findViewById(R.id.editPrimerApellido);
        apellido2Txt = findViewById(R.id.editSegundoApellido);
        Button generarBtn = findViewById(R.id.btnOk);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(formulario.this, info1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        dataSetListener = (datePicker, ano, mes, dia) -> {
            if (mes < 9) {
                fecha1.setText(dia + "/0" + (mes + 1) + "/" + ano);
                if (dia < 10) {
                    fecha1.setText("0" + dia + "/0" + (mes + 1) + "/" + ano);
                }
            } else {
                if (dia < 10) {
                    fecha1.setText("0" + dia + "/" + (mes + 1) + "/" + ano);
                }
            }
            if (dia > 9 && mes > 9) {
                fecha1.setText(dia + "/" + (mes + 1) + "/" + ano);
            }
        };
        fecha1.setOnFocusChangeListener((view, b) -> {
            if (b) {
                calendariee();
            }
        });

        TextView Nombre, Rne, fecha;


        generarBtn.setOnClickListener(view -> {
            if (nombreTxt.getText().toString().isEmpty() || apellido1Txt.getText().toString().isEmpty() || fecha1.getText().toString().isEmpty()) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(formulario.this);
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage(R.string.alertaVacio);
                alertDialog.setPositiveButton("Aceptar", (dialogInterface, i) -> {

                });
                alertDialog.create();
                alertDialog.show();
            } else {
                AlertDialog.Builder alertDialogo = new AlertDialog.Builder(formulario.this);
                alertDialogo.setTitle("Aviso");
                alertDialogo.setMessage(Html.fromHtml("Recuerda que el final del RNE es 0001, en caso de no funcionar, recuerda cambiarlo por 0002 o 0003 o 0004 o 0005, si aun no funciona debes comunicarte con el ministerio de educacion.."));
                alertDialogo.setPositiveButton(Html.fromHtml("<b>Generar RNE</b>"), (dialogInterface, i) -> {
                    if(estado){
                        DisplayInterstitialAd();
                    }else{
                        Toast toast = Toast.makeText(this, "nocargado", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    String name = nombreTxt.getText().toString();
                    String firstname = apellido1Txt.getText().toString();
                    String seccondname = apellido2Txt.getText().toString();
                    String fechaFinal = fecha1.getText().toString();
                    Log.d("Fecha Final", fechaFinal);
                    String RNE = rneGen(name, firstname, seccondname, fechaFinal);
                    Date d = new Date();
                    CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
                    String formattedDate = sdf.format(d);
                    if(dbRne.buscarRNE(RNE)){
                        AlertDialog.Builder alerta = new AlertDialog.Builder(formulario.this);
                        alerta.setTitle("Aviso");
                        alerta.setMessage("Este RNE ya existe en el sistema");
                        alerta.setNeutralButton("Ok", (dialogInterface1, i1)->{});
                        alerta.create();
                        alerta.show();
                    }
                    else {
                        dbRne.rne(RNE, formattedDate, name);
                        dbRne.leerRne();
                        dbRne.close();
                        actualizar();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(formulario.this);
                        alertDialog.setTitle("RNE Generado");
                        alertDialog.setMessage(Html.fromHtml("Tu RNE de las pruebas nacionales es: <b>" + RNE + "</b>, Si esta app te fue de mucha ayuda puedes valorarla con <b>5 Estrellas!</b>, Por cierto... Que deseas hacer con el RNE?."));

                        alertDialog.setPositiveButton("Copiar e ir por el certificado", (dialogInterface1, i1) -> {
                            ClipboardManager clipboard = (ClipboardManager)
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("RNE", RNE);
                            clipboard.setPrimaryClip(clip);
                            Snackbar.make(view, R.string.copiado, Snackbar.LENGTH_SHORT).show();
                            Intent ir = new Intent("android.intent.action.VIEW", Uri.parse("https://certificado.ministeriodeeducacion.gob.do/"));

                            startActivity(ir);


                        });
                        alertDialog.setNegativeButton("Compartir", (dialogInterface1, i1) -> {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, RNE);
                            sendIntent.setType("text/plain");
                            Intent shareIntent = Intent.createChooser(sendIntent, null);
                            startActivity(shareIntent);
                        });
                        alertDialog.setNeutralButton("Valorarnos con 5 Estrellas", (dialogInterface1, i1) -> {
                            ClipboardManager clipboard = (ClipboardManager)
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("RNE", RNE);
                            clipboard.setPrimaryClip(clip);

                            Intent lo = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.carlosgti001.rnegen"));
                            startActivity(lo);
                        });
                        alertDialog.create();
                        alertDialog.show();
                    }
                });
                alertDialogo.create();
                alertDialogo.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calendariee() {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = 2000;
        fecha1.clearFocus();
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(formulario.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dataSetListener,
                anio,
                mes,
                dia);
        dialog.show();
    }

    public String rneGen(String nombre, String apellido1, String apellido2, String fecha) {
        String ap2;
        if (apellido2.isEmpty()) {
            ap2 = "-";
        } else {
            ap2 = apellido2;
        }
        char[] fechaDesp = fecha.toCharArray();
        return "" +  nombre.charAt(0) + apellido1.charAt(0) + ap2.charAt(0) + fechaDesp[8] + fechaDesp[9] + fechaDesp[3] + fechaDesp[4] + fechaDesp[0] + fechaDesp[1] + "0001";
    }

    public void upload() {

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

    public void actualizar() {
        CRUD dbRne = new CRUD(this);
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ListaContactosAdapter adapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            adapter = new ListaContactosAdapter((ArrayList<contacto>) dbRne.leerRne(), clipboard, this);
        }
        rneLista.setAdapter(adapter);
    }
    boolean estado = false;
    @Override
    public void onInitializationComplete() {
        estado = true;
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {

    }

    // Implement a function to load an interstitial ad. The ad will start to show after the ad has been loaded.
    public void DisplayInterstitialAd () {
        UnityAds.load(adUnitId, loadListener);
    }
}

