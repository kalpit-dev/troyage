package com.troyage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.troyage.R;
import com.troyage.views.CommentActivity;

public class PostAdapter extends BaseAdapter {

	private final Activity context;

	public PostAdapter(Activity context) {
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
			rowView = inflater.inflate(R.layout.row_post_list_item, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.llParent = (LinearLayout) rowView
					.findViewById(R.id.row_post_list_item_llParent);
			viewHolder.txtPersonName = (TextView) rowView
					.findViewById(R.id.row_news_feed_list_item_tvPersonName);
			viewHolder.txtDateTime = (TextView) rowView
					.findViewById(R.id.row_news_feed_list_item_tvDateTime);
			viewHolder.txtComment = (TextView) rowView
					.findViewById(R.id.row_news_feed_list_item_tvComment);
			viewHolder.txtEdit = (TextView) rowView
					.findViewById(R.id.row_news_feed_list_item_tvEdit);
			viewHolder.txtFav = (TextView) rowView
					.findViewById(R.id.row_news_feed_list_item_tvFav);

			viewHolder.ivPhoto = (ImageView) rowView
					.findViewById(R.id.row_news_feed_list_item_ivPhoto);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		// holder.txtPersonName.setText();
		// holder.ivPhoto.setImageResource(R.drawable.icon);

		holder.llParent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "LL" + position, Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(context, CommentActivity.class);
				context.startActivity(intent);
			}
		});

		return rowView;
	}

	public class ViewHolder {
		public LinearLayout llParent;
		public TextView txtPersonName, txtDateTime, txtComment, txtEdit,
				txtFav;
		public ImageView ivPhoto;

	}

}
