package com.carlosgti001.rnegen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.carlosgti001.rnegen.list.contacto;

import java.util.ArrayList;

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
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<contacto> leerRne(){
        Log.d("DB","RNE");
        database data = new database(context);
        SQLiteDatabase db = data.getWritableDatabase();

        ArrayList<contacto> listaRne;
        listaRne = new ArrayList<>();
        contacto contactoElement = null;
        Cursor cursorRne = null;

        cursorRne = db.rawQuery("SELECT * FROM t_rne", null);
        Log.d("DB","Antes");
        if(cursorRne.moveToFirst()){
            do{
                contactoElement = new contacto();
                contactoElement.setNombre(cursorRne.getString(3));
                contactoElement.setRne(cursorRne.getString(1));
                contactoElement.setFecha(cursorRne.getString(2));
                listaRne.add(contactoElement);
            } while(cursorRne.moveToFirst());
        }
        Log.d("DB","Despues");
        cursorRne.close();
        return listaRne;
    }
}
