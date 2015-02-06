package com.troyage.views;

import com.troyage.R;
import com.troyage.R.id;
import com.troyage.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class YourInterestActivity extends Activity {

	private Button btnSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_interest);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		btnSubmit=(Button)findViewById(R.id.activity_your_interest_btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(YourInterestActivity.this, NewsFeedActivity.class);
				startActivity(intent);
			}
		});
	}
}
