package co.edu.udea.notasudea.util.ppt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Clase encargada de leer las propiedades
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 12/02/2014
 */
public class LeerPropiedades {

	private Properties properties;
	
	public static final String PROPERTIES = "propiedades.properties";
	public static final String RECURSO_LOGIN = "recurso.login";
	public static final String RECURSO_LOGOUT = "recurso.logout";
	public static final String RECURSO_NOTAS = "recurso.notas";
	public static final String RECURSO_HISTORIAL = "recuros.historial";
	public static final String SERVICIO = "url";
	
	public LeerPropiedades(Context context, String FileName){
		properties = new Properties();
		try {
			//Proporciona acceso a los archivos ubicados en la carpeta assets
			AssetManager assetManager = context.getAssets();
			//Abre el archivo de propiedades
			InputStream inputStream = assetManager.open(FileName);
			//carga las propiedades
			properties.load(inputStream);

		} catch (IOException e) {
			Log.e("AssetsPropertyReader",e.toString());
		}
	}

	public String getPropertie(String name){
		return properties.getProperty(name);
	}
	
	/**
	 * Metodo que devuelve la url de un recurso especifico
	 * @param recurso identificador del recurso
	 * @return
	 */
	public String getResource(String recurso){
		return properties.getProperty(SERVICIO) + properties.getProperty(recurso);
	}
	
}