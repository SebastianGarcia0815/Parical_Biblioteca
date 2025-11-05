package com.uniminuto.biblioteca;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Interfaz gráfica principal de la biblioteca.
 * Presenta inventario (JList), panel de detalles y botones para prestar/devolver.
 */
public class VentanaBiblioteca extends JFrame {

    private Biblioteca biblioteca;
    private DefaultListModel<Libro> modeloLibros;
    private JList<Libro> listaLibros;
    private JTextArea panelDetalle;
    private DefaultListModel<Prestamo> modeloPrestamos;
    private JList<Prestamo> listaPrestamos;
    private JTextField tfUsuario;
    private JSpinner spinnerDias;

    public VentanaBiblioteca() {
        biblioteca = new Biblioteca();
        initUI();
    }

    private void initUI() {
        setTitle("BibliotecaApp — Gestión de Préstamos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Panel superior: título y botón Salir ---
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(new EmptyBorder(12, 12, 12, 12));
        panelSuperior.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Biblioteca Reading World");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(new Color(20, 60, 100));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(220, 53, 69)); // rojo suave
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setPreferredSize(new Dimension(100, 40));

        btnSalir.addActionListener(e -> {
            int salida = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas salir de la biblioteca?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (salida == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                        this,
                        "👋 Gracias por visitar la Biblioteca Reading World",
                        "Salida",
                        JOptionPane.INFORMATION_MESSAGE
                );
                System.exit(0);
            }
        });

        panelSuperior.add(titulo, BorderLayout.CENTER);
        panelSuperior.add(btnSalir, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central: inventario a la izquierda, detalles y controles a la derecha
        JPanel centro = new JPanel(new GridLayout(1, 2, 12, 12));
        centro.setBorder(new EmptyBorder(12, 12, 12, 12));
        add(centro, BorderLayout.CENTER);

        // --- Panel izquierdo: lista de libros ---
        JPanel panelIzq = new JPanel(new BorderLayout(8, 8));
        panelIzq.setBackground(new Color(245, 248, 250));
        panelIzq.setBorder(BorderFactory.createTitledBorder("Inventario"));
        modeloLibros = new DefaultListModel<>();
        for (Libro l : biblioteca.getInventario()) modeloLibros.addElement(l);
        listaLibros = new JList<>(modeloLibros);
        listaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaLibros.addListSelectionListener(e -> mostrarDetalleLibro(listaLibros.getSelectedValue()));
        JScrollPane spLibros = new JScrollPane(listaLibros);
        panelIzq.add(spLibros, BorderLayout.CENTER);

        // Buscador simple
        JPanel buscador = new JPanel(new BorderLayout(6,6));
        JTextField tfBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(ae -> {
            String q = tfBuscar.getText().trim();
            if (q.isEmpty()) {
                actualizarListaCompleta();
            } else {
                List<Libro> encontrados = biblioteca.buscar(q);
                modeloLibros.clear();
                for (Libro x : encontrados) modeloLibros.addElement(x);
            }
        });
        buscador.add(tfBuscar, BorderLayout.CENTER);
        buscador.add(btnBuscar, BorderLayout.EAST);
        panelIzq.add(buscador, BorderLayout.SOUTH);

        centro.add(panelIzq);

        // --- Panel derecho: detalles y acciones ---
        JPanel panelDer = new JPanel(new BorderLayout(8,8));
        panelDer.setBackground(Color.WHITE);

        panelDetalle = new JTextArea();
        panelDetalle.setEditable(false);
        panelDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelDetalle.setLineWrap(true);
        panelDetalle.setWrapStyleWord(true);
        panelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle"));
        panelDer.add(new JScrollPane(panelDetalle), BorderLayout.CENTER);

        // Controles abajo
        JPanel acciones = new JPanel();
        acciones.setLayout(new GridBagLayout());
        acciones.setBorder(new EmptyBorder(8,8,8,8));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;

        acciones.add(new JLabel("Usuario:"), gbc);
        tfUsuario = new JTextField(12);
        gbc.gridx = 1; acciones.add(tfUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; acciones.add(new JLabel("Días préstamo:"), gbc);
        spinnerDias = new JSpinner(new SpinnerNumberModel(7, 1, 60, 1));
        gbc.gridx = 1; acciones.add(spinnerDias, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JButton btnPrestar = new JButton("Prestar");
        btnPrestar.setBackground(new Color(34, 139, 34));
        btnPrestar.setForeground(Color.WHITE);
        btnPrestar.addActionListener(ae -> acciónPrestar());
        acciones.add(btnPrestar, gbc);

        gbc.gridx = 1;
        JButton btnDevolver = new JButton("Devolver seleccionado");
        btnDevolver.setBackground(new Color(178, 34, 34));
        btnDevolver.setForeground(Color.WHITE);
        btnDevolver.addActionListener(ae -> acciónDevolver());
        acciones.add(btnDevolver, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnMostrarPrestamos = new JButton("Mostrar préstamos activos");
        btnMostrarPrestamos.addActionListener(ae -> mostrarPrestamosActivos());
        acciones.add(btnMostrarPrestamos, gbc);

        panelDer.add(acciones, BorderLayout.SOUTH);
        centro.add(panelDer);

        // --- Panel sur: historial ---
        JPanel sur = new JPanel(new BorderLayout());
        sur.setBorder(BorderFactory.createTitledBorder("Historial y mensajes"));
        modeloPrestamos = new DefaultListModel<>();
        listaPrestamos = new JList<>(modeloPrestamos);
        listaPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane spHist = new JScrollPane(listaPrestamos);
        spHist.setPreferredSize(new Dimension(100, 140));
        sur.add(spHist, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        // --- Barra lateral derecha ---
        JPanel east = new JPanel(new BorderLayout());
        east.setBorder(new EmptyBorder(12,12,12,12));
        east.setPreferredSize(new Dimension(200, 0));
        JLabel info = new JLabel("<html><h3>Instrucciones</h3>"
                + "<ul>"
                + "<li>Selecciona un libro</li>"
                + "<li>Escribe tu nombre</li>"
                + "<li>Elige días y presiona Prestar</li>"
                + "</ul></html>");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        east.add(info, BorderLayout.NORTH);
        add(east, BorderLayout.EAST);
    }

    private void actualizarListaCompleta() {
        modeloLibros.clear();
        for (Libro l : biblioteca.getInventario()) modeloLibros.addElement(l);
    }

    private void mostrarDetalleLibro(Libro l) {
        if (l == null) {
            panelDetalle.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(l.getTitulo()).append("\n");
        sb.append("Autor: ").append(l.getAutor()).append("\n");
        sb.append("ISBN: ").append(l.getIsbn()).append("\n");
        sb.append("Categoría: ").append(l.getCategoria()).append("\n");
        sb.append("Ejemplares: ").append(l.getEjemplares()).append("\n");
        sb.append("Prestados: ").append(l.getPrestados()).append("\n");
        sb.append("Disponibles: ").append(l.disponibles()).append("\n");
        panelDetalle.setText(sb.toString());
    }

    private void acciónPrestar() {
        int idx = listaLibros.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro primero.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String usuario = tfUsuario.getText().trim();
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe tu nombre de usuario.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int dias = (Integer) spinnerDias.getValue();
        String resultado = biblioteca.prestarLibro(idx, usuario, dias);
        JOptionPane.showMessageDialog(this, resultado);
        actualizarListaCompleta();
        refrescarHistorial();
    }

    private void acciónDevolver() {
        int idx = listaPrestamos.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo del historial (abajo).", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String resultado = biblioteca.devolverPrestamo(idx);
        JOptionPane.showMessageDialog(this, resultado);
        actualizarListaCompleta();
        refrescarHistorial();
    }

    private void mostrarPrestamosActivos() {
        modeloPrestamos.clear();
        for (Prestamo p : biblioteca.getPrestamosActivos()) {
            modeloPrestamos.addElement(p);
        }
    }

    private void refrescarHistorial() {
        modeloPrestamos.clear();
        for (Prestamo p : biblioteca.getHistorial()) {
            modeloPrestamos.addElement(p);
        }
    }
}
