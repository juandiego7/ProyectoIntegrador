package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */

@XmlRootElement
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    private RespuestaPK respuestaPK;
    private String respuesta;

    public Respuesta() {
    }

    public Respuesta(RespuestaPK respuestaPK) {
        this.respuestaPK = respuestaPK;
    }

    public Respuesta(RespuestaPK respuestaPK, String respuesta) {
        this.respuestaPK = respuestaPK;
        this.respuesta = respuesta;
    }

    public Respuesta(int semestre, String estudiante, int materia, int grupo, String profesor, int pregunta) {
        this.respuestaPK = new RespuestaPK(semestre, estudiante, materia, grupo, profesor, pregunta);
    }

    public RespuestaPK getRespuestaPK() {
        return respuestaPK;
    }

    public void setRespuestaPK(RespuestaPK respuestaPK) {
        this.respuestaPK = respuestaPK;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
