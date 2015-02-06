package com.troyage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.troyage.R;
import com.troyage.views.PostActivity;

public class NewsFeedAdapter extends BaseAdapter {

	private final Activity context;

	public NewsFeedAdapter(Activity context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_news_feed_list_item, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.rlNewsFeed = (RelativeLayout) rowView
					.findViewById(R.id.row_news_feed_list_item_rlNewsFeed);
			viewHolder.txtNewsFeed = (TextView) rowView
					.findViewById(R.id.row_news_feed_list_item_tvNewsFeed);
			viewHolder.ivPhoto = (ImageView) rowView
					.findViewById(R.id.row_news_feed_list_item_ivPhoto);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		// holder.txtNewsFeed.setText();
		// holder.ivPhoto.setImageResource(R.drawable.icon);

		holder.rlNewsFeed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Rl" + position, Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(context, PostActivity.class);
				context.startActivity(intent);

			}
		});

		return rowView;
	}

	public class ViewHolder {
		public RelativeLayout rlNewsFeed;
		public TextView txtNewsFeed;
		public ImageView ivPhoto;

	}

}
