package parcial2web.johanna.rodriguez.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {
    @Id
    private long id;
    private String username;
    private String nombre;
    private String password;
    private boolean administrator;
    private boolean autor;

    public Usuario() {

    }

    public Usuario(String username, String nombre,  String password, boolean administrator, boolean autor, String sesion) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.administrator = administrator;
        this.autor = autor;
    }

    public Usuario(Long id, String username,String nombre, String password, boolean administrator, boolean autor, String sesion) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.administrator = administrator;
        this.autor = autor;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdminstrator() {
        return administrator;
    }

    public void setAdminstrator(boolean adminstrator) {
        this.administrator = adminstrator;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }
}
