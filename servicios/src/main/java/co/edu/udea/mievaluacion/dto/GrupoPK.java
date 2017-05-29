package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;

/**
 *
 * @author Juan Diego
 */

public class GrupoPK implements Serializable {

    private int numero;
    private int materia;
    private int semestre;

    public GrupoPK() {
    }

    public GrupoPK(int numero, int materia, int semestre) {
        this.numero = numero;
        this.materia = materia;
        this.semestre = semestre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getMateria() {
        return materia;
    }

    public void setMateria(int materia) {
        this.materia = materia;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

}
