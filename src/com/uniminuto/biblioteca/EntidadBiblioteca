package com.uniminuto.biblioteca;

import java.awt.Color;

/**
 * Clase base para elementos de la biblioteca (en este caso libros).
 */
public class EntidadBiblioteca {
    protected String titulo;
    protected String autor;
    protected String isbn;
    protected boolean disponible;
    protected Color color; // para representación visual si hace falta

    public EntidadBiblioteca(String titulo, String autor, String isbn, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = disponible;
        this.color = Color.LIGHT_GRAY;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
