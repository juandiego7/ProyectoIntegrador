package co.edu.udea.mievaluacion.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */
@XmlRootElement
public class Profesor {
    private String id;
    private String nombre;

    public Profesor(String id, String name) {
        this.id = id;
        this.nombre = name;
    }

    public Profesor() {
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = name;
    }
    
}
