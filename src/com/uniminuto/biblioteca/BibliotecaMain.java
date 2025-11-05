package com.uniminuto.biblioteca;

import javax.swing.*;

/**
 * Clase principal que lanza la interfaz gráfica con ventana de entrada y salida.
 */
public class BibliotecaMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Deseas entrar a la Biblioteca?",
                    "Biblioteca Reading World",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                VentanaBiblioteca vb = new VentanaBiblioteca();
                vb.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "👋 Hasta luego, vuelve pronto a la Biblioteca Reading World",
                        "Salir",
                        JOptionPane.INFORMATION_MESSAGE
                );
                System.exit(0);
            }
        });
    }
}
