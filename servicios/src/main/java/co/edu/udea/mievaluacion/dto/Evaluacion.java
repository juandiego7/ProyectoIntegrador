package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Juan Diego
 */
@XmlRootElement
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private EvaluacionPK evaluacionPK;
    private Date fechaInicio;
    private Date fechaFin;
    private String realizado;

    public Evaluacion() {
    }


    public Evaluacion(EvaluacionPK evaluacionPK, Date fechaInicio, Date fechaFin, String realizado) {
        this.evaluacionPK = evaluacionPK;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.realizado = realizado;
    }

    public Evaluacion(int semestre, int materia, String estudiante, int grupo, String profesor) {
        this.evaluacionPK = new EvaluacionPK(semestre, materia, estudiante, grupo, profesor);
    }

    public EvaluacionPK getEvaluacionPK() {
        return evaluacionPK;
    }

    public void setEvaluacionPK(EvaluacionPK evaluacionPK) {
        this.evaluacionPK = evaluacionPK;
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

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPK != null ? evaluacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.evaluacionPK == null && other.evaluacionPK != null) || (this.evaluacionPK != null && !this.evaluacionPK.equals(other.evaluacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.udea.mavenproject1.Evaluacion[ evaluacionPK=" + evaluacionPK + " ]";
    }
    
}
