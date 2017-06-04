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

    private String nombre;
    private String cedula;
    private String codigoMateria;
    private String nombreMateria;
    private String grupoMateria;

    public Profesor() {
    }

    public Profesor(String cedula) {
        this.cedula = cedula;
    }

    /**
	 * @param nombre
	 * @param cedula
	 * @param codigoMateria
	 * @param nombreMateria
	 * @param grupoMateria
	 */
	public Profesor(String nombre, String cedula, String codigoMateria, String nombreMateria, String grupoMateria) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
		this.codigoMateria = codigoMateria;
		this.nombreMateria = nombreMateria;
		this.grupoMateria = grupoMateria;
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

	/**
	 * @return the codigoMateria
	 */
	public String getCodigoMateria() {
		return codigoMateria;
	}

	/**
	 * @param codigoMateria the codigoMateria to set
	 */
	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	/**
	 * @return the nombreMateria
	 */
	public String getNombreMateria() {
		return nombreMateria;
	}

	/**
	 * @param nombreMateria the nombreMateria to set
	 */
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	/**
	 * @return the grupoMateria
	 */
	public String getGrupoMateria() {
		return grupoMateria;
	}

	/**
	 * @param grupoMateria the grupoMateria to set
	 */
	public void setGrupoMateria(String grupoMateria) {
		this.grupoMateria = grupoMateria;
	}


}
