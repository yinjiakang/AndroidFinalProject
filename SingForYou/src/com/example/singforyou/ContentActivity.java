package com.example.singforyou;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ContentActivity extends Activity {
	private ListView mListView;
	private FloorAdapter mFloorAdapter;
	private List<Floor> floor_list = new ArrayList<Floor>();
	private TextView Host_content;
	private TextView floorName;
	private TextView singTime;
	private SeekBar mSeekBar;
	private ImageButton mImageButton;
	
	private SimpleDateFormat time = new SimpleDateFormat("m:ss");
	Handler handler = new Handler();
	Runnable r;
	
	private MusicService musicService = new MusicService();
	private ServiceConnection sc = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicService = ((MusicService.myBinder)service).getService();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO 自动生成的方法存根
			musicService = null;
		}
	};
	private void bindServiceConnection() {
		Intent intent = new Intent(ContentActivity.this, MusicService.class);
		bindService(intent,sc,Context.BIND_AUTO_CREATE);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_activity);
		bindServiceConnection();
		
		
		mListView = (ListView)findViewById(R.id.content_listview);
		Host_content = (TextView)findViewById(R.id.Host_content);
		floorName = (TextView)findViewById(R.id.floorName);
		singTime = (TextView)findViewById(R.id.singTime);
		mSeekBar = (SeekBar)findViewById(R.id.seekbar);
		mImageButton = (ImageButton)findViewById(R.id.btn_play);
		
		
		
		mFloorAdapter = new FloorAdapter(this,R.layout.content_item, floor_list);
		mListView.setAdapter(mFloorAdapter);
		
		r = new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				singTime.setText(time.format(musicService.mp.getCurrentPosition())+"/"+time.format(musicService.mp.getDuration()));
				mSeekBar.setProgress(musicService.mp.getCurrentPosition());
				mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO 自动生成的方法存根
						
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO 自动生成的方法存根
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						// TODO 自动生成的方法存根
						if (fromUser) {
							musicService.mp.seekTo(seekBar.getProgress());
						}
					}
				});
				handler.postDelayed(r, 100);
			}
			
		};
		
		mImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				musicService.playorpause();
				singTime.setText("ok");
				if (musicService.mp.isPlaying()) {
					mImageButton.setImageResource(R.drawable.pause);
				} else {
					mImageButton.setImageResource(R.drawable.play);
				}
				singTime.setText(time.format(musicService.mp.getCurrentPosition())+"/"+time.format(musicService.mp.getDuration()));
				mSeekBar.setProgress(musicService.mp.getCurrentPosition());
				mSeekBar.setMax(musicService.mp.getDuration());
				handler.post(r);
			}
			
		});
		
		
		
	}
	public class FloorAdapter extends ArrayAdapter<Floor>{ 
	    private int resource; 
	    public FloorAdapter(Context context, int resourceId, List<Floor> objects) { 
	        super(context, resourceId, objects); 
	        // 记录下来稍后使用 
	        resource = resourceId; 
	    }

	    public View getView(int position, View convertView, ViewGroup parent) { 
	        LinearLayout ContactListView; 
	        // 获取数据 
	        Floor file = getItem(position); 
	        if(convertView == null) { 
	            ContactListView = new LinearLayout(getContext()); 
	            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	            inflater.inflate(resource, ContactListView, true);//把布局解析到LinearLayout里面 
	        } else { 
	            ContactListView = (LinearLayout)convertView; 
	        }

	        // 获取控件,填充数据 
	        TextView tView1 = (TextView) ContactListView.findViewById(R.id.floorName); 
	        tView1.setText(file.getHostName()); 
	        return ContactListView; 
	    }
	}
	
}
