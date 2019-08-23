package parcial2web.johanna.rodriguez.servicios;

import parcial2web.johanna.rodriguez.encapsulacion.Comentario;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class servicioComentario extends servicioBaseDatos<Comentario> {
    private static servicioComentario instancia;

    private servicioComentario() {
        super(Comentario.class);
    }

    public static servicioComentario getInstancia() {
        if (instancia == null) {
            instancia = new servicioComentario();
        }
        return instancia;
    }

    public List<Comentario> encontrarComentario(long articuloID) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "from Comentario comentario where comentario.articulo.id = :comentario_articuloID order by id asc"
            );
            query.setParameter("comentario_articuloID", articuloID);
            return query.getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }
}