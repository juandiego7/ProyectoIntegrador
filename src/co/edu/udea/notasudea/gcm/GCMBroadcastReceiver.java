package co.edu.udea.notasudea.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Clase encargada de recibir las notificaciones que sean enviadas
 * a la aplicacion.
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 4/11/2013
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ComponentName comp = new ComponentName(context.getPackageName(),
				GCMIntentService.class.getName());

		startWakefulService(context, (intent.setComponent(comp)));

		setResultCode(Activity.RESULT_OK);
	}

}