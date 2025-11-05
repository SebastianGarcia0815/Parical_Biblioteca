package com.uniminuto.biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la colección de libros y préstamos
 * Contiene métodos para prestar, devolver y consultar inventario.
 */
public class Biblioteca {
    private List<Libro> inventario;
    private List<Prestamo> prestamos;
    private final long tarifaPorDia = 1000; // ejemplo: 1000 moneda local por día de retraso

    public Biblioteca() {
        inventario = new ArrayList<>();
        prestamos = new ArrayList<>();
        cargarEjemplos();
    }

    public List<Libro> getInventario() { return inventario; }
    public List<Prestamo> getPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) if (p.getFechaDevolucion() == null) activos.add(p);
        return activos;
    }
    public List<Prestamo> getHistorial() { return prestamos; }

    /**
     * Agrega libros de ejemplo (varios géneros y programación).
     */
    private void cargarEjemplos() {
        inventario.add(new Libro("Introducción a Java", "Herbert Schildt", "978-0135166307", "Programación", 3));
        inventario.add(new Libro("Java: Guía de referencia", "Flanagan", "978-1492072508", "Programación", 2));
        inventario.add(new Libro("Clean Code", "Robert C. Martin", "978-0132350884", "Programación", 4));
        inventario.add(new Libro("Cien años de soledad", "Gabriel García Márquez", "978-0307474728", "Novela", 2));
        inventario.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "978-8491050297", "Clásico", 2));
        inventario.add(new Libro("Bajo la misma estrella", "John Green", "978-8332632830", "Romance", 5));
    }

    /**
     * Intenta prestar un libro a un usuario. Devuelve mensaje con resultado.
     */
    public synchronized String prestarLibro(int indiceLibro, String usuario, int dias) {
        if (indiceLibro < 0 || indiceLibro >= inventario.size()) return "Índice de libro inválido.";
        Libro libro = inventario.get(indiceLibro);
        if (!libro.prestar()) return "No hay ejemplares disponibles de ese libro.";
        Prestamo p = new Prestamo(libro, usuario, LocalDate.now(), dias);
        prestamos.add(p);
        return "Préstamo realizado: " + libro.getTitulo() + " a " + usuario + " hasta " + p.getFechaEsperada();
    }

    /**
     * Intenta devolver el libro asociado al préstamo dado. Calcula multa si hay retraso.
     */
    public synchronized String devolverPrestamo(int indicePrestamo) {
        if (indicePrestamo < 0 || indicePrestamo >= prestamos.size()) return "Préstamo inválido.";
        Prestamo p = prestamos.get(indicePrestamo);
        if (p.getFechaDevolucion() != null) return "Este préstamo ya fue devuelto.";
        p.devolver(LocalDate.now());
        p.getLibro().devolver();
        long multa = p.calcularMulta(tarifaPorDia);
        if (multa > 0) return "Libro devuelto. Multa: " + multa + " (por " + p.diasRetraso() + " días de retraso).";
        return "Libro devuelto a tiempo. Gracias.";
    }

    /**
     * Buscar libros por texto en título o autor
     */
    public List<Libro> buscar(String texto) {
        List<Libro> res = new ArrayList<>();
        String t = texto.toLowerCase();
        for (Libro l : inventario) {
            if (l.getTitulo().toLowerCase().contains(t) || l.getAutor().toLowerCase().contains(t) || l.getCategoria().toLowerCase().contains(t)) {
                res.add(l);
            }
        }
        return res;
    }

    void mostrarInventario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void prestarMaterial(String codP) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void devolverMaterial(String codD) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
