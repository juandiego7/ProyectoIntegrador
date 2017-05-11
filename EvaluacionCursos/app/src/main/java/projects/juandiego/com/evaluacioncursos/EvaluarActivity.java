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
        List<Question> listPreguntas = null;
        try {
            listPreguntas = DatabaseHelper.getInstance().getDao(Question.class).queryForAll();
        } catch (SQLException e) {
            Log.d("onHandleIntent", "Error cosultando la base de datos");
            e.printStackTrace();
        }


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