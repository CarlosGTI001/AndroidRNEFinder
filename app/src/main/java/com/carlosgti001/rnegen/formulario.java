package com.carlosgti001.rnegen;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class formulario extends AppCompatActivity {
    public EditText fecha1, nombreTxt, apellido1Txt, apellido2Txt;
    public DatePickerDialog.OnDateSetListener dataSetListener;


    List<ListElement> elements;
    CRUD insert = new CRUD(formulario.this);
    public RecyclerView rneLista;
    ArrayList<contacto> listArrayContacto;


    @SuppressLint({"SourceLockedOrientationActivity", "SetTextI18n", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFormularioBinding binding = ActivityFormularioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Formulario");
        CRUD dbRne = new CRUD(this);
        rneLista = findViewById(R.id.rneItems);
        rneLista.setLayoutManager(new LinearLayoutManager(this));
        Configuration configuration = new Configuration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                Toast toast = new Toast(this);
                toast.setText("Modo diurno");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                Toast toaste = new Toast(this);
                toaste.setText("Modo nocturno");
                toaste.setDuration(Toast.LENGTH_LONG);
                toaste.show();
                break;
        }
        Log.d("DB","RDS");

        ListaContactosAdapter adapter = new ListaContactosAdapter((ArrayList<contacto>) dbRne.leerRne());
        rneLista.setAdapter(adapter);
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

        dataSetListener = (datePicker, i, i1, i2) -> {
            if(i1 < 9){
                fecha1.setText(i2+"/0"+(i1+1)+"/"+i);
                if (i2 < 10){
                    fecha1.setText("0"+i2+"/0"+(i1+1)+"/"+i);
                }
            }else {
                if (i2 < 10){
                    fecha1.setText("0"+i2+"/"+(i1+1)+"/"+i);
                }
            }
            if(i2 > 9 && i1 > 9){
                fecha1.setText(i2+"/"+(i1+1)+"/"+1);
            }
        };
        fecha1.setOnFocusChangeListener((view, b) -> {
            if (b){
                calendariee();
            }
        });

        TextView Nombre, Rne, fecha;



        generarBtn.setOnClickListener(view -> {
            if(nombreTxt.getText().toString().isEmpty()||apellido1Txt.getText().toString().isEmpty()||fecha1.getText().toString().isEmpty()){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(formulario.this);
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage(R.string.alertaVacio);
                alertDialog.setPositiveButton("Aceptar", (dialogInterface, i) -> {

                });
                alertDialog.create();
                alertDialog.show();
            }else {
                AlertDialog.Builder alertDialogo = new AlertDialog.Builder(formulario.this);
                alertDialogo.setTitle("Aviso");
                alertDialogo.setMessage(Html.fromHtml("Recuerda que el final del RNE es 0001, en caso de no funcionar, recuerda cambiarlo por 0002 o 0003 o 0004 o 0005, si aun no funciona debes comunicarte con el ministerio de educacion.."));
                alertDialogo.setPositiveButton(Html.fromHtml("<b>Generar RNE</b>"), (dialogInterface, i) -> {

                    String name = nombreTxt.getText().toString();
                    String firstname = apellido1Txt.getText().toString();
                    String seccondname = apellido2Txt.getText().toString();
                    String fechaFinal = fecha1.getText().toString();
                    Log.d("Fecha Final", fechaFinal);
                    //String RNE = rneGen(name, firstname, seccondname, fechaFinal);
                    String RNE = "RNE01010101";
                    Date d = new Date(); CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());

                    insert.rne(RNE, d.toString(), name);
                    insert.leerRne();

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

    public String rneGen(String nombre, String apellido1, String apellido2, String fecha){
        String ap2;
        if(apellido2.isEmpty()){
            ap2 = "-";
        }else{
            ap2 = apellido2;
        }
        char[] n1C = nombre.toCharArray();
        char[] a1C = apellido1.toCharArray();
        char[] a2C = ap2.toCharArray();
        char[] fechaDesp = fecha.toCharArray();
        String datoSemiFinal;
        StringBuilder deb = new StringBuilder();
        for (char c : fechaDesp) {
            deb.append(c);
        }
        Log.d("Longitud", deb +" tiene :"+fechaDesp.length + " Caracteres");
        datoSemiFinal = ""+ fechaDesp[8] + fechaDesp[9] + fechaDesp[3] + fechaDesp[4] + fechaDesp[0] + fechaDesp[1];
        return ""+n1C[0]+a1C[0]+a2C[0]+datoSemiFinal + "0001";
    }

    public void upload(){

    }
}

