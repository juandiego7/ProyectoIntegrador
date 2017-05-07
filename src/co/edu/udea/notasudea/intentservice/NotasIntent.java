package co.edu.udea.notasudea.intentservice;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.notasudea.model.dao.NotasDAO;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.HttpClientFactory;
import co.edu.udea.notasudea.util.ppt.LeerPropiedades;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 24/03/2014
 */
public class NotasIntent extends IntentService{
	
	private Context context;
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;
	/**
	 * Identificador de la sesion del estudiante
	 */
	private String token;
	private static final String TAG_NOTAS = "actividadesMaterias";
	
	public static final String ACTION_OK = "co.edu.udea.notasudea.action.OK";

	public NotasIntent() {
		super("NotasIntent");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		context = getApplicationContext();
		getSession();
		actualizarNotas();
	}
	
	/**
	 * Obtiene los datos de la sesion del estudiante.
	 */
	private void getSession() {
		//Manejador de la sesion
		session = new SessionManager(context);      
		//Se obtiene el token del estudiante para consultar informacion
		token = session.getToken();
	}

	private void actualizarNotas() {
		LeerPropiedades propiedades = new LeerPropiedades(context, LeerPropiedades.PROPERTIES);			
		HttpClient httpClient;
		try {
			httpClient = HttpClientFactory.getThreadSafeClient();

			HttpGet peticion = new HttpGet(propiedades.getResource(LeerPropiedades.RECURSO_NOTAS));
			peticion.setHeader("token", token);

			HttpResponse resp = httpClient.execute(peticion);
			String respStr = EntityUtils.toString(resp.getEntity());		
			JSONObject jsonObject = new JSONObject(respStr);

			//Se comprueba que respuesta sean las materias
			if(jsonObject.has(TAG_NOTAS)){
				NotasDAO notasDAO = new NotasDAO(context);
				notasDAO.eliminarDatos();
				notasDAO.saveNotas(jsonObject);
				Log.d("Intent",jsonObject.toString());
			}
		} catch (JSONException e) {
		} catch (ClientProtocolException e) {
		} catch (SocketTimeoutException e1) {
		} catch (IOException e) {
		} 	
	}
	
}