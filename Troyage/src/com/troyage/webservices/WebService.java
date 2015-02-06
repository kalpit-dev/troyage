package com.troyage.webservices;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.troyage.R;
import com.troyage.util.Util;

public class WebService {
	/**
	 * This is used to make WS call
	 * 
	 * @param isRefresh
	 * @param listView
	 * 
	 */
	public static void callWebService(final Context context, int method, JSONObject request, String url, final Map<String, String> params, final WebServiceImpl webServiceImpl) {

		Util.e("WebService Url : " + url);
		if (Util.isNetworkAvailable(context)) {
			final ProgressDialog dialog = new ProgressDialog(context);
			dialog.setMessage(context.getString(R.string.loading));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
			final StringRequest sr = new StringRequest(method, url, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					if (dialog != null) {
						dialog.dismiss();
					}

					if (!TextUtils.isEmpty(response) && !response.equalsIgnoreCase("null")) {
						Util.e("Response : " + response.toString());
						// if (tvNodata != null) {
						// tvNodata.setVisibility(View.GONE);
						// }
						webServiceImpl.onSuccess(response);
					} else {
						Util.e("Response Null");
						// if (tvNodata != null) {
						// tvNodata.setVisibility(View.VISIBLE);
						// }
						webServiceImpl.serverError();
						// Util.displayDialog(context.getString(R.string.encounter_server_error),
						// context);
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					if (dialog != null) {
						dialog.dismiss();
					}
					Util.e("Error");
					webServiceImpl.onFailure(error);
					// if (tvNodata != null) {
					// tvNodata.setVisibility(View.VISIBLE);
					// }
					// Util.displayDialog(context.getString(R.string.unable_to_connect_to_server),
					// context);

				}
			}) {
				@Override
				protected Map<String, String> getParams() {
					return params;
				}

				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String, String> params = new HashMap<String, String>();
					params.put("Content-Type", "application/x-www-form-urlencoded");
					return params;
				}
			};
			if (dialog != null) {
				dialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// if (tvNodata != null) {
						// tvNodata.setVisibility(View.VISIBLE);
						// }

						if (dialog != null) {
							dialog.dismiss();
						}
						webServiceImpl.onProgressCancelled();
						if (sr != null) {
							sr.cancel();
						}
					}
				});
			}

			sr.setRetryPolicy(new DefaultRetryPolicy(9999999, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			VolleyHelper.getRequestQueue().add(sr);
		} else {
			// Util.displayDialog(context.getString(R.string.please_connect_to_internet),
			// context);
			// if (tvNodata != null) {
			// tvNodata.setVisibility(View.VISIBLE);
			// }
			webServiceImpl.notConnectedToInternet();
		}
	}
}
