/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udea.mievaluacion.dto;

import java.io.Serializable;

/**
 *
 * @author Juan Diego
 */

public class EvaluacionPK implements Serializable {


    private int semestre;
    private int materia;
    private String estudiante;
    private int grupo;
    private String profesor;

    public EvaluacionPK() {
    }

    public EvaluacionPK(int semestre, int materia, String estudiante, int grupo, String profesor) {
        this.semestre = semestre;
        this.materia = materia;
        this.estudiante = estudiante;
        this.grupo = grupo;
        this.profesor = profesor;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getMateria() {
        return materia;
    }

    public void setMateria(int materia) {
        this.materia = materia;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
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
   
}
