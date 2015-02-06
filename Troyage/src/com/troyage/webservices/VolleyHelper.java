package com.troyage.webservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyHelper {

	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;
	private static LruCache<String, Bitmap> mCache;

	public VolleyHelper(Context context) {

		mRequestQueue = Volley.newRequestQueue(context);
		mCache = new LruCache<String, Bitmap>(25);

		mImageLoader = new ImageLoader(VolleyHelper.getRequestQueue(),
				new ImageLoader.ImageCache() {
					public void putBitmap(String url, Bitmap bitmap) {
						mCache.put(url, bitmap);
					}

					public Bitmap getBitmap(String url) {
						return mCache.get(url);
					}
				});
	}

	public static RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public static ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public static LruCache<String, Bitmap> getCache() {
		return mCache;
	}
}