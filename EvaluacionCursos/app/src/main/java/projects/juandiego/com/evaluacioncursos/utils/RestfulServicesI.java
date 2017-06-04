package projects.juandiego.com.evaluacioncursos.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import projects.juandiego.com.evaluacioncursos.models.Evaluacion;
import projects.juandiego.com.evaluacioncursos.models.Materia;
import projects.juandiego.com.evaluacioncursos.models.Pregunta;
import projects.juandiego.com.evaluacioncursos.models.Profesor;
import projects.juandiego.com.evaluacioncursos.models.Respuesta;
import projects.juandiego.com.evaluacioncursos.models.Ver;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public interface RestfulServicesI {
    @GET("pregunta/all")
    Call<List<Pregunta>> getPreguntas();

    @GET("profesor/{semestre}/{estudiante}")
    Call<List<Profesor>> getProfesores(@Path("semestre") String semestre, @Path("estudiante") String estudiante);

    @GET("materia/notas/{cedula}")
    Call<List<Materia>> getNotas(@Path("cedula") String cedula);

    @GET("evaluacion/puedevernotas/{semestre}/{estudiante}")
    Call<List<Ver>> getPuedeVerNotas(@Path("semestre") String semestre, @Path("estudiante") String estudiante);

    @POST("evaluacion")
    Call<Respuesta> sendEvaluar(@Body() Evaluacion e);
/*
    @GET("programas/{programa}/semestre/{semestre}")
    Call<List<Materia>> getDetalleSemestre(@Path("programa") Integer programa,
                                           @Path("semestre") Integer semestre);

    @GET("development/testlist")
    void getNotasEspecificas(@Header("cedula") String cedula,
                             @Header("programa") String programa,
                             @Header("semestre") String semestre,
                             Callback<List<Materia>> cb);
                             */
}
