package com.carlosgti001.rnegen.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.carlosgti001.rnegen.list.CodeStudiant;

import java.util.ArrayList;
import java.util.List;

public class CRUD extends database {

    Context context;

    public CRUD(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean buscarRNE(String RNE){
        database dbHelper = new database(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorRne = null;
        cursorRne = db.rawQuery("SELECT * FROM t_rne WHERE rne = ?", new String[]{RNE}, null);
        int cantidad = cursorRne.getCount();
        db.close();
        return cantidad > 0;
    }

    public boolean eliminarRNE(String RNE){
        database dbHelper = new database(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Suponiendo que "db" es tu instancia de SQLiteDatabase y "RNE" es el valor que deseas utilizar para eliminar las filas
        String tabla = "t_rne";
        String columna = "rne";
        String[] valores = new String[]{RNE};

        // Eliminar filas de la tabla donde la columna "rne" coincide con el valor de "RNE"
        int filasEliminadas = db.delete(tabla, columna + " = ?", valores);

        db.close();
        // Verificar si se eliminaron filas
        if (filasEliminadas > 0) {
            return true;
        } else {
            return false;
        }
    }

    public CodeStudiant leerRNE(String RNE){
        database dbHelper = new database(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorRne = null;
        cursorRne = db.rawQuery("SELECT * FROM t_rne WHERE rne = ?", new String[]{RNE}, null);
        CodeStudiant contactoElement = new CodeStudiant();
        cursorRne.moveToFirst();
        contactoElement.setNombre(cursorRne.getString(3));
        contactoElement.setRne(cursorRne.getString(1));
        contactoElement.setFecha(cursorRne.getString(2));
        db.close();
        return contactoElement;
    }


    @SuppressLint("Range")
    public long rne (String RNE, String fecha, String nombre)
    {
        long id = 0;
        try {
            database dbHelper = new database(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Verificar si ya existe el registro con el mismo RNE
            Cursor cursor = db.rawQuery("SELECT id FROM t_rne WHERE rne = ?", new String[]{RNE});
            if (cursor != null && cursor.getCount() > 0) {
                // Si existe, obtener el ID y cerrar el cursor y la base de datos
                cursor.moveToFirst();
                id = cursor.getLong(cursor.getColumnIndex("id"));
                cursor.close();
                db.close();
            } else {
                // Si no existe, insertar el nuevo registro
                ContentValues value = new ContentValues();
                value.put("rne", RNE);
                value.put("fecha", fecha);
                value.put("nombre", nombre);
                id = db.insert("t_rne", null, value);
                Log.d("Insertado", "" + id);
                db.close();
            }
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<CodeStudiant> leerRne(){
        Log.d("DB","RNE");
        database data = new database(context);
        SQLiteDatabase db = data.getWritableDatabase();
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "+ TABLE_RNE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "RNE TEXT NOT NULL," +
                        "fecha TEXT," +
                        "nombre TEXT NOT NULL)");
        Log.d("DataBase123", "Paso2");
        ArrayList<CodeStudiant> listaRne;
        listaRne = new ArrayList<>();
        CodeStudiant contactoElement = null;
        Cursor cursorRne = null;

        cursorRne = db.rawQuery("SELECT * FROM t_rne ORDER BY id DESC", null);
        Log.d("DB","Antes");
        if(cursorRne.moveToFirst()) {
            for (int contador = 0; contador < cursorRne.getCount(); contador++) {
                contactoElement = new CodeStudiant();
                contactoElement.setNombre(cursorRne.getString(3));
                contactoElement.setRne(cursorRne.getString(1));
                contactoElement.setFecha(cursorRne.getString(2));
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
