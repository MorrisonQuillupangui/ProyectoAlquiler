package controller;

import java.util.ArrayList;
import java.util.List;
import model.Departamento;

/**
 * Controlador para manejo de departamentos
 */
public class DepartamentoController {
    private List<Departamento> departamentos;

    public DepartamentoController() {
        this.departamentos = new ArrayList<>();

        // Obtener la cantidad de departamentos ocupados (pagos)
        int departamentosOcupados = 90; // Debe coincidir con la cantidad de pagos generados en PagoController
        int departamentosDisponibles = 90; // Cantidad de departamentos disponibles

        // Generar departamentos ocupados (no disponibles)
        for (int i = 1; i <= departamentosOcupados; i++) {
            String id = String.format("D%03d", i);
            double valor = 400 + (i % 10) * 50 + (i * 3 % 100);
            int habitaciones = 1 + (i % 5);
            int banios = 1 + (i % 3);
            boolean parqueadero = (i % 2 == 0);
            int piso = 1 + (i % 10);
            boolean disponible = false; // Ocupado
            String propietario = "Propietario " + i;
            String contacto = "09" + (int)(Math.random() * 100000000);
            boolean necesitaMantenimiento = (i % 7 == 0);
            departamentos.add(new model.Departamento(id, valor, habitaciones, banios, parqueadero, piso, disponible, propietario, contacto, necesitaMantenimiento));
        }

        // Generar departamentos disponibles
        for (int i = 1; i <= departamentosDisponibles; i++) {
            int idx = departamentosOcupados + i;
            String id = String.format("D%03d", idx);
            double valor = 400 + (idx % 10) * 50 + (idx * 3 % 100);
            int habitaciones = 1 + (idx % 5);
            int banios = 1 + (idx % 3);
            boolean parqueadero = (idx % 2 == 0);
            int piso = 1 + (idx % 10);
            boolean disponible = true; // Disponible
            String propietario = "Propietario " + idx;
            String contacto = "09" + (int)(Math.random() * 100000000);
            boolean necesitaMantenimiento = (idx % 7 == 0);
            departamentos.add(new model.Departamento(id, valor, habitaciones, banios, parqueadero, piso, disponible, propietario, contacto, necesitaMantenimiento));
        }
    }

    public List<Departamento> listarTodos() {
        return departamentos;
    }

    public List<Departamento> listarDisponibles() {
        List<Departamento> disponibles = new ArrayList<>();
        for (Departamento d : departamentos) {
            if (d.isDisponible()) {
                disponibles.add(d);
            }
        }
        return disponibles;
    }

    public boolean actualizarDisponibilidad(String id, boolean disponible) {
        for (Departamento d : departamentos) {
            if (d.getId().equalsIgnoreCase(id)) {
                d.setDisponible(disponible);
                return true;
            }
        }
        return false;
    }

    public void agregarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
    }

    public boolean actualizarDepartamento(String id, double valor, int habitaciones, int banios, 
                                        boolean parqueadero, int piso, String propietario, String contacto) {
        for (Departamento d : departamentos) {
            if (d.getId().equalsIgnoreCase(id)) {
                d.setValor(valor);
                d.setHabitaciones(habitaciones);
                d.setBanios(banios);
                d.setParqueadero(parqueadero);
                d.setPiso(piso);
                d.setPropietario(propietario);
                d.setContacto(contacto);
                return true;
            }
        }
        return false;
    }
}
