package co.edu.udea.notasudea;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import co.edu.udea.notasudea.adapter.AdapterEvaluacion;
import co.edu.udea.notasudea.model.Evaluacion;
import co.edu.udea.notasudea.model.dao.NotasDAO;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 28/02/2014
 */
public class CalculoActivity extends Activity {

	/**
	 * ListView para mostrar las evaluaciones de la materia
	 */
	private ListView listViewEvaluaciones;
	/**
	 * Listado de las evaluaciones de la materia
	 */
	private List<Evaluacion> listEvaluaciones;
	
	private String codigoMateria;
	
	private TextView tvMateria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculo);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getComponentes();
		getParametros();
		cargarEvaluacionesMateria();
	}

	private void getComponentes() {
		listViewEvaluaciones = (ListView) findViewById(R.id.list_calculo_materia);
		tvMateria = (TextView) findViewById(R.id.tv_nombre_materia_calculo);
	}

	private void getParametros() {
        Intent i = getIntent();
        codigoMateria = i.getStringExtra("materia");
	}

	private void cargarEvaluacionesMateria() {
		NotasDAO notasDAO = new NotasDAO(getApplicationContext());
		listEvaluaciones = notasDAO.getEvaluacionesMateria(codigoMateria);
		AdapterEvaluacion adapter = new AdapterEvaluacion(getApplicationContext(), R.layout.lis_item_materia_calculo,  listEvaluaciones);		
		listViewEvaluaciones.setAdapter(adapter);
		String nombreMateria = notasDAO.getNombreMateria(codigoMateria);
		tvMateria.setText(nombreMateria);
	}
	
	public void calculo(View view){
		Log.d("size", ""+listEvaluaciones.size());
		for(int i = 0; i < listEvaluaciones.size(); i ++){
			Evaluacion evaluacion =  (Evaluacion) listViewEvaluaciones.getItemAtPosition(i);	
			Log.d("Nota", evaluacion.getNota());
			Log.d("Descripcion", " "+evaluacion.getDescripcion());			
		}

	}

	public void calculoMinimo(View view){
		
	}
}