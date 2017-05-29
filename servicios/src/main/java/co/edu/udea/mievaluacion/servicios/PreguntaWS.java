package co.edu.udea.mievaluacion.servicios;

import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.udea.mievaluacion.bl.PreguntaBL;
import co.edu.udea.mievaluacion.dto.Pregunta;

/**
 * Implementacion de los servicios web de la logica de negocio para los dispositivos
 * @author Raul Antonio Martinez Silgado - rantonio.martinez@udea.edu.co
 * @author Juan Diego Goez Durango - diego.goez@udea.edu.co
 * @version 1
 */

@Path("pregunta")//Definicion de la ruta con que va a responder el servicio
public class PreguntaWS {
	
	PreguntaBL preguntaBL = new PreguntaBL();
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
	
	/**
	 * Metodo para retornar todo los dispositivos
	 * @see RF09
	 * @return List<Device> 
	 * @throws RemoteException
	 */
	@GET//Metodo http con que responde este metodo
	@Path("all")//Definicion de la ruta para invocar este metodo
	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
	public List<Pregunta> obtener() throws RemoteException{

		try {
			return preguntaBL.obtener();
		} catch (Exception e) {
			throw new RemoteException("Problema consultando");
		}
	}
	
	@GET//Metodo http con que responde este metodo
	@Path("p")//Definicion de la ruta para invocar este metodo
	@Produces(MediaType.TEXT_PLAIN)//Formato de respuesta
	public String ob() throws RemoteException{

		try {
			return "pruen";
		} catch (Exception e) {
			throw new RemoteException("Problema consultando");
		}
	}
	
	/**
	 * Servicio para actualizar el estado de un dispositivo
	 * @see RF06 with status=INHABILITADO
	 * @return Response - Confirmacion de actualizacion
	 * @throws RemoteException
	 */
//	@PUT//Metodo http con que responde este metodo
//	@Path("status")//Definicion de la ruta para invocar este metodo
//	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
//	public Response updateStatus(@QueryParam("code")String code,
//							   @QueryParam("copy")String copy,
//							   @QueryParam("status")String status) throws RemoteException{
//		String message = null;
//		String type = null;
//		try {
//			deviceBL.updateStatus(code, copy, status);
//			message = "Dispositivo actualizado";
//			type="ok";
//		} catch (MyException e) {
//			message = "Error actualizando dispositivo";
//			type="error";
//			throw new RemoteException("Problema consultando");
//		}
//		return new Response(type,message); 
//	}
//
//	/**
//	 * Servicio para obtener el dispositivo que corresponde con un codigo y copia ingresados como parametros
//	 * @see RF17
//	 * @param code
//	 * @param copy
//	 * @return Device
//	 * @throws RemoteException
//	 */
//	@GET//Metodo http con que responde este metodo
//	@Path("id")//Definicion de la ruta para invocar este metodo
//	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
//	public Device getDevice(@QueryParam("code")String code,
//							@QueryParam("copy")String copy ) throws RemoteException{
//		try {
//			return deviceBL.getDevice(code, copy);
//		} catch (MyException e) {
//			throw new RemoteException("Problema consultando");
//		}
//	}
//	
//	/**
//	 * Servicio para registrar un dispositivo
//	 * @see RF07
//	 * @param code
//	 * @param copy
//	 * @param name
//	 * @param type
//	 * @param status
//	 * @param details
//	 * @return Response - Confirmaci�n de creaci�n
//	 * @throws RemoteException
//	 */
//	@POST//Metodo http con que responde este metodo
//	@Path("register")//Definicion de la ruta para invocar este metodo
//	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
//	public Response registerDevice(@QueryParam("code")String code,
//								 @QueryParam("copy")String copy,
//								 @QueryParam("name")String name,
//								 @QueryParam("type")String type,
//								 @QueryParam("status")String status,
//								 @QueryParam("details")String details ) throws RemoteException{
//		String message = null;
//		String typeA = null;
//		
//		try {
//			
//			deviceBL.registerDevice(code, copy, name, type, status, details);
//			typeA = "ok";
//			message = "Dispositivo registrado";
//		} catch (MyException e) {
//			typeA = "error";
//			message = e.getMessage();
//		}
//		return new Response(typeA,message);
//	}
//	
//	/**
//	 * Servicio para buscar un dispositivo por medio del codigo, nombre y tipo
//	 * @see RF10 
//	 * @param code
//	 * @param name
//	 * @param type
//	 * @return List<Device>
//	 * @throws RemoteException
//	 */
//	@GET//Metodo http con que responde este metodo
//	@Path("search")//Definicion de la ruta para invocar este metodo
//	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
//	public List<Device> searchDevice(@QueryParam("code")String code,
//			 						 @QueryParam("name")String name,
//			 						 @QueryParam("type")String type) throws RemoteException{
//		try {
//			return deviceBL.searchDevice(code, name, type);
//		} catch (MyException e) {
//			throw new RemoteException("Problema consultando");
//		}
//	}
//	
//	/**
//	 * Actualiza los datos de un dispositivo
//	 * @see RF08
//	 * @param code
//	 * @param copy
//	 * @param name
//	 * @param type
//	 * @param status
//	 * @param details
//	 * @return Response - Confirmaci�n de la actualizaci�n
//	 * @throws RemoteException
//	 */
//	@PUT
//	@Path("update")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateDevice(
//			@QueryParam("code")String code,
//			@QueryParam("copy")String copy,
//			@QueryParam("name")String name,
//			@QueryParam("type")String type,
//			@QueryParam("status")String status,
//			@QueryParam("details")String details) throws RemoteException{
//		String message = null;
//		String typeA = null;
//		try {
//			deviceBL.updateDevice(code, copy, name, type, status, details);
//			typeA = "ok";
//			message = "Dispositivo actualizado";
//		} catch (MyException e) {
//			typeA = "error";
//			message = e.getMessage();
//		}
//		return new Response(typeA,message);
//	}
}
