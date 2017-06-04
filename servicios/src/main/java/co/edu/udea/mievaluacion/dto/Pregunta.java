package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */

@XmlRootElement
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String numero;
    private String tipo;
    private String descripcion;
    private String categoria;

    public Pregunta() {
    }

    public Pregunta(String numero) {
        this.numero = numero;
    }

    public Pregunta(String numero, String tipo, String descripcion, String categoria) {
        this.numero = numero;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
}
