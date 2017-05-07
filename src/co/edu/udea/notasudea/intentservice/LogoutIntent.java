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
 * @version 1.0 21/02/2014
 */
public class LogoutIntent extends IntentService{
	
	public static final String ACTION_OK = "co.edu.udea.notasudea.action.OK";

	public static final String ACTION_FAILE = "co.edu.udea.notasudea.action.FAILE";

	/**
	 * Valor asociado con el label "tipo"
	 */
	private final String TAG_TIPO = "tipo";
	private final String TAG_SUCCESS = "Success";
	/**
	 * Label JSON para obtener el mensaje
	 */
	private final String TAG_MENSAJE = "mensaje";
	
	private Context context;
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;
		
	public LogoutIntent() {
		super("LogoutIntent");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		context = getApplicationContext();
		session = new SessionManager(context);
		realizarPeticion();
	}

	private String getRecurso(){
		LeerPropiedades propiedades = new LeerPropiedades(context, LeerPropiedades.PROPERTIES);      	
		return propiedades.getResource(LeerPropiedades.RECURSO_LOGOUT);
	}
	
	private void realizarPeticion() {
        Intent bcIntent = new Intent();
		try {
			HttpClient httpClient = HttpClientFactory.getThreadSafeClient();
			HttpGet httpGet = new HttpGet(getRecurso());
			httpGet.setHeader("token", session.getToken());
			HttpResponse res = httpClient.execute(httpGet);
			String salida = EntityUtils.toString(res.getEntity());
			Log.d("LogoutIntent", salida);
			JSONObject json = new JSONObject(salida);
			String tipo = json.getString(TAG_TIPO);
			
			if (tipo.equals(this.TAG_SUCCESS)){
				session.cerrarSession();
		        bcIntent.setAction(ACTION_OK);
			}else{
				String error_message =  json.getString(this.TAG_MENSAJE);
		        bcIntent.setAction(ACTION_FAILE);
				bcIntent.putExtra("mensaje", error_message);
			}
			
		} catch (SocketTimeoutException e) {
	        bcIntent.setAction(ACTION_FAILE);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
	        bcIntent.setAction(ACTION_FAILE);
		} catch (JSONException e) {
	        bcIntent.setAction(ACTION_FAILE);
		}
        sendBroadcast(bcIntent);
	}
	
}