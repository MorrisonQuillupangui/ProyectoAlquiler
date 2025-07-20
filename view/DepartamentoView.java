package view;

import controller.DepartamentoController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.Departamento;

public class DepartamentoView extends JFrame {
    private final DepartamentoController departamentoController;
    private JPanel panelDepartamentos;
    private JTextField txtBuscar;
    private JScrollPane scrollPane;
    private JComboBox<String> cbComparadorValor;
    private JTextField txtValor;
    private JTextField txtHabitaciones;
    private JTextField txtBanios;
    private JTextField txtPiso;
    private JComboBox<String> cbParqueadero;
    private JComboBox<String> cbDisponible;
    private JComboBox<String> cbMantenimiento;

    public DepartamentoView(DepartamentoController departamentoController) {
        this.departamentoController = departamentoController;
        initComponents();
        cargarTodosDepartamentos();
    }

    private void initComponents() {
        setTitle("Todos los Departamentos - Arriendos ESPE");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con título y filtros
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titulo = new JLabel("Todos los Departamentos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(50, 50, 50));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setBackground(Color.WHITE);

        // Campos de filtro
        JTextField txtFiltroId = new JTextField(5);
        JTextField txtFiltroValor = new JTextField(5);
        JComboBox<String> cbComparadorValor = new JComboBox<>(new String[]{"<", "=", ">"});
        JTextField txtFiltroHabitaciones = new JTextField(3);
        JTextField txtFiltroBanios = new JTextField(3);
        JTextField txtFiltroPiso = new JTextField(3);
        JComboBox<String> cbParqueadero = new JComboBox<>(new String[]{"Todos", "Sí", "No"});
        JComboBox<String> cbDisponible = new JComboBox<>(new String[]{"Todos", "Sí", "No"});
        JComboBox<String> cbMantenimiento = new JComboBox<>(new String[]{"Todos", "Sí", "No"});

        JButton btnBuscar = new JButton("Buscar");

        // Agregar al panel de filtros
        panelFiltros.add(new JLabel("ID:"));
        panelFiltros.add(txtFiltroId);

        panelFiltros.add(new JLabel("Valor:"));
        panelFiltros.add(cbComparadorValor);
        panelFiltros.add(txtFiltroValor);

        panelFiltros.add(new JLabel("Habitaciones:"));
        panelFiltros.add(txtFiltroHabitaciones);

        panelFiltros.add(new JLabel("Baños:"));
        panelFiltros.add(txtFiltroBanios);

        panelFiltros.add(new JLabel("Piso:"));
        panelFiltros.add(txtFiltroPiso);

        panelFiltros.add(new JLabel("Parqueadero:"));
        panelFiltros.add(cbParqueadero);

        panelFiltros.add(new JLabel("Disponible:"));
        panelFiltros.add(cbDisponible);

        panelFiltros.add(new JLabel("Mantenimiento:"));
        panelFiltros.add(cbMantenimiento);

        panelFiltros.add(btnBuscar);

        panelSuperior.add(panelFiltros, BorderLayout.CENTER);

        // Panel central con departamentos
        panelDepartamentos = new JPanel(new GridLayout(0, 3, 15, 15));
        panelDepartamentos.setBackground(Color.WHITE);
        panelDepartamentos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        scrollPane = new JScrollPane(panelDepartamentos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAgregarDepartamento = new JButton("Agregar departamento");
        btnAgregarDepartamento.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAgregarDepartamento.setBackground(new Color(92, 136, 255));
        btnAgregarDepartamento.setForeground(Color.WHITE);
        btnAgregarDepartamento.setPreferredSize(new Dimension(180, 40));
        btnAgregarDepartamento.setBorderPainted(false);
        btnAgregarDepartamento.setFocusPainted(false);
        btnAgregarDepartamento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelInferior.add(btnAgregarDepartamento, BorderLayout.WEST);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        btnAgregarDepartamento.addActionListener(e -> mostrarDialogoAgregarDepartamento());

        btnBuscar.addActionListener(e -> {
            buscarDepartamentos(
                txtFiltroId.getText().trim().toLowerCase(),
                (String) cbComparadorValor.getSelectedItem(),
                txtFiltroValor.getText().trim(),
                txtFiltroHabitaciones.getText().trim(),
                txtFiltroBanios.getText().trim(),
                txtFiltroPiso.getText().trim(),
                (String) cbParqueadero.getSelectedItem(),
                (String) cbDisponible.getSelectedItem(),
                (String) cbMantenimiento.getSelectedItem()
            );
        });
    }


    private void cargarTodosDepartamentos() {
        panelDepartamentos.removeAll();
        List<Departamento> todos = departamentoController.listarTodos();
        
        for (Departamento depto : todos) {
            JPanel cardDepto = crearCardDepartamento(depto);
            panelDepartamentos.add(cardDepto);
        }
        
        panelDepartamentos.revalidate();
        panelDepartamentos.repaint();
    }

    private JPanel crearCardDepartamento(Departamento depto) {
        JPanel card = new JPanel(new BorderLayout());
        Color bgColor = depto.isDisponible() ? new Color(220, 235, 255) : new Color(255, 220, 220);
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(depto.isDisponible() ? new Color(92, 136, 255) : new Color(255, 100, 100), 3),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        card.setPreferredSize(new Dimension(340, 280));

        // Panel superior con ID y botón editar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0,0,0,0));
        JLabel lblId = new JLabel("ID: " + depto.getId());
        lblId.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblId.setForeground(new Color(30, 30, 120));
        JButton btnEditar = new JButton("✏ Editar");
        btnEditar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEditar.setBackground(new Color(92, 136, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setPreferredSize(new Dimension(90, 32));
        btnEditar.setBorderPainted(false);
        btnEditar.setFocusPainted(false);
        btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelSuperior.add(lblId, BorderLayout.WEST);
        panelSuperior.add(btnEditar, BorderLayout.EAST);

        // Panel central con toda la información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(0,0,0,0));

        JLabel lblValor = new JLabel("$ " + depto.getValor());
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblValor.setForeground(new Color(0, 123, 255));
        panelInfo.add(lblValor);

        JLabel lblHabitaciones = new JLabel("Habitaciones: " + depto.getHabitaciones());
        lblHabitaciones.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblHabitaciones);

        JLabel lblBanios = new JLabel("Baños: " + depto.getBanios());
        lblBanios.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblBanios);

        JLabel lblParqueadero = new JLabel("Parqueadero: " + (depto.tieneParqueadero() ? "Sí" : "No"));
        lblParqueadero.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblParqueadero);

        JLabel lblPiso = new JLabel("Piso: " + depto.getPiso());
        lblPiso.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblPiso);

        JLabel lblDisponible = new JLabel("Disponible: " + (depto.isDisponible() ? "Sí" : "No"));
        lblDisponible.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblDisponible.setForeground(depto.isDisponible() ? new Color(0, 153, 51) : new Color(204, 0, 0));
        panelInfo.add(lblDisponible);

        JLabel lblPropietario = new JLabel("Propietario: " + depto.getPropietario());
        lblPropietario.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblPropietario);

