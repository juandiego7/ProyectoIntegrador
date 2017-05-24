package projects.juandiego.com.evaluacioncursos.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import projects.juandiego.com.evaluacioncursos.MainActivity;
import projects.juandiego.com.evaluacioncursos.Notas;
import projects.juandiego.com.evaluacioncursos.R;
import projects.juandiego.com.evaluacioncursos.config.DatabaseHelper;
import projects.juandiego.com.evaluacioncursos.models.Question;
import projects.juandiego.com.evaluacioncursos.models.Respuesta;
import projects.juandiego.com.evaluacioncursos.utils.RestfulServices;
import retrofit2.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public class DescargarIntentService extends IntentService {

    public static final String ACTION_DESCARGAR_PREGUNTAS = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_PREGUNTAS";
    public static final String ACTION_DESCARGAR_PROFESORES = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_PROFESORES";
    //public static final String ACTION_DESCARGAR_SEMESTRE = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_SEMESTR";
    public static final String ERROR = "error";
    public static final String ONHANDLEINTENT = "onHandleIntent";

    public DescargarIntentService() {
        super("DescargarIntentService");
    }

    public static void startActionDescargarPreguntas() {
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.setAction(ACTION_DESCARGAR_PREGUNTAS);
        MainActivity.getContext().startService(intent);
        Toast.makeText(MainActivity.getContext(), "A penas va a iniciar", Toast.LENGTH_SHORT).show();
    }

    public static void startActionDescargarProfesores() {
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.setAction(ACTION_DESCARGAR_PROFESORES);
        MainActivity.getContext().startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(MainActivity.getContext(), "Iniciando...", Toast.LENGTH_SHORT).show();
        Log.d(ONHANDLEINTENT, "Iniciando");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DESCARGAR_PREGUNTAS.equals(action)) {
                handleActionDescargarPreguntas();
            } else if (ACTION_DESCARGAR_PROFESORES.equals(action)) {
                handleActionDescargarProfesores();
            }
        }
    }

    private void handleActionDescargarPreguntas() {
        Toast.makeText(MainActivity.getContext(), "Descargando...", Toast.LENGTH_SHORT).show();
        Log.d(ONHANDLEINTENT,"Descargando");
        List<Question> list = new ArrayList<Question>();
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
        //MateriaDAO.getInstance().eliminarNotas();
        //MateriaDAO.getInstance().saveAll(response.body());
        Log.d(ONHANDLEINTENT, "isSuccessful");
        //MainActivity.getContext()
        //        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));
        Log.d(ONHANDLEINTENT, "Aja: "+list.get(0).getDescription());
        try {
            long cant = DatabaseHelper.getInstance().getDao(Question.class).countOf();
            if (cant == 0){
                for (Question q: list){
                    //Log.d(ONHANDLEINTENT, "S: "+DatabaseHelper.getInstance().getWritableDatabase().toString());
                    if (q != null && DatabaseHelper.getInstance().getDao(Question.class).isTableExists()){
                        DatabaseHelper.getInstance().getDao(Question.class).createIfNotExists(q);
                    }else{
                        Log.d(ONHANDLEINTENT, "No existe la tabla o Question null");
                    }
                }
            }
        } catch (SQLException e) {
            Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(DescargarIntentService.this)
                .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));

        /*try {
            Response<List<Question>> response = RestfulServices.getInstance().getPreguntas().execute();
            //if (response.isSuccessful()) {
            if (response.isSuccessful()){
                response.body().add(new Question("1","Seguridad en exposiciones","3"));
                response.body().add(new Question("2","Respuesta clara y acertada a preguntas","3"));
                response.body().add(new Question("3","Dominio de los temas del curso o actividad curricular","3"));
                response.body().add(new Question("4","Eficiencia en el uso del tiempo de clase o actividad curricular","3"));
                response.body().add(new Question("5","Empleo de recursos didacticos","3"));
                //MateriaDAO.getInstance().eliminarNotas();
                //MateriaDAO.getInstance().saveAll(response.body());
                Log.d(ONHANDLEINTENT, "isSuccessful");
                //MainActivity.getContext()
                //        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));
                Log.d(ONHANDLEINTENT, "Aja: "+response.body().get(0).getDescription());

                for (Question q: response.body()){
                    try {
                        Log.d(ONHANDLEINTENT, "S: "+DatabaseHelper.getInstance().getWritableDatabase().toString());
                        if (q != null && DatabaseHelper.getInstance().getDao(Question.class).isTableExists()){
                            DatabaseHelper.getInstance().getDao(Question.class).createOrUpdate(q);
                        }else{
                            Log.d(ONHANDLEINTENT, "No existe la tabla o Question null");
                        }
                    } catch (SQLException e) {
                        Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
                        e.printStackTrace();
                    }
                }

                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));

            } else {
                Log.d(ONHANDLEINTENT, "NOT isSuccessful");
                Respuesta respuesta = RestfulServices.parseError(response);
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS).putExtra(ERROR, respuesta.getMensaje()));
            }
        } catch (IOException e) {
            Log.d(ONHANDLEINTENT, "Exception: "+ e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS).putExtra(ERROR, getResources().getString(R.string.error_conexion)));
            e.printStackTrace();
        }*/
    }

    private void handleActionDescargarProfesores() {

    }
}
