package co.edu.udea.mievaluacion.bl;

import co.edu.udea.mievaluacion.database.Database;
import co.edu.udea.mievaluacion.model.Pregunta;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Juan Diego
 */
public class PreguntaBL {
	
	private Map<Long,Pregunta> preguntas = Database.getPreguntas();
    
	public PreguntaBL() {
		preguntas.put(1L,new Pregunta("1","Seguridad en exposiciones"));
        preguntas.put(2L,new Pregunta("2","Respuesta clara y acertada a preguntas"));
        preguntas.put(3L,new Pregunta("3","Dominio de los temas del curso o actividad curricular"));
	}

	public List<Pregunta> getAllPreguntas(){
    	return new ArrayList<Pregunta>(preguntas.values());
    }
    
    public Pregunta getPregunta(Long id){
    	return preguntas.get(id);
    }
    
}
