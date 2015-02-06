package com.troyage.views;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.troyage.R;
import com.troyage.TroyageApplication;
import com.troyage.util.ParsorUtility;
import com.troyage.util.Util;
import com.troyage.webservices.WebService;
import com.troyage.webservices.WebServiceImpl;

public class FBActivity extends Activity {

	private static final List<String> PERMISSIONS_PUBLISH = Arrays.asList("public_profile,email");
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private Session session;
	public com.facebook.widget.LoginButton loginButton;
	public Boolean isClickFbbtn = false;
	private Bundle savedInstanceState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_activity);
		this.savedInstanceState = savedInstanceState;
		setSession(savedInstanceState);
		generateKeyHash();
		loginButton = (com.facebook.widget.LoginButton) findViewById(R.id.btnFbLogin);
		if (loginButton.getText().toString().contains("Login")) {
			isClickFbbtn = true;
		}

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onFacebookLogin();
			}
		});

	}

	private void generateKeyHash() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.troyage", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	private void setSession(Bundle savedInstanceState) {
		try {
			Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
			session = Session.getActiveSession();
			if (session == null) {
				if (savedInstanceState != null) {
					session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
				}
				if (session == null) {
					session = new Session(this);
				}
				Session.setActiveSession(session);
				if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
					session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
				}

				setSession(session);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onFacebookLogin() {
		session = Session.getActiveSession();
		if (loginButton.getText().toString().contains("Login")) {
			isClickFbbtn = true;
			if (session != null && !session.isOpened() && !session.isClosed()) {
				session.openForPublish(new Session.OpenRequest(this).setCallback(statusCallback).setPermissions(PERMISSIONS_PUBLISH));
			} else {
				// if (session.isClosed()) {
				Session.openActiveSession(this, true, statusCallback);
				// }
			}
		} else {
			isClickFbbtn = false;
			onClickLogout();
		}
	}

	public void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	// Facebook

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(final Session session, SessionState state, Exception exception) {
			if (session != null && session.isOpened()) {
				if (isClickFbbtn) {
					getProfileInformation(session);
				}
			} else if (session.isClosed()) {
				// finish();
			}
		}
	}

	private static Session openActiveSession(Activity activity, boolean allowLoginUI, Session.StatusCallback callback, List<String> permissions) {
		Session.OpenRequest openRequest = new Session.OpenRequest(activity).setPermissions(permissions).setCallback(callback);
		Session session = new Session.Builder(activity).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowLoginUI) {
			Session.setActiveSession(session);
			session.openForRead(openRequest);
			return session;
		}
		return null;
	}

	public void getProfileInformation(Session session) {
		Log.e("getProfileInformation", "getProfileInformation");

		if (session.isOpened()) {
			Log.e("sessionopened", "true");
			Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						String strFacebookEmailId = (String) user.getProperty("email");
						strFacebookEmailId = "kalpit@gmail.com";
						TroyageApplication application = (TroyageApplication) getApplicationContext();
						if (!TextUtils.isEmpty(strFacebookEmailId) && !(TextUtils.isEmpty(user.getId()))) {
							if (application.getSharedPrefrence().getString(getString(R.string.shared_key_fb_email), "").equals(strFacebookEmailId)) {
								userLogin(FBActivity.this, strFacebookEmailId, user.getId().substring(0, 8));
							} else {
								application.getSharedPrefrence().edit().putString(getString(R.string.shared_key_fb_email), strFacebookEmailId).commit();
								application.getSharedPrefrence().edit().putString(getString(R.string.shared_key_fb_id), user.getId()).commit();
								application.getSharedPrefrence().edit().putString(getString(R.string.shared_key_password), user.getId().substring(0, 8)).commit();
								userSignUp(FBActivity.this, strFacebookEmailId, user.getId().substring(0, 8));
							}
						}
					}
				}
			}).executeAsync();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	public void setFbLogin(final boolean isLogin) {
		isClickFbbtn = isLogin;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void facebookLogout() {
		final Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	/**
	 * 
	 * Method used for sending web service request to get Barcode result
	 * 
	 */
	private void userSignUp(final Context context, String email, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put(getString(R.string.USER_FIRST_NAME), "Test");
		params.put(getString(R.string.USER_LAST_NAME), "Test");
		params.put(getString(R.string.USER_EMAIL), email);
		params.put(getString(R.string.USER_AUTH_TOKEN), password.substring(0, 8));
		params.put(getString(R.string.USER_TYPE), "0");
		params.put(getString(R.string.USER_TIMEZONE), "America/Los_Angeles");
		WebService.callWebService(context, com.android.volley.Request.Method.POST, null, getString(R.string.base_url) + getString(R.string.sign_up), params, new WebServiceImpl() {

			@Override
			public void onSuccess(String response) {
				parseJSON(response, context);
				// tvNodata.setVisibility(View.GONE);
			}

			@Override
			public void onFailure(VolleyError error) {
				Util.displayDialog(getString(R.string.unable_to_connect_to_server), context);

			}

			@Override
			public void serverError() {
				Util.displayDialog(getString(R.string.encounter_server_error), context);

			}

			@Override
			public void notConnectedToInternet() {
				Util.displayDialog(getString(R.string.please_connect_to_internet), context);

			}

			@Override
			public void onProgressCancelled() {

			}

		});

	}

	private void parseJSON(String response, Context context) {

		try {
			JSONObject jsMain = new JSONObject(response);
			String status = jsMain.getString(getString(R.string.key_code));
			String message = jsMain.getString(getString(R.string.key_msg));
			if (status.equals("2000")) {
				Intent intentToDetails = new Intent(FBActivity.this, YourInterestActivity.class);
				startActivity(intentToDetails);
				finish();
			} else {
				Util.displayDialog(message, context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.displayDialog(getString(R.string.some_error), context);
		}
	}

	/**
	 * 
	 * Method used for sending web service request to get Barcode result
	 * 
	 */
	private void userLogin(final Context context, String email, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put(getString(R.string.USERNMAE), email);
		params.put(getString(R.string.PASSWORD), password);
		WebService.callWebService(context, com.android.volley.Request.Method.POST, null, getString(R.string.base_url) + getString(R.string.login), params, new WebServiceImpl() {

			@Override
			public void onSuccess(String response) {
				parseJSONLogin(response, context);
				// tvNodata.setVisibility(View.GONE);
			}

			@Override
			public void onFailure(VolleyError error) {
				Util.displayDialog(getString(R.string.unable_to_connect_to_server), context);

			}

			@Override
			public void serverError() {
				Util.displayDialog(getString(R.string.encounter_server_error), context);

			}

			@Override
			public void notConnectedToInternet() {
				Util.displayDialog(getString(R.string.please_connect_to_internet), context);

			}

			@Override
			public void onProgressCancelled() {

			}

		});

	}

	private void parseJSONLogin(String response, Context context) {

		try {
			JSONObject jsMain = new JSONObject(response);
			String status = jsMain.getString(getString(R.string.key_code));
			String message = jsMain.getString(getString(R.string.key_msg));
			if (status.equals("2001")) {
				Intent intentToDetails = new Intent(FBActivity.this, YourInterestActivity.class);
				startActivity(intentToDetails);
				finish();
			} else {
				Util.displayDialog(message, context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.displayDialog(getString(R.string.some_error), context);
		}
	}
}
