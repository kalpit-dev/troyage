package com.troyage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.troyage.webservices.VolleyHelper;

public class TroyageApplication extends Application {
	private SharedPreferences preferences;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		new VolleyHelper(this);
	}

	/**
	 * 
	 * create instance of SharedPreferences
	 * 
	 * @return SharedPreferences
	 */
	public SharedPreferences getSharedPrefrence() {
		if (preferences == null) {
			preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
		}
		return preferences;
	}

}
