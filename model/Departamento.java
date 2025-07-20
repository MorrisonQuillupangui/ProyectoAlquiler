package model;


public class Departamento {
    private String id;
    private double valor;
    private int habitaciones;
    private int banios;
    private boolean parqueadero;
    private int piso;
    private boolean disponible;
    private String propietario;
    private String contacto;
    private boolean necesitaMantenimiento;

    public Departamento(String id, double valor, int habitaciones, int banios,
                        boolean parqueadero, int piso, boolean disponible,
                        String propietario, String contacto, boolean necesitaMantenimiento) {
        this.id = id;
        this.valor = valor;
        this.habitaciones = habitaciones;
        this.banios = banios;
        this.parqueadero = parqueadero;
        this.piso = piso;
        this.disponible = disponible;
        this.propietario = propietario;
        this.contacto = contacto;
        this.necesitaMantenimiento = necesitaMantenimiento;
    }

    // Getters y Setters
    public String getId() { return id; }
    public double getValor() { return valor; }
    public int getHabitaciones() { return habitaciones; }
    public int getBanios() { return banios; }
    public boolean tieneParqueadero() { return parqueadero; }
    public int getPiso() { return piso; }
    public boolean isDisponible() { return disponible; }
    public String getPropietario() { return propietario; }
    public String getContacto() { return contacto; }
    public boolean isNecesitaMantenimiento() { return necesitaMantenimiento; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setValor(double valor) { this.valor = valor; }
    public void setHabitaciones(int habitaciones) { this.habitaciones = habitaciones; }
    public void setBanios(int banios) { this.banios = banios; }
    public void setParqueadero(boolean parqueadero) { this.parqueadero = parqueadero; }
    public void setPiso(int piso) { this.piso = piso; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public void setNecesitaMantenimiento(boolean necesitaMantenimiento) { this.necesitaMantenimiento = necesitaMantenimiento; }

    @Override
    public String toString() {
        return "Departamento " + id + ": $" + valor + " | " + habitaciones + " habitaciones | " +
               banios + " baños | Parqueadero: " + (parqueadero ? "Sí" : "No") + 
               " | Piso: " + piso + " | Disponible: " + (disponible ? "Sí" : "No") +
               " | Mantenimiento: " + (necesitaMantenimiento ? "Sí" : "No");
    }
}
