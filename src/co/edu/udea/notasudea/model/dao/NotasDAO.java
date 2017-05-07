package co.edu.udea.notasudea.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.edu.udea.notasudea.model.Evaluacion;
import co.edu.udea.notasudea.model.Materia;
import co.edu.udea.notasudea.model.db.Conexion;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 28/01/2014
 * @version 1.1 08/04/2014
 */
public class NotasDAO {

	private Cursor cursor = null;

	private String[] camposMateria = new String[] {"nombre"};
	private String[] camposActividad = new String[] {"descripcionEvaluacion"}; 
	private String[] camposMateriaAll = new String[] {"codigo", "nombre", "creditos"};
	private String[] camposActividadAll = new String[] {"numeroEvaluacion", "descripcionEvaluacion", "nota", "porcentaje"}; 

	private static final String TAG_NOTAS = "actividadesMaterias";
	private static final String TAG_MATERIA = "materia";
	private static final String TAG_ACTIVIDAD = "actividades";
	private static final String TAG_CODIGO = "materia";
	private static final String TAG_NOMBRE = "nombre";
	private static final String TAG_CREDITOS = "creditos";
	private static final String TAG_NUMERO = "numeroEvaluacion";
	private static final String TAG_DESCRIPCION = "descripcionEvaluacion";    
	private static final String TAG_NOTA = "nota";
	private static final String TAG_PORCENTAJE = "porcentaje";    
	private static final String TAG_NO_NOTA = "mensaje";
	private static final int EVALUACION_ALL = 1;
	private static final int EVALUACION_NO = 2;	

	private Context context;

	public NotasDAO(Context context){
		this.context = context;
	}

	/**
	 * Metodo que retornar el nombre de una materia dado su nombre.
	 * @param codigo
	 * @return
	 */
	public String getNombreMateria(String codigo){
		SQLiteDatabase conexion = Conexion.getConection(context);
		String[] argsMateria = new String[] {codigo};
		String nombreMateria = "";
		cursor = conexion.query("Materia", camposMateria, "codigo=?", argsMateria, null, null, null);
		if (cursor.moveToFirst()) {
			do{
				nombreMateria = cursor.getString(0);
			} while(cursor.moveToNext());
		}
		conexion.close();
		return nombreMateria;

	}
	
	/**
	 * Metodo que comprueba si la nota enviada puede ser notificada verificando
	 * si existe la materia, la evaluacion y que la nota no se encuentre ya en 
	 * la BD, si la nota puede ser notificada la almacena en la BD.
	 * @param codigoMateria
	 * @param nroEvaluacion
	 * @param nota
	 * @return true si se puede notificar false de lo contrario.
	 */
	public boolean verificarNotaNotificacion(String codigoMateria, String nroEvaluacion, String nota){
		SQLiteDatabase conexion = Conexion.getConection(context);
		String[] argsActividad = new String[] {nroEvaluacion, codigoMateria};
		boolean existeNota = false, existeActividad = false;
		cursor = conexion.query("Actividad", camposActividadAll, "numeroEvaluacion=? AND materia = ?", argsActividad, null, null, null);
		if (cursor.moveToFirst()) {
			do{ 
				existeActividad = true;
				String notaBD = cursor.getString(2);
				if(nota.equals(notaBD)){
					existeNota = true;				
				}
			} while(cursor.moveToNext());
		}
		conexion.close();
		if(!existeNota && existeActividad){
			saveNota(codigoMateria, nroEvaluacion, nota);	
			return true;		
		}else
			return false;
	}

	/**
	 * Metodo que retorna la descripcion de una evaluacion dada su numero de evaluacion
	 * @param nroActividad
	 * @return
	 */
	public String getNombreActividad(String nroActividad, String codigoMateria){
		SQLiteDatabase conexion = Conexion.getConection(context);
		String[] argsActividad = new String[] {nroActividad, codigoMateria};
		String descripcion = "";
		cursor = conexion.query("Actividad", camposActividad, "numeroEvaluacion=? AND materia = ?", argsActividad, null, null, null);
		if (cursor.moveToFirst()) {
			do{ 
				descripcion = cursor.getString(0);
			} while(cursor.moveToNext());
		}	
		conexion.close();
		return descripcion;
	}

