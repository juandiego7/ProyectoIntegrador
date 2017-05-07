package co.edu.udea.notasudea;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.notasudea.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.TextView;

/**
 * 
 * @author Ruben Dario Fernandez C. <rdario45@gmail.com>
 * @version 1.0 
 * @version 1.1 18/01/2014 Andres Felipe Arrubla Z <ax.fx.ax@gmail.com>
 */
public class AboutActivity extends Activity {

	private TextView tvIntegrante1;
	private TextView tvIntegrante2;
	private TextView tvIntegrante3;
	private TextView tvAppVersion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		getComponents();

		//Se obtienen los nombres de los desarrolladores
		List<String> integrantes = new ArrayList<String>();
		integrantes.add(getResources().getString(R.string.integrante1));
		integrantes.add(getResources().getString(R.string.integrante2));
		integrantes.add(getResources().getString(R.string.integrante3));

		int i;
		try {
			i = (int) (Math.random() * 3); // random del 0 al 2
			tvIntegrante1.setText(integrantes.get(i)); //escojo el primer nombre
			integrantes.remove(i); //elimino el escogido de la lista

			i = (int) (Math.random() * 2); // random del 0 al 1
			tvIntegrante2.setText(integrantes.get(i));//escojo el segundo nombre
			integrantes.remove(i);//elimino el escogido de la lista

			tvIntegrante3.setText(integrantes.get(0));//escojo el restante
		} catch (Exception e) {
			Log.d("Error", "Error crando el about");
		}
		String versionName = "";
		try {
			 versionName = this.getPackageManager()
				    .getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tvAppVersion.setText(versionName);		
	}

	/**
	 * Metodo que obtiene los componentes de la interfaz grafica
	 */
	private void getComponents() {
		tvIntegrante1 = (TextView) findViewById(R.id.tvIntegrante1);
		tvIntegrante2 = (TextView) findViewById(R.id.tvIntegrante2);
		tvIntegrante3 = (TextView) findViewById(R.id.tvIntegrante3);		
		tvAppVersion = (TextView) findViewById(R.id.tv_app_version);
	}

}