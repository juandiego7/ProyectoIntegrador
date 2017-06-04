package co.edu.udea.mievaluacion.dto;

public class Calificacion {
	private String pregunta;
	private String respuesta;
	/**
	 * @param pregunta
	 * @param calificaion
	 */
	public Calificacion(String pregunta, String calificacion) {
		super();
		this.pregunta = pregunta;
		this.respuesta = calificacion;
	}
	/**
	 * 
	 */
	public Calificacion() {
		super();
	}
	/**
	 * @return the pregunta
	 */
	public String getPregunta() {
		return pregunta;
	}
	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}
	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}
