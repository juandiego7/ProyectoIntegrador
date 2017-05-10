package co.edu.udea.mievaluacion.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.edu.udea.mievaluacion.bl.ProfesorBL;
import co.edu.udea.mievaluacion.model.Profesor;

@Path("/profesores")
public class ProfesoresResource {

	ProfesorBL profesorBL = new ProfesorBL();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Profesor> getProfesores(){
		return profesorBL.getAllProfessors();
	}
	
	@GET
	@Path("/id")
	@Produces(MediaType.APPLICATION_JSON)
	public Profesor getProfesor(@HeaderParam("id")long id){
		return profesorBL.getProfesor(new Long(id));
	}
}
