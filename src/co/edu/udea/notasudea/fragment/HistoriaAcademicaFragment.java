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

import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.SemestreActivity;
import co.edu.udea.notasudea.adapter.AdapterHistoriaAcademica;
import co.edu.udea.notasudea.model.Programa;
import co.edu.udea.notasudea.model.dao.HistoriaDAO;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.ConnectionManager;
import co.edu.udea.notasudea.util.HttpClientFactory;
import co.edu.udea.notasudea.util.ppt.LeerPropiedades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 29/06/2013
 * @version 1.1 1/02/2014  Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.2 15/02/2014 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>>
 */
public class HistoriaAcademicaFragment extends Fragment{

	/**
	 * Identificador de la sesion
	 */
	private String token;
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;
	private AdapterHistoriaAcademica adapter;
	/**
	 * Listado de los programas del estudiante
	 */
	private ArrayList<Programa> listProgramas;
	private ExpandableListView expandList;
	/**
	 * Progress Dialog que se muestra al cargar la historia
	 */
	private ProgressDialog pDialog;	
	private static final String TAG_HISTORIA = "historia";	
	private static final String TAG_ERROR_END_SESSION = "ERROR_END_SESSION";
	private static final String TAG_TIPO = "tipo";
	/**
	 * Objecto JSON que contien los programas con sus semestres
	 */
	public JSONObject programas = null;    

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container == null)
			return null;
		return inflater.inflate(R.layout.tab_historia_academica, container, false);
	} 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getSession();
		getComponentes();
		setListener();
		comprobarDB();
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
	 * Obtiene los componentes de la interfaz grafica
	 */
	private void getComponentes(){
		expandList = (ExpandableListView) getView().findViewById(R.id.elHistoriaAcademica);
	}

	/**
	 * Agrega los listener para los eventos de la interfaz grafica
	 */
	private void setListener() {
		expandList.setOnChildClickListener(new OnChildClickListener() {			
			@Override
			public boolean onChildClick(ExpandableListView parent, View view,
					int groupPosition, int childPosition, long id) {
				TextView tvSemestre = (TextView) view.findViewById(R.id.tvCodSemestre);

				//Se obtiene el programa al cual pertenece el semestre
				Programa e = (Programa) adapter.getGroup(groupPosition);

				//Se obtienen los datos del semestre a consultar
				String semestre = tvSemestre.getText().toString();
				String programa = e.getCodPrograma().toString();

				Intent intent = new Intent( getActivity().getApplicationContext(), SemestreActivity.class);
				intent.putExtra("semestre", semestre);
				intent.putExtra("programa", programa);
				intent.putExtra("token", token);
				startActivity(intent);
				return true;
			}
		});
	}

	private void comprobarDB() {
		HistoriaDAO historiaDAO = new HistoriaDAO(getActivity());  
		if(historiaDAO.comprobarDatos())
			cargarHistoria();
		else{
			if(ConnectionManager.comprobarConexion(getActivity()))
				new LoadHistoria().execute();		
		}
	}

	private void cargarHistoria(){
		HistoriaDAO historiaDAO = new HistoriaDAO(getActivity()); 		
		listProgramas = historiaDAO.cargarHistoria();   
		adapter = new AdapterHistoriaAcademica(getActivity().getApplicationContext(), listProgramas);
		expandList.setAdapter(adapter);
		if(listProgramas.size() > 0)
			expandList.expandGroup(0);		
	}

	public void actualizarHistoria(){
		if(ConnectionManager.comprobarConexion(getActivity()))
			new LoadHistoria().execute();		
		else
			Toast.makeText(getActivity(), R.string.msg_no_conexion, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * Clase que realiza proceso en background de cargar la historia academica
	 * @author "Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>"
	 * @version 1.0 03/07/2013
	 */   
	class LoadHistoria extends AsyncTask<String, String, String> {

		/**
		 * Ejecutado antes de iniciar la tarea en background
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage(getString(R.string.pd_historia));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * Obteniendo las materias del semestre
		 * */
		protected String doInBackground(String... args) {

			LeerPropiedades propiedades = new LeerPropiedades(getActivity(), LeerPropiedades.PROPERTIES);      	
			try{
				HttpClient httpClient = HttpClientFactory.getThreadSafeClient();

				HttpGet peticion = new HttpGet(propiedades.getResource(LeerPropiedades.RECURSO_HISTORIAL));

				peticion.setHeader("token", token);

				HttpResponse resp = httpClient.execute(peticion);
				String respStr = EntityUtils.toString(resp.getEntity());

				Log.d("respuesta",respStr);         		
				JSONObject jsonObject = new JSONObject(respStr);

				if(jsonObject.has(TAG_HISTORIA)){
					HistoriaDAO historiaDAO = new HistoriaDAO(getActivity());   	
					historiaDAO.eliminarDatos();
					historiaDAO.saveHistoriaAcademica(jsonObject);         	
				}else{
					//El contenido de la respuesta no es el historial
					if(TAG_ERROR_END_SESSION.equals(jsonObject.get(TAG_TIPO))){
						getActivity().finish();
						session.cerrarSession();
						return getString(R.string.msg_session_expire);
					}
					return getString(R.string.msg_no_load_historia);
				}
			} catch (JSONException e) {
				return getString(R.string.msg_no_load_historia);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.d("Historia", "Fallo");
			} catch (SocketTimeoutException e1) {
				return getString(R.string.msg_time_out);
			} catch (IOException e) {
				return getString(R.string.msg_no_load_historia);
			} catch (Exception e) {
				return getString(R.string.msg_no_conexion);
			}
			return null;
		}

		/**
		 * Ejecutado despues de completar la tarea en background
		 */
		protected void onPostExecute(String message) {
			pDialog.dismiss();   
			if(message == null)
				cargarHistoria(); 
			else
				Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
		}

	}

}