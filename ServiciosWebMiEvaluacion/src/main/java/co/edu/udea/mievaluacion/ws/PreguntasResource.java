package co.edu.udea.mievaluacion.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.edu.udea.mievaluacion.bl.PreguntaBL;
import co.edu.udea.mievaluacion.bl.ProfesorBL;
import co.edu.udea.mievaluacion.model.Pregunta;
import co.edu.udea.mievaluacion.model.Profesor;

/**
 * @author Juan Diego
 *
 */
@Path("/preguntas")
public class PreguntasResource {
	PreguntaBL preguntaBL = new PreguntaBL();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pregunta> getProfesores(){
		return preguntaBL.getAllPreguntas();
	}
	
	@GET
	@Path("/number")
	@Produces(MediaType.APPLICATION_JSON)
	public Pregunta getProfesor(@HeaderParam("number")long number){
		return preguntaBL.getPregunta(new Long(number));
	}
}
