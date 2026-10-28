package com.example.gestornotas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.addTab(tabs.newTab().setText("Agregar Nota"));
        tabs.addTab(tabs.newTab().setText("Ver Notas"));
        tabs.addTab(tabs.newTab().setText("Editar Notas"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: mostrar(new AgregarFragment()); break;
                    case 1: mostrar(new VerFragment()); break;
                    case 2: mostrar(new EditarFragment()); break;
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });

        if (savedInstanceState == null) {
            mostrar(new AgregarFragment());
        }
    }

    private void mostrar(Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, f)
                .commit();
    }
}