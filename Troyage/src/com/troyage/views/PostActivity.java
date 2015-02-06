package com.troyage.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.troyage.R;
import com.troyage.R.id;
import com.troyage.R.layout;
import com.troyage.adapter.PostAdapter;

public class PostActivity extends Activity {
	private ListView lvPostList;
	private PostAdapter postAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		lvPostList = (ListView) findViewById(R.id.activity_post_lvPost);
		postAdapter = new PostAdapter(PostActivity.this);
		lvPostList.setAdapter(postAdapter);
	}
}