        JLabel lblContacto = new JLabel("Contacto: " + depto.getContacto());
        lblContacto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblContacto);

        JLabel lblMantenimiento = new JLabel("Mantenimiento: " + (depto.isNecesitaMantenimiento() ? "Sí" : "No"));
        lblMantenimiento.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInfo.add(lblMantenimiento);

        card.add(panelSuperior, BorderLayout.NORTH);
        card.add(panelInfo, BorderLayout.CENTER);

        // Evento del botón editar
        btnEditar.addActionListener(e -> mostrarDialogoEditarDepartamento(depto));
        return card;
    }

    private void mostrarDialogoAgregarDepartamento() {
        JDialog dialog = new JDialog(this, "Agregar Departamento", true);
        dialog.setSize(450, 450);
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
        JCheckBox chkDisponible = new JCheckBox();
        chkDisponible.setSelected(true);
        JTextField txtPropietario = new JTextField(20);
        ((AbstractDocument) txtPropietario.getDocument()).setDocumentFilter(new DocumentFilter() {
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
        dialog.add(new JLabel("Disponible:"), gbc);
        gbc.gridx = 1;
        dialog.add(chkDisponible, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        dialog.add(new JLabel("Propietario:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtPropietario, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        dialog.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtContacto, gbc);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(92, 136, 255));
        btnGuardar.setForeground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 9;
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
                boolean disponible = chkDisponible.isSelected();
                String propietario = txtPropietario.getText().trim();
                String contacto = txtContacto.getText().trim();
                
                if (id.isEmpty() || propietario.isEmpty() || contacto.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor complete todos los campos");
                    return;
                }
                
                // El último argumento booleano debe ser ajustado según la definición de tu modelo.
                // Por ejemplo, si es para "activo", puedes poner true por defecto o pedirlo en el formulario.
                Departamento nuevoDepartamento = new Departamento(id, valor, habitaciones, banios, 
                        parqueadero, piso, disponible, propietario, contacto, true);
                departamentoController.agregarDepartamento(nuevoDepartamento);
                cargarTodosDepartamentos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Departamento agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor ingrese un valor numérico válido");
            }
        });

        dialog.setVisible(true);
    }

    private void mostrarDialogoEditarDepartamento(Departamento depto) {
        JDialog dialog = new JDialog(this, "Editar Departamento", true);
        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtId = new JTextField(depto.getId(), 20);
        txtId.setEnabled(false);
        JTextField txtValor = new JTextField(String.valueOf(depto.getValor()), 20);
        JSpinner spnHabitaciones = new JSpinner(new SpinnerNumberModel(depto.getHabitaciones(), 1, 10, 1));
        JSpinner spnBanios = new JSpinner(new SpinnerNumberModel(depto.getBanios(), 1, 5, 1));
        JCheckBox chkParqueadero = new JCheckBox();
        chkParqueadero.setSelected(depto.tieneParqueadero());
        JSpinner spnPiso = new JSpinner(new SpinnerNumberModel(depto.getPiso(), 1, 20, 1));
        JCheckBox chkDisponible = new JCheckBox();
        chkDisponible.setSelected(depto.isDisponible());
        JTextField txtPropietario = new JTextField(depto.getPropietario(), 20);
        ((AbstractDocument) txtPropietario.getDocument()).setDocumentFilter(new DocumentFilter() {
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
        JTextField txtContacto = new JTextField(depto.getContacto(), 20);

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
        dialog.add(new JLabel("Disponible:"), gbc);
        gbc.gridx = 1;
        dialog.add(chkDisponible, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        dialog.add(new JLabel("Propietario:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtPropietario, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        dialog.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtContacto, gbc);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(92, 136, 255));
        btnGuardar.setForeground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 9;
        gbc.gridwidth = 2;
        dialog.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                int habitaciones = (Integer) spnHabitaciones.getValue();
                int banios = (Integer) spnBanios.getValue();
                boolean parqueadero = chkParqueadero.isSelected();
                int piso = (Integer) spnPiso.getValue();
                boolean disponible = chkDisponible.isSelected();
                String propietario = txtPropietario.getText().trim();
                String contacto = txtContacto.getText().trim();
                
                if (propietario.isEmpty() || contacto.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Por favor complete todos los campos");
                    return;
                }
                
                depto.setValor(valor);
                depto.setHabitaciones(habitaciones);
                depto.setBanios(banios);
                depto.setParqueadero(parqueadero);
                depto.setPiso(piso);
                depto.setDisponible(disponible);
                depto.setPropietario(propietario);
                depto.setContacto(contacto);
                
                cargarTodosDepartamentos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Departamento actualizado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor ingrese un valor numérico válido");
            }
        });

        dialog.setVisible(true);
    }

    private void buscarDepartamentos(
            String id, String comparadorValor, String valorStr,
            String habitacionesStr, String baniosStr, String pisoStr,
            String parqueadero, String disponible, String mantenimiento
    ) {
        panelDepartamentos.removeAll();
        List<Departamento> todos = departamentoController.listarTodos();

        for (Departamento depto : todos) {
            boolean coincide = true;

            if (!id.isEmpty() && !depto.getId().toLowerCase().contains(id)) coincide = false;

            if (!valorStr.isEmpty()) {
                try {
                    double valorFiltro = Double.parseDouble(valorStr);
                    double valorDepto = depto.getValor();
                    if ("<".equals(comparadorValor) && !(valorDepto < valorFiltro)) coincide = false;
                    if ("=".equals(comparadorValor) && !(valorDepto == valorFiltro)) coincide = false;
                    if (">".equals(comparadorValor) && !(valorDepto > valorFiltro)) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!habitacionesStr.isEmpty()) {
                try {
                    int h = Integer.parseInt(habitacionesStr);
                    if (depto.getHabitaciones() != h) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!baniosStr.isEmpty()) {
                try {
                    int b = Integer.parseInt(baniosStr);
                    if (depto.getBanios() != b) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!pisoStr.isEmpty()) {
                try {
                    int p = Integer.parseInt(pisoStr);
                    if (depto.getPiso() != p) coincide = false;
                } catch (NumberFormatException ignored) {}
            }

            if (!"Todos".equals(parqueadero)) {
                boolean tiene = depto.tieneParqueadero();
                if ("Sí".equals(parqueadero) && !tiene) coincide = false;
                if ("No".equals(parqueadero) && tiene) coincide = false;
            }

            if (!"Todos".equals(disponible)) {
                boolean disp = depto.isDisponible();
                if ("Sí".equals(disponible) && !disp) coincide = false;
                if ("No".equals(disponible) && disp) coincide = false;
            }

            if (!"Todos".equals(mantenimiento)) {
                boolean mant = depto.isNecesitaMantenimiento();
                if ("Sí".equals(mantenimiento) && !mant) coincide = false;
                if ("No".equals(mantenimiento) && mant) coincide = false;
            }

            if (coincide) {
                JPanel card = crearCardDepartamento(depto);
                panelDepartamentos.add(card);
            }
        }

        panelDepartamentos.revalidate();
        panelDepartamentos.repaint();
    }


    public void mostrarTodos() {
        setVisible(true);
    }
}
