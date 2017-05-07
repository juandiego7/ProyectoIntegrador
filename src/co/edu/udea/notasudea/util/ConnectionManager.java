package co.edu.udea.notasudea.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 21/01/2014
 */
public class ConnectionManager {
	
	/**
	 * Comprueba si el dispositivo posee conexión a Internet.
	 * @return true, si tiene conexión, false en caso contrario.
	 */
	public static boolean comprobarConexion(Context context){
		boolean enabled = true;

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if ((info == null || !info.isConnected() || !info.isAvailable())) {
			enabled = false;
		}
		return enabled;    
    }
	
}
