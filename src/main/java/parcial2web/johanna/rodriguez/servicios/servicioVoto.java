package parcial2web.johanna.rodriguez.servicios;

import parcial2web.johanna.rodriguez.encapsulacion.Voto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class servicioVoto extends servicioBaseDatos<Voto> {
    private static servicioVoto instancia;

    private servicioVoto() {
        super(Voto.class);
    }

    public static servicioVoto getInstancia() {
        if (instancia == null) {
            instancia = new servicioVoto();
        }

        return instancia;
    }

    public Object encontrarVotoUsuarioEnArticulo(long articuloID, long usuarioID) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "from Voto voto where voto.articulo.id = :voto_articuloID and voto.usuario.id = :voto_usuarioID"
            );

            query.setParameter("voto_articuloID", articuloID);
            query.setParameter("voto_usuarioID", usuarioID);
            return query.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Voto> encontrarVotosPorArticulo(long articuloID, String tipoVoto) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "from Voto voto where voto.articulo.id = :voto_articuloID and voto.voto = :voto_tipoVoto"
            );

            query.setParameter("voto_articuloID", articuloID);
            query.setParameter("voto_tipoVoto", tipoVoto);
            return query.getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }
}
