package projects.juandiego.com.evaluacioncursos.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import projects.juandiego.com.evaluacioncursos.MainActivity;
import projects.juandiego.com.evaluacioncursos.Notas;
import projects.juandiego.com.evaluacioncursos.R;
import projects.juandiego.com.evaluacioncursos.models.Question;
import projects.juandiego.com.evaluacioncursos.models.Respuesta;
import projects.juandiego.com.evaluacioncursos.utils.RestfulServices;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public class DescargarIntentService extends IntentService {

    public static final String ACTION_DESCARGAR_PREGUNTAS = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_PREGUNTAS";
    public static final String ACTION_DESCARGAR_PROFESORES = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_PROFESORES";
    //public static final String ACTION_DESCARGAR_SEMESTRE = "projects.juandiego.com.evaluacioncursos.services.action.DESCARGAR_SEMESTR";
    public static final String ERROR = "error";



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
        Log.d("onHandleIntent", "Iniciando");
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
        Log.d("onHandleIntent", "Descargando");
        try {
            Response<List<Question>> response = RestfulServices.getInstance().getPreguntas().execute();
            if (response.isSuccessful()) {
                //MateriaDAO.getInstance().eliminarNotas();
                //MateriaDAO.getInstance().saveAll(response.body());
                Log.d("onHandleIntent", "isSuccessful");

                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS));

                Log.d("onHandleIntent", "Aja: "+response.body().get(0).getDescription());
            } else {
                Log.d("onHandleIntent", "NOT isSuccessful");
                Respuesta respuesta = RestfulServices.parseError(response);
                LocalBroadcastManager.getInstance(DescargarIntentService.this)
                        .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS).putExtra(ERROR, respuesta.getMensaje()));
            }
        } catch (IOException e) {
            Log.d("onHandleIntent", "Exception: "+ e.getMessage());
            LocalBroadcastManager.getInstance(DescargarIntentService.this)
                    .sendBroadcast(new Intent(ACTION_DESCARGAR_PREGUNTAS)
                            .putExtra(ERROR, getResources().getString(R.string.error_conexion)));
            e.printStackTrace();
        }
    }

    private void handleActionDescargarProfesores() {

    }
}
