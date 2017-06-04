package projects.juandiego.com.evaluacioncursos.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rantonio.martinez on 02/06/2017.
 */

@DatabaseTable
public class Materia {
    @DatabaseField(id = true)
    private String codigo;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String semestre;
    @DatabaseField
    private String grupo;
    @DatabaseField
    private String estudiante;

    public Materia() {}

    public Materia(String codigo, String nombre, String semestre, String grupo, String estudiante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.semestre = semestre;
        this.grupo = grupo;
        this.estudiante = estudiante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }
}
