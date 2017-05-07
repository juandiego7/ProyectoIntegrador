package co.edu.udea.notasudea.session;

import co.edu.udea.notasudea.LoginActivity;
import co.edu.udea.notasudea.MainActivity;
import co.edu.udea.notasudea.model.dao.HistoriaDAO;
import co.edu.udea.notasudea.model.dao.NotasDAO;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 3/07/2013
 */
public class SessionManager {
	
	/**
	 * Preferencias compartidas
	 */
	private SharedPreferences preferences;
	/**
	 * Editor para las preferencias compartidas
	 */
	private Editor editor;
	/**
	 * 
	 */
	private Context context;
	/**
	 * Indica el tipo de acceso de las preferencias
	 */
	int PRIVATE_MODE = 0;
	/**
	 * Nombre del archivo de las preferencias compartidas
	 */
	private static final String PREFERENCIAS_NAME = "NotasPreferences";
	/**
	 * Determina si se un estudiante se encuentra logeado
	 */
	private static final String IS_LOGIN = "IsLoggedIn";
	/**
	 * Token de autenticacion del estudiante
	 */
	private static final String KEY_TOKEN = "token";
	
	
	private static final String KEY_ID = "id";
	
	public SessionManager(Context context){
		this.context = context;
		preferences = context.getSharedPreferences(PREFERENCIAS_NAME, PRIVATE_MODE);
		editor = preferences.edit();
	}
	
	/**
	 * Metodo que crea las preferencias compartidasd para la sesion.
	 * @param token
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 3/07/2013
	 */
	public void crearSession(String token){
		//Almacenando el valor de login como TRUE en las preferencias
		editor.putBoolean(IS_LOGIN, true);
	
		//Almacenando el valor del token en las preferencias
		editor.putString(KEY_TOKEN, token);
		
		//Commit a los cambios
		editor.commit();
	}
	
	/**
	 * Metodo que retorna el token de la sesion del estudiante.
	 * @return Token identificador del estudiante
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 3/07/2013
	 */
	public String getToken(){
		return preferences.getString(KEY_TOKEN, null);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRegistrationID(){
		return preferences.getString(KEY_ID,null);
	}
	
	/**
	 * 
	 */
	public void setRegistrationID(String id){
		editor.putString(KEY_ID, id);	
		//Commit a los cambios
		editor.commit();
	}

	/**
	 * Metodo que comprueba el estado de la session del estudiante, si esta
	 * logeado redirige a MainActivity, si no lo esta no hace nada.
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 3/07/2013
	 */
	public void comprobarLogin(){
		//Comprueba el estado del login
		if(this.isLoggedIn()){
			//El estudiante no ha iniciado session
			Intent intent = new Intent(context, MainActivity.class);
			
			//Se cierran todas las activity
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			//Añade una nueva bandera para iniciar nuevas actividades
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			//Inicia la actividad de login
			context.startActivity(intent);			
		}
	}
	
	/**
	 * Metodo que elimina las preferencias compartidas de la sesion del
	 * estudiante, y redirige a la activity de login.
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 3/07/2013
	 */
	public void cerrarSession(){
		//Eliminacion de todas las preferencias compartidas
		editor.remove(KEY_TOKEN);
		editor.remove(IS_LOGIN);
		editor.commit();
		
		//Se elimnan los registros de la BD local
		new HistoriaDAO(context).eliminarDatos(); 
		new NotasDAO(context).eliminarDatos();
		
		// Despues de logout redirige al estudiante a la activity de login
		Intent i = new Intent(context, LoginActivity.class);
		
		//Se cierran todas las activity
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		//Añade una nueva bandera para iniciar nuevas actividades
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		//Inicia la actividad de login
		context.startActivity(i);
	}
	
	/**
	 * Metodo que retorna si una sesion se encuentra iniciada o no.
	 * @return
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 3/07/2013
	 */
	public boolean isLoggedIn(){
		return preferences.getBoolean(IS_LOGIN, false);
	}
	
}