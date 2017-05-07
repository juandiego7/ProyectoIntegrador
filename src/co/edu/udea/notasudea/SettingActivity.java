package co.edu.udea.notasudea;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Se agregan las preferencias
		addPreferencesFromResource(R.xml.pref_notification);
		
	}
	
}