package com.troyage.util;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class for parsor utility
 * 
 * @author Kalpit Seksaria
 * 
 */
public class ParsorUtility {
	public <T> ArrayList<T> parseJSONArray(String jsonString, Type typeOfDest) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		ArrayList<T> list = gson.fromJson(jsonString, typeOfDest);
		return list;
	}

	public <T> T parseJSONObject(String jsonString, Type typeOfDest) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		T mClass = gson.fromJson(jsonString, typeOfDest);
		return mClass;
	}
}
