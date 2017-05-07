package co.edu.udea.notasudea;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.adapter.PagerAdapter;
import co.edu.udea.notasudea.fragment.HistoriaAcademicaFragment;
import co.edu.udea.notasudea.fragment.NotasFragment;
import co.edu.udea.notasudea.intentservice.LogoutIntent;
import co.edu.udea.notasudea.session.SessionManager;
import co.edu.udea.notasudea.util.ConnectionManager;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 29/06/2013
 * @version 1.1 4/11/2013 Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 */
public class MainActivity extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener{

	
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
		
	private HistoriaAcademicaFragment historiaFragment;
	private NotasFragment notasFragment;
	
	private List<Fragment> fragments;
	/**
	 * Indicador de progreso para mostrar al usuario
	 */
	private ProgressDialog pDialog;	
	ProgressReceiver rcv;
	
	/**
	 * 
	 */
	private ActionBar actionBar;	
	/**
	 * Clase encarga de manejar las preferencias y la session
	 */
	private SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSession();	
		getComponents();
		setComponents();
		setAdapter();
		setListener();
		setInformationActionBar();
		addTabs();	
	}
	
	@Override
	protected void onStart() {
		super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(LogoutIntent.ACTION_FAILE);
        filter.addAction(LogoutIntent.ACTION_OK);
        rcv = new ProgressReceiver();
        registerReceiver(rcv, filter);		
	}
	
	@Override
	protected void onStop()	{
	    super.onStop();
	    unregisterReceiver(rcv);
	}
	
	/**
	 * 
	 */
	private void getSession() {	
		//Manejador de la sesion
        session = new SessionManager(getApplicationContext());
	}
	
	/**
	 * 
	 */
	private void getComponents() {
		//Se obtiene el action bar
		actionBar = getSupportActionBar();
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
	}
	
	private void setComponents() {
		//Se instancia los tabs
		notasFragment = new NotasFragment();
		historiaFragment = new HistoriaAcademicaFragment();
		fragments = new ArrayList<Fragment>();	
		fragments.add(notasFragment);
		fragments.add(historiaFragment);
	}
	
	/**
	 * 
	 */
	private void setListener() {
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}			
		});
	}
	
	private void setAdapter() {
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(mPagerAdapter);	
	}
	
	private void setInformationActionBar() {
		//Se le coloca el titulo al actionBar
		actionBar.setTitle("Mis Notas UdeA");		
		//se agrega el modo de navegacion por tab
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//quita el titulo del action bar
//		actionBar.setDisplayShowTitleEnabled(false);			
	}
	
	/**
	 * 
	 */
	private void addTabs() {
		//Se crea el tab de nota
		Tab tab = actionBar.newTab().setText("Notas").setTabListener(this);		      
	    //se agrega el tab de notas al actionBar
		actionBar.addTab(tab);
		Tab tab2 = actionBar.newTab().setText("Historia").setTabListener(this);
		//Se agrega el tab de historia al actionbar
		actionBar.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Verificando el menu seleccionado
		switch (item.getItemId()) {
		case R.id.action_update:
			updateDatos();
			return true;   
		case R.id.action_settings:
			mostrarAjustes();
			return true;
		case R.id.action_about:
			showAbout();
			return true;		
		case R.id.action_logout:
			logOut();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Muesta el layout de about
	 */
	private void showAbout(){
		Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
		//Iniciando la actividad about
        startActivity(aboutIntent);
	}
		
	/**
	 * Metodo que elimina todas las preferencias compartidas, redirige a
	 * LoginActivity y desabilitda el token en el servidor.
	 */
	private void logOut(){
       	if(ConnectionManager.comprobarConexion(this)){
    		pDialog = new ProgressDialog(this);
    		pDialog.setMessage(getString(R.string.pd_logout));
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(false); 
       		pDialog.show();
    		Intent logoutIntent = new Intent(MainActivity.this, LogoutIntent.class);
           	startService(logoutIntent);      		
       	}else
			Toast.makeText(this, R.string.msg_no_conexion, Toast.LENGTH_SHORT).show();		
	}
	
	/**
	 * 
	 */
	private void updateDatos() {
		switch(mViewPager.getCurrentItem()){
		case 0:
			notasFragment.actualizarNotas();
			break;
		case 1:
			historiaFragment.actualizarHistoria();
			break;
		default:
			break;
		}		
	}
	
	private void mostrarAjustes() {
		Intent settingtIntent = new Intent(getApplicationContext(), SettingActivity.class);
		//Iniciando la actividad about
        startActivity(settingtIntent);
	}
	
	@Override
	protected void onNewIntent (Intent intetn){
		notasFragment.cargarNotas();
	}
		
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		mViewPager.setCurrentItem(tab.getPosition());		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {}

	public class ProgressReceiver extends BroadcastReceiver {
		 
	    @Override
	    public void onReceive(Context context, Intent intent) {
			pDialog.dismiss();
	        if(intent.getAction().equals(LogoutIntent.ACTION_OK)) {
				finish();
				session.cerrarSession();
	        }
	        else if(intent.getAction().equals(LogoutIntent.ACTION_FAILE)) {
	        	String mensaje = intent.getStringExtra("mensaje");
	        	Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
}