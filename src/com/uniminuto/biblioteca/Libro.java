package com.uniminuto.biblioteca;

/**
 * Representa un libro físico de la biblioteca.
 * Hereda de EntidadBiblioteca y añade categoría (ej. Programación, Novela).
 */
public class Libro extends EntidadBiblioteca {
    private String categoria;
    private int ejemplares; // número total de ejemplares
    private int prestados;   // cuántos están prestados

    public Libro(String titulo, String autor, String isbn, String categoria, int ejemplares) {
        super(titulo, autor, isbn, ejemplares > 0);
        this.categoria = categoria;
        this.ejemplares = Math.max(0, ejemplares);
        this.prestados = 0;
    }

    public String getCategoria() { return categoria; }
    public int getEjemplares() { return ejemplares; }
    public int getPrestados() { return prestados; }

    public int disponibles() {
        return ejemplares - prestados;
    }

    /**
     * Intenta prestar una copia. Devuelve true si tuvo éxito.
     */
    public boolean prestar() {
        if (disponibles() > 0) {
            prestados++;
            this.disponible = disponibles() > 0;
            return true;
        }
        return false;
    }

    /**
     * Devuelve una copia. Devuelve true si hubo una devolución válida.
     */
    public boolean devolver() {
        if (prestados > 0) {
            prestados--;
            this.disponible = disponibles() > 0;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s — %s (%s) [%s] • Disp: %d", titulo, autor, categoria, isbn, disponibles());
    }
}

