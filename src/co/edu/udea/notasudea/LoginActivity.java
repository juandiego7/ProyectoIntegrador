package co.edu.udea.notasudea;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import co.edu.udea.notasudea.intentservice.LoginIntent;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.ConnectionManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 29/06/2013
 * @version 1.1 03/01/2014
 * @version 1.2 21/02/2014 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 */
public class LoginActivity extends Activity{

	/**
	 * Inputs para los datos del usuario
	 */
	private EditText etUsuario;
	private EditText etContrasena;
	private Button btnLogin;
	/**
	 * Almacenan los datos ingresados por el usuario
	 */
	private String username;
	private String passwordCifrado;		
	/**
	 * Indicador de progreso para mostrar al usuario
	 */
	private ProgressDialog pDialog;	
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	ProgressReceiver rcv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	protected void onStart() {
		super.onStart();
		session = new SessionManager(getApplicationContext());
		/*
		 * Se comprueba que se encuentre iniciada una session,
		 * si no esta logeado lo redirige a LoginActivity
		 */
		session.comprobarLogin();		
		getComponents();
		addListeners();
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoginIntent.ACTION_DATOS_ERROR);
        filter.addAction(LoginIntent.ACTION_OK);
        filter.addAction(LoginIntent.ACTION_SERVER_ERROR);
        rcv = new ProgressReceiver();
        registerReceiver(rcv, filter);
	}
	
	@Override
	protected void onStop()	{
	    super.onStop();
	    unregisterReceiver(rcv);
	}

	/**
	 * Obtiene los componentes de la interfaz grafica del usuario.
	 */
	private void getComponents(){
		etUsuario = (EditText) findViewById (R.id.etUsuario);
		etContrasena = (EditText) findViewById (R.id.etPassWord);
		btnLogin = (Button) findViewById (R.id.btnLogin);	
		// Desabilita el boton de iniciar sesion
		btnLogin.setEnabled(false);
	}

	/**
	 * Metodo que agrega los listeners a los diferentes componentes
	 * de la interfaz grafica.
	 * 
	 */
	private void addListeners(){
		etUsuario.addTextChangedListener(new TextWatcher() {				
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}				
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				comprobarDatos();
			}
		});

		etContrasena.addTextChangedListener(new TextWatcher() {				
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}				
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				comprobarDatos();
			}
		});		

		btnLogin.setOnClickListener(new View.OnClickListener() { 
			public void onClick(View view) {			
				login();
			}
		});
	}

	/**
	 * Verifica si los datos de usuario y contrasena fueron ingresados, en caso afirmativo
	 * habilita el boton para el inicio de sesion.
	 * @param btnLogin
	 */
	private void comprobarDatos() {
		if (etUsuario.getText().toString().trim().equals("") | etContrasena.getText().toString().trim().equals(""))
			btnLogin.setEnabled(false);	  
		else
			btnLogin.setEnabled(true);	       	
	}
	
	private void login() {
		//TODO Verficar que se tenga Google Play Services
		if(ConnectionManager.comprobarConexion(this)){
			pDialog = new ProgressDialog(this);
			pDialog.setMessage(getString(R.string.pd_login));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false); 
			pDialog.show();
			getInformation();
	       	Intent loginIntent = new Intent(LoginActivity.this, LoginIntent.class);
	        loginIntent.putExtra("username", username);
	        loginIntent.putExtra("password", passwordCifrado);
	        startService(loginIntent);			
		}else
			Toast.makeText(this,  R.string.msg_no_conexion, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Obtiene los datos ingresados por el usuario
	 */
	private void getInformation(){
		username = etUsuario.getText().toString();
		passwordCifrado =  etContrasena.getText().toString();		
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS)	{
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))	{
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else {
				Log.i("", "Dispositivo no soportado.");
				finish();
			}
			return false;
		}
		return true;
	}
	
	public class ProgressReceiver extends BroadcastReceiver {
		 
	    @Override
	    public void onReceive(Context context, Intent intent) {
			pDialog.dismiss();
	        if(intent.getAction().equals(LoginIntent.ACTION_OK)) {
	        	Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(mainIntent);
	        }
	        else if(intent.getAction().equals(LoginIntent.ACTION_DATOS_ERROR)) {
	        	String mensaje = intent.getStringExtra("mensaje");
	        	Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
	        }else if(intent.getAction().equals(LoginIntent.ACTION_SERVER_ERROR)){
	            Toast.makeText(LoginActivity.this, R.string.msg_no_login, Toast.LENGTH_SHORT).show();
	        }
	    }
	}

}