package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;
/**
 *
 * @author Juan Diego
 */

public class RespuestaPK implements Serializable {

    private int semestre;
    private String estudiante;
    private int materia;
    private int grupo;
    private String profesor;
    private int pregunta;

    public RespuestaPK() {
    }

    public RespuestaPK(int semestre, String estudiante, int materia, int grupo, String profesor, int pregunta) {
        this.semestre = semestre;
        this.estudiante = estudiante;
        this.materia = materia;
        this.grupo = grupo;
        this.profesor = profesor;
        this.pregunta = pregunta;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public int getMateria() {
        return materia;
    }

    public void setMateria(int materia) {
        this.materia = materia;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getPregunta() {
        return pregunta;
    }

    public void setPregunta(int pregunta) {
        this.pregunta = pregunta;
    }
    
}
