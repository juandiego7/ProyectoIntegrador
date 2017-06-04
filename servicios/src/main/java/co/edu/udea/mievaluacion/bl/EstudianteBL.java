package co.edu.udea.mievaluacion.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.udea.mievaluacion.database.DataSource;
import co.edu.udea.mievaluacion.dto.Materia;

public class EstudianteBL {

	
	public List<Materia> getMaterias(String cedula){
		
		Connection con = null;// para la conexion con la base de datos
		PreparedStatement ps = null;// para crear la consulta sql
		ResultSet rs = null;// para  capturar los datos que devuelve la consulta
		List<Materia> materias = null;
		DataSource ds = DataSource.getInstance();
		try {
			con = ds.getConnection();
			materias = new ArrayList<>(); 
			ps = con.prepareStatement("SELECT ma.materia, ma.semestre, ma.estudiante, m.nombre, ma.grupo"
								   + " FROM matricula ma, grupo g, materia m "
								   + " WHERE  ma.materia = g.materia"
								   + " AND ma.semestre = g.semestre"
								   + " AND ma.grupo = g.numero"
								   + " AND g.materia = m.codigo "
								   + " AND ma.estudiante = " + cedula
								   + " AND ma.semestre = "+20171);
			rs = ps.executeQuery();
			while(rs.next()){
				materias.add(new Materia(rs.getString("semestre"),
									 	 rs.getString("estudiante"),
									     rs.getString("materia"),
										 rs.getString("grupo"),
										 rs.getString("nombre")));
			}
		} catch (SQLException e) {//No existe la BD
			e.printStackTrace();
		} 
		return materias;
		
	
	}
}
