package view;

import controller.AuthController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private AuthController authController;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;

    public LoginView() {
        authController = new AuthController();
        initComponents();
    }

    // Constructor para recibir AuthController existente
    public LoginView(AuthController authController) {
        this.authController = authController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Arriendos ESPE");
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

        JLabel titulo = new JLabel("¡Bienvenido de nuevo!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));

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

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(new Color(92, 136, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        JLabel registrarse = new JLabel("¿Todavía no tienes una cuenta? ");
        JButton btnRegistrar = new JButton("Regístrate");
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setContentAreaFilled(false);
        btnRegistrar.setForeground(new Color(0, 102, 204));
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Agregar componentes al panel derecho
        gbc.gridy = 0;
        panelDerecho.add(titulo, gbc);
        gbc.gridy++;
        panelDerecho.add(txtCorreo, gbc);
        gbc.gridy++;
        panelDerecho.add(panelContrasena, gbc);
        gbc.gridy++;
        panelDerecho.add(btnLogin, gbc);
        gbc.gridy++;
        JPanel panelRegistro = new JPanel(new FlowLayout());
        panelRegistro.setBackground(Color.WHITE);
        panelRegistro.add(registrarse);
        panelRegistro.add(btnRegistrar);
        panelDerecho.add(panelRegistro, gbc);

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

        // Acción del botón login
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String correo = txtCorreo.getText().trim();
                String pass = new String(txtContrasena.getPassword());
                
                // Debug: mostrar datos ingresados
                System.out.println("Intentando login con:");
                System.out.println("Correo: '" + correo + "'");
                System.out.println("Contraseña: '" + pass + "'");
                
                Usuario usuario = authController.login(correo, pass);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
                    dispose();
                    new MainMenuView(usuario, authController).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas\nVerifique su correo y contraseña");
                }
            }
        });

        // Acción del botón registrar
        btnRegistrar.addActionListener(e -> {
            dispose();
            new RegisterView(authController).setVisible(true);
        });

        // Añadir los dos paneles al frame
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);
    }
}
