package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Juan Diego
 */
@XmlRootElement
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String semestre;
    private String materia;
    private String estudiante;
    private String grupo;
    private String profesor;
    private List<Calificacion> respuestas; 

    public Evaluacion() {
    }


	/**
	 * @param semestre
	 * @param materia
	 * @param estudiante
	 * @param grupo
	 * @param profesor
	 * @param pregunta
	 * @param respuesta
	 * @param respuestas
	 */
	public Evaluacion(String semestre, String materia, String estudiante, String grupo, String profesor,
			 List<Calificacion> respuestas) {
		super();
		this.semestre = semestre;
		this.materia = materia;
		this.estudiante = estudiante;
		this.grupo = grupo;
		this.profesor = profesor;
		this.respuestas = respuestas;
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
	 * @return the materia
	 */
	public String getMateria() {
		return materia;
	}

	/**
	 * @param materia the materia to set
	 */
	public void setMateria(String materia) {
		this.materia = materia;
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
	 * @return the profesor
	 */
	public String getProfesor() {
		return profesor;
	}

	/**
	 * @param profesor the profesor to set
	 */
	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}


	/**
	 * @return the respuestas
	 */
	public List<Calificacion> getRespuestas() {
		return respuestas;
	}


	/**
	 * @param respuestas the respuestas to set
	 */
	public void setRespuestas(List<Calificacion> respuestas) {
		this.respuestas = respuestas;
	}

    
}
