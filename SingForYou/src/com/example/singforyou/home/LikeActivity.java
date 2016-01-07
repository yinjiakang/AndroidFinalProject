/*package com.example.singforyou.home;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.singforyou.ContentActivity;
import com.example.singforyou.LoginActivity;
import com.example.singforyou.Posts;
import com.example.singforyou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LikeActivity extends Activity {
	public class message {
		private String name;
		private String title;
		private String PID;
		public message() {}
		public message(String name, String title, String PID) {
			this.name = name;
			this.title = title;
			this.PID = PID;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setPID(String PID) {
			this.PID = PID;
		}
		public String getName() {
			return name;
		}
		public String getTitle() {
			return title;
		}
		public String getPID() {
			return PID;
		}
	}
	
	//private List<message> messagelist = new ArrayList<message>();
	private ListView l;
	private MessageAdapter Fadapter;
	
	private List<message> parseXMLWithPull(String xml) {
		List<message> mlist = new ArrayList<message>();
		String name = "", title = "", PId = "";
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("PId".equals(parser.getName())) {
						PId = parser.nextText();
					} else if ("PTitle".equals(parser.getName())) {
						title = parser.nextText();
					} else if ("PName".equals(parser.getName())) {
						name = parser.nextText();
						mlist.add(new message(name, title, PId));
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
		return mlist;
	}
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.like_activity);
		String xml = "<?xml version='1.0' encoding='UTF-8'?><recipe><PId>1</PId><PAc>����������</PAc><PTitle>��������������</PTitle><PContent>����һ�����й�</PContent><NOF>2</NOF><Isgood>0</Isgood><PName>�ҽ�MT</PName><PId>2</PId><PAc>������</PAc><PTitle>�����������´�������</PTitle><PContent>������������</PContent><NOF>3</NOF><Isgood>0</Isgood><PName>�ҽ�MT</PName><PId>3</PId><PAc>������</PAc><PTitle>��˹�</PTitle><PContent>��Բ����</PContent><NOF>2</NOF><Isgood>1</Isgood><PName>�ҽ�MT</PName><PId>4</PId><PAc>you are my sunshine</PAc><PTitle>my only sunshine</PTitle><PContent>you make me happy</PContent><NOF>2</NOF><Isgood>0</Isgood><PName>�ҽ�MT</PName></recipe>";
		
		//��������һ��xml�ļ��������е�����
		//messagelist = parseXMLWithPull(xml);
		//messagelist.add(new message("����XXX", "��Ҫ������������������������������"));
		//messagelist.add(new message("¥����SB", "���������������"));
		//messagelist.add(new message("¥�³��������", "����һ�������~~~~~~~~~"));
		
		l = (ListView)findViewById(R.id.home_lv_forum);
		Fadapter = new MessageAdapter(this, LoginActivity.all_posts);
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
					Intent intent = new Intent(LikeActivity.this, ContentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("PID", String.valueOf(messagelist.get(arg2).getPostID()));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			return convertView;
		}
		
	}
	
}
*/




