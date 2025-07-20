package view;

import controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterView extends JFrame {
    private JTextField txtNombre, txtApellido, txtCorreo;
    private JPasswordField txtContrasena;
    private AuthController authController;

    public RegisterView(AuthController authController) {
        this.authController = authController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro - Arriendos ESPE");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo con imagen
        JLabel panelIzquierdo = new JLabel(new ImageIcon("img/login-banner.png"));
        panelIzquierdo.setPreferredSize(new Dimension(350, 450));

        // Panel derecho con formulario
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        JLabel titulo = new JLabel("¡Crea tu cuenta!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(200, 35));
        txtNombre.setToolTipText("Nombre");
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));

        txtApellido = new JTextField();
        txtApellido.setPreferredSize(new Dimension(200, 35));
        txtApellido.setToolTipText("Apellido");
        txtApellido.setBorder(BorderFactory.createTitledBorder("Apellido"));

        txtCorreo = new JTextField();
        txtCorreo.setPreferredSize(new Dimension(200, 35));
        txtCorreo.setToolTipText("Correo electrónico");
        txtCorreo.setBorder(BorderFactory.createTitledBorder("Correo electrónico"));

        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(200, 35));
        txtContrasena.setToolTipText("Contraseña");
        txtContrasena.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        // Panel para contraseña con botón de mostrar/ocultar
        JPanel panelContrasena = new JPanel(new BorderLayout());
        panelContrasena.setBackground(Color.WHITE);
        JButton btnMostrarContrasena = new JButton("👁");
        btnMostrarContrasena.setPreferredSize(new Dimension(35, 35));
        btnMostrarContrasena.setBorderPainted(false);
        btnMostrarContrasena.setContentAreaFilled(false);
        btnMostrarContrasena.setFocusPainted(false);
        btnMostrarContrasena.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        panelContrasena.add(txtContrasena, BorderLayout.CENTER);
        panelContrasena.add(btnMostrarContrasena, BorderLayout.EAST);

        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBackground(new Color(92, 136, 255));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        JLabel yaRegistrado = new JLabel("¿Ya tienes una cuenta? ");
        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setForeground(new Color(0, 102, 204));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Agregar componentes al panel derecho
        gbc.gridy = 0;
        panelDerecho.add(titulo, gbc);
        gbc.gridy++;
        panelDerecho.add(txtNombre, gbc);
        gbc.gridy++;
        panelDerecho.add(txtApellido, gbc);
        gbc.gridy++;
        panelDerecho.add(txtCorreo, gbc);
        gbc.gridy++;
        panelDerecho.add(panelContrasena, gbc);
        gbc.gridy++;
        panelDerecho.add(btnRegistrar, gbc);
        gbc.gridy++;
        JPanel panelLogin = new JPanel(new FlowLayout());
        panelLogin.setBackground(Color.WHITE);
        panelLogin.add(yaRegistrado);
        panelLogin.add(btnLogin);
        panelDerecho.add(panelLogin, gbc);

        // Acción del botón mostrar/ocultar contraseña
        btnMostrarContrasena.addActionListener(e -> {
            if (txtContrasena.getEchoChar() == 0) {
                txtContrasena.setEchoChar('*');
                btnMostrarContrasena.setText("👁");
            } else {
                txtContrasena.setEchoChar((char) 0);
                btnMostrarContrasena.setText("🙈");
            }
        });

        // Acción del botón registrar
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String correo = txtCorreo.getText().trim();
                String pass = new String(txtContrasena.getPassword());

                // Validar campos vacíos
                if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
                    return;
                }

                // Debug: mostrar datos de registro
                System.out.println("Registrando usuario:");
                System.out.println("Nombre: '" + nombre + "'");
                System.out.println("Apellido: '" + apellido + "'");
                System.out.println("Correo: '" + correo + "'");
                System.out.println("Contraseña: '" + pass + "'");

                boolean ok = authController.registrarUsuario(nombre, apellido, correo, pass);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "¡Registro exitoso!\nYa puede iniciar sesión con sus credenciales");
                    dispose();
                    new LoginView(authController).setVisible(true); // Pasar el mismo AuthController
                } else {
                    JOptionPane.showMessageDialog(null, "El correo ya está registrado");
                }
            }
        });

        // Acción del botón login
        btnLogin.addActionListener(e -> {
            dispose();
            new LoginView(authController).setVisible(true); // Pasar el mismo AuthController
        });

        // Añadir los dos paneles al frame
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }
}
