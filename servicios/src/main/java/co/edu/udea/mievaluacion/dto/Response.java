package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */

@XmlRootElement
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    public Response() {
    }

	/**
	 * @param message
	 */
	public Response(String message) {
		super();
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}



}
