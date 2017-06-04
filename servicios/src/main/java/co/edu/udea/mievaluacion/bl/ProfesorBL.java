package co.edu.udea.mievaluacion.bl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.mievaluacion.database.DataSource;
import co.edu.udea.mievaluacion.dto.Materia;
import co.edu.udea.mievaluacion.dto.Profesor;

public class ProfesorBL {
	
	public List<Profesor> getProfesores(String semestre, String cedula){
		
		Connection con = null;// para la conexion con la base de datos
		PreparedStatement ps = null;// para crear la consulta sql
		ResultSet rs = null;// para  capturar los datos que devuelve la consulta
		List<Profesor> profesores = null;
		DataSource ds = DataSource.getInstance();
		try {
			con = ds.getConnection();
			profesores = new ArrayList<>(); 
			ps = con.prepareStatement("SELECT m.nombre nombreMateria, m.codigo, g.numero, p.nombre, p.apellidos, p.cedula"
								   + " FROM matricula ma, grupo g, materia m, grupo_profesor gp, profesor p "
								   + " WHERE  ma.materia = g.materia "
								   + " AND ma.semestre = g.semestre "
								   + " AND ma.grupo = g.numero"
								   + " AND g.materia = m.codigo "
								   + " AND g.numero = gp.numero "
								   + " AND g.semestre = gp.semestre "
								   + " AND g.materia = gp.materia "
								   + " AND gp.docente = p.cedula "
								   + " AND ma.estudiante = " + cedula
								   + " AND ma.semestre = "+semestre);
			rs = ps.executeQuery();
			while(rs.next()){
				profesores.add(new Profesor(rs.getString("nombre")+" "+rs.getString("apellidos"),
						rs.getString("cedula"),
						rs.getString("codigo"),
						rs.getString("nombreMateria"),
						rs.getString("numero")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return profesores;
	}
}
