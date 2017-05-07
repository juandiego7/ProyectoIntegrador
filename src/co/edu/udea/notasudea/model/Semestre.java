package co.edu.udea.notasudea.model;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 3/07/2013
 */
public class Semestre {

	/**
	 * Identificador unico del semestre 
	 */
	private String codSemestre;
	/**
	 * Promedio obtenido en el semestre por el estudiante
	 */
	private String promedio;
	
	public String getCodSemestre() {
		return codSemestre;
	}
	
	public void setCodSemestre(String codSemestre) {
		this.codSemestre = codSemestre;
	}
	
	public String getPromedio() {
		return promedio;
	}
	
	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}
	
}