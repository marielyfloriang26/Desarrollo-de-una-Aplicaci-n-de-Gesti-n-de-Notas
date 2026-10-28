package com.example.gestornotas;

public class Nota {
    private int id;
    private String titulo;
    private String descripcion;
    private String imagen; // ruta del archivo

    public Nota(int id, String titulo, String descripcion, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
}