	/**
	 * Metodo que guarda una nueva nota en la BD.
	 * @param materia
	 * @param nroEvaluacion
	 * @param nota
	 */
	public void saveNota(String materia, String nroEvaluacion, String nota){
		SQLiteDatabase conexion = Conexion.getConection(context);
		ContentValues evaluacion = new ContentValues(); 
		evaluacion.put(TAG_NOTA, nota);
		String[] argsActividad = new String[] {materia, nroEvaluacion};		
		conexion.update("Actividad", evaluacion, "materia = ? AND numeroEvaluacion = ?", argsActividad);
		conexion.close();
	}

	/**
	 * Metodo que obtiene el listado de materias y sus evaluaciones para el semestre
	 * en curso que se encuentran almacenadas en la BD del dispositivo
	 * @return Listado de materias y sus evaluaciones
	 */
	public ArrayList<Materia> cargarNotas(){
		SQLiteDatabase conexion = Conexion.getConection(context);
		ArrayList<Materia> listMaterias = new ArrayList<Materia>();
		String[] args = null;
		String[] argsActividad = null;
		Cursor cursorMaterias = conexion.query("Materia", camposMateriaAll, null, args, null, null, null);
		Cursor cursorActividades = null;
		ArrayList<Evaluacion> listActividades = null;
		// Se comprueba que hayan registros
		if (cursorMaterias.moveToFirst()) {
			do {
				Materia materia = agregarMateria(cursorMaterias);
				argsActividad = new String[] {materia.getCodigo()};
				cursorActividades =  conexion.query("Actividad", camposActividadAll, "materia=?", argsActividad, null, null, null);
				if (cursorActividades.moveToFirst()) {
					listActividades = new ArrayList<Evaluacion>();
					do{
						Evaluacion evaluacion = agregarEvaluacion(cursorActividades);
						listActividades.add(evaluacion);
					} while(cursorActividades.moveToNext());
				}
				materia.setNotas(listActividades);
				listMaterias.add(materia);
			}
			while (cursorMaterias.moveToNext());
		}
		conexion.close();				

		return listMaterias;
	}

	/**
	 * Se obtienen los datos de la evaluacion a partir
	 * del cursosr de la BD
	 * @param cursorActividades
	 * @return
	 */
	private Evaluacion agregarEvaluacion(Cursor cursorActividades) {
		Evaluacion evaluacion = new Evaluacion();
		String numeroEvaluacion = cursorActividades.getString(0);	
		String descripcionEvaluacion = cursorActividades.getString(1);
		String nota = cursorActividades.getString(2);
		String porcentaje = cursorActividades.getString(3)  + "%";	
		if(numeroEvaluacion == null){
			numeroEvaluacion = "";
			nota = "";
			porcentaje = "";		
		}
		evaluacion.setNumero(numeroEvaluacion);
		evaluacion.setDescripcion(descripcionEvaluacion);
		evaluacion.setNota(nota);
		evaluacion.setPorcentaje(porcentaje);

		return evaluacion;
	}

	/**
	 * Se obtienen los datos de una materia a partir
	 * del cursor de la BD.
	 * @param cursor
	 * @return
	 */
	private Materia agregarMateria(Cursor cursor) {
		Materia materia = new Materia();
		String codigo = cursor.getString(0);
		String nombreMateria = cursor.getString(1);
		String creditosMateria = cursor.getString(2);
		//Se agregan los valores a la materia
		materia.setCodigo(codigo);
		materia.setNombre(nombreMateria);
		materia.setCreditos("CR "+ creditosMateria);  

		return materia;
	}

