package co.edu.udea.mievaluacion.ws;

import co.edu.udea.mievaluacion.bl.ProfessorBL;
import co.edu.udea.mievaluacion.model.Professor;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Juan Diego
 */
@Path("/professors")
public class ProfessorsResource {
    
    ProfessorBL professorBL = new ProfessorBL();
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Professor> getProfessors() {
        return professorBL.getAllProfessors();
    }
}
