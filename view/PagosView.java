package view;

import controller.PagoController;
import controller.PagoController.PagoExtendido;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.Pago;

public class PagosView extends JFrame {
    private PagoController pagoController;
    private JPanel panelPagos;
    private JScrollPane scrollPane;

    // Campos de búsqueda
    private JTextField txtBuscar;
    private JComboBox<String> cbEstado;
    private JTextField txtDepartamento;
    private JComboBox<String> cbMantenimiento;

    public PagosView() {
        this.pagoController = new PagoController();
        initComponents();
        cargarPagos(null, "Todos", null, "Todos");
    }

    private void initComponents() {
        setTitle("Gestión de Pagos - Arriendos ESPE");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Pagos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(50, 50, 50));
        panelSuperior.add(titulo, BorderLayout.WEST);

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelFiltros.setBackground(Color.WHITE);

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(120, 30));
        txtBuscar.setToolTipText("Buscar inquilino");

        cbEstado = new JComboBox<>(new String[]{"Todos", "Pagado", "Mora"});
        cbEstado.setPreferredSize(new Dimension(100, 30));

        txtDepartamento = new JTextField();
        txtDepartamento.setPreferredSize(new Dimension(120, 30));
        txtDepartamento.setToolTipText("Departamento");

        cbMantenimiento = new JComboBox<>(new String[]{"Todos", "Sí", "No"});
        cbMantenimiento.setPreferredSize(new Dimension(100, 30));

        JButton btnBuscar = new JButton("🔍");
        btnBuscar.setPreferredSize(new Dimension(40, 30));
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelFiltros.add(new JLabel("Inquilino:"));
        panelFiltros.add(txtBuscar);
        panelFiltros.add(new JLabel("Estado:"));
        panelFiltros.add(cbEstado);
        panelFiltros.add(new JLabel("Departamento:"));
        panelFiltros.add(txtDepartamento);
        panelFiltros.add(new JLabel("Mantenimiento:"));
        panelFiltros.add(cbMantenimiento);
        panelFiltros.add(btnBuscar);

        panelSuperior.add(panelFiltros, BorderLayout.EAST);

        // Panel central
        panelPagos = new JPanel(new GridLayout(0, 3, 15, 15));
        panelPagos.setBackground(Color.WHITE);
        panelPagos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        scrollPane = new JScrollPane(panelPagos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnAgregarPago = new JButton("Agregar pago");
        btnAgregarPago.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAgregarPago.setBackground(new Color(92, 136, 255));
        btnAgregarPago.setForeground(Color.WHITE);
        btnAgregarPago.setPreferredSize(new Dimension(150, 40));
        btnAgregarPago.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelInferior.add(btnAgregarPago, BorderLayout.WEST);

        // Eventos
        btnAgregarPago.addActionListener(e -> mostrarDialogoAgregarPago());
        btnBuscar.addActionListener(e -> filtrar());

        // Componentes al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void filtrar() {
        String texto = txtBuscar.getText();
        String estado = (String) cbEstado.getSelectedItem();
        String departamento = txtDepartamento.getText();
        String mantenimiento = (String) cbMantenimiento.getSelectedItem();

        cargarPagos(texto, estado, departamento, mantenimiento);
    }

    private void cargarPagos(String texto, String estado, String departamento, String mantenimiento) {
        panelPagos.removeAll();
        List<Pago> pagos;
        if (texto == null && estado.equals("Todos") && (departamento == null || departamento.isEmpty()) && mantenimiento.equals("Todos")) {
            pagos = pagoController.listarTodos();
        } else {
            pagos = pagoController.filtrarPagos(texto, estado, departamento, mantenimiento);
        }

        for (int i = 0; i < pagos.size(); i++) {
            Pago pago = pagos.get(i);
            JPanel cardPago = crearCardPago(pago, i);
            panelPagos.add(cardPago);
        }
        panelPagos.revalidate();
        panelPagos.repaint();
    }

    private JPanel crearCardPago(Pago pago, int index) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(135, 158, 255, 100));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(135, 158, 255), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(320, 220));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0,0,0,0));
        JLabel lblId = new JLabel("Departamento " + String.format("%02d", index + 1));
        JLabel lblEstado = new JLabel("Estado: " + pago.getEstado());
        lblEstado.setForeground(pago.getEstado().equalsIgnoreCase("Mora") ? Color.RED : new Color(0, 120, 0));
        panelSuperior.add(lblId, BorderLayout.WEST);
        panelSuperior.add(lblEstado, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(92, 136, 255));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelSuperior.add(btnActualizar, BorderLayout.EAST);

        btnActualizar.addActionListener(e -> mostrarDialogoEditarPago(pago, index));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(0,0,0,0));

        if (pago instanceof PagoExtendido) {
            PagoExtendido ext = (PagoExtendido) pago;
            panelInfo.add(new JLabel("Fecha contrato: " + ext.getFechaContrato()));
            panelInfo.add(new JLabel("Valor alquiler: $" + String.format("%.2f", ext.getValorAlquiler())));
            panelInfo.add(new JLabel("Departamento: " + ext.getDepartamento()));
            panelInfo.add(new JLabel("Mantenimiento: " + (ext.isRequiereMantenimiento() ? "Sí" : "No")));
            if (pago.getEstado().equalsIgnoreCase("Mora")) {
                panelInfo.add(new JLabel("Tiempo de mora: " + ext.getMesesMora() + " meses"));
                panelInfo.add(new JLabel("Monto adeudado: $" + String.format("%.2f", ext.getMontoAdeudado())));
            }
            panelInfo.add(new JLabel("Fecha de pago: " + ext.getFechaPago()));
        }

        panelInfo.add(new JLabel("Inquilino: " + pago.getInquilino()));
        panelInfo.add(new JLabel("Contacto: " + pago.getContacto()));

        card.add(panelSuperior, BorderLayout.NORTH);
        card.add(panelInfo, BorderLayout.CENTER);

        return card;
    }

    private void mostrarDialogoAgregarPago() {
        JDialog dialog = new JDialog(this, "Agregar Nuevo Pago", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtInquilino = new JTextField(20);
        ((AbstractDocument) txtInquilino.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        JTextField txtContacto = new JTextField(20);
        JComboBox<String> cbEstadoDialog = new JComboBox<>(new String[]{"Pagado", "Mora"});
        JTextField txtFecha = new JTextField(LocalDate.now().toString());
        JTextField txtDepartamentoNuevo = new JTextField(20);
        JCheckBox chkMantenimiento = new JCheckBox("Requiere mantenimiento");

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Inquilino:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtInquilino, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtContacto, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        dialog.add(cbEstadoDialog, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dialog.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtDepartamentoNuevo, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(chkMantenimiento, gbc);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(92, 136, 255));
        btnGuardar.setForeground(Color.WHITE);

        gbc.gridy = 6;
        dialog.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                String inquilino = txtInquilino.getText();
                String contacto = txtContacto.getText();
                String estado = (String) cbEstadoDialog.getSelectedItem();
                LocalDate fecha = LocalDate.parse(txtFecha.getText());
                String departamento = txtDepartamentoNuevo.getText();
                boolean requiereMantenimiento = chkMantenimiento.isSelected();

                if (inquilino.trim().isEmpty() || contacto.trim().isEmpty() || departamento.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor complete todos los campos");
                    return;
                }

                PagoExtendido nuevoPago = new PagoExtendido(
                    estado, fecha, inquilino, contacto,
                    LocalDate.now(), 400.0, estado.equals("Mora") ? 2 : 0, estado.equals("Mora") ? 800.0 : 0.0,
                    departamento, requiereMantenimiento
                );

                pagoController.registrarPago(nuevoPago);
                cargarPagos(null, "Todos", null, "Todos");
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Pago agregado exitosamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

    private void mostrarDialogoEditarPago(Pago pago, int index) {
        JDialog dialog = new JDialog(this, "Actualizar Pago", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos básicos
        JTextField txtInquilino = new JTextField(pago.getInquilino(), 20);
        ((AbstractDocument) txtInquilino.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        JTextField txtContacto = new JTextField(pago.getContacto(), 20);
        JComboBox<String> cbEstadoDialog = new JComboBox<>(new String[]{"Pagado", "Mora"});
        cbEstadoDialog.setSelectedItem(pago.getEstado());
        JTextField txtFecha = new JTextField(pago.getFechaPago().toString());
        JTextField txtDepartamentoNuevo = new JTextField(
            (pago instanceof PagoExtendido) ? ((PagoExtendido) pago).getDepartamento() : "", 20
        );
        JCheckBox chkMantenimiento = new JCheckBox("Requiere mantenimiento",
            (pago instanceof PagoExtendido) && ((PagoExtendido) pago).isRequiereMantenimiento());

        // Campos adicionales para Mora
        final JTextField txtMesesMora = new JTextField(20);
        final JTextField txtMontoAdeudado = new JTextField(20);
        final JPanel panelMoraCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        panelMoraCampos.setBackground(Color.WHITE);
        panelMoraCampos.setVisible(false);

        panelMoraCampos.add(new JLabel("Meses de mora:"));
        panelMoraCampos.add(txtMesesMora);
        panelMoraCampos.add(new JLabel("Monto adeudado ($):"));
        panelMoraCampos.add(txtMontoAdeudado);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Inquilino:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtInquilino, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtContacto, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        dialog.add(cbEstadoDialog, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dialog.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtDepartamentoNuevo, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(chkMantenimiento, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        dialog.add(panelMoraCampos, gbc);

        // Botón guardar
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(92, 136, 255));
        btnGuardar.setForeground(Color.WHITE);

        gbc.gridy = 7;
        dialog.add(btnGuardar, gbc);

        // Listener para mostrar/ocultar campos de mora
        cbEstadoDialog.addActionListener(e -> {
            String seleccionado = (String) cbEstadoDialog.getSelectedItem();
            boolean esMora = "Mora".equalsIgnoreCase(seleccionado);
            panelMoraCampos.setVisible(esMora);
            dialog.pack();
        });

        // Inicializar campos de mora si ya está en mora
        if (pago instanceof PagoExtendido && "Mora".equalsIgnoreCase(pago.getEstado())) {
            PagoExtendido ext = (PagoExtendido) pago;
            txtMesesMora.setText(String.valueOf(ext.getMesesMora()));
            txtMontoAdeudado.setText(String.format("%.2f", ext.getMontoAdeudado()));
            panelMoraCampos.setVisible(true);
        }

        btnGuardar.addActionListener(e -> {
            try {
                pagoController.actualizarPago(index,
                    (String) cbEstadoDialog.getSelectedItem(),
                    LocalDate.parse(txtFecha.getText()),
                    txtInquilino.getText(),
                    txtContacto.getText());

                if (pago instanceof PagoExtendido) {
                    ((PagoExtendido) pago).setDepartamento(txtDepartamentoNuevo.getText());
                    ((PagoExtendido) pago).setRequiereMantenimiento(chkMantenimiento.isSelected());

                    if ("Mora".equalsIgnoreCase((String) cbEstadoDialog.getSelectedItem())) {
                        try {
                            int meses = Integer.parseInt(txtMesesMora.getText().trim());
                            double monto = Double.parseDouble(txtMontoAdeudado.getText().trim());
                            ((PagoExtendido) pago).setMesesMora(meses);
                            ((PagoExtendido) pago).setMontoAdeudado(monto);
                        } catch (NumberFormatException ex2) {
                            JOptionPane.showMessageDialog(dialog, "Ingrese valores numéricos válidos para mora y monto.");
                            return;
                        }
                    } else {
                        // Si no es mora, limpiar
                        ((PagoExtendido) pago).setMesesMora(0);
                        ((PagoExtendido) pago).setMontoAdeudado(0.0);
                    }
                }

                cargarPagos(null, "Todos", null, "Todos");
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Pago actualizado exitosamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

}
