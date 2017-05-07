package co.edu.udea.notasudea.adapter;

import java.util.ArrayList;


import co.edu.udea.notasudea.R;
import co.edu.udea.notasudea.model.Semestre;
import co.edu.udea.notasudea.model.Programa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 3/07/2013
 */
public class AdapterHistoriaAcademica extends BaseExpandableListAdapter{

	/**
	 * 
	 */
	private Context context;
	/**
	 * Listado de programas del estudiante
	 */
	private ArrayList<Programa> programas;
	
	public AdapterHistoriaAcademica(Context context, ArrayList<Programa> programas){
		this.context = context;
		this.programas = programas;
	}
	
	/**
	 * Metodo que ingresa un nuevo hijo a un padre, si el padre no existe lo agrega a la lista
	 * @param child
	 * @param group
	 */
	public void addItem(Semestre child, Programa group){
		//Se comprueba si el padre ya esta en la lista
		if(!programas.contains(group)){
			programas.add(group);
		}
		//Se obtiene la posicion del padre
		int index = programas.indexOf(group);
		//Se obtiene los hijos del padre
		ArrayList<Semestre> childList = programas.get(index).getSemestres();
		//Se agrega el nuevo hijo a la lista de hijos del padre
		childList.add(child);
		//Se agrega el listado de hijos nuevamente al padre
		programas.get(index).setSemestres(childList);
	}
	
	/**
	 * Metodo que retorna un hijo de la lista de un padre.
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<Semestre> semestres = 
				programas.get(groupPosition).getSemestres();
		return semestres.get(childPosition);
	}

	@Override
	public long getChildId(int gropPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * Metodo que agrega los hijos
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		//Se obtiene el hijo
		Semestre child = (Semestre) getChild(groupPosition, childPosition);
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_semestre, null);
		}
		//Se le agrega el codigo del semestre
		TextView semestre = (TextView) view.findViewById(R.id.tvCodSemestre);
		//se modifica el formato del codigo del semetre de un formato 20122 a 2012-2
		String codSemestre = child.getCodSemestre().toString().substring(0,4)+"-"+child.getCodSemestre().toString().substring(4, 5);
		semestre.setText(codSemestre);

		//Se agrega el promedio del semestre
		TextView promedio = (TextView) view.findViewById(R.id.tvPromedioSemestre);
		promedio.setText(child.getPromedio().toString());
			
		return view;
	}

	/**
	 * Metodo que retorna el numero de hijos que posea un padre.
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<Semestre> semestres = programas.get(groupPosition).getSemestres();
		return semestres.size();
	}

	/**
	 * Metodo que retorna un padre dada su posicion.
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return programas.get(groupPosition);
	}

	/**
	 * Metodo que retorna el numero de padres que posea la lista.
	 */
	@Override
	public int getGroupCount() {
		return programas.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * Metodo que agrega los padres
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View view, ViewGroup parent) {
		//Se obtiene el padre
		Programa group = (Programa) getGroup(groupPosition);
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_programa, null);
		}
		//Se agrega el codigo del programa
		TextView codPrograma = (TextView) view.findViewById(R.id.tvCodPrograma);
		codPrograma.setText(group.getCodPrograma());

		//Se agrega el nombre del programa
		TextView nombrePrograma = (TextView) view.findViewById(R.id.tvNombrePrograma);
		nombrePrograma.setText(group.getPrograma());
		
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Metodo que determina si un hijo se puede seleccionar o no.
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}