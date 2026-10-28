package com.example.gestornotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NotasDbHelper extends SQLiteOpenHelper {

    private static final String DB_NOMBRE = "notas.db";
    private static final int DB_VERSION = 1;

    public static final String TABLA = "notas";
    public static final String COL_ID = "id";
    public static final String COL_TITULO = "titulo";
    public static final String COL_DESCRIPCION = "descripcion";
    public static final String COL_IMAGEN = "imagen";

    public NotasDbHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITULO + " TEXT NOT NULL, "
                + COL_DESCRIPCION + " TEXT, "
                + COL_IMAGEN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA);
        onCreate(db);
    }

    // CREATE
    public long insertarNota(String titulo, String descripcion, String imagen) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_TITULO, titulo);
        v.put(COL_DESCRIPCION, descripcion);
        v.put(COL_IMAGEN, imagen);
        return db.insert(TABLA, null, v);
    }

    // READ
    public List<Nota> obtenerNotas() {
        List<Nota> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLA, null, null, null, null, null, COL_ID + " DESC");
        while (c.moveToNext()) {
            lista.add(new Nota(
                    c.getInt(c.getColumnIndexOrThrow(COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_TITULO)),
                    c.getString(c.getColumnIndexOrThrow(COL_DESCRIPCION)),
                    c.getString(c.getColumnIndexOrThrow(COL_IMAGEN))));
        }
        c.close();
        return lista;
    }

    // UPDATE
    public int actualizarNota(int id, String titulo, String descripcion, String imagen) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_TITULO, titulo);
        v.put(COL_DESCRIPCION, descripcion);
        v.put(COL_IMAGEN, imagen);
        return db.update(TABLA, v, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    // DELETE
    public int eliminarNota(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLA, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}