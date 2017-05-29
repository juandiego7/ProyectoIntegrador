package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Juan Diego
 */

@XmlRootElement

public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;


    private String cedula;
    private String nombre;
    private String apellidos;

    public Profesor() {
    }

    public Profesor(String cedula) {
        this.cedula = cedula;
    }

    public Profesor(String cedula, String nombre, String apellidos) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
