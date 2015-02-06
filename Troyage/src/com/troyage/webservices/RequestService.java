package com.troyage.webservices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.troyage.R;
import com.troyage.views.CustomProgressDialog;

public class RequestService {
	public static boolean progressVisible = true;

	public static final void callWebService(final Context context, int method,
			String url, final Map<String, String> params,
			final HttpEntity entity, final WebServiceImpl webServiceImpl) {

		if (checkInternetConnection(context)) {

			final CustomProgressDialog dialog = new CustomProgressDialog(
					context, android.R.style.TextAppearance_Holo);
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			if (progressVisible) {
				dialog.show();
			}
			final StringRequest sr = new StringRequest(method, url,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							if (dialog != null) {
								dialog.dismiss();
							}

							if (!TextUtils.isEmpty(response)
									&& !response.equalsIgnoreCase("null")) {
								// Util.e(context, "Response : " +
								// response.toString());
								webServiceImpl.onSuccess(response);
							} else {
								displayPrompt(
										context,
										context.getString(R.string.encounter_server_error),
										context.getString(android.R.string.ok),
										webServiceImpl);
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							if (dialog != null) {
								dialog.dismiss();
							}
							displayPrompt(
									context,
									context.getString(R.string.unable_to_connect_to_server),
									context.getString(android.R.string.ok),
									error, webServiceImpl);
						}
					}) {

				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					if (params != null) {
						return params;
					}
					return super.getParams();
				}

				@Override
				public byte[] getBody() throws AuthFailureError {

					if (entity != null) {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						try {
							entity.writeTo(bos);
						} catch (IOException e) {
							VolleyLog
									.e("IOException writing to ByteArrayOutputStream");
						}
						return bos.toByteArray();
					}

					return super.getBody();
				}

				@Override
				public String getBodyContentType() {
					if (entity != null) {
						return entity.getContentType().getValue();
					}
					return super.getBodyContentType();

				}

			};

			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (dialog != null) {
						dialog.dismiss();
					}
					webServiceImpl.onProgressCancelled();
					if (sr != null) {
						sr.cancel();
					}
				}
			});
			sr.setRetryPolicy(new DefaultRetryPolicy(9999999,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			VolleyHelper.getRequestQueue().add(sr);

		} else {
			displayPrompt(context,
					context.getString(R.string.please_connect_to_internet),
					context.getString(android.R.string.ok), null,
					webServiceImpl);
		}
	}

	/**
	 * This Method will check either a network is connected or not
	 * 
	 * @param context
	 *            Reference of Activity
	 * @return
	 * @return true if Internet Connection is available otherwise false
	 */

	public static boolean checkInternetConnection(final Context context) {
		if (context != null) {
			final ConnectivityManager mgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (mgr != null) {

				final NetworkInfo mobileInfo = mgr
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				final NetworkInfo wifiInfo = mgr
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if (wifiInfo != null && wifiInfo.isAvailable()
						&& wifiInfo.isAvailable() && wifiInfo.isConnected()) {

					final WifiManager wifiManager = (WifiManager) context
							.getSystemService(Context.WIFI_SERVICE);
					final WifiInfo wifiInfoStatus = wifiManager
							.getConnectionInfo();
					final SupplicantState supState = wifiInfoStatus
							.getSupplicantState();

					if (String.valueOf(supState).equalsIgnoreCase("COMPLETED")
							|| String.valueOf(supState).equalsIgnoreCase(
									"ASSOCIATED")) {
						// WiFi is connected
						return true;
					}
				}

				if (mobileInfo != null && mobileInfo.isAvailable()
						&& mobileInfo.isConnected()) {
					// Mobile Network is connected
					return true;
				}
			}
		}
		return false;
	}

	public static void displayPrompt(final Context context, String msg,
			String positiveMsg, final VolleyError error,
			final WebServiceImpl webServiceImpl) {

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.app_name));
		alertDialog.setCancelable(false);
		alertDialog.setMessage(msg);
		alertDialog.setPositiveButton(positiveMsg,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (error == null) {
							webServiceImpl.notConnectedToInternet();
						} else {
							webServiceImpl.onFailure(error);
						}
					}
				});
		alertDialog.create().show();
	}

	public static void displayPrompt(final Context context, String msg,
			String positiveMsg, final WebServiceImpl webServiceImpl) {

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.app_name));
		alertDialog.setCancelable(false);
		alertDialog.setMessage(msg);
		alertDialog.setPositiveButton(positiveMsg,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						webServiceImpl.serverError();
					}
				});
		alertDialog.create().show();
	}

}
