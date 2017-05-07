package projects.juandiego.com.evaluacioncursos;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;


import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import projects.juandiego.com.evaluacioncursos.adapter.AdapterEvaluacion;
import projects.juandiego.com.evaluacioncursos.models.Question;
import projects.juandiego.com.evaluacioncursos.models.Teacher;
import projects.juandiego.com.evaluacioncursos.models.TeacherCreator;


public class EvaluarActivity extends AppCompatActivity {

    RecyclerView recyclerViewEvaluacion;
    TextView btnEnviar;
    private Toolbar toolbar;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((AdapterEvaluacion)recyclerViewEvaluacion.getAdapter()).onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Evalua tu Curso");
        setContentView(R.layout.activity_evaluar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewEvaluacion = (RecyclerView)findViewById(R.id.myRecyclerViewEvaluar);
        recyclerViewEvaluacion.setLayoutManager(new LinearLayoutManager(this));

        btnEnviar = (TextView) findViewById(R.id.btnSend);

        AdapterEvaluacion adapter = new AdapterEvaluacion(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerViewEvaluacion.setAdapter(adapter);

    }


    private List<ParentObject> initData() {
        TeacherCreator titleCreator = TeacherCreator.get(this);
        List<Teacher> titles = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        for(Teacher title:titles)
        {
            List<Object> childList = new ArrayList<>();
            childList.add(new Question("1","Seguridad en exposiciones","3"));
            childList.add(new Question("2","Respuesta clara y acertada a preguntas","3"));
            childList.add(new Question("3","Dominio de los temas del curso o actividad curricular","3"));
            childList.add(new Question("4","Eficiencia en el uso del tiempo de clase o actividad curricular","3"));
            childList.add(new Question("5","Empleo de recursos didacticos","3"));
            childList.add(new Question("6","Orden, coherencia y claridad en la exposicion de los temas","3"));
            childList.add(new Question("7","Capacidad para despertar interes","3"));
            childList.add(new Question("8","Apoyo a las actividades de aprendizaje independientes","3"));
            childList.add(new Question("9","Puntualidad y asistencia a las sesiones de clase o actividades","3"));
            childList.add(new Question("10","Puntualidad en la entrega de notas","3"));
            childList.add(new Question("11","Disposicion para atender consultas fuera de la actividad curricular","3"));
            childList.add(new Question("12","Ecuanimidad y respeto en el trato con los estudiantes","3"));
            childList.add(new Question("13","Objetivo en las calificaciones","3"));
            childList.add(new Question("14","Elaboracion de pruebas y examenes","3"));
            childList.add(new Question("15","Si usted tuviera que darle una calificacion global al profesor ¿Cual pondria?","3"));
            childList.add(new Question("16","Conveniencia de la intensidad horaria semanal","3"));
            childList.add(new Question("17","Logro de los objetivos formulados en el curso o actividad curricular","3"));
            childList.add(new Question("18","Relacion con los prerriquisitos del curso o actividad curricular","3"));
            childList.add(new Question("19","Interes y actualidad de los contenidos del curso o actividad curricular","3"));
            childList.add(new Question("20","Es importante dentro del plan de estudios","3"));
            title.setChildObjectList(childList);
            parentObject.add(title);
        }
        return parentObject;

    }

}