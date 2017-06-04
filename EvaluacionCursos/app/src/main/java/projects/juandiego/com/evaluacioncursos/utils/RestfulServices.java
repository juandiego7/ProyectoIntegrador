package projects.juandiego.com.evaluacioncursos.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;

import projects.juandiego.com.evaluacioncursos.models.Respuesta;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public class RestfulServices {

    //public static final String END_POINT = "http://hakuna.udea.edu.co:8080/NotasAndroidWS/api/";
    public static final String END_POINT = "http://192.168.1.4:8080/servicios/webapi/";
    private static RestfulServicesI instance;
    private static Converter<ResponseBody, Respuesta> converter;

    public static RestfulServicesI getInstance() {
        if (instance == null) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain)
                        throws IOException {
                    Request request = chain.request();
                    /**
                     * se añade el token de sesion del usuario a cada peticion.
                     */
                    request = request.newBuilder()
                            .addHeader("token", "2ijbdhm2mpmqt04du3sckasa6h")
                            //.addHeader("token", UdeApps.getToken(Notas.getContext()))
                            .build();
                    return chain.proceed(request);
                }
            };
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(END_POINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();

            converter = retrofit.responseBodyConverter(Respuesta.class, new Annotation[0]);
            instance = retrofit.create(RestfulServicesI.class);
        }
        return instance;
    }

    public static Respuesta parseError(Response<?> response) {
        Respuesta respuesta;
        try {
            respuesta = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Respuesta();
        }
        return respuesta;
    }

}
