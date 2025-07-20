package view;

import controller.DepartamentoController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import model.Departamento;



public class DisponibilidadView extends JFrame {
    private final DepartamentoController departamentoController;
    private JPanel panelDepartamentos;
    private JTextField txtBuscar;
    private JScrollPane scrollPane;
    private JTextField txtId;
    private JComboBox<String> cbComparadorValor;
    private JTextField txtValor;
    private JTextField txtHabitaciones;
    private JTextField txtBanios;
    private JTextField txtPiso;
    private JComboBox<String> cbMantenimiento;

    public DisponibilidadView(DepartamentoController departamentoController) {
        this.departamentoController = departamentoController;
        initComponents();
        cargarDepartamentosDisponibles();
    }

    private void initComponents() {
        setTitle("Departamentos Disponibles - Arriendos ESPE");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Disponibilidad");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(new Color(50, 50, 50));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFiltros.setBackground(Color.WHITE);

        txtId = new JTextField();
        txtId.setPreferredSize(new Dimension(60, 25));
        txtId.setToolTipText("ID (ej. D091)");

        cbComparadorValor = new JComboBox<>(new String[]{"<", "=", ">"});
        cbComparadorValor.setPreferredSize(new Dimension(40, 25));
        txtValor = new JTextField();
        txtValor.setPreferredSize(new Dimension(60, 25));
        txtValor.setToolTipText("Valor arriendo");

        txtHabitaciones = new JTextField();
        txtHabitaciones.setPreferredSize(new Dimension(50, 25));
        txtHabitaciones.setToolTipText("Habitaciones");

        txtBanios = new JTextField();
        txtBanios.setPreferredSize(new Dimension(50, 25));
        txtBanios.setToolTipText("Baños");

        txtPiso = new JTextField();
        txtPiso.setPreferredSize(new Dimension(50, 25));
        txtPiso.setToolTipText("Plantas");

        cbMantenimiento = new JComboBox<>(new String[]{"Todos", "Sí", "No"});
        cbMantenimiento.setPreferredSize(new Dimension(70, 25));

        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setBackground(new Color(92, 136, 255));
        btnBuscar.setForeground(Color.WHITE);

        panelFiltros.add(new JLabel("ID:"));
        panelFiltros.add(txtId);
        panelFiltros.add(cbComparadorValor);
        panelFiltros.add(txtValor);
        panelFiltros.add(new JLabel("Hab:"));
        panelFiltros.add(txtHabitaciones);
        panelFiltros.add(new JLabel("Baños:"));
        panelFiltros.add(txtBanios);
        panelFiltros.add(new JLabel("Piso:"));
        panelFiltros.add(txtPiso);
        panelFiltros.add(new JLabel("Mantenimiento:"));
        panelFiltros.add(cbMantenimiento);
        panelFiltros.add(btnBuscar);

        panelSuperior.add(titulo, BorderLayout.WEST);
        panelSuperior.add(panelFiltros, BorderLayout.EAST);

        panelDepartamentos = new JPanel(new GridLayout(0, 3, 15, 15));
        panelDepartamentos.setBackground(Color.WHITE);
        panelDepartamentos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        scrollPane = new JScrollPane(panelDepartamentos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarDepartamentos());
    }


