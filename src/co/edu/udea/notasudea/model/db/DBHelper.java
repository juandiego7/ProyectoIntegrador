package co.edu.udea.notasudea.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{
	
	/**
	 * Version de la base de datos
	 */
	private static final int DB_VERSION = 2;
	/**
	 * Nombre de la Base de datos
	 */
	private static final String DB_NAME = "DBNotas";
	/**
	 * Nombres de las tablas de la Base de datos.
	 */
	private static final String TABLE_MATERIA = "Materia";
	private static final String TABLE_ACTIVIDAD = "Actividad";	
	private static final String TABLE_PROGRAMA = "Programa";
	private static final String TABLE_SEMESTRE = "Semestre";		

	private static final String CREATE_TABLE_MATERIA = "CREATE TABLE "+ TABLE_MATERIA+ " (" +
		"codigo TEXT," +
		"nombre TEXT, " +
		"creditos INTEGER, " +
		"nota TEXT) ";

	
	private static final String CREATE_TABLE_ACTIVIDAD = "CREATE TABLE " +TABLE_ACTIVIDAD + " (" +
		"numeroEvaluacion TEXT," +
		"descripcionEvaluacion TEXT," +
		"nota TEXT," +
		"porcentaje TEXT," +
		"tipoNota TEXT, " +
		"materia TEXT," +
		"FOREIGN KEY(materia) REFERENCES Materia(Codigo))";
	
	private static final String CREATE_TABLE_PROGRAMA = "CREATE TABLE " + TABLE_PROGRAMA + " (" +
			"codigo TEXT," +
			"nombre TEXT)";
		
	private static final String CREATE_TABLE_SEMESTRE = "CREATE TABLE " + TABLE_SEMESTRE+ " (" +
			"codigo TEXT," +
			"promedio TEXT, " +
			"programa TEXT, " +
			"FOREIGN KEY(programa) REFERENCES Programa(Codigo))";
	
	public DBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);		
	}

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MATERIA);	
		db.execSQL(CREATE_TABLE_ACTIVIDAD);	
		db.execSQL(CREATE_TABLE_PROGRAMA);	
		db.execSQL(CREATE_TABLE_SEMESTRE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVIDAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEMESTRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMA);
        onCreate(db);
	}

}