package parcial2web.johanna.rodriguez.servicios;

import parcial2web.johanna.rodriguez.encapsulacion.Articulo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class servicioArticulo extends servicioBaseDatos<Articulo> {
    private static servicioArticulo instancia;

    private servicioArticulo() {
        super(Articulo.class);
    }

    public static servicioArticulo getInstancia() {
        if (instancia == null) {
            instancia = new servicioArticulo();
        }
        return instancia;
    }

    public List<Articulo> buscarArticulos() {
        EntityManager em = servicioArticulo.getInstancia().getEntityManager();

        Query query = em.createQuery("select a from Articulo a order by id desc");
        List<Articulo> lista = query.getResultList();

        return lista;
    }

    public List<Articulo> buscarArticulosPaginados(int pagina, int tamano) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "from Articulo a order by id desc"
            );

            query.setFirstResult((pagina-1)*tamano);
            query.setMaxResults(tamano);

            return query.getResultList();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public double conseguirCantidadPaginas() {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(
                    "select count(*) from Articulo"
            );


            double cantidadPaginas = Math.ceil(Double.parseDouble(query.getSingleResult().toString()) / 5f);

            return cantidadPaginas;
        } catch (Exception ex) {
            return new Long(0);
        } finally {
            em.close();
        }
    }
}