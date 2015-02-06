package com.troyage.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.troyage.R;

/**
 * Class used to utility methods
 * 
 * @author Kalpit Seksaria
 * 
 */
public class Util {
	private static final String TAG = "CitiFlite";
	public static final String SERVER_BASE_URL = "http://ws.citiflite.com/fuderush/";
	// public static final String SERVER_BASE_URL =
	// "http://php54.indianic.com/fuderush/";

	public static final String USER_IMAGE_BASE_URL = SERVER_BASE_URL + "public/uploads/user/";
	public static final String RESTAURANT_IMAGE_BASE_URL = SERVER_BASE_URL + "public/uploads/restaurant/";

	/**
	 * Get Aural font from assets
	 */
	public static Typeface getAuralFont(Context context) {
		String otfName = "AvenirNextLTPro-Demi.otf";
		Typeface font = Typeface.createFromAsset(context.getAssets(), otfName);
		return font;
	}

	/**
	 * Get Regular font from assets
	 */
	public static Typeface getRegularFont(Context context) {
		String otfName = "AvenirNextLTPro-Regular.otf";
		Typeface font = Typeface.createFromAsset(context.getAssets(), otfName);
		return font;
	}

	/**
	 * Get SemiBold font from assets
	 */
	public static Typeface getSemiBoldFont(Context context) {
		String otfName = "AvenirNextLTPro-Demi.otf";
		Typeface font = Typeface.createFromAsset(context.getAssets(), otfName);
		return font;
	}

