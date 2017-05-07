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

public class TeacherCreator {
    static TeacherCreator _titleCreator;
    List<Teacher> _titleParents;

    public TeacherCreator(Context context) {
        _titleParents = new ArrayList<>();
        Teacher title = new Teacher("RAUL ANTONIO MARTINEZ SILGADO");
        _titleParents.add(title);
        title = new Teacher("JOHNNY ALEJADNRO CSASTAÑEDA VILLA");
        _titleParents.add(title);
        /*title = new Teacher(String.format("Profesor 3"));
        _titleParents.add(title);
        title = new Teacher(String.format("Profesor 4"));
        _titleParents.add(title);
        title = new Teacher(String.format("Profesor 5"));
        _titleParents.add(title);*/
        /*for(int i=1;i<=5;i++)
        {
            TitleParent title = new TitleParent(String.format("INGENIERIA WEB"));
            _titleParents.add(title);
        }*/
    }

    public static TeacherCreator get(Context context)
    {
        if(_titleCreator == null)
            _titleCreator = new TeacherCreator(context);
        return _titleCreator;
    }

    public List<Teacher> getAll() {
        return _titleParents;
    }
}