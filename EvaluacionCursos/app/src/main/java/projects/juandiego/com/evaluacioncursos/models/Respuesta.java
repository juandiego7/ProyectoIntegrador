package projects.juandiego.com.evaluacioncursos.models;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public class Respuesta {

    public enum Tipo {
        Error
    }

    private Tipo tipo;
    private String mensaje;

    public Respuesta() {
    }

    public Respuesta(Tipo tipo, String mensaje) {
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
