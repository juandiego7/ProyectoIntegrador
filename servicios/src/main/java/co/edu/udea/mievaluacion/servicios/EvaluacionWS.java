package co.edu.udea.mievaluacion.servicios;

import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.edu.udea.mievaluacion.bl.EvaluacionBL;
import co.edu.udea.mievaluacion.dto.Pregunta;

/**
 * Implementacion de los servicios web de la logica de negocio para los dispositivos
 * @author Raul Antonio Martinez Silgado - rantonio.martinez@udea.edu.co
 * @author Juan Diego Goez Durango - diego.goez@udea.edu.co
 * @version 1
 */

@Path("evaluacion")//Definicion de la ruta con que va a responder el servicio
public class EvaluacionWS {

	EvaluacionBL evaluacionBL = new EvaluacionBL();
	
	@GET//Metodo http con que responde este metodo
	@Path("puedervernotas")//Definicion de la ruta para invocar este metodo
	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
	public String puedeVerNotas(@QueryParam("semestre")String username,
			@QueryParam("startDate")String startDate,
			@QueryParam("endDate")String endDate,) throws RemoteException{

		try {
			return evaluacionBL.puedeVerNotas();
		} catch (Exception e) {
			throw new RemoteException("Problema consultando");
		}
	}
//	@GET//Metodo http con que responde este metodo
//	@Path("all")//Definicion de la ruta para invocar este metodo
//	@Produces(MediaType.APPLICATION_JSON)//Formato de respuesta
//	public List<LoanW> getAllLoan(
//			@QueryParam("username")String username,
//			@QueryParam("status")String status
//			) throws RemoteException{
//		List<LoanW> loanWs = new ArrayList<LoanW>();
//		try {
//			for(Loan loan: loanBL.getLoansUser(username, status)){
//				LoanW loanW = new LoanW(
//								new LoanId(
//										new User(
//											loan.getLoanId().getUsername().getUsername(),
//											loan.getLoanId().getUsername().getTypeId(),
//											loan.getLoanId().getUsername().getNumberId(),
//											loan.getLoanId().getUsername().getName(),
//											loan.getLoanId().getUsername().getLastName(),
//											loan.getLoanId().getUsername().getEmail(),
//											loan.getLoanId().getUsername().getRole()
//										),
//									loan.getLoanId().getDevice(),
//									loan.getLoanId().getStartDate()
//								 ),
//								loan.getEndDate(),
//								loan.getReturnDate(),
//								loan.getStatus()
//							  );
//				loanWs.add(loanW);
//			}
//			return loanWs;
//		} catch (MyException e) {
//			throw new RemoteException("Problema consultando");
//		}
//	}
//	
//	/**
//	 * Servicio para registrar(crear) un prestamo
//	 * @see RF04 with status=RESERVADO
//	 * @see RF14 with status=PRESTADO
//	 * @param username
//	 * @param startDate
//	 * @param endDate
//	 * @param returnDate
//	 * @param status
//	 * @param code
//	 * @param copy
//	 * @return Response - Confirmaci�n de creaci�n
//	 * @throws RemoteException
//	 */
//	@POST
//	@Path("register")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response registerLoan(
//			@QueryParam("username")String username,
//			@QueryParam("startDate")String startDate,
//			@QueryParam("endDate")String endDate,
//			@QueryParam("status")String status,
//			@QueryParam("code")String code,
//			@QueryParam("copy")String copy) throws RemoteException{
//		
//		String message = null;
//		String type = null;
//		Date startD = null;
//		Date endD = null;
//		Date returnD = null; 
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH");
//		try {
//			startD = simpleDateFormat.parse(startDate);
//			endD = simpleDateFormat.parse(endDate);
//			loanBL.registerLoan(username, startD, endD, null, status, code, copy);
//			type = "ok";
//			message = "Prestamo registrado";
//		}catch (MyException e) {
//			type = "error";
//			message = e.getMessage();
//			//message = message + " starDate " + startD + " endDate " + endD + " returnDate " + returnD;
//		}catch (ParseException e) {
//			type = "error";
//			message = "Error, verifique el formato de la fecha ingresada";
//		}		
//		return new Response(type, message);
//	}
//	
//	/**
//	 * Retorna los prestamos para un dispositvo en la fecha(dia) indicado
//	 * @see RF02
//	 * @param code
//	 * @param copy
//	 * @param date
//	 * @return Lista de prestamos
//	 * @throws RemoteException
//	 */
//	@GET
//	@Path("get")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<LoanW> getLoans(
//			@QueryParam("code")String code, 
//			@QueryParam("copy")String copy, 
//			@QueryParam("date")String date) throws RemoteException{
//		Date d = null;
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		List<LoanW> loanWs = new ArrayList<LoanW>();
//		try {
//			d = simpleDateFormat.parse(date);
//			for(Loan loan: loanBL.getLoans(code, copy, d)){
//				LoanW loanW = new LoanW(
//						new LoanId(new User(
//								loan.getLoanId().getUsername().getUsername(),
//								loan.getLoanId().getUsername().getTypeId(),
//								loan.getLoanId().getUsername().getNumberId(),
//								loan.getLoanId().getUsername().getName(),
//								loan.getLoanId().getUsername().getLastName(),
//								loan.getLoanId().getUsername().getEmail(),
//								loan.getLoanId().getUsername().getRole()
//							),
//						loan.getLoanId().getDevice(),
//						loan.getLoanId().getStartDate()
//								),
//					loan.getEndDate(),
//					loan.getReturnDate(),
//					loan.getStatus());
//				loanWs.add(loanW);
//			}
//			return loanWs;
//		} catch (MyException e) {
//			throw new RemoteException("Problema consultando");
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		}
//	} 
//	
//	/**
//	 * Actualiza el estado de un prestamo a PRESTADO (Aprobar prestamo)
//	 * @see RF05
//	 * @param username
//	 * @param startDate
//	 * @param code
//	 * @param copy
//	 * @return Response - Confirmaci�n de actualizaci�n
//	 */
//	@PUT
//	@Path("lend")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateStatusLoan(
//			@QueryParam("username")String username, 
//			@QueryParam("startDate")String startDate,  
//			@QueryParam("code")String code, 
//			@QueryParam("copy")String copy) throws RemoteException{
//		Date date = null;
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH");
//		String message = null;
//		String type = null;
//		try {
//			if (startDate != null && !"".equals(startDate)){
//				date = simpleDateFormat.parse(startDate);
//			}
//			loanBL.updateLoan(username, date, null, "PRESTADO", code, copy);
//			type = "ok";
//			message = "Prestamo actualizado";
//		} catch (MyException e) {
//			type = "error";
//			message = e.getMessage();
//		} catch (ParseException e) {
//			type = "error";
//			message = "Error, verifique el formato de la fecha ingresada";
//		}
//		return new Response(type, message);
//	}
//	
//	/**
//	 * Actualiza el estado(status) a DEVUELTO y fecha de devoluci�n (returnDate) 
//	 * de un pr�stamo a la fecha actual, lo que significa que el dispositvo
//	 * fue entregado en el Laboratorio 
//	 * @see RF16
//	 * @param username
//	 * @param startDate
//	 * @param code
//	 * @param copy
//	 * @return Response - Confirmaci�n de actualizaci�n
//	 */
//	@PUT
//	@Path("return")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateReturnLoan(
//			@QueryParam("username")String username, 
//			@QueryParam("startDate")String startDate,  
//			@QueryParam("code")String code, 
//			@QueryParam("copy")String copy) throws RemoteException{
//		String message = null;
//		String type = null;
//		Date date = null;
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH");
//		try {
//			if (startDate != null && !"".equals(startDate)){
//				date = simpleDateFormat.parse(startDate);
//			}
//			//System.out.println("Date WS"+ date);
//			loanBL.updateLoan(username, date, new Date(), "DEVUELTO", code, copy);
//			type = "ok";
//			message = "Prestamo actualizado ";
//		} catch (MyException e) {
//			type = "error";
//			message = e.getMessage();
//		} catch (ParseException e) {
//			type = "error";
//			message = "Error, verifique el formato de la fecha ingresada";
//		}
//		return new Response(type, message);
//	}
//	
//	/**
//	 * Obtiene la lista de solicitudes de prestamos para el usuario con el tipo y
//	 * n�mero de identificaci�n ingresados
//	 * @see RF13 
//	 * @param typeId
//	 * @param numberId
//	 * @return Lista de Prestamos
//	 * @throws RemoteException
//	 */
//	@GET
//	@Path("search")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<LoanW> searchLoan(
//			@QueryParam("typeId")String typeId, 
//			@QueryParam("numberId")String numberId) throws RemoteException{
//		List<LoanW> loanWs = new ArrayList<LoanW>();
//		try {
//			
//			for(Loan loan: loanBL.getLoansDevice(typeId, numberId)){
//				LoanW loanW = new LoanW(
//						new LoanId(new User(
//								loan.getLoanId().getUsername().getUsername(),
//								loan.getLoanId().getUsername().getTypeId(),
//								loan.getLoanId().getUsername().getNumberId(),
//								loan.getLoanId().getUsername().getName(),
//								loan.getLoanId().getUsername().getLastName(),
//								loan.getLoanId().getUsername().getEmail(),
//								loan.getLoanId().getUsername().getRole()
//							),
//						loan.getLoanId().getDevice(),
//						loan.getLoanId().getStartDate()
//								),
//					loan.getEndDate(),
//					loan.getReturnDate(),
//					loan.getStatus());
//				loanWs.add(loanW);
//			}
//			return loanWs;
//		} catch (MyException e) {
//			throw new RemoteException("Error consultando los prestamos por tipo y numero de Id");
//		}
//	}
//	
//	/**
//	 * Deshace(elimina) una solicitud de prestamo
//	 * @see RF15 
//	 * @param username
//	 * @param startDate
//	 * @param code
//	 * @param copy
//	 * @return Response - Confirmaci�n del delete
//	 * @throws RemoteException
//	 */
//	@DELETE
//	@Path("delete")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleteLoan(
//			@QueryParam("username")String username, 
//			@QueryParam("startDate")String startDate,  
//			@QueryParam("code")String code, 
//			@QueryParam("copy")String copy) throws RemoteException{
//		String message = null;
//		String type = null;
//		Date date = null;
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH");
//		try {
//			if (startDate != null && !"".equals(startDate)){
//				date = simpleDateFormat.parse(startDate);
//			}
//			loanBL.deleteLoan(username, code, copy, date);
//			type = "ok";
//			message = "Solicitud de prestamo eliminada";
//		}catch (MyException e) {
//			type = "error";
//			message = e.getMessage();
//		} catch (ParseException e) {
//			type = "error";
//			message = "Error, verifique el formato de la fecha ingresada";
//		}		
//		return new Response(type, message);
//	}
//	
//	
//	
	
	

}
