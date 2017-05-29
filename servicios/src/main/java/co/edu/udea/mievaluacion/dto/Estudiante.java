package co.edu.udea.mievaluacion.dto;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */

@XmlRootElement
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cedula;
    private String nombre;
    private String apellidos;
    private int programa;


    public Estudiante() {
    }

    public Estudiante(String cedula) {
        this.cedula = cedula;
    }

    public Estudiante(String cedula, String nombre, String apellidos, int programa) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.programa = programa;
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

    public int getPrograma() {
        return programa;
    }

    public void setPrograma(int programa) {
        this.programa = programa;
    }
}
