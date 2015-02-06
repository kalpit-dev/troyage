package com.troyage.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.troyage.R;
import com.troyage.R.id;
import com.troyage.R.layout;
import com.troyage.adapter.CommentAdapter;

public class CommentActivity extends Activity {
	private ListView lvCommentList;
	private CommentAdapter commentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		lvCommentList = (ListView) findViewById(R.id.activity_comments_lvCommentList);
		commentAdapter = new CommentAdapter(CommentActivity.this);
		lvCommentList.setAdapter(commentAdapter);
	}
}
