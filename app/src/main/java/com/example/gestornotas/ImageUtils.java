package com.example.gestornotas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageUtils {

    // Copia la imagen elegida a la carpeta interna de la app y devuelve su ruta
    public static String guardarImagen(Context ctx, Uri uri) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            try (InputStream in = ctx.getContentResolver().openInputStream(uri)) {
                BitmapFactory.decodeStream(in, null, o);
            }
            int sample = 1;
            while (o.outWidth / sample > 1024 || o.outHeight / sample > 1024) sample *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = sample;
            Bitmap bmp;
            try (InputStream in = ctx.getContentResolver().openInputStream(uri)) {
                bmp = BitmapFactory.decodeStream(in, null, o2);
            }
            if (bmp == null) return null;

            File archivo = new File(ctx.getFilesDir(), "nota_" + System.currentTimeMillis() + ".jpg");
            try (FileOutputStream out = new FileOutputStream(archivo)) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 85, out);
            }
            return archivo.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Carga una imagen reducida
    public static Bitmap cargar(String ruta, int maxLado) {
        if (ruta == null || ruta.isEmpty() || !new File(ruta).exists()) return null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(ruta, o);
        int sample = 1;
        while (o.outWidth / sample > maxLado || o.outHeight / sample > maxLado) sample *= 2;
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = sample;
        return BitmapFactory.decodeFile(ruta, o2);
    }

    public static void borrar(String ruta) {
        if (ruta != null && !ruta.isEmpty()) {
            File f = new File(ruta);
            if (f.exists()) f.delete();
        }
    }
}