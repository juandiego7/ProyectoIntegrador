package co.edu.udea.mievaluacion.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.udea.mievaluacion.database.DataSource;

public class EvaluacionBL {

	public String puedeVerNotas(int semestre, String cedula, int materia, int grupo){
		Connection con = null;// para la conexion con la base de datos
		PreparedStatement ps = null;// para crear la consulta sql
		ResultSet rs = null;// para  capturar los datos que devuelve la consulta
		String o="vacio";
		DataSource ds = DataSource.getInstance();
		try {
			con = ds.getConnection();			
			ps = con.prepareStatement("SELECT * FROM evaluacion"
								   + " WHERE semestre="+semestre+","
									   	  + "cedula="+cedula+","
									   	  + "materia="+materia+","
									   	  + "grupo="+grupo);//Consulta a la BD
			rs = ps.executeQuery();//Ejecucion y captura del resultado
			while (rs.next()) {//Ciclo para procesar los datos
				o = rs.getString("descripcion");
				
			}
			
			
		} catch (SQLException e) {//No existe la BD
			e.printStackTrace();
		} 
		return o;
		
	}
}
