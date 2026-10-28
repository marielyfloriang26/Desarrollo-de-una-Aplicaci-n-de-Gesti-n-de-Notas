package com.example.gestornotas;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AgregarFragment extends Fragment {

    private EditText edtTitulo, edtDescripcion;
    private ImageView imgPrevia;
    private String rutaImagen = null;
    private NotasDbHelper db;

    // Selector de imagenes del sistema
    private final ActivityResultLauncher<String> selectorImagen =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) procesarImagen(uri);
            });

    public AgregarFragment() {
        super(R.layout.fragment_agregar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new NotasDbHelper(requireContext());

        edtTitulo = view.findViewById(R.id.edtTitulo);
        edtDescripcion = view.findViewById(R.id.edtDescripcion);
        imgPrevia = view.findViewById(R.id.imgPrevia);
        Button btnImagen = view.findViewById(R.id.btnSeleccionarImagen);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);

        btnImagen.setOnClickListener(v -> selectorImagen.launch("image/*"));
        btnGuardar.setOnClickListener(v -> guardarNota());
    }

    private void procesarImagen(Uri uri) {
        String ruta = ImageUtils.guardarImagen(requireContext(), uri);
        if (ruta == null) {
            Toast.makeText(getContext(), "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        // Si ya habia elegido otra imagen antes de guardar, se borra la anterior
        ImageUtils.borrar(rutaImagen);
        rutaImagen = ruta;
        Bitmap bmp = ImageUtils.cargar(ruta, 800);
        imgPrevia.setImageBitmap(bmp);
    }

    private void guardarNota() {
        String titulo = edtTitulo.getText().toString().trim();
        String desc = edtDescripcion.getText().toString().trim();

        if (titulo.isEmpty()) {
            edtTitulo.setError("El título es obligatorio");
            return;
        }

        long id = db.insertarNota(titulo, desc, rutaImagen);
        if (id != -1) {
            Toast.makeText(getContext(), "Nota guardada", Toast.LENGTH_SHORT).show();
            edtTitulo.setText("");
            edtDescripcion.setText("");
            imgPrevia.setImageResource(android.R.drawable.ic_menu_gallery);
            rutaImagen = null;
        } else {
            Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}