package co.edu.udea.mievaluacion.servicios;

import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.edu.udea.mievaluacion.bl.ProfesorBL;
import co.edu.udea.mievaluacion.dto.Pregunta;
import co.edu.udea.mievaluacion.dto.Profesor;

@Path("profesor")
public class ProfesorWS {

	ProfesorBL profesorBL = new ProfesorBL();
	
	/**
	 * Metodo para retornar todo los profesores
	 * @see RF09
	 * @return List<Device> 
	 * @throws RemoteException
	 */
	@GET//Metodo http con que responde este metodo
	@Path("{semestre}/{cedula}")
	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
	public List<Profesor> obtener(@PathParam("semestre")String semestre,
								  @PathParam("cedula")String cedula) throws RemoteException{

		try {
			return profesorBL.getProfesores(semestre, cedula);
		} catch (Exception e) {
			throw new RemoteException("Problema consultando");
		}
	}
}
