package co.edu.udea.mievaluacion.bl;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Respuesta {
	
	private String tipo;
	private String mensaje;
	/**
	 * @param tipo
	 * @param mensaje
	 */
	public Respuesta(String tipo, String mensaje) {
		super();
		this.tipo = tipo;
		this.mensaje = mensaje;
	}
	/**
	 * 
	 */
	public Respuesta() {
		super();
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
