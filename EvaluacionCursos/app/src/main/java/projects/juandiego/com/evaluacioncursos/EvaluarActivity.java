package projects.juandiego.com.evaluacioncursos;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projects.juandiego.com.evaluacioncursos.adapter.AdapterEvaluacion;
import projects.juandiego.com.evaluacioncursos.config.DatabaseHelper;
import projects.juandiego.com.evaluacioncursos.models.Question;
import projects.juandiego.com.evaluacioncursos.models.Respuesta;
import projects.juandiego.com.evaluacioncursos.models.Teacher;
import projects.juandiego.com.evaluacioncursos.models.TeacherCreator;
import projects.juandiego.com.evaluacioncursos.services.DescargarIntentService;
import projects.juandiego.com.evaluacioncursos.utils.RestfulServices;
import retrofit2.Response;


public class EvaluarActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEvaluacion;
    private TextView btnEnviar;
    private Toolbar toolbar;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((AdapterEvaluacion)recyclerViewEvaluacion.getAdapter()).onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mi Evaluacion");
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
        List<Question> listPreguntas = null;
        List<Question> list = new ArrayList<Question>();
        try {
            listPreguntas = DatabaseHelper.getInstance().getDao(Question.class).queryForAll();
        } catch (SQLException e) {
            Log.d("onHandleIntent", "Error cosultando la base de datos");
            e.printStackTrace();
        }
        list.add(new Question("0","CONOCIMIENTOS",""));
        list.add(new Question("Seguridad en exposiciones"));
        list.add(new Question("Respuesta clara y acertada a preguntas"));
        list.add(new Question("Dominio de los temas del curso o actividad curricular"));
        list.add(new Question("0","METODOLOGIA",""));
        list.add(new Question("Eficiencia en el uso del tiempo de clase o actividad curricular"));
        list.add(new Question("Empleo de recursos didacticos"));
        list.add(new Question("Orden, coherencia y claridad en las exposiciones de los temas"));
        list.add(new Question("Capacidad para despertar interes"));
        list.add(new Question("Apoyo a las actividades de aprendizaje independientes"));
        list.add(new Question("Puntualidad y asistencia a las sesiones de clase o actividades"));
        list.add(new Question("0","RELACIONES CON LOS ESTUDIANTES",""));
        list.add(new Question("Puntualidad en la entrega de notas"));
        list.add(new Question("Disposición para atender consultas fuera de la actividad curricular"));
        list.add(new Question("Ecuanimidad y respeto en el trato con los estudiantes"));
        list.add(new Question("0","MANEJO DE LA EVALUACION",""));
        list.add(new Question("Objetivo en las calificaciones"));
        list.add(new Question("Elaboracion de pruebas y exámenes"));
        list.add(new Question("0","APRECIACION GENERAL",""));
        list.add(new Question("Si usted tuviera que darle una calificación global al profesor¿Cual le pondria?"));
        list.add(new Question("0","EVALUACION DEL CURSO",""));
        list.add(new Question("Conveniencia de la intensidad horaria semanal"));
        list.add(new Question("Logros de los objetivos formulados en el curso o actividad curricular"));
        list.add(new Question("Relacion con los prerrequisitos del curso o actividad curricular"));
        list.add(new Question("Interes y actualidad de los contenidos del curso o actividad curricular"));
        list.add(new Question("Es importante dentro del plan de estudios"));
        listPreguntas = list;
        for(Teacher title:titles)        {
            List<Object> childList = new ArrayList<>();
            if(listPreguntas == null){
                childList.add(new Question("1","Sin preguntas","3"));
                //finish();
            }else{
                for (Question q: listPreguntas){
                    childList.add(q);
                }
            }
            title.setChildObjectList(childList);
            parentObject.add(title);
        }
        return parentObject;

    }

}