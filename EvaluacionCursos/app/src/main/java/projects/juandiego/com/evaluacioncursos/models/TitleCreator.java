package projects.juandiego.com.evaluacioncursos.models;

/**
 * Created by Juan Diego on 12/04/2017.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reale on 23/11/2016.
 */

public class TitleCreator {
    static TitleCreator _titleCreator;
    List<TitleParent> _titleParents;

    public TitleCreator(Context context) {
        _titleParents = new ArrayList<>();
        TitleParent title = new TitleParent(String.format("INGENIERIA WEB"));
        _titleParents.add(title);
        title = new TitleParent(String.format("COMUNICACIONES Y LABORATORIO"));
        _titleParents.add(title);
        title = new TitleParent(String.format("ARQUITECTURA DEL SOFTWARE"));
        _titleParents.add(title);
        title = new TitleParent(String.format("SIMULACION DE SISTEMAS Y LABORATORIO"));
        _titleParents.add(title);
        title = new TitleParent(String.format("PROYECTO INTEGRADOR I"));
        _titleParents.add(title);
        /*for(int i=1;i<=5;i++)
        {
            TitleParent title = new TitleParent(String.format("INGENIERIA WEB"));
            _titleParents.add(title);
        }*/
    }

    public static TitleCreator get(Context context)
    {
        if(_titleCreator == null)
            _titleCreator = new TitleCreator(context);
        return _titleCreator;
    }

    public List<TitleParent> getAll() {
        return _titleParents;
    }
}