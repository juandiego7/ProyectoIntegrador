package co.edu.udea.notasudea.intentservice;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.HttpClientFactory;
import co.edu.udea.notasudea.util.encrypt.AESEncryption;
import co.edu.udea.notasudea.util.encrypt.IEncryption;
import co.edu.udea.notasudea.util.ppt.LeerPropiedades;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 21/02/2014
 */
public class LoginIntent extends IntentService{

	private String SENDER_ID = "906398734383";

	private String id;

	private String username;

	private String password;

	public static final String ACTION_OK = "co.edu.udea.notasudea.action.OK";

	public static final String ACTION_DATOS_ERROR = "co.edu.udea.notasudea.action.DATOS_ERROR";

	public static final String ACTION_SERVER_ERROR = "co.edu.udea.notasudea.action.SERVER_ERROR";
	
	private GoogleCloudMessaging gcm;
	
	private Context context;
	
	/**
	 * Valor asociado con el label "tipo"
	 */
	private final String TAG_TOKEN = "Token";
	/**
	 * Valor asociado con el label "tipo"
	 */
	private final String TAG_ERROR = "Error";
	/**
	 * Label JSON para obtener el mensaje
	 */
	private final String TAG_MENSAJE = "mensaje";

	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;

	public LoginIntent() {
		super("LoginIntent");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		context = getApplicationContext();
		session = new SessionManager(context);
		getParamtros(intent);
		registrarGCM();
		realizarPeticion();
	}

	private void getParamtros(Intent intent){
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");
	}

	private void registrarGCM(){
		try {
			id = session.getRegistrationID();
//			id = "nexus4";
			//Se verifica si ya se tiene el ID del dispositivo
			if(id == null || ("").equals(id)){
				gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
				id = gcm.register(SENDER_ID);
				session.setRegistrationID(id);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private String getRecurso(){
		LeerPropiedades propiedades = new LeerPropiedades(context, LeerPropiedades.PROPERTIES);      	
		return propiedades.getResource(LeerPropiedades.RECURSO_LOGIN);
	}
	
	private void realizarPeticion(){
        Intent bcIntent = new Intent();
		try {
			HttpClient httpClient = HttpClientFactory.getThreadSafeClient();
			HttpGet httpGet = new HttpGet(getRecurso());
			cifrarPassword();
			httpGet.setHeader("user", username);
			httpGet.setHeader("pass", URLEncoder.encode(password, "UTF-8"));
			httpGet.setHeader("dispositivo", id);
			HttpResponse res = httpClient.execute(httpGet);
			String salida = EntityUtils.toString(res.getEntity());
			Log.d("LoginIntent", salida);
			JSONObject json = new JSONObject(salida);
			String tipo = json.getString("tipo"); 
			/*
			 * Si el tipo es "token" se obtiene el mansaje
			 * y se setea el token devuelto en la variable
			 * local token
			 */
			if (tipo.equals(this.TAG_TOKEN)){
				String token = json.getString(this.TAG_MENSAJE);
				session.crearSession(token);
		        bcIntent.setAction(ACTION_OK);
			}
			if (tipo.equals(this.TAG_ERROR)){
				String error_message =  json.getString(this.TAG_MENSAJE);
				bcIntent.putExtra("mensaje", error_message);
		        bcIntent.setAction(ACTION_DATOS_ERROR);
			}
		} catch (SocketTimeoutException e) {
	        bcIntent.setAction(ACTION_SERVER_ERROR);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
	        bcIntent.setAction(ACTION_SERVER_ERROR);
		} catch (JSONException e) {
	        bcIntent.setAction(ACTION_SERVER_ERROR);
		}
        sendBroadcast(bcIntent);
	}

	private void cifrarPassword() {
		IEncryption enc = new AESEncryption();
		password = enc.cifrar(password);
	}

}
