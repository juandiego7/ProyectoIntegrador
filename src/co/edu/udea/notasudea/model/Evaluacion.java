package co.edu.udea.notasudea.model;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 16/07/2013
 */
public class Evaluacion {

	/**
	 * Número de la evaluación.
	 */
	private String numero;
	/**
	 * Descripcion de la evaluación.
	 */
	private String descripcion;
	/**
	 * Porcentaje de la evalucion dentro de la materia.
	 */
	private String porcentaje;
	/**
	 * Nota obtenida por el estudiante en la evaluación.
	 */
	private String nota;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
}
