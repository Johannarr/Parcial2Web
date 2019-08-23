package parcial2web.johanna.rodriguez.servicios;

import parcial2web.johanna.rodriguez.encapsulacion.Valoracion;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class servicioValoracion extends servicioBaseDatos<Valoracion>{
    private static servicioValoracion instancia;

    private servicioValoracion() {
        super(Valoracion.class);
    }

    public static servicioValoracion getInstancia() {
        if (instancia == null) {
            instancia = new servicioValoracion();
        }
        return instancia;
    }

    public Object encontrarValoracionUsuarioEnComentario(long comentarioID, long usuarioID) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "from Valoracion valoracion where valoracion.comentario.id = :valoracion_comentarioID and valoracion.usuario.id = :valoracion_usuarioID"
            );
            query.setParameter("valoracion_comentarioID", comentarioID);
            query.setParameter("valoracion_usuarioID", usuarioID);
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Valoracion> encontrarValoracionesPorComentario(long comentarioID, String tipoValoracion) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "from Valoracion valoracion where valoracion.comentario.id = :valoracion_comentarioID and valoracion.valoracion = :valoracion_tipoValoracion"
            );

            query.setParameter("valoracion_comentarioID", comentarioID);
            query.setParameter("valoracion_tipoValoracion", tipoValoracion);

            return query.getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

}
