package projects.juandiego.com.evaluacioncursos.utils;

import java.util.List;

import projects.juandiego.com.evaluacioncursos.models.Question;
import projects.juandiego.com.evaluacioncursos.models.Teacher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public interface RestfulServicesI {
    @GET("preguntas")
    Call<List<Question>> getPreguntas();

    @GET("profesores")
    Call<List<Teacher>> getProfesores();
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
