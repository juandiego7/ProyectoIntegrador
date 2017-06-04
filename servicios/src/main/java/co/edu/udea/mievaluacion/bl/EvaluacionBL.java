package co.edu.udea.mievaluacion.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.edu.udea.mievaluacion.database.DataSource;
import co.edu.udea.mievaluacion.dto.Evaluacion;
import co.edu.udea.mievaluacion.dto.Response;
import co.edu.udea.mievaluacion.dto.Ver;

public class EvaluacionBL {

	public List<Ver> puedeVerNotas(String semestre, String cedula){
		
		Connection con = null;// para la conexion con la base de datos
		PreparedStatement ps = null;// para crear la consulta sql
		ResultSet rs = null;// para  capturar los datos que devuelve la consulta
		ResultSet rs2 = null;// para  capturar los datos que devuelve la consulta
		String o="vacio";
		String facultad = null;
		DataSource ds = DataSource.getInstance();
		int count = 0;
		List<Ver> vers = new ArrayList<>();
		try {
			con = ds.getConnection();
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
			String fecha = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
			// el que parsea
			SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
			// el que formatea
			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

			ps = con.prepareStatement("SELECT m.materia, m.grupo "
					+ "FROM matricula m, grupo g "
					+ "WHERE m.materia = g.materia "
					+ "AND m.grupo = g.numero "
					+ "AND m.semestre = g.semestre "
					+ "AND m.semestre = " + semestre + " "
					+ "AND m.estudiante = " + cedula);
			rs = ps.executeQuery();
			while(rs.next()){
				vers.add(new Ver(rs.getString("materia"),"S"));
			}			
			
			try {
				date = parseador.parse(fecha);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			ps = con.prepareStatement("SELECT p.facultad "
									+ "FROM programa p, estudiante e "
									+ "WHERE e.programa = p.codigo " 
									+ "AND e.cedula = " + cedula);
			rs = ps.executeQuery();
			if(rs.next()){
				facultad = rs.getString("facultad").toString();
				System.out.println("facultad "+facultad + "  " );	
			}
			//TO_DATE ('2016/04/19', 'yyyy/mm/dd')
			ps = con.prepareStatement("SELECT * "
									+ "FROM encuesta "
									+ "WHERE facultad = "+facultad + " " 
									+ "AND semestre = "+semestre+" "
									+ "AND '" + formateador.format(date)+"' "
									+" BETWEEN f_inicio AND f_fin");
			rs = ps.executeQuery();
			
			if(!rs.next()){
				return vers;
			}
			ps = con.prepareStatement("SELECT DISTINCT materia FROM evaluacion "
					   				+ "WHERE semestre = "+semestre+" "
					   				+ "AND estudiante = "+ cedula);
			
			rs = ps.executeQuery();
			int i = 0;
			while(rs.next()){
				i = 1;
				for (Ver ver : vers) {
					System.out.println("rs "+rs.getString("materia") + " vers(i): "+ver.getMateria());
					System.out.println(!rs.getString("materia").equals(ver.getMateria()));
					if(!rs.getString("materia").equals(ver.getMateria())){
						ver.setPuede("N");
					}
				}
			}
			if(i==0){
				for (Ver ver : vers) {
					ver.setPuede("N");
				}
			}
			
		} catch (SQLException e) {//No existe la BD
			e.printStackTrace();
		}
		
		return vers;
	}

	public Respuesta regitrarEvaluacion(Evaluacion evaluacion){
		Connection con = null;// para la conexion con la base de datos
		PreparedStatement ps = null;// para crear la consulta sql
		ResultSet rs = null;// para  capturar los datos que devuelve la consulta
		ResultSet rs2 = null;// para  capturar los datos que devuelve la consulta
		String o="vacio";
		String facultad = null;
		DataSource ds = DataSource.getInstance();
		int count = 0;
		List<Ver> vers = new ArrayList<>();
		
		try {
			con = ds.getConnection();
			int t = evaluacion.getRespuestas().size();
			for ( int i = 0;i < t;i++) {
				ps = con.prepareStatement("INSERT INTO evaluacion(semestre, materia, estudiante, grupo, profesor, pregunta, respuesta)"
										+ "VALUES ("+evaluacion.getSemestre()+","
													+evaluacion.getMateria()+","
													+evaluacion.getEstudiante()+","
													+evaluacion.getGrupo()+","
													+evaluacion.getProfesor()+","
													+evaluacion.getRespuestas().get(i).getPregunta()+","
													+evaluacion.getRespuestas().get(i).getRespuesta()+")");//Consulta a la BD
				rs = ps.executeQuery();//Ejecucion y captura del resultado	
			}
		}catch (Exception e) {
			return new Respuesta("Error","Datos no insertados");
			//e.printStackTrace();
		}
		
		return new Respuesta("Exito","Datos insertados");
	}

}
