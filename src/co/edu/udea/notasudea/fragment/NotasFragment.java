package co.edu.udea.notasudea.fragment;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.notasudea.CalculoActivity;
import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.adapter.AdapterNotas;
import co.edu.udea.notasudea.model.Materia;
import co.edu.udea.notasudea.model.dao.NotasDAO;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.ConnectionManager;
import co.edu.udea.notasudea.util.HttpClientFactory;
import co.edu.udea.notasudea.util.ppt.LeerPropiedades;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 29/06/2013 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.1 4/01/2014 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.2 15/02/2014 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 */
public class NotasFragment extends Fragment{

	/**
	 * Identificador de la sesion
	 */
	private String token;
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;
	private AdapterNotas adapter;
	/**
	 * Listado de las materias del estudiante
	 */
	private ArrayList<Materia> listMaterias;
	private ExpandableListView expandList;
	/**
	 * Progress Dialog que se muestra al cargas las notas
	 */
	private ProgressDialog pDialog;
	private static final String TAG_NOTAS = "actividadesMaterias";
	private static final String TAG_ERROR_END_SESSION = "ERROR_END_SESSION";
	private static final String TAG_TIPO = "tipo";
	/**
	 * Objecto JSON que contien las materias con sus actividades
	 */
	public JSONObject materias = null;
	
	private int countEgg = 0;
	private CountDownTimer timerEgg = null;
	private int idEgg = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container == null)
			return null;
		return inflater.inflate(R.layout.tab_notas, container, false);		
	}    

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);  
		getSession();
		getComponentes();
		comprobarDB();	
		setListener();
	}

	/**
	 * Obtiene los datos de la sesion del estudiante.
	 */
	private void getSession() {
		//Manejador de la sesion
		session = new SessionManager(getActivity().getApplicationContext());      
		//Se obtiene el token del estudiante para consultar informacion
		token = session.getToken();
	}

	/** 
	 * Obtiene los componentes de la interfaz grafica.
	 */
	private void getComponentes(){
		expandList = (ExpandableListView) getView().findViewById(R.id.elNotas);
	}

	private void comprobarDB() {
		NotasDAO notasDAO = new NotasDAO(getActivity());
		if(notasDAO.comprobarDatos())
			cargarNotas();
		else{
			if(ConnectionManager.comprobarConexion(getActivity()))
				new LoadNotas().execute();			
		}
	}
	
	private void setListener() {
		expandList.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				
				if (countEgg == 0){
					idEgg = groupPosition;
					new CountDownTimer(3000,100) {
							
						@Override
						public void onTick(long millisUntilFinished) {}
						
						@Override
						public void onFinish() {
							countEgg = 0;
						}
					}.start();
				}			
				
				if(idEgg == groupPosition)
					countEgg++;
				else {
					idEgg = groupPosition;
					countEgg = 1;
				}
				
				if (countEgg == 7){
					String materia = listMaterias.get(groupPosition).getCodigo();
					Intent intent = new Intent( getActivity().getApplicationContext(), CalculoActivity.class);	
					intent.putExtra("materia", materia);
					startActivity(intent);					
				}
				return false;
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void cargarNotas(){
		NotasDAO evaluacionDAO = new NotasDAO(getActivity());
		listMaterias = evaluacionDAO.cargarNotas();
		adapter = new AdapterNotas(getActivity().getApplicationContext(), listMaterias);
		expandList.setAdapter(adapter);
	}

	public void actualizarNotas(){
		if(ConnectionManager.comprobarConexion(getActivity()))
			new LoadNotas().execute();			
		else
			Toast.makeText(getActivity(), R.string.msg_no_conexion, Toast.LENGTH_LONG).show();
	}

	/**
	 * Clase que realiza proceso en background de cargar la historia academica
	 * @author "Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>"
	 * @version 1.0 03/07/2013
	 */   
	class LoadNotas extends AsyncTask<String, String, String> {

		/**
		 * Ejecutado antes de iniciar la tarea en background
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage(getString(R.string.pd_notas));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * Obteniendo las materias del semestre
		 * */
		protected String doInBackground(String... args) {

			LeerPropiedades propiedades = new LeerPropiedades(getActivity(), LeerPropiedades.PROPERTIES);			
			HttpClient httpClient;
			try {
				httpClient = HttpClientFactory.getThreadSafeClient();

				HttpGet peticion = new HttpGet(propiedades.getResource(LeerPropiedades.RECURSO_NOTAS));
				peticion.setHeader("token", token);

				HttpResponse resp = httpClient.execute(peticion);
				String respStr = EntityUtils.toString(resp.getEntity());		
				Log.d("notas",respStr);
				JSONObject jsonObject = new JSONObject(respStr);

				//Se comprueba que respuesta sean las materias
				if(jsonObject.has(TAG_NOTAS)){
					NotasDAO notasDAO = new NotasDAO(getActivity());
					notasDAO.eliminarDatos();
					notasDAO.saveNotas(jsonObject);
				}else{
//El contenido de la respuesta no son las materias
//					if(TAG_ERROR_END_SESSION.equals(jsonObject.get(TAG_TIPO))){
//						getActivity().finish();
//						session.cerrarSession();
//						return getString(R.string.msg_session_expire);
//					}
					return getString(R.string.msg_no_load_notas);
				}

			} catch (JSONException e) {
				return getString(R.string.msg_no_load_notas);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (SocketTimeoutException e1) {
				return getString(R.string.msg_time_out);
			} catch (IOException e) {
				return getString(R.string.msg_no_load_notas);
			} 
			return null;
		}

		/**
		 * Ejecutado despues de completar la tarea en background
		 */
		protected void onPostExecute(String message) {
			pDialog.dismiss();
			if(message == null)
				cargarNotas();
			else
				Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
		}
	}	

}