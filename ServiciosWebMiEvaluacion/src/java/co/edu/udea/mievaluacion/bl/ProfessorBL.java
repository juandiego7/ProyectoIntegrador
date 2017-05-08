package co.edu.udea.mievaluacion.bl;

import co.edu.udea.mievaluacion.model.Professor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan Diego
 */
public class ProfessorBL {
    
    public List<Professor> getAllProfessors(){
       Professor professor1 = new Professor("1", "JUAN DIEGO GOEZ DURANGO");
       Professor professor2 = new Professor("2", "JOHNNY ALEJANDRO CASTAÑEDA VILLA");
       Professor professor3 = new Professor("3", "RAUL ANTONIO MARTINEZ SILGADO");
       
       List<Professor> professors = new ArrayList<>();
       professors.add(professor1);
       professors.add(professor2);
       professors.add(professor3);
       
       return professors;
    }
}
