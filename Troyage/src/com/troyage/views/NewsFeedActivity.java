package com.troyage.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.troyage.R;
import com.troyage.R.id;
import com.troyage.R.layout;
import com.troyage.adapter.NewsFeedAdapter;

public class NewsFeedActivity extends Activity {
	private ListView lvFeedList;
	private NewsFeedAdapter newsFeedAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		lvFeedList = (ListView) findViewById(R.id.activity_news_feed_lvFeedList);
		newsFeedAdapter = new com.troyage.adapter.NewsFeedAdapter(
				NewsFeedActivity.this);
		lvFeedList.setAdapter(newsFeedAdapter);
	}
}
