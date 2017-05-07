package co.edu.udea.notasudea.adapter;

import java.util.List;

import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.model.Materia;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 30/10/2013
 */
public class AdapterSemestre extends ArrayAdapter<Materia>{
	
	private Context context;
	private int layoutId;
	private List<Materia> materias;
	private TextView tvNombre;
	private TextView tvNota;
	private TextView tvCreditos;
	private Materia materia;
	
	public AdapterSemestre(Context context, int resource, List<Materia> materias) {
		super(context, resource, materias);
		this.context = context;
		this.layoutId = resource;
		this.materias = materias;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(layoutId, parent, false);
		
		tvNombre = (TextView) rowView.findViewById(R.id.item_materia_nombre);
		tvNota = (TextView) rowView.findViewById(R.id.item_materia_nota);
		tvCreditos = (TextView) rowView.findViewById(R.id.item_materia_creditos);
		
		materia = materias.get(position);
		tvNombre.setText(materia.getNombre());
		tvNota.setText(materia.getNota());
		tvCreditos.setText(materia.getCreditos());
			
		return rowView;
	}
	
}