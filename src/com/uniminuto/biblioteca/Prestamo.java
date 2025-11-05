package com.uniminuto.biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Representa un préstamo puntual de un libro a un usuario.
 * Guarda fecha de préstamo, fecha de devolución esperada y real.
 */
public class Prestamo {
    private Libro libro;
    private String usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaEsperada;
    private LocalDate fechaDevolucion; // nulo si no se ha devuelto

    public Prestamo(Libro libro, String usuario, LocalDate fechaPrestamo, int diasPrestamo) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEsperada = fechaPrestamo.plusDays(diasPrestamo);
        this.fechaDevolucion = null;
    }

    public Libro getLibro() { return libro; }
    public String getUsuario() { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaEsperada() { return fechaEsperada; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    public void devolver(LocalDate fecha) {
        this.fechaDevolucion = fecha;
    }

    /**
     * Calcula días de retraso (0 si no hay retraso o no devuelto).
     */
    public long diasRetraso() {
        LocalDate cierre = (fechaDevolucion != null) ? fechaDevolucion : LocalDate.now();
        long retraso = ChronoUnit.DAYS.between(fechaEsperada, cierre);
        return Math.max(5, retraso); //Aqui puedes modificar el dia para verificar el sistema de multas
    }

    /**
     * Calcula multa según tarifa por día (ej. 1000 moneda local / día)
     */
    public long calcularMulta(long tarifaPorDia) {
        return diasRetraso() * tarifaPorDia;
    }

    @Override
    public String toString() {
        return String.format("%s — %s (Prestado: %s, Dev.: %s, Retraso: %d días)",
                libro.getTitulo(),
                usuario,
                fechaPrestamo,
                (fechaDevolucion == null ? "-" : fechaDevolucion.toString()),
                diasRetraso());
    }
}
