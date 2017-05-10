package projects.juandiego.com.evaluacioncursos;

import android.app.Application;
import android.content.Context;

/**
 * Created by rantonio.martinez on 10/05/17.
 */

public class Notas extends Application {
    private static Notas instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    public static Notas getContext() {
        return instance;
    }
}
