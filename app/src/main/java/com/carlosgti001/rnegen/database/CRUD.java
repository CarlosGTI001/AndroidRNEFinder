package com.carlosgti001.rnegen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosgti001.rnegen.list.contacto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CRUD extends database {

    Context context;

    public CRUD(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long rne (String RNE, String fecha, String nombre)
    {
        long id = 0;
        try {
            database dbHelper = new database(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            value.put("rne", RNE);
            value.put("fecha", fecha);
            value.put("nombre",  nombre);
            id = db.insert("t_rne", null, value);
            db.close();
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<contacto> leerRne(){
        Log.d("DB","RNE");
        database data = new database(context);
        SQLiteDatabase db = data.getWritableDatabase();

        ArrayList<contacto> listaRne;
        listaRne = new ArrayList<>();
        contacto contactoElement = null;
        Cursor cursorRne = null;

        cursorRne = db.rawQuery("SELECT * FROM t_rne ORDER BY id DESC", null);
        Log.d("DB","Antes");
        if(cursorRne.moveToFirst()) {
            for (int contador = 0; contador < cursorRne.getCount(); contador++) {
                contactoElement = new contacto();
                contactoElement.setNombre(cursorRne.getString(3));
                contactoElement.setRne(cursorRne.getString(1));
                contactoElement.setApellido(cursorRne.getString(4));
                if (listaRne.add(contactoElement)) {
                    Log.d("DB", listaRne.get(contador).getRne());
                    cursorRne.moveToNext();
                } else {
                    Log.d("DB", "no leydo");
                }
            }
        }
        Log.d("DB","Despues");
        cursorRne.close();
        return listaRne;
    }
}
