package co.edu.udea.notasudea.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Conexion {

	private static DBHelper dbHelper;
	private static SQLiteDatabase db;
	
	public static SQLiteDatabase getConection(Context context){
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();	
		return db;
	}
	
	public static void closeConection(){
		db.close();
	}
	
}