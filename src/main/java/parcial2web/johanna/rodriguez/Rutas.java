package parcial2web.johanna.rodriguez;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.jasypt.util.text.StrongTextEncryptor;
import parcial2web.johanna.rodriguez.encapsulacion.*;
import parcial2web.johanna.rodriguez.servicios.*;
import spark.Session;

import java.io.StringWriter;
import java.util.*;

import static spark.Spark.*;

public class Rutas {

    static List<Articulo> articulos = servicioArticulo.getInstancia().buscarArticulos();
    static String nombreUsuario = "";
    static Usuario usuario = null;

    public static void crearRutas() {
        final Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(Main.class, "/");

        staticFiles.location("/publico");

        before("/", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/login");
            }
        });

        before("/registrar", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/");
            }

            if (!usuario.isAdminstrator()) {
                res.redirect("/");
            }
        });

        before("/articulo/crear", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/");
            }

            if (!usuario.isAdminstrator()) {
                if (!usuario.isAutor()) {
                    res.redirect("/");
                }
            }
        });

        before("/articulo/editar/:id", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/");
            }
            if (!usuario.isAdminstrator()) {
                if (!usuario.isAutor()) {
                    res.redirect("/");
                }
            }
        });

        before("/articulo/eliminar/:id", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/");
            }

            if (!usuario.isAdminstrator()) {
                if (!usuario.isAutor()) {
                    res.redirect("/");
                }
            }
        });

        before("articulo/:id", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/login");
            }
        });

        before("/inicio", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/login");
            }
        });

        before("inicio/:pagina", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/login");
            }

            int pagina = Integer.parseInt(req.params("pagina"));
            double cantidadPaginas = servicioArticulo.getInstancia().conseguirCantidadPaginas();

            if (cantidadPaginas == 0f) {
                res.redirect("/articulo/crear");
            }

            if (pagina > cantidadPaginas) {
                res.redirect("/");
            }
        });

        before("/etiqueta/:id", (req, res) -> {
            if (req.cookie("sesionSemanal") != null) {
                Usuario usuarioRestaurado = restaurarSesion(req.cookie("sesionSemanal"));

                if (usuarioRestaurado != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuarioRestaurado);
                }
            }

            if (req.session().attribute("sesionUsuario") == null) {
                res.redirect("/login");
            }
        });

        get("/", (req, res) -> {
            res.redirect("/inicio/1");

            return null;
        });

        get("/inicio", (req, res) -> {
            res.redirect("/inicio/1");

            return null;
        });

        get("/inicio/:pagina", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("templates/inicio.ftl");

            int pagina = Integer.parseInt(req.params("pagina"));

            //aqui trabajo con la paginacion de los articulo

            articulos = servicioArticulo.getInstancia().buscarArticulosPaginados(pagina, 5);
            double cantidadPaginas = servicioArticulo.getInstancia().conseguirCantidadPaginas();

            atributos.put("articulos", articulos);
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
            atributos.put("esAdmin", usuario.isAdminstrator());
            atributos.put("paginaActual", pagina);
            atributos.put("paginaMaxima", cantidadPaginas);

            template.process(atributos, writer);

            return writer;
        });

        get("/login", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("templates/login.ftl");
            template.process(atributos, writer);

            return writer;
        });

        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            nombreUsuario = req.queryParams("username");
            String password = req.queryParams("password");
            usuario = (Usuario) servicioUsuario.getInstancia().encontrarUsuario(username, password);

            try {
                if (usuario != null) {
                    req.session(true);
                    req.session().attribute("sesionUsuario", usuario);

                    if (req.queryParams("guardarSesion") != null) {
                        String sesionID = req.session().id();
                        StrongTextEncryptor encriptador = new StrongTextEncryptor();
                        encriptador.setPassword("manga-anime-empire");
                        String sesionIDEncriptado = encriptador.encrypt(sesionID);

                        System.out.println("Sesión sin encriptar: " + sesionID);
                        System.out.println("Sesión encriptada: " + sesionIDEncriptado);

                        res.cookie("/", "sesionSemanal", sesionIDEncriptado, 604800, false);
                        servicioUsuario.getInstancia().editar(new Usuario(usuario.getId(), usuario.getNombre(), usuario.getUsername(), usuario.getPassword(), usuario.isAdminstrator(), usuario.isAutor(), req.session().id()));
                    }

                    res.redirect("/");
                } else {
                    res.redirect("/login");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        });

        get("/registrar", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("templates/registroUsuario.ftl");

            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
            atributos.put("esAdmin", usuario.isAdminstrator());

            template.process(atributos, writer);

            return writer;
        });

        post("/registrar", (req, res) -> {
            String nombre = req.queryParams("nombre");
            String nombreUsuario = req.queryParams("username");
            String contrasena = req.queryParams("password");

            boolean seraAutor = false;
            boolean seraAdmin = false;

            if (req.queryParams("seraAutor") != null) {
                seraAutor = true;
            }

            if (req.queryParams("seraAdmin") != null) {
                seraAdmin = true;
                seraAutor = true;
            }

            long id = servicioUsuario.getInstancia().findAll().get(servicioUsuario.getInstancia().findAll().size() - 1).getId() + 1;

            Usuario usuarioARegistrar = new Usuario(id, nombre, nombreUsuario, contrasena, seraAdmin, seraAutor, null);

            servicioUsuario.getInstancia().crear(usuarioARegistrar);
            res.redirect("/");

            return null;
        });

        get("/salir", (req, res) ->
        {
            Session sesion = req.session(true);
            sesion.invalidate();

            res.removeCookie("sesionSemanal");

            res.redirect("/");

            return null;
        });

        path("/articulo", () -> {
            get("/crear", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("templates/crearArticulo.ftl");

                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());
                template.process(atributos, writer);

                return writer;
            });

            post("/crear", (request, response) -> {
                // Conseguir las etiquetas y separar cada una por las comas ingresadas.
                String[] etiquetas = request.queryParams("etiquetas").split(",");

                // Reemplazar espacios en las etiquetas.
                for (int i = 0; i < etiquetas.length; i++) {
                    etiquetas[i] = etiquetas[i].replace(" ", "");
                }

                Set<Etiqueta> setDeEtiquetas = new HashSet<>();
                long tiempoAhora = System.currentTimeMillis();

                // Crear articulo con las etiquetas dadas.
                Usuario usuario = request.session(true).attribute("usuario");
                servicioEtiqueta.agregarEtiquetas(etiquetas, setDeEtiquetas);
                Articulo articulo = new Articulo(request.queryParams("titulo"), request.queryParams("cuerpo"), usuario, new Date(tiempoAhora), new ArrayList<>(), setDeEtiquetas, null, new HashSet<>());
                servicioArticulo.getInstancia().crear(articulo);

                response.redirect("/");

                return null;
            });

            get("/editar/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("templates/editarArticulo.ftl");

                Articulo articulo = servicioArticulo.getInstancia().find(Long.parseLong(req.params("id")));

                atributos.put("articulo", articulo);
                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());
                template.process(atributos, writer);

                return writer;
            });

            post("/editar/:id", (request, response) -> {

                Set<Etiqueta> setDeEtiquetas = new HashSet<>();

                String[] etiquetas = request.queryParams("etiquetas").split(",");
                servicioEtiqueta.agregarEtiquetas(etiquetas, setDeEtiquetas);
                Articulo articulo = servicioArticulo.getInstancia().find(Long.parseLong(request.params("id")));

                if (servicioEtiqueta.seModificaronLasEtiquetas(setDeEtiquetas, articulo.getListaEtiquetas())) {
                    articulo.setListaEtiquetas(setDeEtiquetas);
                    servicioArticulo.getInstancia().editar(articulo);
                }

                if (!articulo.getTitulo().equals(request.queryParams("titulo"))) {
                    articulo.setTitulo(request.queryParams("titulo"));
                    servicioArticulo.getInstancia().editar(articulo);
                }

                if (!articulo.getCuerpo().equals(request.queryParams("cuerpo"))) {
                    articulo.setCuerpo(request.queryParams("cuerpo"));
                    servicioArticulo.getInstancia().editar(articulo);
                }

                response.redirect("/");

                return null;
            });


            get("/eliminar/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("templates/eliminarArticulo.ftl");

                Articulo articulo = servicioArticulo.getInstancia().find(Long.parseLong(req.params("id")));

                atributos.put("articulo", articulo);
                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());
                template.process(atributos, writer);

                return writer;
            });

            post("/eliminar/:id", (req, res) -> {

                servicioArticulo.getInstancia().eliminar(Long.parseLong(req.params("id")));
                res.redirect("/");

                return null;
            });

            get("/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("templates/articulo.ftl");

                Articulo articulo = servicioArticulo.getInstancia().find(Long.parseLong(req.params("id")));

                List<Voto> meGusta = servicioVoto.getInstancia().encontrarVotosPorArticulo(articulo.getId(), "Me gusta");
                List<Voto> meDisgusta = servicioVoto.getInstancia().encontrarVotosPorArticulo(articulo.getId(), "Me disgusta");



                long votosMeGusta = meGusta != null ? meGusta.size() : 0;
                long votosMeDisgusta = meDisgusta != null ? meDisgusta.size() : 0;

                Voto votoActual = (Voto) servicioVoto.getInstancia().encontrarVotoUsuarioEnArticulo(articulo.getId(), usuario.getId());

                articulo.setListaComentarios(servicioComentario.getInstancia().encontrarComentario(articulo.getId()));

                for (Comentario comentario : articulo.getListaComentarios()) {
                    comentario.setMeGusta(servicioValoracion.getInstancia().encontrarValoracionesPorComentario(comentario.getId(), "Me gusta"));
                    comentario.setMeDisgusta(servicioValoracion.getInstancia().encontrarValoracionesPorComentario(comentario.getId(), "Me disgusta"));

                }

                atributos.put("articulo", articulo);
                atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
                atributos.put("nombreUsuario", nombreUsuario);
                atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
                atributos.put("esAdmin", usuario.isAdminstrator());
                atributos.put("votosMeGusta", votosMeGusta);
                atributos.put("votosMeDisgusta", votosMeDisgusta);
                atributos.put("votoActual", votoActual);

                template.process(atributos, writer);

                return writer;
            });

            post("/:id/votar", (req, res) -> {
                Articulo articulo = servicioArticulo.getInstancia().find(Long.parseLong(req.params("id")));

                Voto voto = (Voto) servicioVoto.getInstancia().encontrarVotoUsuarioEnArticulo(articulo.getId(), usuario.getId());

                if (voto == null) {
                    servicioVoto.getInstancia().crear(
                            new Voto(
                                    req.queryParams("voto"),
                                    usuario,
                                    articulo
                            )
                    );
                } else {
                    voto.setVoto(req.queryParams("voto"));
                    servicioVoto.getInstancia().editar(voto);
                }

                servicioArticulo.getInstancia().editar(articulo);

                res.redirect("/articulo/" + req.params("id"));

                return null;
            });

            post("/:id/comentar", (req, res) -> {
                String comentario = req.queryParams("comentario");
                Long articuloID = Long.parseLong(req.params("id"));

                Articulo articulo = servicioArticulo.getInstancia().find(articuloID);

                Comentario comentarioAux = new Comentario(comentario, usuario, articulo, null);
                servicioComentario.getInstancia().crear(comentarioAux);

                res.redirect("/articulo/" + articuloID);
                return null;
            });

            post("/:id/valorar", (req, res) -> {
                Comentario comentario = servicioComentario.getInstancia().find(Long.parseLong(req.params("id")));

                Valoracion valoracion = (Valoracion) servicioValoracion.getInstancia().encontrarValoracionUsuarioEnComentario(comentario.getId(), usuario.getId());

                if (valoracion == null) {
                    servicioValoracion.getInstancia().crear(
                            new Valoracion(
                                    req.queryParams("valoracion"),
                                    usuario,
                                    comentario
                            )
                    );
                } else {
                    valoracion.setValoracion(req.queryParams("valoracion"));
                    servicioValoracion.getInstancia().editar(valoracion);
                }


                servicioComentario.getInstancia().editar(comentario);

                res.redirect("/articulo/" + comentario.getArticulo().getId());

                return null;
            });
        });

        // filtrado de articulo por etiquetas
        get("/etiqueta/:id", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("templates/articulosPorEtiqueta.ftl");

            Etiqueta etiquetaSeleccionada = servicioEtiqueta.getInstancia().find(Long.parseLong(req.params("id")));

            Set<Articulo> articulos = new HashSet<>();

            for (Articulo art : servicioArticulo.getInstancia().findAll()) {
                for (Etiqueta eti : art.getListaEtiquetas()) {
                    if (eti.getId() == etiquetaSeleccionada.getId()) {
                        articulos.add(art);
                    }
                }
            }

            atributos.put("etiquetaSeleccionada", etiquetaSeleccionada);
            atributos.put("articulos", articulos);
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
            atributos.put("esAdmin", usuario.isAdminstrator());
            template.process(atributos, writer);

            return writer;
        });


        notFound((req, res) -> {
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate("templates/404.ftl");
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("estaLogueado", req.session().attribute("sesionUsuario") != null);
            atributos.put("nombreUsuario", nombreUsuario);
            atributos.put("tienePermisos", usuario.isAdminstrator() || usuario.isAutor());
            atributos.put("esAdmin", usuario.isAdminstrator());

            template.process(atributos, writer);
            res.status(404);
            res.body(writer.toString());

            return writer;
        });

    }

    private static Usuario restaurarSesion(String cookie) {
        StrongTextEncryptor encriptador = new StrongTextEncryptor();
        encriptador.setPassword("manga-anime-empire");
        String sesionSemanal = encriptador.decrypt(cookie);

        Usuario usuarioRestaurado = (Usuario) servicioUsuario.getInstancia().encontrarUsuarioSesion(sesionSemanal);
        nombreUsuario = usuarioRestaurado.getUsername();
        usuario = usuarioRestaurado;

        return usuarioRestaurado;
    }
}