package projects.juandiego.com.evaluacioncursos.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import projects.juandiego.com.evaluacioncursos.EvaluarActivity;
import projects.juandiego.com.evaluacioncursos.MainActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context contegit xt, Intent intent) {
        Log.d("onHandleIntent", "Recibido");
        Toast.makeText(MainActivity.getContext(), "Recibido "+intent.getAction(), Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(MainActivity.getContext(), EvaluarActivity.class);
    }
}
