package co.edu.udea.notasudea.adapter;

import java.util.ArrayList;
import java.util.List;


import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.model.Evaluacion;
import co.edu.udea.notasudea.model.Materia;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 18/07/2013
 */
public class AdapterNotas extends BaseExpandableListAdapter{

	/**
	 * 
	 */
	private Context context;
	/**
	 * Listado de materias de un semestre del estudiante
	 */
	private List<Materia> materias;
	
	/**
	 * Contructor de la clase
	 * @param context
	 * @param materias
	 */
	public AdapterNotas(Context context, List<Materia> materias){
		this.context = context;
		this.materias = materias;
	}
	
	/**
	 * Metodo que ingresa un nuevo hijo(actividad) a un padre(materia), si el padre no existe
	 * lo agrega a la lista.
	 * @param actividad
	 * @param materia
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	public void addItem(Evaluacion actividad, Materia materia ){
		//Se comprueba si el padre ya esta en la lista
		if(!materias.contains(materia)){
			materias.add(materia);
		}
		//Se obtiene la posicion del padre
		int index = materias.indexOf(materia);
		//Se obtiene las actividades de la materia
		ArrayList<Evaluacion> actividades = (ArrayList<Evaluacion>) materias.get(index).getNotas();
		//Se agrega la actividad a la lista actividades de la materia
		actividades.add(actividad);
		//Se agregan las actividades nuevamente a la materia
		materias.get(index).setNotas(actividades);		
	}
	
	/**
	 * Metodo que retorna una actividad de una materia.
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<Evaluacion> actividades = (ArrayList<Evaluacion>) 
				materias.get(groupPosition).getNotas();
		return actividades.get(childPosition);
	}

	/**
	 * Metodo que retorna el id de una actividad, todavia no implementado
	 * @param groupPosition
	 * @param childPosition
	 * @return
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * Metodo que agrega las actividades a una materia.
	 * @param groupPosition
	 * @param childPosition
	 * @param isLastChild
	 * @param view
	 * @param parent
	 * @return
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		//Se obtiene la actividad
		Evaluacion actividad = (Evaluacion) getChild(groupPosition, childPosition);
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_actividad, null);
		}
		//Se agrega el numero de la actividad
		TextView nroActividad = (TextView) view.findViewById(R.id.tvNumeroActividad);
		nroActividad.setText(actividad.getNumero().toString());
		
		//Se agrega la descripcion de la actividad
		TextView descripcion = (TextView) view.findViewById(R.id.tvdescripcionActividad);
		descripcion.setText(actividad.getDescripcion().toString());
		
		//Se agrega la nota de la actividad
		TextView nota = (TextView) view.findViewById(R.id.tvNotaActividad);
		nota.setText(actividad.getNota().toString());
		
		TextView porcentaje = (TextView) view.findViewById(R.id.tvPorcentajeActividad);
		porcentaje.setText(actividad.getPorcentaje().toString());	
		
		return view;
	}

	/**
	 * Metodo que retorna el numero de actividades que tiene una materia.
	 * @param groupPosition Posicion de la materia
	 * @return int numero de actividades
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<Evaluacion> actividades = (ArrayList<Evaluacion>) 
				materias.get(groupPosition).getNotas();
		return actividades.size();
	}

	/**
	 * Metodo que retorna una materia dada su posicion.
	 * @param groupPosition Posicion de la materia
	 * @return
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return materias.get(groupPosition);
	}

	/**
	 * Metodo que retorna el numero de materias que hay en la lista.
	 * @return int Numero de materias en la lista
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public int getGroupCount() {
		return materias.size();
	}

	/**
	 * Metodo que retorna el id de una materia, no implementado
	 * @param groupPosition Posicion de la materia
	 * @return
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * Metodo que agrega las materias a la vista.
	 * @param groupPosition posicion de la materia
	 * @param isExpanded
	 * @param convertView
	 * @param parent
	 * @return
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View view, ViewGroup parent) {
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_materia, null);
		}
		//Se obtiene la materia
		Materia materia = (Materia) getGroup(groupPosition);
		
		//Se agrega el nombre de la materia
		TextView nombre = (TextView) view.findViewById(R.id.tvNombreMateria);
		nombre.setText(materia.getNombre().toString());
		
		//Se agrega los creditos de la materia
		TextView creditos = (TextView) view.findViewById(R.id.tvCreditosMateria);
		creditos.setText(materia.getCreditos().toString());
				
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Metodo que determina si una actividad puede ser seleccionada o no.
	 * @param groupPosition Posicion de la materia.
	 * @param childPosition Posicion de la actividad.
	 * @return boolean, 
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/07/2013
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}