package parcial2web.johanna.rodriguez.servicios;

import parcial2web.johanna.rodriguez.encapsulacion.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class servicioUsuario extends servicioBaseDatos<Usuario>{
    private static servicioUsuario instancia;

    private servicioUsuario() {
        super(Usuario.class);
    }

    public static servicioUsuario getInstancia() {
        if (instancia == null) {
            instancia = new servicioUsuario();
        }
        return instancia;
    }

    public Object encontrarUsuarioSesion(String sesion) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery("from Usuario user where user.sesion = :user_sesion");
            query.setParameter("user_sesion", sesion);
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public Object encontrarUsuario(String username, String password) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery("from Usuario user where user.username = :user_username and user.password = :user_password");
            query.setParameter("user_username", username);
            query.setParameter("user_password", password);
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

}