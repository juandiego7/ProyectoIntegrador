package co.edu.udea.notasudea;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.adapter.AdapterSemestre;
import co.edu.udea.notasudea.model.Materia;
import co.edu.udea.notasudea.util.ConnectionManager;
import co.edu.udea.notasudea.util.HttpClientFactory;
import co.edu.udea.notasudea.util.ppt.LeerPropiedades;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 30/06/2013
 * @version 1.1 30/10/2013 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.2 20/02/2014 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 */
public class SemestreActivity extends Activity {

	/**
	 * Progress Dialog a mostrar mientras cargan las materias
	 */
    private ProgressDialog pDialog;
    /**
     * Listado de las materias del semestre
     */
    private List<Materia> listMaterias;
    
    private LinearLayout layoutHistorialSemestre;
    
    private RelativeLayout layoutMessage;
    
    private TextView tvMessage;
    
    private Button btRetry;
    /**
     * ListView para mostrar las materias del semestre
     */
    private ListView listViewMaterias;
    /**
     * TextView para mostrar el semestre
     */
    private TextView tvSemestre;	
	/**
	 * Listado de las materias del semestre en JSON
	 */
    private JSONArray materias = null;       
    /**
     * Identificador del estudiante
     */
    private String token = "";   
    /**
     * Semestre academico a consultar
     */
    private String semestre = "";   
    /**
     * Programa academico a consultar
     */
    private String programa = "";   
    /**
     * Constantes para recuperar los datos del JSON
     */
    private static final String TAG_SEMESTRE = "materias";    
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_CREDITOS = "creditos";
    private static final String TAG_NOTA = "nota";    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_semestre);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getComponentes();
		getParametros();
		setInformacion();
		setListeners();
		cargarHistorialSemestre();
	}
	
	/**
	 * Metodo que obtiene los componentes de la interfaz grafica
	 */
	private void getComponentes(){
		listViewMaterias = (ListView) findViewById(R.id.list_materias_semestre);
		tvSemestre = (TextView) findViewById(R.id.semestre2);
		layoutHistorialSemestre = (LinearLayout) findViewById(R.id.layout_historial_semestre);
		layoutMessage = (RelativeLayout) findViewById(R.id.layout_message);
		btRetry = (Button) findViewById(R.id.bt_retry);
		tvMessage = (TextView) findViewById(R.id.tv_message);
	}
	
	/**
	 * Metodo que recupera los parametros necesarios para realizar la
	 * consulta de las materias.
	 */
	private void getParametros() {
        Intent i = getIntent();
        semestre = i.getStringExtra("semestre");
        programa = i.getStringExtra("programa");
        token = i.getStringExtra("token");		
	}
	
	/**
	 * Metodo que muestra el semestre
	 */
	private void setInformacion(){
        tvSemestre.setText(semestre);       
        //se re formatea el semestre a su estado original para poder llamar al servicio sin generar error
        semestre = semestre.replace("-", "");
	}
	
	/**
	 * Metodo que agrega listeners a los componentes de la interfaz de usuario
	 */
	private void setListeners() {
		btRetry.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				cargarHistorialSemestre();				
			}
		});
	}
	
	/**
	 * Metodo que carga el historail del semestre si hay Internet, en caso de que no
	 * muestra un mensaje de no conexion
	 */
	private void cargarHistorialSemestre(){
		if(ConnectionManager.comprobarConexion(this)){
			layoutHistorialSemestre.setVisibility(View.VISIBLE);
			layoutMessage.setVisibility(View.INVISIBLE);		
			new LoadSemestre().execute();   
		}else{
			layoutHistorialSemestre.setVisibility(View.INVISIBLE);
			layoutMessage.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Clase que realiza procesos en background
	 * @author "Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>"
	 * @version 1.0 30/06/2013
	 */
    class LoadSemestre extends AsyncTask<String, String, String> {
 
    	/**
    	 * Ejecutado antes de iniciar la tarea en background
    	 */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();           
            pDialog = new ProgressDialog(SemestreActivity.this);
            pDialog.setMessage(getString(R.string.pd_semestre));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * Obteniendo las materias del semestre
         * */
        protected String doInBackground(String... args) {  
			LeerPropiedades propiedades = new LeerPropiedades(getApplicationContext(), LeerPropiedades.PROPERTIES);      	
            try {    		
	    		HttpClient httpClient = HttpClientFactory.getThreadSafeClient();
	    		String url = propiedades.getResource(LeerPropiedades.RECURSO_HISTORIAL) + programa + "/" + semestre;
	    		HttpGet get = new HttpGet(url);
	    		get.setHeader("token", token);
 
            	HttpResponse resp = httpClient.execute(get);
            	String respStr = EntityUtils.toString(resp.getEntity());            	
            	
            	JSONObject json = new JSONObject(respStr);   
            	            	
            	if(json.has(TAG_SEMESTRE)){
                    materias = json.getJSONArray(TAG_SEMESTRE);                
                    listMaterias = new ArrayList<Materia>();
                    // Ciclo recorriendo las materias del semestre
                    for (int i = 0; i < materias.length(); i++) {
                        JSONObject materia = materias.getJSONObject(i);
                        // Almacenando cada item del JSON en una variable 
                        String nombre = materia.getString(TAG_NOMBRE);
                        String nota = materia.getString(TAG_NOTA);
                        String creditos = "CR " + materia.getString(TAG_CREDITOS);
                        //Se agrega la materia al listado
                        listMaterias.add(new Materia(nombre, nota, creditos));       		
                    }
                }else
            		return getString(R.string.msg_no_load_semestre);
            	
            } catch (JSONException e) {
				return getString(R.string.msg_no_load_semestre);
            } catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.d("1", "1");
			} catch (SocketTimeoutException e1) {
				return getString(R.string.msg_time_out);
			} catch (IOException e) {
				return getString(R.string.msg_no_load_semestre);
			}
 
            return null;
        }
        
        /**
         * Ejecutado despues de completar la tarea en background
         */
        protected void onPostExecute(String message) {
            pDialog.dismiss();
            if(message == null){
                //Actualiza la UI del proceso principal
                runOnUiThread(new Runnable() {
                    public void run() {
                    	if(listMaterias != null){
                        	AdapterSemestre adapter = new AdapterSemestre(getApplicationContext(), 
                        			R.layout.materia_item, listMaterias);
                        	listViewMaterias.setAdapter(adapter);                		
                    	}else
                        	Toast.makeText(getApplicationContext(), R.string.msg_no_load_semestre, Toast.LENGTH_SHORT).show();
                    }
                });            	
            }else
            	mostrarLayoutMessage(message);
        }
 
    }
    
    private void mostrarLayoutMessage(String message){
		layoutHistorialSemestre.setVisibility(View.INVISIBLE);
		layoutMessage.setVisibility(View.VISIBLE);
		tvMessage.setText(message);
    }

}