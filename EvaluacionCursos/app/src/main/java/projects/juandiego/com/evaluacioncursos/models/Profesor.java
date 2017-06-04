package projects.juandiego.com.evaluacioncursos.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
import java.util.UUID;

/**
 * Created by Juan Diego on 12/04/2017.
 */

@DatabaseTable
public class Profesor implements ParentObject {

    private List<Object> mQuestionsList;
    private UUID _id;

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int cod;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String cedula;
    @DatabaseField
    private String codigoMateria;
    @DatabaseField
    private String nombreMateria;
    @DatabaseField
    private String grupoMateria;

    public Profesor() {    }

    public Profesor(String nombre, String cedula, String codigoMateria, String nombreMateria, String grupoMateria) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.codigoMateria = codigoMateria;
        this.nombreMateria = nombreMateria;
        this.grupoMateria = grupoMateria;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getGrupoMateria() {
        return grupoMateria;
    }

    public void setGrupoMateria(String grupoMateria) {
        this.grupoMateria = grupoMateria;
    }

    public Profesor(String name) {
        this.nombre = name;
        _id = UUID.randomUUID();
    }

    public Profesor(String cedula, String name) {
        this.nombre = name;
        this.cedula = cedula;
        _id = UUID.randomUUID();
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mQuestionsList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mQuestionsList = list;
    }
}
