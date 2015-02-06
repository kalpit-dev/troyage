package com.troyage.views;

import com.troyage.R;
import com.troyage.R.drawable;
import com.troyage.R.id;
import com.troyage.R.layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;


public class CustomProgressDialog extends ProgressDialog {

	private AnimationDrawable animation;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progressbar);
		// getWindow().setWindowAnimations(R.style.dialog_animation_fade);
		final ImageView birdImageView = (ImageView) findViewById(R.id.dialog_progressbar_bird);
		birdImageView.setBackgroundResource(R.drawable.icon);
		animation = (AnimationDrawable) birdImageView.getBackground();

	}

	@Override
	public void show() {
		super.show();
		animation.start();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		animation.stop();
	}

}
