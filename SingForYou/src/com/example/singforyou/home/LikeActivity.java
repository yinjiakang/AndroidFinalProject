package com.example.singforyou.home;

import java.util.ArrayList;
import java.util.List;

import com.example.singforyou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LikeActivity extends Activity {
	public int NUM = 1;
	public class message {
		private String name;
		private String title;
		private int num;
		public message(String name, String title) {
			this.name = name;
			this.title = title;
			this.num = NUM;
		}
		public String getName() {
			return name;
		}
		public String getTitle() {
			return title;
		}
		public int getNum() {
			return num;
		}
	}
	
	private List<message> messagelist = new ArrayList<message>();
	private ListView l;
	private MessageAdapter Fadapter;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.like_activity);
		
		messagelist.add(new message("叫我XXX", "我要听哈哈哈哈哈哈哈哈哈哈哈哈哈哈"));
		messagelist.add(new message("楼上是SB", "大大大大大大大大大大大大大大"));
		messagelist.add(new message("楼下唱歌好难听", "想听一曲凤池吟~~~~~~~~~"));
		
		l = (ListView)findViewById(R.id.home_lv_forum);
		Fadapter = new MessageAdapter(this, messagelist);
		l.setAdapter(Fadapter);
	}
	
	
	public class MessageAdapter extends BaseAdapter {

		private List<message> messagelist;
		private Context context;
		private LayoutInflater layoutInflater;
		public class messagelistview {
			public TextView t1;
			public TextView t2;
		}
		public MessageAdapter(Context context, List<message> objects) {
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
		public View getView(int position, View convertView, ViewGroup parent) {
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
			mlistview.t1.setText((String)messagelist.get(position).getName());
    		mlistview.t2.setText((String)messagelist.get(position).getTitle());
    		l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(LikeActivity.this, LikeActivity.class);
					startActivity(intent);
				}
			});
			return convertView;
		}
		
	}
	
}
