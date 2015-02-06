package com.troyage.webservices;

import com.android.volley.VolleyError;

public interface WebServiceImpl {

	public void onSuccess(String response);

	public void onFailure(VolleyError error);

	public void serverError();

	public void notConnectedToInternet();

	public void onProgressCancelled();
}