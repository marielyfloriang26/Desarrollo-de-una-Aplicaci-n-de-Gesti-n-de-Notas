package com.example.gestornotas;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class VerFragment extends Fragment {

    private ListView lista;
    private TextView txtVacio;
    private NotasDbHelper db;
    private NotaAdapter adapter;

    public VerFragment() {
        super(R.layout.fragment_ver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new NotasDbHelper(requireContext());
        lista = view.findViewById(R.id.listaNotas);
        txtVacio = view.findViewById(R.id.txtVacio);

        List<Nota> notas = db.obtenerNotas();
        adapter = new NotaAdapter(requireContext(), notas);
        lista.setAdapter(adapter);
        txtVacio.setVisibility(notas.isEmpty() ? View.VISIBLE : View.GONE);
    }
}