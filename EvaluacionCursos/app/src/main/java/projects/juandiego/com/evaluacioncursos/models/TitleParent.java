package projects.juandiego.com.evaluacioncursos.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import java.util.List;
import java.util.UUID;

/**
 * Created by Juan Diego on 12/04/2017.
 */

public class TitleParent implements ParentObject{

    private List<Object> mChildrenList;
    private UUID _id;
    private String title;
    private String puedeVerNota;
    private String codigo;

    public TitleParent(String title) {
        this.title = title;
        _id = UUID.randomUUID();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPuedeVerNota() {
        return puedeVerNota;
    }

    public void setPuedeVerNota(String puedeVerNota) {
        this.puedeVerNota = puedeVerNota;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }
}