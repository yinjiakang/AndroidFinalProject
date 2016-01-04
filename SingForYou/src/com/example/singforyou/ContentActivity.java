package com.example.singforyou;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.singforyou.home.LikeActivity.message;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	
	
	/////////////////////////////////////////////
	private static final String url = "http://115.28.70.78/querypost";
	private static final String url1 = "http://115.28.70.78/queryfloor";
	private Posts post = new Posts();
	private Button share;
	/////////////////////////////////////////////
	
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
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////
		//---------------------------------------------------------------------------
		//以下为获取bundle的数据并且进行查询解析出贴子的主内容 （不包含楼层内容）
		Intent intent = getIntent();
		final String PID = intent.getStringExtra("PID");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
    			try {
        			connection = (HttpURLConnection)((new URL(url.toString()).openConnection()));
        			connection.setRequestMethod("POST");
        	        connection.setConnectTimeout(40000);
        	        connection.setReadTimeout(40000);
        	        
        	        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        	        
        	        //out.writeBytes("mobileCode=" + pNumber.getText().toString() + "&userID=");
        	        out.writeBytes("PID=" + PID); 
        	        InputStream in = connection.getInputStream();
        	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        	        StringBuilder response = new StringBuilder();
        	        
        	        String line;
        	        while ((line = reader.readLine()) != null) {
        	        	response.append(line);
        	        }
        	        post = parsePostWithPull(response.toString());
        	        
        	        
        	        //以下为对楼层进行xml获取
        	        connection = (HttpURLConnection)((new URL(url1.toString()).openConnection()));
        			connection.setRequestMethod("POST");
        	        connection.setConnectTimeout(40000);
        	        connection.setReadTimeout(40000);
        	        
        	        out = new DataOutputStream(connection.getOutputStream());
        	        out.writeBytes("PID=" + PID);
        	        in = connection.getInputStream();
        	        reader = new BufferedReader(new InputStreamReader(in));
        	        StringBuilder response1 = new StringBuilder();
        	        while ((line = reader.readLine()) != null) {
        	        	response1.append(line);
        	        }
        	        floor_list = parseFloorWithPull(response1.toString());
        	        
    			} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
    				if (connection != null) {
        				connection.disconnect();
        			}
    			}
			}
			
		}).start();
		
		
		
		
		share = (Button)findViewById(R.id.contentactivity_button);
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				post.setIsGood(1);
				LoginActivity.all_good_posts.add(post);
				//传递给佳鹏数据库（未实现）
			}
		});
		
		//---------------------------------------------------------------------
		/////////////////////////////////////////////////////////////////////////
		
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
	
	
	//////////////////////////////////////////////////////////////////
	//---------------------------------------------------------------
	private Posts parsePostWithPull(String xml) {
		Posts p = new Posts();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("PId".equals(parser.getName())) {
						p.setPostID(Integer.parseInt(parser.nextText()));
					} else if ("PAc".equals(parser.getName())) {
						p.setPostAccount(parser.nextText());
					} else if ("PTitle".equals(parser.getName())) {
						p.setPostTitle(parser.nextText());
					} else if ("PContent".equals(parser.getName())) {
						p.setContent(parser.nextText());
					} else if ("NOF".equals(parser.getName())) {
						p.setNumOfFloor(Integer.parseInt(parser.nextText()));
					} else if ("Isgood".equals(parser.getName())) {
						p.setIsGood(Integer.parseInt(parser.nextText()));
					} else if ("PName".equals(parser.getName())) {
						p.setPostName(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = parser.next();
				
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	
	
	private List<Floor> parseFloorWithPull(String xml) {
		List<Floor> f = new ArrayList<Floor>();
		String FContent = "", HostName = "", MId = "";
		int BTo = -1, FId = -1;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("FId".equals(parser.getName())) {
						FId = Integer.parseInt(parser.nextText());
					} else if ("BTo".equals(parser.getName())) {
						BTo = Integer.parseInt(parser.nextText());
					} else if ("FContent".equals(parser.getName())) {
						FContent = parser.nextText();
					} else if ("HostName".equals(parser.getName())) {
						HostName = parser.nextText();
					} else if ("MId".equals(parser.getName())) {
						MId = parser.nextText();
						f.add(new Floor(FContent, HostName, MId, BTo, FId));
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = parser.next();
				
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
	//----------------------------------------------------------------------
	////////////////////////////////////////////////////////////////////////
	
}
