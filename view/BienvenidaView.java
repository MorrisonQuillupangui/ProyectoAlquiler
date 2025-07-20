package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BienvenidaView extends JFrame {
    public BienvenidaView() {
        setTitle("Programación Orientada a Objetos");
        setSize(600, 400);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Imagen
        ImageIcon icono = new ImageIcon("img/casa.png");
        Image imgEscalada = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imagenLabel = new JLabel(new ImageIcon(imgEscalada));
        imagenLabel.setHorizontalAlignment(JLabel.CENTER);

        // Título y subtítulo
        JLabel titulo = new JLabel("Programación Orientada a Objetos", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(0, 102, 204));

        JLabel subtitulo = new JLabel("Sistema de gestión de departamentos", JLabel.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(60, 60, 60));

        // Botón
        JButton btnIngresar = new JButton("Ingresar a la aplicación");
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(0, 123, 255));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnIngresar.setPreferredSize(new Dimension(220, 40));
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new LoginView().setVisible(true);
            }
        });

        // Panel para botones y textos
        JPanel textoPanel = new JPanel(new GridLayout(3, 1));
        textoPanel.setBackground(Color.WHITE);
        textoPanel.add(titulo);
        textoPanel.add(subtitulo);
        textoPanel.add(btnIngresar);

        panel.add(imagenLabel, BorderLayout.CENTER);
        panel.add(textoPanel, BorderLayout.SOUTH);

        add(panel);
    }
}