	/**
	 * Email validation
	 */
	public static boolean isEmailValid(String email) {
		boolean isValid = false;
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * To check network is available or not
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			final ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (mgr != null) {

				final NetworkInfo mobileInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				final NetworkInfo wifiInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if (wifiInfo != null && wifiInfo.isAvailable() && wifiInfo.isAvailable() && wifiInfo.isConnected()) {

					final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
					final WifiInfo wifiInfoStatus = wifiManager.getConnectionInfo();
					final SupplicantState supState = wifiInfoStatus.getSupplicantState();

					if (String.valueOf(supState).equalsIgnoreCase("COMPLETED") || String.valueOf(supState).equalsIgnoreCase("ASSOCIATED")) {
						// WiFi is connected
						return true;
					}
				}

				if (mobileInfo != null && mobileInfo.isAvailable() && mobileInfo.isConnected()) {
					// Mobile Network is connected
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * To display a dialog with title as app name
	 */
	// public static void displayDialog(String msg, final Context context) {
	//
	// displayDialog(context.getString(R.string.app_name), msg, context);
	// }

	/**
	 * To display a dialog with title
	 */
	public static void displayDialog(String msg, final Context context) {

		final AlertDialog.Builder alerDialog = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Dialog_MinWidth);
		alerDialog.setTitle(context.getString(R.string.app_name));
		alerDialog.setMessage(msg);
		alerDialog.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		alerDialog.show();

	}

	/**
	 * To get data stored in shared preference
	 */
	public static String getString(Context c, String key) {
		String data;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
		data = preferences.getString(key, "");
		return data;
	}

	/**
	 * To get array stored in shared preference
	 */
	// public static ArrayList<ShiftDetailModel> getArray(Context c, String key)
	// {
	// SharedPreferences preferences =
	// PreferenceManager.getDefaultSharedPreferences(c);
	// Gson gson = new Gson();
	// String json = preferences.getString(key, "");
	// Type type = new TypeToken<ArrayList<ShiftDetailModel>>() {
	// }.getType();
	// ArrayList<ShiftDetailModel> arrayList = gson.fromJson(json, type);
	// return arrayList;
	// }

	/**
	 * To print log
	 */
	public static void e(String msg) {
		Log.e(TAG, msg);
	}

	public static void w(String msg) {
		Log.w(TAG, msg);
	}

	public static void i(String msg) {
		Log.i(TAG, msg);
	}

	public static void e(final Fragment fragment, String msg) {
		Log.e(fragment.getClass().getSimpleName(), msg);
	}

	public static void e(Context fragment, String msg) {
		Log.e(fragment.getClass().getSimpleName(), msg);
	}

	/**
	 * To store string data into shared preference
	 */
	public static void storeString(Context c, String key, String value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * To store a array to shared preference
	 */
	// public static void storeArray(Context c, String key,
	// ArrayList<ShiftDetailModel> arrayList) {
	// SharedPreferences preferences =
	// PreferenceManager.getDefaultSharedPreferences(c);
	// SharedPreferences.Editor editor = preferences.edit();
	// Gson gson = new Gson();
	// String json = gson.toJson(arrayList);
	// editor.putString(key, json);
	// editor.commit();
	// }

	/**
	 * To print check location provider
	 */
	public static boolean checkLocationProvider(final Context context) {
		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

			final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle(context.getString(R.string.app_name));
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setMessage("Enable GPS for accurate data");
			alertDialogBuilder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					((Activity) context).startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

			return false;
		} else {
			return true;
		}
	}

	/**
	 * Creates dialog to choose images from either gallery or camera.
	 * 
	 * @param context
	 *            : context to pass to the pickers.
	 * @param imagePickerInterface
	 *            : {@link ImagePickerInterface} to handle user selection.
	 *            Activities or Fragments calling this method must implement
	 *            this interface
	 */
	// public static void createImageChooser(final Context context, final
	// ImagePickerInterface imagePickerInterface) {
	// List<String> list =
	// Arrays.asList(context.getResources().getStringArray(R.array.image_chooser));
	// CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
	//
	// final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
	// dialog.setTitle(context.getString(R.string.img_capture));
	// dialog.setSingleChoiceItems(cs, -1, new DialogInterface.OnClickListener()
	// {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// if (which == 0) {
	// if (imagePickerInterface != null)
	// imagePickerInterface.handleCameraPicker(generateCameraPickerIntent(context));
	// } else {
	// if (imagePickerInterface != null)
	// imagePickerInterface.handleGalleryPicker(generateGalleryPickerIntent(context));
	// }
	// }
	// });
	//
	// final AlertDialog alertDialog = dialog.create();
	// if (!alertDialog.isShowing()) {
	// alertDialog.show();
	// }
	// }

	/**
	 * Generate Intent to open camera
	 * 
	 * @param context
	 *            : {@link Context} to create intent
	 * @return : {@link Intent} to open camera.
	 */
	public static Intent generateCameraPickerIntent(Context context) {
		File root = null;
		if (Util.sdCardMounted()) {
			root = new File(Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.app_name));

			if (!root.exists()) {
				root.mkdirs();
			}
		}

		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		return intent;
	}

	/**
	 * To find if sd card is mounted or not
	 */
	public static boolean sdCardMounted() {
		boolean isMediaMounted = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			isMediaMounted = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			isMediaMounted = false;
		} else if (Environment.MEDIA_CHECKING.equals(state)) {
			isMediaMounted = false;
		} else if (Environment.MEDIA_NOFS.equals(state)) {
			isMediaMounted = false;
		} else if (Environment.MEDIA_REMOVED.equals(state)) {
			isMediaMounted = false;
		} else if (Environment.MEDIA_SHARED.equals(state)) {
			isMediaMounted = false;
		} else if (Environment.MEDIA_UNMOUNTABLE.equals(state)) {
			isMediaMounted = false;
		} else if (Environment.MEDIA_UNMOUNTED.equals(state)) {
			isMediaMounted = false;
		}
		return isMediaMounted;
	}

