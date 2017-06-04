package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */

@XmlRootElement
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    private String semestre;
	private String estudiante;
	private String codigo;
	private String grupo;
	private String nombre;

    public Materia() {
    }

    public Materia(String codigo) {
        this.codigo = codigo;
    }
    

	/**
	 * @param semestre
	 * @param estudiante
	 * @param codigo
	 * @param grupo
	 * @param nombre
	 */
	public Materia(String semestre, String estudiante, String codigo, String grupo, String nombre) {
		super();
		this.semestre = semestre;
		this.estudiante = estudiante;
		this.codigo = codigo;
		this.grupo = grupo;
		this.nombre = nombre;
	}

	/**
	 * @return the semestre
	 */
	public String getSemestre() {
		return semestre;
	}

	/**
	 * @param semestre the semestre to set
	 */
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	/**
	 * @return the estudiante
	 */
	public String getEstudiante() {
		return estudiante;
	}

	/**
	 * @param estudiante the estudiante to set
	 */
	public void setEstudiante(String estudiante) {
		this.estudiante = estudiante;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
