package co.edu.udea.mievaluacion.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */
@XmlRootElement
public class Professor {
    private String id;
    private String name;

    public Professor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Professor() {
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
