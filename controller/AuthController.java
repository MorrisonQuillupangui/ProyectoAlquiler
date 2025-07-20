package controller;

import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 * Controlador para manejo de registro e inicio de sesión
 */
public class AuthController {
    private List<Usuario> usuarios;

    public AuthController() {
        this.usuarios = new ArrayList<>();
        // Para pruebas, agregamos un usuario por defecto
        usuarios.add(new Usuario("Admin", "ESPE", "admin@espe.com", "1234"));
    }

    /**
     * Registra un nuevo usuario
     */
    public boolean registrarUsuario(String nombre, String apellido, String correo, String contrasena) {
        // Validar que no exista el correo
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return false;
            }
        }
        usuarios.add(new Usuario(nombre, apellido, correo, contrasena));
        return true;
    }

    /**
     * Verifica las credenciales de inicio de sesión
     */
    public Usuario login(String correo, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
