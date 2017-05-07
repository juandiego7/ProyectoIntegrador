package co.edu.udea.notasudea.model.dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.edu.udea.notasudea.model.Programa;
import co.edu.udea.notasudea.model.Semestre;
import co.edu.udea.notasudea.model.db.Conexion;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 1/02/2014
 */
public class HistoriaDAO {

	private Context context;

	private String[] camposPrograma = new String[] {"codigo", "nombre"};
	private String[] camposSemestre = new String[] {"codigo", "promedio"}; 
	private static final String TAG_HISTORIA = "historia";
	private static final String TAG_SEMESTRES = "semestres";
	private static final String TAG_CODIGO_SEMESTRE = "semestre";  
	private static final String TAG_PROMEDIO = "promedioSemestre";
	private static final String TAG_CODIGO_PROGRAMA = "programa";   
	private static final String TAG_NOMBRE_PROGRAMA = "nombrePrograma";  

	public HistoriaDAO(Context context){
		this.context = context;
	}

	public ArrayList<Programa> cargarHistoria(){
		SQLiteDatabase conexion = Conexion.getConection(context);
		ArrayList<Programa> listProgramas = new ArrayList<Programa>();		
		String[] argsPrograma = null;
		String[] argsSemestre= null;		
		Cursor cursorPrograma = conexion.query("Programa", camposPrograma, null, argsPrograma, null, null, null);
		Cursor cursorSemestre = null;
		ArrayList<Semestre> listSemestres = null;
		if (cursorPrograma.moveToFirst()) {
			do{
				Programa programa = agregarPrograma(cursorPrograma);
				argsSemestre = new String[] {programa.getCodPrograma()};
				cursorSemestre = conexion.query("Semestre", camposSemestre, "programa=?", argsSemestre, null, null, null);
				if(cursorSemestre.moveToFirst()){
					listSemestres = new ArrayList<Semestre>();
					do{
						Semestre semestre = agregarSemestre(cursorSemestre);
						listSemestres.add(semestre);
					}
					while(cursorSemestre.moveToNext());
				}
				programa.setSemestres(listSemestres);
				listProgramas.add(programa);			
			}
			while(cursorPrograma.moveToNext());
		}
		conexion.close();

		return listProgramas;
	}

	private Programa agregarPrograma(Cursor cursorActividades) {
		Programa programa = new Programa();

		String codPrograma = cursorActividades.getString(0);	
		String nombrePrograma = cursorActividades.getString(1);

		programa.setCodPrograma(codPrograma);
		programa.setPrograma(nombrePrograma);

		return programa;
	}

	private Semestre agregarSemestre(Cursor cursorSemestre) {
		Semestre semestre = new Semestre();    	

		String codSemestre = cursorSemestre.getString(0);
		String promedioSemestre = cursorSemestre.getString(1);

		semestre.setCodSemestre(codSemestre);
		semestre.setPromedio(promedioSemestre);

		return semestre;
	}

	public void saveHistoriaAcademica(JSONObject historiaJSON){        
		try {
			//Se obtiene los programas
			JSONArray programas = historiaJSON.getJSONArray(TAG_HISTORIA);

			for (int i = 0; i < programas.length(); i++) {
				JSONObject programaJSON = programas.getJSONObject(i);

				//Se obtienen los datos del programa
				String codPrograma = addProgramaDB(programaJSON);

				JSONArray semestres = programaJSON.getJSONArray(TAG_SEMESTRES);
				for (int j = 0; j < semestres.length(); j++) {
					JSONObject semestreJSON = semestres.getJSONObject(j);
					//Se obtienen los datos del semestre
					addSemestreDB(semestreJSON, codPrograma);
				}                  	
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String addProgramaDB(JSONObject programaJSON){
		ContentValues registro = new ContentValues();
		SQLiteDatabase conexion = Conexion.getConection(context);
		String codigo = "";
		try{
			codigo = programaJSON.getString(TAG_CODIGO_PROGRAMA);
			registro.put("codigo", codigo);
			registro.put("nombre", programaJSON.getString(TAG_NOMBRE_PROGRAMA)); 					
		} catch (JSONException e) {
			e.printStackTrace();
		}
		conexion.insert("Programa", null, registro);
		conexion.close();
		return codigo; 		
	}

	private void addSemestreDB(JSONObject semestreJSON, String codPrograma){
		ContentValues registro = new ContentValues();
		SQLiteDatabase conexion = Conexion.getConection(context);

		try{
			registro.put("codigo", semestreJSON.getString(TAG_CODIGO_SEMESTRE));
			registro.put("promedio", semestreJSON.getString(TAG_PROMEDIO)); 
			registro.put("programa", codPrograma); 
		} catch (JSONException e) {
			e.printStackTrace();
		} 		
		conexion.insert("Semestre", null, registro);
		conexion.close();		
	}

	/**
	 * Metodo que elimina los registros de las tablas Semestre y Programa
	 */
	public void eliminarDatos(){
		SQLiteDatabase conexion = Conexion.getConection(context);
		conexion.delete("Semestre", null, null);
		conexion.delete("Programa", null, null);	
		conexion.close();
	}

	/**
	 * Metodo que comprueba si existen datos en la tabla de historia academica
	 * @return boolean true si hay datos, false de lo contrario.
	 */
	public boolean comprobarDatos(){
		ArrayList<Programa> historia = cargarHistoria();
		if(historia == null || historia.size() == 0 )
			return false;
		return true;
	}

}	