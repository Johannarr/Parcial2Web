package parcial2web.johanna.rodriguez.servicios;

import parcial2web.johanna.rodriguez.encapsulacion.Etiqueta;

import java.util.List;
import java.util.Set;


public class servicioEtiqueta extends servicioBaseDatos<Etiqueta> {
    private static servicioEtiqueta instancia;

    private servicioEtiqueta() {
        super(Etiqueta.class);
    }

    public static servicioEtiqueta getInstancia() {
        if (instancia == null) {
            instancia = new servicioEtiqueta();
        }
        return instancia;
    }

    public static void agregarEtiquetas(String[] etiquetas, Set<Etiqueta> etiquetasSet) {
        List<Etiqueta> tags = servicioEtiqueta.getInstancia().findAll();
        for (int i = 0; i < etiquetas.length; i++) {
            Etiqueta et = new Etiqueta(etiquetas[i]);
            boolean esta = false;
            for (Etiqueta e : tags) {
                if (e.getEtiqueta().contains(etiquetas[i])) {
                    esta = true;
                }
            }

            if (!esta) {
                servicioEtiqueta.getInstancia().crear(et);
                tags = servicioEtiqueta.getInstancia().findAll();
            }
        }

        for (int i = 0; i < etiquetas.length; i++) {
            for (Etiqueta e : servicioEtiqueta.getInstancia().findAll()) {
                if (e.getEtiqueta().equals(etiquetas[i])) {
                    etiquetasSet.add(e);
                }
            }
        }
    }

    public static boolean seModificaronLasEtiquetas(Set<Etiqueta> etiquetas, Set<Etiqueta> setEtiquetas) {

        if ((etiquetas.size() == 0 && setEtiquetas.size() > 0) || (etiquetas.size() > 0 && setEtiquetas.size() == 0)) {
            return true;
        }
        for (Etiqueta etiquetaSet : setEtiquetas) {
            for (Etiqueta etiqueta : etiquetas) {
                if (!etiquetaSet.getEtiqueta().contains(etiqueta.getEtiqueta())) {
                    return true;
                }
            }
        }
        return false;
    }
}