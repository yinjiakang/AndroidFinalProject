package com.example.singforyou;

import java.util.List;

import com.example.singforyou.home.LikeActivity;
import com.example.singforyou.home.LikeActivity.MessageAdapter;
import com.example.singforyou.home.LikeActivity.MessageAdapter.messagelistview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MentionActivity extends Activity {

	
	private ListView l;
	private MessageAdapter Fadapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mention_activity);
		
		l = (ListView)findViewById(R.id.mentionactivity_listview);
		Fadapter = new MessageAdapter(this, LoginActivity.all_good_posts);
		l.setAdapter(Fadapter);
	}
	
	public class MessageAdapter extends BaseAdapter {

		private List<Posts> messagelist;
		private Context context;
		private LayoutInflater layoutInflater;
		public class messagelistview {
			public TextView t1;
			public TextView t2;
		}
		public MessageAdapter(Context context, List<Posts> objects) {
			this.context = context;
			layoutInflater = LayoutInflater.from(context);
			messagelist = objects;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return messagelist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			messagelistview mlistview = null;
			if (convertView == null) {
    			mlistview = new messagelistview();
    			convertView = layoutInflater.inflate(R.layout.listview, null);
    			mlistview.t1 = (TextView)convertView.findViewById(R.id.listview_writer);
    			mlistview.t2 = (TextView)convertView.findViewById(R.id.listview_title);
    			convertView.setTag(mlistview);
    		} else {
    			mlistview = (messagelistview)convertView.getTag();
    		}
			mlistview.t1.setText((String)messagelist.get(position).getPostName());
    		mlistview.t2.setText((String)messagelist.get(position).getPostTitle());
    		l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MentionActivity.this, ContentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("PID", String.valueOf(messagelist.get(position).getPostID()));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			return convertView;
		}
		
	}

}