package com.example.singforyou.home;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.singforyou.ContentActivity;
import com.example.singforyou.LoginActivity;
import com.example.singforyou.Posts;
import com.example.singforyou.R;
import com.example.singforyou.TiebaActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LikeActivity extends Activity implements SensorEventListener{ 
	/*public class message {
		private String name;
		private String title;
		private String PID;
		public message() {}
		public message(String name, String title, String PID) {
			this.name = name;
			this.title = title;
			this.PID = PID;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setPID(String PID) {
			this.PID = PID;
		}
		public String getName() {
			return name;
		}
		public String getTitle() {
			return title;
		}
		public String getPID() {
			return PID;
		}
	}*/
	
	//private List<message> messagelist = new ArrayList<message>();
	private ListView l;
	private MessageAdapter Fadapter;
	private SensorManager mSensorManager;  
    //��  
    private Vibrator vibrator;  
  
	/*private List<message> parseXMLWithPull(String xml) {
		List<message> mlist = new ArrayList<message>();
		String name = "", title = "", PId = "";
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("PId".equals(parser.getName())) {
						PId = parser.nextText();
					} else if ("PTitle".equals(parser.getName())) {
						title = parser.nextText();
					} else if ("PName".equals(parser.getName())) {
						name = parser.nextText();
						mlist.add(new message(name, title, PId));
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
		return mlist;
	}*/
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.like_activity);
		String xml = "<?xml version='1.0' encoding='UTF-8'?><recipe><PId>1</PId><PAc>����������</PAc><PTitle>��������������</PTitle><PContent>����һ�����й�</PContent><NOF>2</NOF><Isgood>0</Isgood><PName>�ҽ�MT</PName><PId>2</PId><PAc>������</PAc><PTitle>�����������´�������</PTitle><PContent>������������</PContent><NOF>3</NOF><Isgood>0</Isgood><PName>�ҽ�MT</PName><PId>3</PId><PAc>������</PAc><PTitle>��˹�</PTitle><PContent>��Բ����</PContent><NOF>2</NOF><Isgood>1</Isgood><PName>�ҽ�MT</PName><PId>4</PId><PAc>you are my sunshine</PAc><PTitle>my only sunshine</PTitle><PContent>you make me happy</PContent><NOF>2</NOF><Isgood>0</Isgood><PName>�ҽ�MT</PName></recipe>";
		
		//��������һ��xml�ļ��������е�����
		//messagelist = parseXMLWithPull(xml);
		//messagelist.add(new message("����XXX", "��Ҫ������������������������������"));
		//messagelist.add(new message("¥����SB", "���������������"));
		//messagelist.add(new message("¥�³��������", "����һ�������~~~~~~~~~"));
		
		l = (ListView)findViewById(R.id.home_lv_forum);
		Fadapter = new MessageAdapter(this, LoginActivity.all_posts);
		l.setAdapter(Fadapter);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  
        //��  
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);  
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
					Intent intent = new Intent(LikeActivity.this, ContentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("PID", String.valueOf(messagelist.get(arg2).getPostID()));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			return convertView;
		}
		
		
	}
	
	  
	@Override  
	protected void onResume(){  
	  super.onResume();  
	  
	  //���ٶȴ�����  
	  mSensorManager.registerListener(this,  
	  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),  
	  //����SENSOR_DELAY_UI��SENSOR_DELAY_FASTEST��SENSOR_DELAY_GAME�ȣ�  
	  //���ݲ�ͬӦ�ã���Ҫ�ķ�Ӧ���ʲ�ͬ���������ʵ������趨  
	  SensorManager.SENSOR_DELAY_NORMAL);  
	}  
	  
	@Override  
	protected void onStop(){  
	  mSensorManager.unregisterListener(this);  
	  super.onStop();  
	}  
	  
	@Override  
	protected void onPause(){  
	  mSensorManager.unregisterListener(this);  
	  super.onPause();  
	}  
	  
	@Override  
	public void onAccuracyChanged(Sensor sensor, int accuracy) {  
	  // TODO Auto-generated method stub  
	  //�����������ȸı�ʱ�ص��÷�����Do nothing.  
	}  
	@Override  
	public void onSensorChanged(SensorEvent event) {  
	  // TODO Auto-generated method stub  
	  int sensorType = event.sensor.getType();  
	  
	  //values[0]:X�ᣬvalues[1]��Y�ᣬvalues[2]��Z��  
	  float[] values = event.values;  
	  
	  if(sensorType == Sensor.TYPE_ACCELEROMETER){  
	  
	  /*��Ϊһ����������£���������ֵ������9.8~10֮�䣬ֻ������ͻȻҡ���ֻ� 
	  *��ʱ��˲ʱ���ٶȲŻ�ͻȻ�������١� 
	  *���ԣ�����ʵ�ʲ��ԣ�ֻ�������һ��ļ��ٶȴ���14��ʱ�򣬸ı�����Ҫ������ 
	  *��OK��~~~ 
	  */  
	   if((Math.abs(values[0])>14 || Math.abs(values[1])>14 || Math.abs(values[2])>14)){  
	  
	    //ҡ���ֻ����ٰ�������ʾ~~  
	    vibrator.vibrate(500);  
	    Intent intent = new Intent(this, TiebaActivity.class);
	    startActivity(intent);
	    finish();
	   }  
	  }  
	}  
	  
	
}
