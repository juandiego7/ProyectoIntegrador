package co.edu.udea.mievaluacion.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
	
	private static DataSource dataSource;
	
	private DataSource(){
		
	}
	
	public static DataSource getInstance(){
		if(dataSource == null){
			dataSource=new DataSource();
		}
		return dataSource;
	}
	
	public Connection getConnection() {
		Connection con = null;// para la conexion con la base de datos

		try {
			Class.forName("com.mysql.jdbc.Driver");//Cargar driver de mysql
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/evaluacion","root","root");			

		} catch (ClassNotFoundException e) {//No existe el driver de mysql
			e.printStackTrace();
		} catch (SQLException e) {//No existe la BD
			e.printStackTrace();
		}
		return con;
	}
}