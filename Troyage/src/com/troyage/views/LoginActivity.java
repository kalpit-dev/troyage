package com.troyage.views;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.troyage.R;
import com.troyage.R.layout;

public class LoginActivity extends FragmentActivity {
	private static final int FACEBOOK_LOGIN_CODE = 101;
	public static final List<String> PERMISSIONS_PUBLISH = Arrays.asList("public_profile", "email");
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private String TAG = "Main";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_activity);
		// setSession(savedInstanceState);
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session;

		try {
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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.troyage", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// startActivity(new Intent(LoginActivity.this, LoginActivity.class));
		finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	// private void setSession(Bundle savedInstanceState) {
	// Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
	// Log.e(TAG, "OnCreate setSession called");
	// session = Session.getActiveSession();
	// if (session == null) {
	// Log.e(TAG, "OnCreate Session Null");
	// if (savedInstanceState != null) {
	// Log.e(TAG, "OnCreate Restoring session");
	// session = Session.restoreSession(this, null, statusCallback,
	// savedInstanceState);
	// }
	// if (session == null) {
	// Log.e(TAG, "OnCreate Open new session - could not restore");
	// session = new Session(this);
	// }
	// Session.setActiveSession(session);
	// if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	// Log.e(TAG, "OnCreate Open for read");
	// session.openForRead(new
	// Session.OpenRequest(this).setCallback(statusCallback));
	// }
	// }
	// }

	// private class SessionStatusCallback implements Session.StatusCallback {
	// @Override
	// public void call(Session session, SessionState state, Exception
	// exception) {
	// onSessionStateChange(session, state, exception);
	// if (session.isOpened() && isClickFbbtn) {
	// Log.e(TAG, "Session status callback");
	// getProfileInformation();
	// }
	// }
	// }

	// private void onFacebookLogin() {
	// Log.e(TAG, "Facebook Login clicked");
	// // session = Session.getActiveSession();
	// // if (!session.isOpened() && !session.isClosed()) {
	// // Log.e(TAG, "Session open for publish");
	// // session.openForPublish(new
	// //
	// Session.OpenRequest(this).setCallback(statusCallback).setPermissions(PERMISSIONS_PUBLISH));
	// //
	// // } else {
	// // Log.e(TAG, "Session open Active session");
	// // Session.openActiveSession(this, true, statusCallback);
	// // }
	// getProfileInformation();
	//
	// }

	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	// public void getProfileInformation() {
	// openActiveSession(this, true, new Session.StatusCallback() {
	// @Override
	// public void call(Session session, SessionState state, Exception
	// exception) {
	// if (session.isOpened() && isClickFbbtn) {
	// // make request to the /me API
	// Log.e("sessionopened", "true");
	// Request.newMeRequest(session, new Request.GraphUserCallback() {
	// @Override
	// public void onCompleted(GraphUser user, Response response) {
	// Log.e(TAG, "Print Login");
	// if (user != null) {
	// String strFacebookEmailId = (String) user.getProperty("email");
	// Log.e(TAG, "FB email id : " + strFacebookEmailId);
	// Log.e(TAG, "facebook id" + user.getId());
	// try {
	// // email = (String)
	// // user.getProperty("email");
	// // nickName = user.getName();
	// // profileUrl = url.toString();
	// // if
	// // (WebService.isNetworkAvailable(LoginActivity.this))
	// // {
	// // new FbLoginTask(LoginActivity.this,
	// // user.getId()).execute();
	// // }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }).executeAsync();
	// }
	// }
	// }, PERMISSIONS_PUBLISH);
	// }

	// private static Session openActiveSession(Activity activity, boolean
	// allowLoginUI, Session.StatusCallback callback, List<String> permissions)
	// {
	// Session.OpenRequest openRequest = new
	// Session.OpenRequest(activity).setPermissions(permissions).setCallback(callback);
	// Session session = new Session.Builder(activity).build();
	// if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) ||
	// allowLoginUI) {
	// Session.setActiveSession(session);
	// session.openForRead(openRequest);
	// return session;
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// session = Session.getActiveSession();
	// Session.saveSession(session, outState);
	// }

	// @Override
	// protected void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// Session.getActiveSession().addCallback(statusCallback);
	//
	// }
	//
	// @Override
	// protected void onStop() {
	// // TODO Auto-generated method stub
	// super.onStop();
	// Session.getActiveSession().removeCallback(statusCallback);
	// }

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// super.onActivityResult(requestCode, resultCode, data);
	// Session.getActiveSession().onActivityResult(this, requestCode,
	// resultCode, data);
	// }
	//
	// private void onSessionStateChange(Session session, SessionState state,
	// Exception exception) {
	// if (exception instanceof FacebookOperationCanceledException || exception
	// instanceof FacebookAuthorizationException) {
	// // Cancelled by user, show alert
	// // new
	// //
	// AlertDialog.Builder(this).setTitle(R.string.cancelled).setMessage(R.string.permission_not_granted).setPositiveButton(R.string.ok,
	// // null).show();
	// }
	// }
	//
	// private void onClickFB() {
	//
	// if (session != null && session.isOpened()) {
	// getFBUserData(session);
	// } else {
	// Session.openActiveSession(this, true, this);
	// }
	// }
	//
	// public void getFBUserData(Session session) {
	//
	// if (session.isOpened()) {
	// Request.newMeRequest(session, new Request.GraphUserCallback() {
	// @Override
	// public void onCompleted(GraphUser user, Response response) {
	// Log.e(TAG, "Print Login");
	// if (user != null) {
	// String strFacebookEmailId = (String) user.getProperty("email");
	// Log.e(TAG, "FB email id : " + strFacebookEmailId);
	// Log.e(TAG, "facebook id" + user.getId());
	// try {
	// // email = (String)
	// // user.getProperty("email");
	// // nickName = user.getName();
	// // profileUrl = url.toString();
	// // if
	// // (WebService.isNetworkAvailable(LoginActivity.this))
	// // {
	// // new FbLoginTask(LoginActivity.this,
	// // user.getId()).execute();
	// // }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }).executeAsync();
	// }
	// }
	//
	// @Override
	// public void call(Session session, SessionState state, Exception
	// exception) {
	// if (session.isOpened()) {
	// this.session = session;
	// getFBUserData(session);
	//
	// }
	// }
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// Log.v("Session Permisison", ":" + hasPublishPermission());
			// Log.v("Session New", ":" + state);
			if (session != null && session.isOpened()) {
				getFBUserData(session);
			}
		}
	}

	public void getFBUserData(Session session) {
		// Log.e("GET FB USER DATA", "fb data");
		if (session.isOpened()) {
			// Request.executeMeRequestAsync(session, new
			// Request.GraphUserCallback() {
			// @Override
			// public void onCompleted(GraphUser user, com.facebook.Response
			// response) {
			//
			// if (user != null) {
			// if (user.asMap().get("email") != null) {
			// // fbEmailId = user.asMap().get("email").toString();
			// // Log.e("EMAIL ", fbEmailId);
			//
			// String fbId = user.getId();
			// // String strForHash =
			// //
			// fbEmailId.concat(getResources().getString(R.string.tuisy_sha_code)).concat(fbEmailId);
			// // SAVE FB GRAPH API DATA TO MODEL
			//
			// } else {
			// // Util.displayDialogNormalMessage(getString(R.string.app_name),
			// // getString(R.string.not_registered_with_fb),
			// // MainActivity.this);
			// }
			//
			// }
			// }
			// });
			Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					Log.e(TAG, "Print Login");
					closeFB();
					if (user != null) {
						try {
							String strFacebookEmailId = (String) user.getProperty("email");
							Log.e(TAG, "FB email id : " + strFacebookEmailId);
							Log.e(TAG, "FB name : " + user.getName());
							Log.e(TAG, "facebook id" + user.getId());
							// new CallWSFBLogin(LoginActivity.this,
							// user.getId(), strFacebookEmailId,
							// user.getName()).execute();
							// email = (String)
							// user.getProperty("email");
							// nickName = user.getName();
							// profileUrl = url.toString();
							// if
							// (WebService.isNetworkAvailable(LoginActivity.this))
							// {
							// new FbLoginTask(LoginActivity.this,
							// user.getId()).execute();
							// }
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).executeAsync();
		}
	}

	public void openFaceBook() {
		Session session;
		try {
			session = Session.getActiveSession();
			if (session != null) {
				session.closeAndClearTokenInformation();
				session = null;
			}
			if (session == null) {

				if (session == null) {
					session = new Session(this);
				}
				Session.setActiveSession(session);
				if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				}
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage().toString());
		}

		session = Session.getActiveSession();
		try {
			if (!session.isOpened()) {
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback).setPermissions(PERMISSIONS_PUBLISH)
						.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO));
			} else {
				Session.openActiveSession(this, true, statusCallback);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage().toString());
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);

	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == FACEBOOK_LOGIN_CODE) {
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	public void closeFB() {
		Session session;
		try {
			session = Session.getActiveSession();
			session.closeAndClearTokenInformation();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}