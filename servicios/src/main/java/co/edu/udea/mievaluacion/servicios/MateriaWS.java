package co.edu.udea.mievaluacion.servicios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.edu.udea.mievaluacion.bl.EstudianteBL;
import co.edu.udea.mievaluacion.dto.Materia;

@Path("materia")
public class MateriaWS {
	
	EstudianteBL estudianteBL = new EstudianteBL();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("notas/{cedula}")
	public List<Materia> getMaterias(@PathParam("cedula")String cedula){
		return estudianteBL.getMaterias(cedula);
	}
}
