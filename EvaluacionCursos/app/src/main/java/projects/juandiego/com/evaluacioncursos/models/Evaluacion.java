package projects.juandiego.com.evaluacioncursos.models;

import java.util.List;

/**
 * Created by rantonio.martinez on 03/06/2017.
 */

public class Evaluacion {
    private String semestre;
    private String materia;
    private String estudiante;
    private String grupo;
    private String profesor;
    List<Calificacion> respuestas;

    public Evaluacion() {    }


    public Evaluacion(String semestre, String materia, String estudiante, String grupo, String profesor) {
        this.semestre = semestre;
        this.materia = materia;
        this.estudiante = estudiante;
        this.grupo = grupo;
        this.profesor = profesor;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public List<Calificacion> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Calificacion> respuestas) {
        this.respuestas = respuestas;
    }

    public static class Calificacion{
        private String pregunta;
        private String respuesta;

        public Calificacion() {
        }

        public Calificacion(String pregunta, String respuesta) {
            this.pregunta = pregunta;
            this.respuesta = respuesta;
        }

        public String getPregunta() {
            return pregunta;
        }

        public void setPregunta(String pregunta) {
            this.pregunta = pregunta;
        }

        public String getRespuesta() {
            return respuesta;
        }

        public void setRespuesta(String respuesta) {
            this.respuesta = respuesta;
        }
    };
}
