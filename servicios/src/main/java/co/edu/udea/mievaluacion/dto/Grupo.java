package co.edu.udea.mievaluacion.dto;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Juan Diego
 */
@XmlRootElement
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private GrupoPK grupoPK;
    private Materia materia;
    private Semestre semestre;

    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(int numero, int materia, int semestre) {
        this.grupoPK = new GrupoPK(numero, materia, semestre);
    }

    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia1) {
        this.materia = materia1;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre1) {
        this.semestre = semestre1;
    }
    
}
