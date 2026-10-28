package com.example.gestornotas;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotaAdapter extends BaseAdapter {

    private final Context context;
    private List<Nota> notas;

    public NotaAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void actualizar(List<Nota> nuevas) {
        this.notas = nuevas;
        notifyDataSetChanged();
    }

    @Override public int getCount() { return notas.size(); }
    @Override public Object getItem(int i) { return notas.get(i); }
    @Override public long getItemId(int i) { return notas.get(i).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        }
        Nota n = notas.get(position);
        ImageView img = convertView.findViewById(R.id.imgItem);
        TextView titulo = convertView.findViewById(R.id.txtItemTitulo);
        TextView desc = convertView.findViewById(R.id.txtItemDescripcion);

        titulo.setText(n.getTitulo());
        desc.setText(n.getDescripcion());

        Bitmap bmp = ImageUtils.cargar(n.getImagen(), 200);
        if (bmp != null) {
            img.setImageBitmap(bmp);
        } else {
            img.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        return convertView;
    }
}