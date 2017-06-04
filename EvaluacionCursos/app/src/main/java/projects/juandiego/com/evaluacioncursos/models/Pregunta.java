package projects.juandiego.com.evaluacioncursos.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Juan Diego on 12/04/2017.
 */
@DatabaseTable
public class Question {
    @DatabaseField(id = true)
    private String numero;
    @DatabaseField
    private String tipo;
    @DatabaseField
    private String descripcion;
    @DatabaseField
    private  String categoria;

    private String answer;

    public Question(){}

    public Question(String description) {
        this.descripcion = description;
    }

    public Question(String type, String description, String answer) {
        this.tipo = type;
        this.descripcion = description;
        this.answer = answer;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
