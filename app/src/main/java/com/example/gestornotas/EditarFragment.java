package com.example.gestornotas;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

public class EditarFragment extends Fragment {

    private View panelLista, panelForm;
    private ListView lista;
    private EditText edtTitulo, edtDescripcion;
    private ImageView imgEditar;

    private NotasDbHelper db;
    private NotaAdapter adapter;

    private Nota notaActual; // nota que se esta editando
    private String rutaImagenNueva;

    private final ActivityResultLauncher<String> selectorImagen =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) procesarImagen(uri);
            });

    public EditarFragment() {
        super(R.layout.fragment_editar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new NotasDbHelper(requireContext());

        panelLista = view.findViewById(R.id.panelLista);
        panelForm = view.findViewById(R.id.panelForm);
        lista = view.findViewById(R.id.listaEditar);
        edtTitulo = view.findViewById(R.id.edtEditTitulo);
        edtDescripcion = view.findViewById(R.id.edtEditDescripcion);
        imgEditar = view.findViewById(R.id.imgEditar);

        Button btnCambiar = view.findViewById(R.id.btnCambiarImagen);
        Button btnActualizar = view.findViewById(R.id.btnActualizar);
        Button btnEliminar = view.findViewById(R.id.btnEliminar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        adapter = new NotaAdapter(requireContext(), db.obtenerNotas());
        lista.setAdapter(adapter);

        lista.setOnItemClickListener((parent, v, position, id) ->
                abrirFormulario((Nota) adapter.getItem(position)));

        btnCambiar.setOnClickListener(v -> selectorImagen.launch("image/*"));
        btnActualizar.setOnClickListener(v -> actualizarNota());
        btnEliminar.setOnClickListener(v -> confirmarEliminar());
        btnCancelar.setOnClickListener(v -> cerrarFormulario());
    }

    private void abrirFormulario(Nota nota) {
        notaActual = nota;
        rutaImagenNueva = null;
        edtTitulo.setText(nota.getTitulo());
        edtDescripcion.setText(nota.getDescripcion());

        android.graphics.Bitmap bmp = ImageUtils.cargar(nota.getImagen(), 800);
        if (bmp != null) imgEditar.setImageBitmap(bmp);
        else imgEditar.setImageResource(android.R.drawable.ic_menu_gallery);

        panelLista.setVisibility(View.GONE);
        panelForm.setVisibility(View.VISIBLE);
    }

    private void cerrarFormulario() {
        // Si eligio una imagen nueva pero cancelo, se descarta
        ImageUtils.borrar(rutaImagenNueva);
        rutaImagenNueva = null;
        notaActual = null;
        panelForm.setVisibility(View.GONE);
        panelLista.setVisibility(View.VISIBLE);
        recargarLista();
    }

    private void procesarImagen(Uri uri) {
        String ruta = ImageUtils.guardarImagen(requireContext(), uri);
        if (ruta == null) {
            Toast.makeText(getContext(), "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        ImageUtils.borrar(rutaImagenNueva); // descarta una eleccion previa no guardada
        rutaImagenNueva = ruta;
        imgEditar.setImageBitmap(ImageUtils.cargar(ruta, 800));
    }

    private void actualizarNota() {
        String titulo = edtTitulo.getText().toString().trim();
        String desc = edtDescripcion.getText().toString().trim();

        if (titulo.isEmpty()) {
            edtTitulo.setError("El título es obligatorio");
            return;
        }

        String rutaFinal = notaActual.getImagen();
        if (rutaImagenNueva != null) {
            ImageUtils.borrar(notaActual.getImagen()); // elimina la imagen vieja
            rutaFinal = rutaImagenNueva;
            rutaImagenNueva = null;
        }

        db.actualizarNota(notaActual.getId(), titulo, desc, rutaFinal);
        Toast.makeText(getContext(), "Nota actualizada", Toast.LENGTH_SHORT).show();
        cerrarFormulario();
    }

    private void confirmarEliminar() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar nota")
                .setMessage("¿Seguro que deseas eliminar esta nota?")
                .setPositiveButton("Eliminar", (d, w) -> {
                    db.eliminarNota(notaActual.getId());
                    ImageUtils.borrar(notaActual.getImagen());
                    Toast.makeText(getContext(), "Nota eliminada", Toast.LENGTH_SHORT).show();
                    cerrarFormulario();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void recargarLista() {
        List<Nota> notas = db.obtenerNotas();
        adapter.actualizar(notas);
    }
}