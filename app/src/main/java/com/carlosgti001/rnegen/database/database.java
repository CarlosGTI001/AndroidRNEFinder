package com.carlosgti001.rnegen.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "RNE";
    public static final String TABLE_RNE = "t_rne";

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DataBase123", "Paso1");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(
                    "CREATE TABLE "+ TABLE_RNE + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "RNE TEXT NOT NULL," +
                    "fecha TEXT," +
                    "nombre TEXT NOT NULL)");
        Log.d("DataBase123", "Paso2");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " +TABLE_RNE+ "");
        Log.d("DataBase123", "Borrado");

    }
}
