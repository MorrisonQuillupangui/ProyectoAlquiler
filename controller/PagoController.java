package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Pago;

/**
 * Controlador para manejo de pagos
 */
public class PagoController {
    private List<Pago> pagos;

    public PagoController() {
        this.pagos = new ArrayList<>();
        String[] departamentos = { "Departamento A", "Departamento B", "Departamento C" };
        java.time.LocalDate inicioContrato = java.time.LocalDate.now().minusMonths(24);

        for (int i = 1; i <= 90; i++) {
            String estado = (i % 4 == 0) ? "Mora" : "Pagado";
            java.time.LocalDate fechaContrato = inicioContrato.plusMonths(i % 24);
            double valorAlquiler = 350 + (i % 10) * 40 + (i * 2 % 80);
            int mesesMora = (estado.equals("Mora")) ? 1 + (i % 6) : 0;
            double montoAdeudado = mesesMora * valorAlquiler;
            java.time.LocalDate fechaPago = fechaContrato.plusMonths((i % 24)).withDayOfMonth(3);
            String inquilino = "Inquilino " + i;
            String contacto = "09" + (int) (Math.random() * 100000000);

            // Nuevos campos
            String departamento = departamentos[i % departamentos.length];
            boolean requiereMantenimiento = (i % 3 == 0);

            PagoExtendido pago = new PagoExtendido(
                    estado, fechaPago, inquilino, contacto,
                    fechaContrato, valorAlquiler, mesesMora, montoAdeudado,
                    departamento, requiereMantenimiento);
            pagos.add(pago);
        }
    }

    public List<Pago> listarTodos() {
        return pagos;
    }

    public void registrarPago(Pago pago) {
        pagos.add(pago);
    }

    public boolean actualizarEstadoPago(int index, String nuevoEstado) {
        if (index >= 0 && index < pagos.size()) {
            pagos.get(index).setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public boolean actualizarPago(int index, String estado, java.time.LocalDate fechaPago, String inquilino,
            String contacto) {
        if (index >= 0 && index < pagos.size()) {
            Pago pago = pagos.get(index);
            pago.setEstado(estado);
            pago.setFechaPago(fechaPago);
            pago.setInquilino(inquilino);
            pago.setContacto(contacto);
            return true;
        }
        return false;
    }

    public double abonarPago(int index, double abono) {
        if (index >= 0 && index < pagos.size()) {
            Pago pago = pagos.get(index);
            if (pago instanceof PagoExtendido && pago.getEstado().equalsIgnoreCase("Mora")) {
                PagoExtendido ext = (PagoExtendido) pago;
                double nuevaDeuda = ext.getMontoAdeudado() - abono;
                if (nuevaDeuda < 0)
                    nuevaDeuda = 0;
                ext.montoAdeudado = nuevaDeuda;
                if (ext.valorAlquiler > 0) {
                    ext.mesesMora = (int) Math.ceil(nuevaDeuda / ext.valorAlquiler);
                } else {
                    ext.mesesMora = 0;
                }
                if (nuevaDeuda == 0) {
                    pago.setEstado("Pagado");
                }
                return nuevaDeuda;
            }
        }
        return -1;
    }

    /**
     * Filtra pagos por parámetros
     */
    public List<Pago> filtrarPagos(String textoBusqueda, String estado, String departamento, String mantenimiento) {
        return pagos.stream()
                .filter(p -> {
                    boolean matches = true;

                    // Filtro texto general (inquilino)
                    if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
                        matches &= p.getInquilino().toLowerCase().contains(textoBusqueda.toLowerCase());
                    }

                    // Filtro estado
                    if (estado != null && !estado.equalsIgnoreCase("Todos")) {
                        matches &= p.getEstado().equalsIgnoreCase(estado);
                    }

                    // Filtro departamento
                    if (departamento != null && !departamento.trim().isEmpty() && p instanceof PagoExtendido) {
                        PagoExtendido ext = (PagoExtendido) p;
                        matches &= ext.getDepartamento().equalsIgnoreCase(departamento.trim());
                    }

                    // Filtro mantenimiento
                    if (mantenimiento != null && !mantenimiento.equalsIgnoreCase("Todos")
                            && p instanceof PagoExtendido) {
                        PagoExtendido ext = (PagoExtendido) p;
                        boolean necesita = mantenimiento.equalsIgnoreCase("Sí");
                        matches &= (ext.isRequiereMantenimiento() == necesita);
                    }

                    return matches;
                })
                .collect(Collectors.toList());
    }

    // Clase interna para pagos extendidos
    public static class PagoExtendido extends Pago {
        private java.time.LocalDate fechaContrato;
        private double valorAlquiler;
        private int mesesMora;
        private double montoAdeudado;
        private String departamento;
        private boolean requiereMantenimiento;

        public PagoExtendido(
                String estado, java.time.LocalDate fechaPago, String inquilino, String contacto,
                java.time.LocalDate fechaContrato, double valorAlquiler, int mesesMora, double montoAdeudado,
                String departamento, boolean requiereMantenimiento) {
            super(estado, fechaPago, inquilino, contacto);
            this.fechaContrato = fechaContrato;
            this.valorAlquiler = valorAlquiler;
            this.mesesMora = mesesMora;
            this.montoAdeudado = montoAdeudado;
            this.departamento = departamento;
            this.requiereMantenimiento = requiereMantenimiento;
        }

        public java.time.LocalDate getFechaContrato() {
            return fechaContrato;
        }

        public double getValorAlquiler() {
            return valorAlquiler;
        }

        public int getMesesMora() {
            return mesesMora;
        }

        public double getMontoAdeudado() {
            return montoAdeudado;
        }

        public String getDepartamento() {
            return departamento;
        }

        public boolean isRequiereMantenimiento() {
            return requiereMantenimiento;
        }

        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }

        public void setRequiereMantenimiento(boolean requiereMantenimiento) {
            this.requiereMantenimiento = requiereMantenimiento;
        }

        public void setMesesMora(int mesesMora) {
            this.mesesMora = mesesMora;
        }

        public void setMontoAdeudado(double montoAdeudado) {
            this.montoAdeudado = montoAdeudado;
        }
    }
}
