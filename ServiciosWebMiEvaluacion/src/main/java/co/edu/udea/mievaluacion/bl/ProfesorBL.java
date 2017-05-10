package co.edu.udea.mievaluacion.bl;

import co.edu.udea.mievaluacion.database.Database;
import co.edu.udea.mievaluacion.model.Profesor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Juan Diego
 */
public class ProfesorBL {
	
	private Map<Long,Profesor> profesores = Database.getProfesores();

	public ProfesorBL() {
		profesores.put(1L,new Profesor("1", "JUAN DIEGO GOEZ DURANGO"));
		profesores.put(2L,new Profesor("2", "JOHNNY ALEJANDRO CASTAÑEDA VILLA"));
		profesores.put(3L,new Profesor("3", "RAUL ANTONIO MARTINEZ SILGADO"));
	}

	public List<Profesor> getAllProfessors(){
    	return new ArrayList<Profesor>(profesores.values());
    }
    
    public Profesor getProfesor(Long id){
    	return profesores.get(id);
    }
}
