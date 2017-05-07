package projects.juandiego.com.patinaje;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rantonio.martinez on 26/08/16.
 */
public class BdHelper extends SQLiteOpenHelper {

    public BdHelper(Context context) {
        super(context, Database.DB_NAME, null, Database.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTablaUser = String.format("create table %s(%s integer primary key autoincrement, " +
                                                               "%s text not null unique," +
                                                               "%s text not null," +
                                                               "%s text," +
                                                               "%s text," +
                                                               "%s text not null," +
                                                               "%s text);",
                                                               Database.TABLE_USER,
                                                               Database.Column_User.ID,
                                                               Database.Column_User.DOCUMENT,
                                                               Database.Column_User.NAME,
                                                               Database.Column_User.LAST_NAME,
                                                               Database.Column_User.DATE_START,
                                                               Database.Column_User.BIRTHDAY,
                                                               Database.Column_User.STATUS);
        db.execSQL(crearTablaUser);

        String crearTablaPlace = String.format("create table %s(%s integer primary key autoincrement, " +
                                                                "%s text not null," +
                                                                "%s text not null," +
                                                                "%s text," +
                                                                "unique(%s,%s));",
                                                                Database.TABLE_PAYMENT,
                                                                Database.Column_Payment.COD,
                                                                Database.Column_Payment.DOCUMENT,
                                                                Database.Column_Payment.DATE_PAYMENT,
                                                                Database.Column_Payment.QUANTITY,
                                                                Database.Column_Payment.DOCUMENT,
                                                                Database.Column_Payment.DATE_PAYMENT);
        db.execSQL(crearTablaPlace);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Tag", "OnUpgrade");
    }
}
