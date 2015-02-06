package com.troyage.views;
//package com.plumperfect.dialogs;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.URL;
//
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.SystemClock;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.facebook.FacebookException;
//import com.facebook.FacebookOperationCanceledException;
//import com.facebook.Session;
//import com.facebook.widget.WebDialog;
//import com.facebook.widget.WebDialog.OnCompleteListener;
//import com.plumperfect.PlumPerfectApplication;
//import com.plumperfect.R;
//import com.plumperfect.activities.MainActivity;
//import com.plumperfect.models.MyMatchProductListModel;
//import com.plumperfect.twitter.TwitterLogin;
//import com.plumperfect.utils.Utils;
//
//public class SharingDialog extends DialogFragment implements OnClickListener {
//	private TwitterLogin twitter;
//	private PlumPerfectApplication plumperfectApp;
//	private File picFile;
//	private MyMatchProductListModel productModel;
//	private long mLastClickTime = 0;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		if (getArguments() != null) {
//			productModel = getArguments().getParcelable(getString(R.string.key_share_model));
//		}
//		PlumPerfectApplication.getInstance().trackEvent("Social Share");
//
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		final View view = inflater.inflate(R.layout.layout_share_dialog, null);
//		initComp(view);
//		return view;
//	}
//
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		return dialog;
//	}
//
//	private void initComp(final View view) {
//		final FrameLayout llParent = (FrameLayout) view.findViewById(R.id.layout_share_dialog_ll);
//		final ImageView imgFacebook = (ImageView) view.findViewById(R.id.layout_share_dialog_iv_fb);
//		final ImageView imgTwitter = (ImageView) view.findViewById(R.id.layout_share_dialog_iv_twitter);
//		final ImageView imgMail = (ImageView) view.findViewById(R.id.layout_share_dialog_iv_email);
//		imgFacebook.setOnClickListener(this);
//		imgTwitter.setOnClickListener(this);
//		imgMail.setOnClickListener(this);
//		llParent.setOnClickListener(this);
//		plumperfectApp = (PlumPerfectApplication) getActivity().getApplicationContext();
//		createFile();
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.layout_share_dialog_iv_fb:
//			/**
//			 * Logic to Prevent the Launch of the Fragment Twice if User makes the Tap(Click) very Fast.
//			 */
//			if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//				return;
//			}
//			mLastClickTime = SystemClock.elapsedRealtime();
//			PlumPerfectApplication.getInstance().trackEvent("Share Via Facebook");
//			((MainActivity) getActivity()).onFacebookLogin();
//			((MainActivity) getActivity()).setShareFlag(true, false, false);
//			((MainActivity) getActivity()).setFbLogin(true);
//			break;
//		case R.id.layout_share_dialog_iv_twitter:
//			/**
//			 * Logic to Prevent the Launch of the Fragment Twice if User makes the Tap(Click) very Fast.
//			 */
//			if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//				return;
//			}
//			mLastClickTime = SystemClock.elapsedRealtime();
//			PlumPerfectApplication.getInstance().trackEvent("Share Via Twitter");
//			if (productModel != null) {
//				if (Utils.isConnectedToInternet(getActivity())) {
//					if (productModel != null) {
//						new AsyncDownloadImage(false).execute(productModel.getProductColor().getProductSellerColors().get(0).getImageUrl());
//					}
//				} else {
//					Utils.showAlertDialog(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.internet_not_available));
//				}
//			}
//			break;
//		case R.id.layout_share_dialog_iv_email:
//			/**
//			 * Logic to Prevent the Launch of the Fragment Twice if User makes the Tap(Click) very Fast.
//			 */
//			if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//				return;
//			}
//			mLastClickTime = SystemClock.elapsedRealtime();
//			PlumPerfectApplication.getInstance().trackEvent("Share Via Email");
//			if (productModel != null) {
//				if (Utils.isConnectedToInternet(getActivity())) {
//					new AsyncDownloadImage(true).execute(productModel.getProductColor().getProductSellerColors().get(0).getImageUrl());
//				} else {
//					Utils.showAlertDialog(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.internet_not_available));
//				}
//			}
//			break;
//		case R.id.layout_share_dialog_ll:
//			this.dismiss();
//			break;
//		}
//	}
//
//	private void createFile() {
//		final String state = Environment.getExternalStorageState();
//		if (Environment.MEDIA_MOUNTED.equals(state)) {
//			picFile = Utils.getFilePathSharing(getActivity());
//		} else {
//			picFile = new File(getActivity().getFilesDir(), Utils.TEMP_PHOTO_FILE_NAME);
//		}
//
//	}
//
//	private void onClickTwitterLogin() {
//		twitter = new TwitterLogin(getActivity());
//		plumperfectApp.setTwitterLogin(twitter);
//
//		/**
//		 * Here 3 parameters you need to pass 1> context of Activity 2> Text to Share 3> ImagePath for the Image which you want to share
//		 */
//		if (productModel != null) {
//			twitter.checkLoginForTwitter(
//					getActivity(),
//					String.format(getResources().getString(R.string.msg_twitter), "" + productModel.getProductColor().getColorName(), ""
//							+ productModel.getProductColor().getProduct().getTitle()), picFile.getAbsolutePath().toString());
//		}
//	}
//
//	private class AsyncDownloadImage extends AsyncTask<String, Void, Void> {
//		private ProgressDialog progressDialog;
//		private boolean isClick;
//
//		private AsyncDownloadImage(final boolean isClick) {
//			this.isClick = isClick;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			progressDialog = Utils.showProgressDialog(getActivity(), "", getString(R.string.loading));
//		}
//
//		protected Void doInBackground(String... params) {
//			try {
//				final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
//				final FileOutputStream fos = new FileOutputStream(picFile);
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//				fos.flush();
//				fos.close();
//				return null;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//
//		protected void onPostExecute(Void result) {
//			Utils.dismissProgressDialog(progressDialog);
//			try {
//				if (isClick) {
//					sendMail();
//				} else {
//					onClickTwitterLogin();
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
//			}
//		}
//	}
//
//	/**
//	 * Actual Method to check for required permissions and posting the status itself.
//	 */
//	public void postStatus(final Context context, final Session session) {
//		if (session != null && session.isOpened()) {
//			final Bundle postParams = new Bundle();
//			if (productModel != null) {
//				postParams.putString(
//						"description",
//						String.format(getResources().getString(R.string.msg_facebook), "" + productModel.getProductColor().getColorName(), ""
//								+ productModel.getProductColor().getProduct().getTitle()));
//				postParams.putString("link", productModel.getProductColor().getProductSellerColors().get(0).getUrl());
//				postParams.putString("picture", productModel.getProductColor().getProductSellerColors().get(0).getImageUrl());
//			}
//
//			final WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(getActivity(), Session.getActiveSession(), postParams)).setOnCompleteListener(new OnCompleteListener() {
//				@Override
//				public void onComplete(Bundle values, FacebookException error) {
//					if (error == null) {
//						// When the story is posted, echo the success
//						// and the post Id.
//						final String postId = values.getString("post_id");
//						if (postId != null) {
//							Toast.makeText(getActivity(), "Post Sent Successfully", Toast.LENGTH_LONG).show();
//						}
//					} else if (error instanceof FacebookOperationCanceledException) {
//					} else {
//						Toast.makeText(getActivity(), "" + getString(R.string.internet_not_available), Toast.LENGTH_LONG).show();
//					}
//				}
//			}).build();
//			feedDialog.show();
//		}
//	}
//
//	private void sendMail() {
//		final Intent shareIntent = new Intent();
//		shareIntent.setAction(Intent.ACTION_SEND);
//		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(picFile.getPath())));
//		shareIntent.setType("image/jpeg");
//		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "PlumPerfect recommends");
//		shareIntent.putExtra(
//				Intent.EXTRA_TEXT,
//				Html.fromHtml(String.format(getResources().getString(R.string.msg_mail), "" + productModel.getProductColor().getColorName(), ""
//						+ productModel.getProductColor().getProduct().getTitle())
//						+ "<br>" + "download the app: https://play.google.com/store/apps/details?id=com.plumperfect"));
//		// + "<br><a href= https://play.google.com/store/apps/details?id=com.plumperfect >" + " download the app: https://play.google.com/store/apps/details?id=com.plumperfect" +
//		// " </a>"));
//		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//		startActivity(Intent.createChooser(shareIntent, "Share via.."));
//	}
// }
