package projects.juandiego.com.patinaje;

import android.provider.BaseColumns;

/**
 * Created by rantonio.martinez on 26/08/16.
 */
public class Database {
    public static final String DB_NAME = "patinajeBaseDeDatos.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USER = "user";
    public static final String TABLE_PAYMENT = "payment";

    public class Column_User {
        public static final String ID =  BaseColumns._ID;
        public static final String DOCUMENT = "document";
        public static final String NAME = "name";
        public static final String LAST_NAME = "last_name";
        public static final String DATE_START = "date_start";
        public static final String BIRTHDAY = "birthday";
        public static final String STATUS = "status";
    }

    public class Column_Payment {
        public static final String COD =  BaseColumns._ID;
        public static final String DOCUMENT = "document";
        public static final String DATE_PAYMENT = "date_payment";
        public static final String QUANTITY = "quantity";
    }
}
