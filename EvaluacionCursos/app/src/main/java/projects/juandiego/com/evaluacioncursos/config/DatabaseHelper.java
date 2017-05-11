package projects.juandiego.com.evaluacioncursos.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import projects.juandiego.com.evaluacioncursos.MainActivity;
import projects.juandiego.com.evaluacioncursos.models.Question;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper instance;

    private static final String DATABASE_NAME = "Notas.db";
    private static final int DATABASE_VERSION = 1;

    public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = OpenHelperManager.getHelper(MainActivity.getContext(), DatabaseHelper.class);
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        instance = this;

        try {
            TableUtils.createTableIfNotExists(connectionSource, Question.class);
            Log.d("onHand", "Tabla creada");
            //TableUtils.createTableIfNotExists(connectionSource, Materia.class);
            //TableUtils.createTableIfNotExists(connectionSource, Programa.class);
            //TableUtils.createTableIfNotExists(connectionSource, Semestre.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        instance = this;
        try {
            switch (oldVersion) {
                case 1: {
                    //db.execSQL("ALTER TABLE user ADD COLUMN contact INTEGER;");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
        System.gc();
    }
}