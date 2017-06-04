package projects.juandiego.com.evaluacioncursos;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projects.juandiego.com.evaluacioncursos.Dialog.DialogInfo;
import projects.juandiego.com.evaluacioncursos.adapter.AdapterEvaluacion;
import projects.juandiego.com.evaluacioncursos.config.DatabaseHelper;
import projects.juandiego.com.evaluacioncursos.models.Evaluacion;
import projects.juandiego.com.evaluacioncursos.models.Materia;
import projects.juandiego.com.evaluacioncursos.models.Pregunta;
import projects.juandiego.com.evaluacioncursos.models.Profesor;
import projects.juandiego.com.evaluacioncursos.models.TeacherCreator;
import projects.juandiego.com.evaluacioncursos.services.DescargarIntentService;


public class EvaluarActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEvaluacion;
    private TextView btnEnviar;
    private TextView tvMateria;
    private Toolbar toolbar;
    List<Pregunta> listPreguntas = new ArrayList<>();
    List<Profesor> listProfesor = new ArrayList<>();
    Materia materia = null;
    private String codigoMat = "";

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

        btnEnviar = (TextView) findViewById(R.id.btnSendEvaluacion);
        tvMateria = (TextView) findViewById(R.id.tvMateria);

        Intent intent = getIntent();
        if (intent != null){
            codigoMat = intent.getStringExtra(MainActivity.TAG_CODIGO);
        }

        inicializarDatos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void Info(View v){
        android.app.FragmentManager fm = getFragmentManager();
        DialogInfo dialoginfo = new DialogInfo();
        dialoginfo.show(fm, "Alerta");
    }

    public void inicializarDatos(){
        try {
            listPreguntas = DatabaseHelper.getInstance().getDao(Pregunta.class).queryForAll();
            Log.d(DescargarIntentService.ONHANDLEINTENT, "Codigo MAT: "+codigoMat);
            if(codigoMat == null){
                finish();
            }
            listProfesor = DatabaseHelper.getInstance().getDao(Profesor.class).queryBuilder().where().eq("codigoMateria", codigoMat).query();
            //DatabaseHelper.getInstance().getDao(Profesor.class).queryForEq("codigoMateria", codigoMat);
            Dao<Materia, String> dao = DatabaseHelper.getInstance().getDao(Materia.class);
            materia = dao.queryForId(codigoMat);
            //materia = DatabaseHelper.getInstance().getDao(Materia.class).
        } catch (SQLException e) {
            Log.d(DescargarIntentService.ONHANDLEINTENT, "Error en la BD " +  e.getMessage());
            //e.printStackTrace();
        }
        if (materia != null){
            tvMateria.setText(materia.getCodigo() + " - " + materia.getNombre());
        }else{
            Log.d(DescargarIntentService.ONHANDLEINTENT,"Ha ocurrido un error consultando la Materia");
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error consultando la Materia", Toast.LENGTH_SHORT).show();
            finish();
        }

        AdapterEvaluacion adapter = new AdapterEvaluacion(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerViewEvaluacion.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nuevo:
                Log.i("ActionBar", "Nuevo!");
                this.Info(getCurrentFocus());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view){
        Toast.makeText(this, "Imaginemos que se envió la evaluación", Toast.LENGTH_SHORT).show();
        /*
        * Parametros de la evaluación
        * Semestre, materia, estudiante, grupo, profesor, pregunta, respuesta
        * */
        DescargarIntentService.startActionEnviarEvaluacion();
    }

    private List<ParentObject> initData() {
        TeacherCreator titleCreator = TeacherCreator.get(this);
        //List<Profesor> listProfesor = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();

        /*list.add(new Pregunta("0","CONOCIMIENTOS",""));
        list.add(new Pregunta("Seguridad en exposiciones"));
        list.add(new Pregunta("Respuesta clara y acertada a preguntas"));
        list.add(new Pregunta("Dominio de los temas del curso o actividad curricular"));
        list.add(new Pregunta("0","METODOLOGIA",""));
        list.add(new Pregunta("Eficiencia en el uso del tiempo de clase o actividad curricular"));
        list.add(new Pregunta("Empleo de recursos didacticos"));
        list.add(new Pregunta("Orden, coherencia y claridad en las exposiciones de los temas"));
        list.add(new Pregunta("Capacidad para despertar interes"));
        list.add(new Pregunta("Apoyo a las actividades de aprendizaje independientes"));
        list.add(new Pregunta("Puntualidad y asistencia a las sesiones de clase o actividades"));
        list.add(new Pregunta("0","RELACIONES CON LOS ESTUDIANTES",""));
        list.add(new Pregunta("Puntualidad en la entrega de notas"));
        list.add(new Pregunta("Disposición para atender consultas fuera de la actividad curricular"));
        list.add(new Pregunta("Ecuanimidad y respeto en el trato con los estudiantes"));
        list.add(new Pregunta("0","MANEJO DE LA EVALUACION",""));
        list.add(new Pregunta("Objetivo en las calificaciones"));
        list.add(new Pregunta("Elaboracion de pruebas y exámenes"));
        list.add(new Pregunta("0","APRECIACION GENERAL",""));
        list.add(new Pregunta("Si usted tuviera que darle una calificación global al profesor¿Cual le pondria?"));
        list.add(new Pregunta("0","EVALUACION DEL CURSO",""));
        list.add(new Pregunta("Conveniencia de la intensidad horaria semanal"));
        list.add(new Pregunta("Logros de los objetivos formulados en el curso o actividad curricular"));
        list.add(new Pregunta("Relacion con los prerrequisitos del curso o actividad curricular"));
        list.add(new Pregunta("Interes y actualidad de los contenidos del curso o actividad curricular"));
        list.add(new Pregunta("Es importante dentro del plan de estudios"));
        listPreguntas = list;*/
        for(Profesor title: listProfesor){
            List<Object> childList = new ArrayList<>();
            if(listPreguntas == null){
                childList.add(new Pregunta("1","Sin preguntas","3"));

                //finish();
            }else{
                childList.addAll(listPreguntas);
                Log.d("tag", "null "+listPreguntas.size());
            }
            title.setChildObjectList(childList);
            parentObject.add(title);
        }
        return parentObject;

    }

}