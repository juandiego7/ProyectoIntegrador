package projects.juandiego.com.evaluacioncursos.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Juan Diego on 12/04/2017.
 */

public class Teacher implements ParentObject {

    private List<Object> mQuestionsList;
    private UUID _id;
    private String name;

    public Teacher(String name) {
        this.name = name;
        _id = UUID.randomUUID();
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
