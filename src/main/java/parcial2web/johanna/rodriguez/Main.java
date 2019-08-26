package parcial2web.johanna.rodriguez;

import parcial2web.johanna.rodriguez.encapsulacion.Usuario;
import parcial2web.johanna.rodriguez.servicios.servicioBootStrap;
import parcial2web.johanna.rodriguez.servicios.servicioUsuario;

public class Main {

    public static void main(String[] args) {
        // Iniciando el servicio de Base de datos por medio de Hibernate y H2.
        try {
            servicioBootStrap.getInstancia().startDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insertando el usuario por defecto (administrador).
        if (servicioUsuario.getInstancia().find(new Long(1)) == null) {
            servicioUsuario.getInstancia().crear(new Usuario(new Long(1), "Administrador", "admin", "1234", true, true, null));
        }

        // Creando las rutas.
        Rutas.crearRutas();
    }
}
