package model;

import java.time.LocalDate;

/**
 * Modelo que representa un Pago
 */
public class Pago {
    private String estado; // Pagado o Mora
    private LocalDate fechaPago;
    private String inquilino;
    private String contacto;

    public Pago(String estado, LocalDate fechaPago, String inquilino, String contacto) {
        this.estado = estado;
        this.fechaPago = fechaPago;
        this.inquilino = inquilino;
        this.contacto = contacto;
    }

    // Getters y Setters
    public String getEstado() { return estado; }
    public LocalDate getFechaPago() { return fechaPago; }
    public String getInquilino() { return inquilino; }
    public String getContacto() { return contacto; }

    public void setEstado(String estado) { this.estado = estado; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public void setInquilino(String inquilino) { this.inquilino = inquilino; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    @Override
    public String toString() {
        return "Estado: " + estado + " | Fecha: " + fechaPago + " | Inquilino: " + inquilino + " | Contacto: " + contacto;
    }
}
