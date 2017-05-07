package co.edu.udea.notasudea.gcm;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.notasudea.MainActivity;
import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.model.dao.NotasDAO;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.HttpClientFactory;
import co.edu.udea.notasudea.util.ppt.LeerPropiedades;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 4/11/2013
 * @version 1.1 31/01/2014
 * @version 1.2 08/04/2014
 */
public class GCMIntentService extends IntentService {

	private static final int NOTIFICACION_ID = 1;
	/**
	 * Encargada de los ajustes
	 */
	private SharedPreferences pref;	
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;

	private long[] pattern_short = new long[]{0, 1000, 500, 1000};

	private long[] pattern_long = new long[]{0, 2000, 500, 2000, 500, 2000};

	private static final String PREFERENCE_VIBRACION = "notifications_new_note_vibrate";

	private static final String PREFERENCE_LED = "notifications_new_note_led";

	private static final String PREFERENCE_SOUND = "notifications_new_note_ringtone";

	private static final String PREFERENCE_NOTIFICATION = "notifications_new_message";
	
	private static final String TAG_NOTAS = "actividadesMaterias";
	
	private String token;
	
	public GCMIntentService() {
		super("GCMIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		//Obtiene el tipo de mensaje enviado a la aplicacion
		String messageType = gcm.getMessageType(intent);
		//Obtiene el mensaje enviado a la aplicacion
		Bundle extras = intent.getExtras();		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging. MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				Log.d("GCM", "Error");
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				Log.d("GCM", "Deleted");
			} else 		
				if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
					//Se comprueba si se tienen las notificaciones activas
					if(pref.contains(PREFERENCE_NOTIFICATION)){	
						if(pref.getBoolean(PREFERENCE_NOTIFICATION, true)){
							mostrarNotification(extras);
						}
					}else{
						mostrarNotification(extras);
					}
				} 
		}
		GCMBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	/**
	 * Metodo que muestra la notificacion en pantalla de una nueva nota.
	 * @param msg datos de la nota obtenida.
	 */
	private void mostrarNotification(Bundle data) {
		session = new SessionManager(getApplicationContext());
		actualizarNotas();

		NotasDAO notasDAO = new NotasDAO(getApplicationContext());		
		String codigoMateria = data.getString("codigo");
		String nroEvaluacion = data.getString("actividad");	
		String nota = data.getString("nota");
		Log.d("Notificacion", codigoMateria);
		/*
		 * Se verifica si la materia esta en la BD y la sesion esta activa.
		 */
		if(notasDAO.verificarNotaNotificacion(codigoMateria, nroEvaluacion, nota) && session.isLoggedIn()){
			NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			//Se guarda la nueva nota en la BD
//			notasDAO.saveNota(codigoMateria, nroEvaluacion, nota);

			//Se crea la notificaicon
			NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
			//Se le agrega el icono a la notificaicon	
			notificacion.setSmallIcon(R.drawable.ic_notification);		
			//Se le agrega el titulo a la notificacion
			notificacion.setContentTitle(notasDAO.getNombreMateria(codigoMateria));
			//Se agrega el texto de la notificacion
			String actividad = notasDAO.getNombreActividad(nroEvaluacion, codigoMateria) + ": "  + nota;
			notificacion.setContentText(actividad);
			//Texto que se muestra al llegar la notificacion
			notificacion.setTicker("Tienes una nueva nota");
			//Texto que se muestra a la izquierda del icono pequeño
			notificacion.setContentInfo("nota");
			//Quitar la notificacion cuando se pulse sobre ella
			notificacion.setAutoCancel(true);		
			//Se define el patron de vibracion al dispositivo
			if(pref.contains(PREFERENCE_VIBRACION)){
				switch (Integer.parseInt(pref.getString(PREFERENCE_VIBRACION, ""))){
				case 0:
					notificacion.setVibrate(pattern_short);
					break;
				case 1:
					notificacion.setVibrate(pattern_long);
					break;
				default:
					break;
				}
			}else{
				notificacion.setVibrate(pattern_short);
			}
			//Se define el color del led	
			if(pref.contains(PREFERENCE_LED)){
				int colorLed = Integer.parseInt(pref.getString(PREFERENCE_LED, ""));	
				notificacion.setLights(colorLed, 500, 60000);
			}	
			//Se la uri del sonido escogido
			Uri sound = Uri.parse(pref.getString(PREFERENCE_SOUND, ""));		
			//Se le agrega el sonido a la notifiacion
			notificacion.setSound(sound);
			//Actividad que sera lanzada cuando se pulsa sobre la notificacion
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			PendingIntent contIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
			//Se asocia la actividad con la notificacion
			notificacion.setContentIntent(contIntent);
			//Se genera la notificacion
			notificationManager.notify(NOTIFICACION_ID, notificacion.build());
		}
	}
	
	private void actualizarNotas() {		
		LeerPropiedades propiedades = new LeerPropiedades(getApplicationContext(), LeerPropiedades.PROPERTIES);			
		token = session.getToken();		
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
				NotasDAO notasDAO = new NotasDAO(getApplicationContext());
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