    // Método para buscar departamentos por ID, propietario o contacto
        private void buscarDepartamentos() {
        String id = txtId.getText().trim().toLowerCase();
        String comparador = (String) cbComparadorValor.getSelectedItem();
        String valorStr = txtValor.getText().trim();
        String habitacionesStr = txtHabitaciones.getText().trim();
        String baniosStr = txtBanios.getText().trim();
        String pisoStr = txtPiso.getText().trim();
        String mantenimiento = (String) cbMantenimiento.getSelectedItem();

        panelDepartamentos.removeAll();
        List<Departamento> disponibles = departamentoController.listarDisponibles();
        for (Departamento depto : disponibles) {
            boolean coincide = true;

            if (!id.isEmpty() && !depto.getId().toLowerCase().contains(id)) {
                coincide = false;
            }

            if (!valorStr.isEmpty()) {
                try {
                    double valorFiltro = Double.parseDouble(valorStr);
                    double valorDepto = depto.getValor();
                    switch (comparador) {
                        case "<" -> { if (!(valorDepto < valorFiltro)) coincide = false; }
                        case "=" -> { if (!(valorDepto == valorFiltro)) coincide = false; }
                        case ">" -> { if (!(valorDepto > valorFiltro)) coincide = false; }
                    }
                } catch (NumberFormatException ignored) {}
            }

            if (!habitacionesStr.isEmpty()) {
                try {
                    int habFiltro = Integer.parseInt(habitacionesStr);
                    if (depto.getHabitaciones() != habFiltro) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!baniosStr.isEmpty()) {
                try {
                    int banioFiltro = Integer.parseInt(baniosStr);
                    if (depto.getBanios() != banioFiltro) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!pisoStr.isEmpty()) {
                try {
                    int pisoFiltro = Integer.parseInt(pisoStr);
                    if (depto.getPiso() != pisoFiltro) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!"Todos".equals(mantenimiento)) {
                boolean necesita = depto.isNecesitaMantenimiento();
                if ("Sí".equals(mantenimiento) && !necesita) coincide = false;
                if ("No".equals(mantenimiento) && necesita) coincide = false;
            }

            if (coincide) {
                JPanel cardDepto = crearCardDepartamento(depto);
                panelDepartamentos.add(cardDepto);
            }
        }

        panelDepartamentos.revalidate();
        panelDepartamentos.repaint();
    }


    private void cargarDepartamentosDisponibles() {
        panelDepartamentos.removeAll();
        List<Departamento> disponibles = departamentoController.listarDisponibles();
        for (Departamento depto : disponibles) {
            JPanel cardDepto = crearCardDepartamento(depto);
            panelDepartamentos.add(cardDepto);
        }
        panelDepartamentos.revalidate();
        panelDepartamentos.repaint();
    }


    private JPanel crearCardDepartamento(Departamento depto) {
        JPanel card = new JPanel(new BorderLayout());
        Color bgColor = depto.isNecesitaMantenimiento() ? new Color(255, 120, 120, 180) : new Color(135, 158, 255, 100);
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(depto.isNecesitaMantenimiento() ? Color.RED : new Color(135, 158, 255), 2),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(320, 240));

        // Panel superior con ID y propietario
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0, 0, 0, 0));
        JLabel lblId = new JLabel(depto.getId());
        lblId.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblId.setForeground(new Color(50, 50, 50));
        JLabel lblPropietario = new JLabel("Propietario: " + depto.getPropietario());
        lblPropietario.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panelSuperior.add(lblId, BorderLayout.WEST);
        panelSuperior.add(lblPropietario, BorderLayout.EAST);

        // Panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(0, 0, 0, 0));
        panelInfo.add(new JLabel("Valor de arriendo: $" + depto.getValor()));
        panelInfo.add(new JLabel("Habitaciones: " + depto.getHabitaciones()));
        panelInfo.add(new JLabel("Baños: " + depto.getBanios()));
        panelInfo.add(new JLabel("Plantas: " + depto.getPiso()));
        panelInfo.add(new JLabel("Contacto: " + depto.getContacto()));
        panelInfo.add(new JLabel("Mantenimiento: " + (depto.isNecesitaMantenimiento() ? "Sí" : "No")));

        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(92, 136, 255));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> mostrarDialogoEditarDepartamento(depto));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnActualizar);

        card.add(panelSuperior, BorderLayout.NORTH);
        card.add(panelInfo, BorderLayout.CENTER);
        card.add(panelBoton, BorderLayout.SOUTH);

        return card;
    }

    private void mostrarDialogoEditarDepartamento(Departamento depto) {
        JDialog dialog = new JDialog(this, "Editar Departamento", true);
        dialog.setSize(450, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel(depto.getId());
        JTextField txtValor = new JTextField(String.valueOf(depto.getValor()));
        JSpinner spnHabitaciones = new JSpinner(new SpinnerNumberModel(depto.getHabitaciones(), 1, 10, 1));
        JSpinner spnBanios = new JSpinner(new SpinnerNumberModel(depto.getBanios(), 1, 5, 1));
        JCheckBox chkParqueadero = new JCheckBox("Tiene parqueadero", depto.tieneParqueadero());
        JSpinner spnPiso = new JSpinner(new SpinnerNumberModel(depto.getPiso(), 1, 20, 1));
        JTextField txtPropietario = new JTextField(depto.getPropietario());
        JTextField txtContacto = new JTextField(depto.getContacto());
        JCheckBox chkMantenimiento = new JCheckBox("Necesita mantenimiento", depto.isNecesitaMantenimiento());

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(lblId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtValor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Habitaciones:"), gbc);
        gbc.gridx = 1;
        dialog.add(spnHabitaciones, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Baños:"), gbc);
        gbc.gridx = 1;
        dialog.add(spnBanios, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Parqueadero:"), gbc);
        gbc.gridx = 1;
        dialog.add(chkParqueadero, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(new JLabel("Piso:"), gbc);
        gbc.gridx = 1;
        dialog.add(spnPiso, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        dialog.add(new JLabel("Propietario:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtPropietario, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        dialog.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtContacto, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        dialog.add(chkMantenimiento, gbc);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(92, 136, 255));
        btnGuardar.setForeground(Color.WHITE);

        gbc.gridy = 9;
        dialog.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText().trim());
                int habitaciones = (int) spnHabitaciones.getValue();
                int banios = (int) spnBanios.getValue();
                boolean parqueadero = chkParqueadero.isSelected();
                int piso = (int) spnPiso.getValue();
                String propietario = txtPropietario.getText().trim();
                String contacto = txtContacto.getText().trim();
                boolean mantenimiento = chkMantenimiento.isSelected();

                boolean actualizado = departamentoController.actualizarDepartamento(
                    depto.getId(), valor, habitaciones, banios, parqueadero, piso, propietario, contacto
                );
                depto.setNecesitaMantenimiento(mantenimiento);

                if (actualizado) {
                    cargarDepartamentosDisponibles();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Departamento actualizado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al actualizar el departamento.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Valor de arriendo inválido.");
            }
        });
        dialog.setVisible(true);
    }



    private void mostrarDialogoAgregarDepartamento() {
        JDialog dialog = new JDialog(this, "Agregar Departamento Disponible", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtId = new JTextField(20);
        JTextField txtValor = new JTextField(20);
        JSpinner spnHabitaciones = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JSpinner spnBanios = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        JCheckBox chkParqueadero = new JCheckBox();
        JSpinner spnPiso = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        JTextField txtPropietario = new JTextField(20);
        JTextField txtContacto = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtValor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Habitaciones:"), gbc);
        gbc.gridx = 1;
        dialog.add(spnHabitaciones, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Baños:"), gbc);
        gbc.gridx = 1;
        dialog.add(spnBanios, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Parqueadero:"), gbc);
        gbc.gridx = 1;
        dialog.add(chkParqueadero, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(new JLabel("Piso:"), gbc);
        gbc.gridx = 1;
        dialog.add(spnPiso, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        dialog.add(new JLabel("Propietario:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtPropietario, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        dialog.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtContacto, gbc);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(92, 136, 255));
        btnGuardar.setForeground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        dialog.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                String id = txtId.getText().trim();
                double valor = Double.parseDouble(txtValor.getText());
                int habitaciones = (Integer) spnHabitaciones.getValue();
                int banios = (Integer) spnBanios.getValue();
                boolean parqueadero = chkParqueadero.isSelected();
                int piso = (Integer) spnPiso.getValue();
                String propietario = txtPropietario.getText().trim();
                String contacto = txtContacto.getText().trim();
                
                if (id.isEmpty() || propietario.isEmpty() || contacto.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor complete todos los campos");
    // Si no se usa mostrarDialogoEditarDepartamento, puedes eliminar este método para evitar la advertencia de "never used".
               JOptionPane.showMessageDialog(dialog, "Por favor complete todos los campos");
                    return;
                }
                
                // Actualizar departamento existente
                departamentoController.actualizarDepartamento(id, valor, habitaciones, 
                                                           banios, parqueadero, piso, propietario, contacto);
                cargarDepartamentosDisponibles();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Departamento actualizado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor ingrese un valor numérico válido");
            }
        });

        dialog.setVisible(true);
    }
}