	/**
	 * Metodo que guarda las materias en la BD del dispositivo.
	 * @param notasJSON JSON con las materias del semestre actual
	 */
	public void saveNotas(JSONObject notasJSON){

		try {

			JSONArray materias = notasJSON.getJSONArray(TAG_NOTAS);              	              	

			for (int i = 0; i < materias.length(); i++) {
				JSONObject materia = materias.getJSONObject(i);
				//Se obtiene el object que contiene a la materia
				JSONObject materiaJSON = materia.getJSONObject(TAG_MATERIA);
				//Se obtienen los datos de la materia
				String codMateria = addMateriaDB(materiaJSON);

				//Se comprueba que la materia tenga actividades
				if(materia.has(TAG_ACTIVIDAD)){

					//La materia tiene varias actividades
					JSONArray materiasArray = materia.getJSONArray(TAG_ACTIVIDAD);
					for (int j = 0; j < materiasArray.length(); j++) {
						JSONObject actividad = materiasArray.getJSONObject(j);
						//Se obtiene los datos de la actividad
						addActividadDB(actividad, codMateria, EVALUACION_ALL);
					}

				}else{
					/**
					 * La materia no posee actividades o no se ha realizado la evaluacion
					 * del docente
					 */
					JSONObject mensajeObject = materia.getJSONObject(TAG_NO_NOTA);

					addActividadDB(mensajeObject, codMateria, EVALUACION_NO);   								
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que recibe la informacion de una materia en formato JSON, la 
	 * parsea y la guarda en la BD del dispositivo.
	 * @param materiaJSON datos de la materia en JSON
	 * @return codMateria el codigo de la materia
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/01/2014
	 */
	private String addMateriaDB(JSONObject materiaJSON){
		ContentValues registro = new ContentValues();
		//Se abre la base de datos 'DBNotas' en modo escritura
		SQLiteDatabase conexion = Conexion.getConection(context);
		String codigo = "";
		try {
			codigo = materiaJSON.getString(TAG_CODIGO);
			registro.put("codigo", codigo);    		 
			registro.put(TAG_NOMBRE, materiaJSON.getString(TAG_NOMBRE));
			registro.put(TAG_CREDITOS, materiaJSON.getString(TAG_CREDITOS));
			registro.put(TAG_NOTA, materiaJSON.getString(TAG_NOTA));			

		} catch (JSONException e) {
			e.printStackTrace();
		}
		conexion.insert("Materia", null, registro);
		conexion.close();
		return codigo;
	}

	/**
	 * Metodo que recibe la informacion de una actividad en formato JSON, la 
	 * parsea y la almacena en la BD del dispositivo.
	 * @param actividadJSON datos de la actividad en JSON
	 * @param codMateria
	 * @param opcion
	 * @autor Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
	 * @version 1.0 19/01/2014
	 */
	private void addActividadDB(JSONObject actividadJSON, String codMateria, int opcion){
		ContentValues registro = new ContentValues(); 
		SQLiteDatabase conexion = Conexion.getConection(context);
		try {
			if(opcion ==  EVALUACION_ALL){
				registro.put(TAG_NUMERO, actividadJSON.getString(TAG_NUMERO));	
				registro.put(TAG_DESCRIPCION, actividadJSON.getString(TAG_DESCRIPCION));
				registro.put(TAG_NOTA, actividadJSON.getString(TAG_NOTA));			
				registro.put(TAG_PORCENTAJE, actividadJSON.getString(TAG_PORCENTAJE));	
			}else{
				registro.put(TAG_DESCRIPCION, actividadJSON.getString(TAG_NO_NOTA));
			}
			registro.put(TAG_MATERIA, codMateria);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		conexion.insert("Actividad", null, registro);	 
		conexion.close();
	}

	/**
	 * Metodo que comprueba si existen datos en las tablas de las notas
	 * @return boolean true si hay datos, false de lo contrario.
	 */
	public boolean comprobarDatos(){
		ArrayList<Materia> notas = cargarNotas();
		if(notas == null || notas.size() == 0)
			return false;
		return true;		
	}

	/**
	 * Metodo que elimina los registros que hay en las tablas de Materia y Actividad.
	 */
	public void eliminarDatos(){
		SQLiteDatabase conexion = Conexion.getConection(context);
		conexion.delete("Materia", null, null);
		conexion.delete("Actividad", null, null);
		conexion.close();	
	}

	/**
	 * Metodo que retorna las actividades de una materia.
	 * @param materia Codigo de la materia
	 * @return List de Evaluaciones
	 */
	public List<Evaluacion> getEvaluacionesMateria(String materia) {
		SQLiteDatabase conexion = Conexion.getConection(context);
		ArrayList<Evaluacion> listActividades = new ArrayList<Evaluacion>();
		String[] argsActividad = new String[]{materia};
		Cursor cursorActividades = conexion.query("Actividad", camposActividadAll, "materia=?", argsActividad, null, null, null);
		if (cursorActividades.moveToFirst()) {
			do{
				Evaluacion evaluacion = agregarEvaluacion(cursorActividades);
				listActividades.add(evaluacion);
			} while(cursorActividades.moveToNext());
		}
		return listActividades;
	}

}