package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */

@XmlRootElement
public class Semestre implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer codigo;

    private Date fechaInicio;

    private Date fechaFin;

    public Semestre() {
    }

    public Semestre(Integer codigo) {
        this.codigo = codigo;
    }

    public Semestre(Integer codigo, Date fechaInicio, Date fechaFin) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
