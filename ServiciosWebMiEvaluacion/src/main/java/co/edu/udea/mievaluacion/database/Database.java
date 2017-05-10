/**
 * 
 */
package co.edu.udea.mievaluacion.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.udea.mievaluacion.model.Pregunta;
import co.edu.udea.mievaluacion.model.Profesor;

/**
 * @author Juan Diego
 *
 */
public class Database {
	
	private static Map<Long,Profesor> profesores = new HashMap<>();
	private static Map<Long,Pregunta> preguntas = new HashMap<>();
	
	public static Map<Long,Profesor> getProfesores(){
		return profesores;
	}
	public static Map<Long,Pregunta> getPreguntas(){
		return preguntas;
	}
}