	/**
	 * Generate intent to open gallery to choose image
	 * 
	 * @param context
	 *            :{@link Context} to create intent
	 * @return : {@link Intent} to open gallery
	 */
	public static Intent generateGalleryPickerIntent(Context context) {
		Toast.makeText(context, "GALLERY", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		return intent;
	}

	// public static void showDialogAndDismiss(String msg, final Context
	// context) {
	// final Dialog alerDialog = new Dialog(context, R.style.category_dialog);
	// alerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// alerDialog.setContentView(R.layout.dialog_custom_view);
	// TextView titleTextView = (TextView)
	// alerDialog.findViewById(R.id.dialog_title);
	// TextView messageTextView = (TextView)
	// alerDialog.findViewById(R.id.dialog_message);
	// Button okButton = (Button) alerDialog.findViewById(R.id.dialog_btn_ok);
	//
	// titleTextView.setTypeface(Util.getSemiBoldFont(context));
	// messageTextView.setTypeface(Util.getRegularFont(context));
	// okButton.setTypeface(Util.getSemiBoldFont(context));
	//
	// titleTextView.setText(context.getString(R.string.app_name));
	// messageTextView.setText(msg);
	//
	// okButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// alerDialog.dismiss();
	// ((DashBoardActivity) context).finish();
	// }
	// });
	//
	// alerDialog.show();
	// }

	// public static void showDialogAndDismissSettings(String msg, final Context
	// context, final DashBoardFragment boardFragment) {
	// final Dialog alerDialog = new Dialog(context, R.style.category_dialog);
	// alerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// alerDialog.setContentView(R.layout.dialog_custom_view);
	// TextView titleTextView = (TextView)
	// alerDialog.findViewById(R.id.dialog_title);
	// TextView messageTextView = (TextView)
	// alerDialog.findViewById(R.id.dialog_message);
	// Button okButton = (Button) alerDialog.findViewById(R.id.dialog_btn_ok);
	//
	// titleTextView.setTypeface(Util.getSemiBoldFont(context));
	// messageTextView.setTypeface(Util.getRegularFont(context));
	// okButton.setTypeface(Util.getSemiBoldFont(context));
	//
	// titleTextView.setText(context.getString(R.string.app_name));
	// messageTextView.setText(msg);
	//
	// okButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// alerDialog.dismiss();
	// boardFragment.checkOpenFragment(2);
	// }
	// });
	//
	// alerDialog.show();
	// }

	// public static void showDialogAndShowTV(String msg, final Context context,
	// final TextView textView) {
	// final Dialog alerDialog = new Dialog(context, R.style.category_dialog);
	// alerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// alerDialog.setContentView(R.layout.dialog_custom_view);
	// TextView titleTextView = (TextView)
	// alerDialog.findViewById(R.id.dialog_title);
	// TextView messageTextView = (TextView)
	// alerDialog.findViewById(R.id.dialog_message);
	// Button okButton = (Button) alerDialog.findViewById(R.id.dialog_btn_ok);
	//
	// titleTextView.setTypeface(Util.getSemiBoldFont(context));
	// messageTextView.setTypeface(Util.getRegularFont(context));
	// okButton.setTypeface(Util.getSemiBoldFont(context));
	//
	// titleTextView.setText(context.getString(R.string.app_name));
	// messageTextView.setText(msg);
	//
	// okButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// alerDialog.dismiss();
	// textView.setVisibility(View.VISIBLE);
	// }
	// });
	//
	// alerDialog.show();
	// }

	// public static void goToSplashActivity(String msg, final Context context)
	// {
	//
	// final AlertDialog.Builder alertDialogBuilder = new
	// AlertDialog.Builder(context);
	// alertDialogBuilder.setTitle(context.getString(R.string.app_name));
	// alertDialogBuilder.setCancelable(false);
	// alertDialogBuilder.setMessage(msg);
	// alertDialogBuilder.setPositiveButton(context.getString(android.R.string.ok),
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// Intent in = new Intent(context, SplashActivity.class);
	// context.startActivity(in);
	// ((Activity) context).finish();
	//
	// }
	// });
	//
	// // create alert dialog
	// AlertDialog alertDialog = alertDialogBuilder.create();
	//
	// // show it
	// alertDialog.show();
	// }

	public static boolean checkStatus(int status) {
		if (status == 3) {
			return true;
		} else {
			return false;
		}
	}

	public static String roundValue(double number) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(number);
	}

	// /**
	// * To store int data into shared preference
	// */
	// public static void storeInt(Context c, String key, int value) {
	// SharedPreferences preferences =
	// PreferenceManager.getDefaultSharedPreferences(c);
	// SharedPreferences.Editor editor = preferences.edit();
	// editor.putInt(key, value);
	// editor.commit();
	// }
	//
	// /**
	// * To get data stored in shared preference
	// */
	// public static int getInt(Context c, String key) {
	// int data;
	// SharedPreferences preferences =
	// PreferenceManager.getDefaultSharedPreferences(c);
	// data = preferences.getInt(key, 0);
	// return data;
	// }
	public static String getFileRootPath(Context context) {

		return context.getCacheDir().getAbsolutePath().toString();

	}
}
