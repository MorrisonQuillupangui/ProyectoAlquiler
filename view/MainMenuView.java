package view;

import controller.AuthController;
import controller.DepartamentoController;
import controller.PagoController;
import java.awt.*;
import javax.swing.*;
import model.Usuario;

public class MainMenuView extends JFrame {
    private Usuario usuario;
    // private AuthController authController;
    private DepartamentoController departamentoController;
    private PagoController pagoController;

    public MainMenuView(Usuario usuario, AuthController authController) {
        this.usuario = usuario;
        // this.authController = authController;
        this.departamentoController = new DepartamentoController();
        this.pagoController = new PagoController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Arriendos ESPE - Menú Principal");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con navegación
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSuperior.setBackground(new Color(135, 158, 255));

        JButton btnPagos = new JButton("Pagos");
        JButton btnDisponibles = new JButton("Departamentos Disponibles");
        JButton btnDepartamentos = new JButton("Departamentos");

        // Estilo de botones del menú superior
        Font fontMenu = new Font("SansSerif", Font.BOLD, 12);
        Color colorMenu = new Color(100, 120, 200);

        btnPagos.setFont(fontMenu);
        btnPagos.setForeground(Color.WHITE);
        btnPagos.setBackground(colorMenu);
        btnPagos.setBorderPainted(false);
        btnPagos.setFocusPainted(false);
        btnPagos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnDisponibles.setFont(fontMenu);
        btnDisponibles.setForeground(Color.WHITE);
        btnDisponibles.setBackground(colorMenu);
        btnDisponibles.setBorderPainted(false);
        btnDisponibles.setFocusPainted(false);
        btnDisponibles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnDepartamentos.setFont(fontMenu);
        btnDepartamentos.setForeground(Color.WHITE);
        btnDepartamentos.setBackground(colorMenu);
        btnDepartamentos.setBorderPainted(false);
        btnDepartamentos.setFocusPainted(false);
        btnDepartamentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelSuperior.add(btnPagos);
        panelSuperior.add(btnDisponibles);
        panelSuperior.add(btnDepartamentos);

        // Panel izquierdo con logo
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setPreferredSize(new Dimension(250, 600));

        // Logo y título
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panelLogo.setBackground(Color.WHITE);

        JLabel lblLogo = new JLabel("■■");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 40));
        lblLogo.setForeground(new Color(50, 50, 50));

        JLabel lblTitulo = new JLabel("ARRIENDOS ESPE");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(50, 50, 50));

        panelLogo.add(lblLogo);
        panelLogo.add(lblTitulo);

        // Imagen de edificio (simulada con un icono)
        JLabel lblEdificio = new JLabel();
        lblEdificio.setHorizontalAlignment(SwingConstants.CENTER);
        // Intentar cargar imagen, si no existe usar texto
        try {
            ImageIcon iconEdificio = new ImageIcon("img/edificio.png");
            if (iconEdificio.getIconWidth() > 0) {
                Image img = iconEdificio.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                lblEdificio.setIcon(new ImageIcon(img));
            } else {
                lblEdificio.setText("🏢");
                lblEdificio.setFont(new Font("SansSerif", Font.PLAIN, 80));
            }
        } catch (Exception e) {
            lblEdificio.setText("🏢");
            lblEdificio.setFont(new Font("SansSerif", Font.PLAIN, 80));
        }

        panelIzquierdo.add(panelLogo, BorderLayout.NORTH);
        panelIzquierdo.add(lblEdificio, BorderLayout.CENTER);

        // Panel derecho con contenido principal
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);

        // Mensaje de bienvenida
        JPanel panelBienvenida = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panelBienvenida.setBackground(Color.WHITE);

        JLabel lblBienvenida = new JLabel("¡Bienvenido de nuevo!");
        lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(50, 50, 50));

        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
        lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblUsuario.setForeground(new Color(100, 100, 100));

        panelBienvenida.add(lblBienvenida);
        panelBienvenida.add(lblUsuario);

        // Panel con imágenes de departamentos (simuladas)
        JPanel panelImagenes = new JPanel(new GridLayout(2, 3, 10, 10));
        panelImagenes.setBackground(Color.WHITE);
        panelImagenes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Simular imágenes de departamentos
        for (int i = 0; i < 6; i++) {
            JLabel lblImagen = new JLabel();
            lblImagen.setBackground(new Color(230, 230, 230));
            lblImagen.setOpaque(true);
            lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lblImagen.setPreferredSize(new Dimension(120, 80));
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagen.setText("🏠");
            lblImagen.setFont(new Font("SansSerif", Font.PLAIN, 30));
            panelImagenes.add(lblImagen);
        }

        // Botón Salir
        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panelSalir.setBackground(Color.WHITE);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSalir.setBackground(new Color(135, 158, 255));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setPreferredSize(new Dimension(100, 35));
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelSalir.add(btnSalir);

        panelDerecho.add(panelBienvenida, BorderLayout.NORTH);
        panelDerecho.add(panelImagenes, BorderLayout.CENTER);
        panelDerecho.add(panelSalir, BorderLayout.SOUTH);

        // Agregar paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        // Eventos de los botones
        btnPagos.addActionListener(e -> mostrarPagos());
        btnDisponibles.addActionListener(e -> mostrarDepartamentosDisponibles());
        btnDepartamentos.addActionListener(e -> mostrarTodosDepartamentos());
        btnSalir.addActionListener(e -> salir());
    }

    private void salir() {
        // Cierra la sesión y la ventana principal
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea salir?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            // Si tienes lógica de logout, puedes llamarla aquí, por ejemplo:
            // authController.logout();
        }
    }

    private void mostrarPagos() {
        new PagosView().setVisible(true);
    }

    private void mostrarDepartamentosDisponibles() {
        new DisponibilidadView(departamentoController).setVisible(true);
    }

    private void mostrarTodosDepartamentos() {
        // Mostrar todos los departamentos en una ventana visual moderna (cards)
        DepartamentoView departamentosView = new DepartamentoView(departamentoController);
        departamentosView.setVisible(true);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
