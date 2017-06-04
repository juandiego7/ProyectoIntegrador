package co.edu.udea.mievaluacion.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.mievaluacion.database.DataSource;
import co.edu.udea.mievaluacion.dto.Pregunta;

public class PreguntaBL {
	
	public List<Pregunta> obtener(){
		Connection con = null;// para la conexion con la base de datos
		PreparedStatement ps = null;// para crear la consulta sql
		ResultSet rs = null;// para  capturar los datos que devuelve la consulta
		List<Pregunta> preguntas = null;
		DataSource ds = DataSource.getInstance();
		try {
			con = ds.getConnection();			
			ps = con.prepareStatement("SELECT * FROM pregunta");//Consulta a la BD
			rs = ps.executeQuery();//Ejecucion y captura del resultado
			preguntas = new ArrayList<>();
			while(rs.next()){
				preguntas.add(new Pregunta(rs.getString("numero"),
						rs.getString("tipo"),rs.getString("descripcion"),
						rs.getString("categoria")));
			}
			
		} catch (SQLException e) {//No existe la BD
			e.printStackTrace();
		} 
		return preguntas;
		
	}
}
