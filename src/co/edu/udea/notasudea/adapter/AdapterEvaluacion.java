package co.edu.udea.notasudea.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.model.Evaluacion;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 28/02/2014
 */
public class AdapterEvaluacion extends ArrayAdapter<Evaluacion>{

	private Context context;
	private int layoutId;
	private List<Evaluacion> evaluaciones;

	public AdapterEvaluacion(Context context, int resource, List<Evaluacion> evaluaciones) {
		super(context, resource, evaluaciones);
		this.context = context;
		this.layoutId = resource;
		this.evaluaciones = evaluaciones;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 view = inflater.inflate(layoutId, parent, false);
		}
		TextView nroActividad = (TextView) view.findViewById(R.id.tvNumeroActividad);
		TextView porcentaje = (TextView) view.findViewById(R.id.tvPorcentajeActividad);
		TextView descripcion = (TextView) view.findViewById(R.id.tvdescripcionActividad);
		TextView nota = (TextView) view.findViewById(R.id.tvNotaActividad);

		nroActividad.setText(evaluaciones.get(position).getNumero().toString());	
		descripcion.setText(evaluaciones.get(position).getDescripcion().toString());
		nota.setText(evaluaciones.get(position).getNota().toString());
		porcentaje.setText(evaluaciones.get(position).getPorcentaje().toString());	

		return view;
	}

}
