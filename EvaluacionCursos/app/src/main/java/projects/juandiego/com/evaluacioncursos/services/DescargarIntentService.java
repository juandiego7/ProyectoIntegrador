package projects.juandiego.com.evaluacioncursos.services;

import android.app.IntentService;
import android.content.Intent;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import projects.juandiego.com.evaluacioncursos.MainActivity;
import projects.juandiego.com.evaluacioncursos.R;
import projects.juandiego.com.evaluacioncursos.config.DatabaseHelper;
import projects.juandiego.com.evaluacioncursos.models.Evaluacion;
import projects.juandiego.com.evaluacioncursos.models.Materia;
import projects.juandiego.com.evaluacioncursos.models.Pregunta;
import projects.juandiego.com.evaluacioncursos.models.Profesor;
import projects.juandiego.com.evaluacioncursos.models.Respuesta;
import projects.juandiego.com.evaluacioncursos.models.Ver;
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
    public static final String ACTION_DESCARGAR_NOTAS = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_NOTAS";
    public static final String ACTION_DESCARGAR_PUEDE_VER_NOTAS = "projects.juandiego.com.evaluacioncursos.services.action.PUEDE_VER_NOTAS";
    //public static final String ACTION_DESCARGAR_SEMESTRE = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_SEMESTR";
    public static final String TAG_SEMESTRE = "tagSemestre";
    public static final String TAG_ESTUDIANTE = "tagEstudiante";
    public static final String ERROR = "error";
    public static final String ONHANDLEINTENT = "onHandleIntent";
    public static final String ACTION_ENVIAR_EVALUACION = "projects.juandiego.com.evaluacioncursos.services.action.ENVIAR_EVALUACION";;

    public DescargarIntentService() {
        super("DescargarIntentService");
    }

    public static void startActionDescargarPreguntas() {
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.setAction(ACTION_DESCARGAR_PREGUNTAS);
        MainActivity.getContext().startService(intent);
        //Toast.makeText(MainActivity.getContext(), "A penas va a iniciar", Toast.LENGTH_SHORT).show();
    }

    public static void startActionDescargarNotas(String cedula){
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.setAction(ACTION_DESCARGAR_NOTAS);
        intent.putExtra(MainActivity.TAG_CEDULA, cedula);
        MainActivity.getContext().startService(intent);
    }

    public static void startActionEnviarEvaluacion(){
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.setAction(ACTION_ENVIAR_EVALUACION);
        MainActivity.getContext().startService(intent);
    }

    public static void startActionDescargarPuedeVerNotas(String semestre, String estudiante){
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.setAction(ACTION_DESCARGAR_PUEDE_VER_NOTAS);
        intent.putExtra(TAG_SEMESTRE, semestre);
        intent.putExtra(TAG_ESTUDIANTE, estudiante);
        MainActivity.getContext().startService(intent);
    }

    public static void startActionDescargarProfesores(String semestre, String estudiante) {
        Intent intent = new Intent(MainActivity.getContext(), DescargarIntentService.class);
        intent.putExtra(TAG_SEMESTRE, semestre);
        intent.putExtra(TAG_ESTUDIANTE, estudiante);
        intent.setAction(ACTION_DESCARGAR_PROFESORES);
        MainActivity.getContext().startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Toast.makeText(MainActivity.getContext(), "Iniciando...", Toast.LENGTH_SHORT).show();
        Log.d(ONHANDLEINTENT, "Iniciando");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DESCARGAR_PREGUNTAS.equals(action)) {
                handleActionDescargarPreguntas();
            } else if (ACTION_DESCARGAR_PROFESORES.equals(action)) {
                handleActionDescargarProfesores(intent.getStringExtra(TAG_SEMESTRE), intent.getStringExtra(TAG_ESTUDIANTE));
            } else if(ACTION_DESCARGAR_NOTAS.equals(action)){
                handleActionDescargarNotas(intent.getStringExtra(MainActivity.TAG_CEDULA));
            } else if(ACTION_DESCARGAR_PUEDE_VER_NOTAS.equals(action)){
                handleActionDescargarPuedeVerNotas(intent.getStringExtra(TAG_SEMESTRE), intent.getStringExtra(TAG_ESTUDIANTE));
            } else if(ACTION_ENVIAR_EVALUACION.equals(action)){
                handleActionEnviarEvaluacion();
            }
        }
    }

    private void handleActionEnviarEvaluacion() {
        Log.d(ONHANDLEINTENT,"Descargando Enviando Evaluacion");
        try {

            List<Evaluacion.Calificacion> respuestas = new ArrayList<>();
            Evaluacion e = new Evaluacion("sem","mat","est123","gr","profe");
            respuestas.add(new Evaluacion.Calificacion("1","2"));
            respuestas.add(new Evaluacion.Calificacion("4","5"));
            respuestas.add(new Evaluacion.Calificacion("3","3"));
            e.setRespuestas(respuestas);
            Response<Respuesta> response = RestfulServices.getInstance().sendEvaluar(e).execute();

            if (response.isSuccessful()){
                Log.d(ONHANDLEINTENT, "isSuccessful");
                Log.d(ONHANDLEINTENT, "Aja: "+response.body().getTipo());
                Log.d(ONHANDLEINTENT, "Aja: "+response.body().getMensaje());
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS));

            } else {
                Log.d(ONHANDLEINTENT, "NOT isSuccessful");
                Respuesta respuesta = RestfulServices.parseError(response);
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS).putExtra(ERROR, respuesta.getMensaje()));
            }
        } catch (IOException e) {
            Log.d(ONHANDLEINTENT, "Exception: "+ e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS).putExtra(ERROR, getResources().getString(R.string.error_conexion)));
            //e.printStackTrace();
        }
    }

    private void handleActionDescargarPuedeVerNotas(String sementre, String estudiante) {
        //Toast.makeText(MainActivity.getContext(), "Descargando... Puede Ver Notas", Toast.LENGTH_SHORT).show();
        Log.d(ONHANDLEINTENT,"Descargando Puede Ver Notas");
        try {
            Response<List<Ver>> response = RestfulServices.getInstance().getPuedeVerNotas(sementre, estudiante).execute();
            if (response.isSuccessful()){
                Log.d(ONHANDLEINTENT, "isSuccessful");
                Log.d(ONHANDLEINTENT, "Aja: "+response.body().get(0).getPuede());
                DatabaseHelper.getInstance().getDao(Ver.class).delete(DatabaseHelper.getInstance().getDao(Ver.class).queryForAll());
                for (Ver ver: response.body()){
                    try {
                        if (ver != null){
                            DatabaseHelper.getInstance().getDao(Ver.class).createOrUpdate(ver);
                        }else{
                            Log.d(ONHANDLEINTENT, "Ver null");
                        }
                    } catch (SQLException e) {
                        Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
                        //e.printStackTrace();
                    }
                }

                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS));

            } else {
                Log.d(ONHANDLEINTENT, "NOT isSuccessful");
                Respuesta respuesta = RestfulServices.parseError(response);
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS).putExtra(ERROR, respuesta.getMensaje()));
            }
        } catch (IOException e) {
            Log.d(ONHANDLEINTENT, "Exception: "+ e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS).putExtra(ERROR, getResources().getString(R.string.error_conexion)));
            //e.printStackTrace();
        } catch (SQLException e) {
            Log.d(ONHANDLEINTENT, "Exception Ver SQL: "+ e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PUEDE_VER_NOTAS).putExtra(ERROR, "Error con la BD"));
            //e.printStackTrace();
        }
    }

    private void handleActionDescargarPreguntas() {
        //Toast.makeText(MainActivity.getContext(), "Descargando... Preguntas", Toast.LENGTH_SHORT).show();
        Log.d(ONHANDLEINTENT,"Descargando Preguntas");
        /*List<Pregunta> list = new ArrayList<Pregunta>();
        list.add(new Pregunta("0","CONOCIMIENTOS",""));
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
        list.add(new Pregunta("Es importante dentro del plan de estudios"));*/
        //MateriaDAO.getInstance().eliminarNotas();
        //MateriaDAO.getInstance().saveAll(response.body());
        /*Log.d(ONHANDLEINTENT, "isSuccessful");



        //MainActivity.getContext()
        //        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));
        Log.d(ONHANDLEINTENT, "Aja: "+list.get(0).getDescripcion());
        try {
            long cant = DatabaseHelper.getInstance().getDao(Pregunta.class).countOf();
            if (cant == 0){
                for (Pregunta q: list){
                    //Log.d(ONHANDLEINTENT, "S: "+DatabaseHelper.getInstance().getWritableDatabase().toString());
                    if (q != null && DatabaseHelper.getInstance().getDao(Pregunta.class).isTableExists()){
                        DatabaseHelper.getInstance().getDao(Pregunta.class).createIfNotExists(q);
                    }else{
                        Log.d(ONHANDLEINTENT, "No existe la tabla o Pregunta null");
                    }
                }
            }
        } catch (SQLException e) {
            Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(DescargarIntentService.this)
                .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));

        */
        try {
            Response<List<Pregunta>> response = RestfulServices.getInstance().getPreguntas().execute();
            //if (response.isSuccessful()) {
            if (response.isSuccessful()){
                //response.body().add(new Pregunta("1","Seguridad en exposiciones","3"));
                //response.body().add(new Pregunta("2","Respuesta clara y acertada a preguntas","3"));
                //response.body().add(new Pregunta("3","Dominio de los temas del curso o actividad curricular","3"));
                //response.body().add(new Pregunta("4","Eficiencia en el uso del tiempo de clase o actividad curricular","3"));
                //response.body().add(new Pregunta("5","Empleo de recursos didacticos","3"));
                //MateriaDAO.getInstance().eliminarNotas();
                //MateriaDAO.getInstance().saveAll(response.body());
                Log.d(ONHANDLEINTENT, "isSuccessful");
                //MainActivity.getContext()
                //        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));
                Log.d(ONHANDLEINTENT, "Aja: "+response.body().get(0).getDescripcion());
                DatabaseHelper.getInstance().getDao(Pregunta.class).delete(DatabaseHelper.getInstance().getDao(Pregunta.class).queryForAll());
                for (Pregunta q: response.body()){
                    try {
                        //Log.d(ONHANDLEINTENT, "S: "+DatabaseHelper.getInstance().getWritableDatabase().toString());
                        if (q != null){
                            DatabaseHelper.getInstance().getDao(Pregunta.class).createOrUpdate(q);
                        }else{
                            Log.d(ONHANDLEINTENT, "No existe la tabla o Pregunta null");
                        }
                    } catch (SQLException e) {
                        Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
                        //e.printStackTrace();
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
            //e.printStackTrace();
        } catch (SQLException e) {
            Log.d(ONHANDLEINTENT, "Exception SQL: "+ e.getMessage());
            //e.printStackTrace();
        }
    }

    private void handleActionDescargarProfesores(String semestre, String estudiante) {
        Log.d(ONHANDLEINTENT, "Descargando Profesores");


        try {
            Response<List<Profesor>> response = RestfulServices.getInstance().getProfesores(semestre, estudiante).execute();
            if (response.isSuccessful()) {
                Log.d(ONHANDLEINTENT, "isSuccessful");
                Log.d(ONHANDLEINTENT, "Profesores: " + response.body().size());
                DatabaseHelper.getInstance().getDao(Profesor.class).delete(DatabaseHelper.getInstance().getDao(Profesor.class).queryForAll());
                for (Profesor p : response.body()) {
                    try {
                        if (p != null) {
                            DatabaseHelper.getInstance().getDao(Profesor.class).createOrUpdate(p);
                        } else {
                            Log.d(ONHANDLEINTENT, "Profesor null");
                        }
                    } catch (SQLException e) {
                        Log.d(ONHANDLEINTENT, "Error guardando en la base de datos " + e.getMessage());
                        //e.printStackTrace();
                    }
                }
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES));

            } else {
                Respuesta respuesta = RestfulServices.parseError(response);
                Log.d(ONHANDLEINTENT, "NOT isSuccessful " + respuesta.getMensaje());
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES).putExtra(ERROR, respuesta.getMensaje()));
            }
        } catch (IOException e) {
            Log.d(ONHANDLEINTENT, "Exception: " + e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES).putExtra(ERROR, getResources().getString(R.string.error_conexion)));
            //e.printStackTrace();
        } catch (SQLException e) {
            Log.d(ONHANDLEINTENT, "Exception Ver SQL: " + e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES).putExtra(ERROR, "Error con la BD"));
            //e.printStackTrace();

        }


        /*

        try {
            List<Materia> listMAterias = DatabaseHelper.getInstance().getDao(Materia.class).queryForAll();
            DatabaseHelper.getInstance().getDao(Profesor.class).delete(DatabaseHelper.getInstance().getDao(Profesor.class).queryForAll());
            Profesor p = null;
            for (Materia m: listMAterias){
                try {
                    if (m != null){
                        p = new Profesor("Pepito Perez ", "20001"+m.getCodigo(), m.getCodigo(),m.getNombre(),m.getGrupo());
                        DatabaseHelper.getInstance().getDao(Profesor.class).createOrUpdate(p);
                    }else{
                        Log.d(ONHANDLEINTENT, "No existe la tabla o Materia null");
                        LocalBroadcastManager.getInstance(DescargarIntentService.this)
                                .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES).putExtra(ERROR, "Error con la BD tabla Profesor"));
                    }
                } catch (SQLException e) {
                    Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
                    LocalBroadcastManager.getInstance(DescargarIntentService.this)
                            .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES).putExtra(ERROR, "Error con la BD tabla Profesor"));
                    //e.printStackTrace();
                }
            }
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PROFESORES));
        } catch (SQLException e) {
            Log.d(ONHANDLEINTENT, "Error Profesor SQL: "+e.getMessage());
            //e.printStackTrace();
        }
       */
    }

    private void handleActionDescargarNotas(String cedula) {
        //Toast.makeText(MainActivity.getContext(), "Descargando... Notas", Toast.LENGTH_SHORT).show();
        Log.d(ONHANDLEINTENT,"Descargando Notas");
        List<Materia> list = new ArrayList<Materia>();
        list.add(new Materia("255", "Campos", "2017-I", "05", "12345"));
        try {
            Response<List<Materia>> response = RestfulServices.getInstance().getNotas(cedula).execute();
            //if (response.isSuccessful()) {
            if (response.isSuccessful()){
                //response.body().add(new Materia("255", "Campos", "2017-I", "05", "12345"));
                //MateriaDAO.getInstance().eliminarNotas();
                //MateriaDAO.getInstance().saveAll(response.body());
                Log.d(ONHANDLEINTENT, "isSuccessful");
                //MainActivity.getContext()
                //        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));
                Log.d(ONHANDLEINTENT, "Notas "+response.body().size());
                DatabaseHelper.getInstance().getDao(Materia.class).delete(DatabaseHelper.getInstance().getDao(Materia.class).queryForAll());
                for (Materia q: response.body()){
                    try {
                        if (q != null){
                            DatabaseHelper.getInstance().getDao(Materia.class).createOrUpdate(q);
                        }else{
                            Log.d(ONHANDLEINTENT, "No existe la tabla o Materia null");
                        }
                    } catch (SQLException e) {
                        Log.d(ONHANDLEINTENT, "Error guardando en la base de datos "+e.getMessage());
                        //e.printStackTrace();
                    }
                }

                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_NOTAS));

            } else {
                Respuesta respuesta = RestfulServices.parseError(response);
                Log.d(ONHANDLEINTENT, "NOT isSuccessful: "+ respuesta.getMensaje());
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_NOTAS).putExtra(ERROR, respuesta.getMensaje()));
            }
        } catch (IOException e) {
            Log.d(ONHANDLEINTENT, "Exception: "+ e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_NOTAS).putExtra(ERROR, getResources().getString(R.string.error_conexion)));
            //e.printStackTrace();
        } catch (SQLException e) {
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_NOTAS).putExtra(ERROR, "Error con la BD"));
            //e.printStackTrace();
        }
    }
    }
