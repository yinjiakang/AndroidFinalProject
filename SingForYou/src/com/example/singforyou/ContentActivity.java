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
	public static FloorAdapter mFloorAdapter;
	public static Floor newFloor;
	public static List<Floor> floor_list = new ArrayList<Floor>();
	private TextView Host_name;
	private TextView Host_content;
	private TextView floorName;
	private TextView singTime;
	private SeekBar mSeekBar;
	private ImageButton play;
	private SimpleDateFormat time = new SimpleDateFormat("m:ss");
	private String musicId;
	private String fileName;
	private ImageButton reply;
	
	/////////////////////////////////////////////
	private static final String url = "http://115.28.70.78/querypost";
	private static final String url1 = "http://115.28.70.78/queryfloor";
	private static final String url2 = "http://115.28.70.78/addgood";
	private static final String url3 = "http://115.28.70.78/addfloor";
	private Posts post = new Posts();
	private Button share, return_btn;
	
	private String fileNamePrefix;
	/////////////////////////////////////////////
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {   
            switch (msg.what) {   
                 case 1:   
                	 Host_name.setText(post.getPostName());
                     Host_content.setText(post.getContent());
                     if (floor_list.size() != 0) {
             			mFloorAdapter = new FloorAdapter(ContentActivity.this,R.layout.content_item, floor_list);
             			mListView.setAdapter(mFloorAdapter);
             		}
                     break;
                 default:
                	 break;
            }   
            super.handleMessage(msg);   
       }   
		
	};
	Runnable r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_activity);
		
		
		Host_name = (TextView)findViewById(R.id.HostName);
		Host_content = (TextView)findViewById(R.id.Host_content);

		mListView = (ListView)findViewById(R.id.content_listview);
		
		
		reply = (ImageButton)findViewById(R.id.reply);
		reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContentActivity.this, RecordActivity.class);
				Bundle bundle = new Bundle();
				int newfloorId = post.getNumOfFloor()+1;
				int newBelongto = post.getPostID();

				bundle.putInt("floor", newfloorId);
				bundle.putInt("post", newBelongto);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
				post.setNumOfFloor(newfloorId);
				
				new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							HttpURLConnection connection = null;
			    			try {
			        			connection = (HttpURLConnection)((new URL(url3.toString()).openConnection()));
			        			connection.setRequestMethod("POST");
			        	        connection.setConnectTimeout(40000);
			        	        connection.setReadTimeout(40000);
			        	        
			        	        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			        	        
			        	        //out.writeBytes("mobileCode=" + pNumber.getText().toString() + "&userID=");
			        	        out.writeBytes("fid=" + newFloor.getFloorID() + "&bto=" + newFloor.getBelongTo() + "&fcontent=" + newFloor.getContent() + "&hostname=" + newFloor.getHostName() + "&mid=" + newFloor.getMusicID()); 
			        	        InputStream in = connection.getInputStream();
			        	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        	        StringBuilder response = new StringBuilder();
			        	        
			        	        String line;
			        	        while ((line = reader.readLine()) != null) {
			        	        	response.append(line);
			        	        }
			        	        
			        	        
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
					
					
					
				
			}
			
		});
		
		//////////////////////////////////////////////////////////////////////////////
		//---------------------------------------------------------------------------
		//����Ϊ��ȡbundle�����ݲ��ҽ��в�ѯ���������ӵ������� ��������¥�����ݣ�
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
        	        Log.w("h1", PID);
        	        out.writeBytes("pid=" + PID); 
        	        InputStream in = connection.getInputStream();
        	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        	        StringBuilder response = new StringBuilder();
        	        
        	        String line;
        	        while ((line = reader.readLine()) != null) {
        	        	response.append(line);
        	        }
        	        post = parsePostWithPull(response.toString());
        	        
        	        
        	        //����Ϊ��¥�����xml��ȡ
        	        connection = (HttpURLConnection)((new URL(url1.toString()).openConnection()));
        			connection.setRequestMethod("POST");
        	        connection.setConnectTimeout(40000);
        	        connection.setReadTimeout(40000);
        	        
        	        out = new DataOutputStream(connection.getOutputStream());
        	        out.writeBytes("pid=" + PID);
        	        in = connection.getInputStream();
        	        reader = new BufferedReader(new InputStreamReader(in));
        	        StringBuilder response1 = new StringBuilder();
        	        while ((line = reader.readLine()) != null) {
        	        	response1.append(line);
        	        }
        	        Log.w("hhhhhhhaaaaaaaaa", response.toString());
        	        floor_list = parseFloorWithPull(response1.toString());
        	        Message mes = new Message();
        	        mes.what = 1;
        	        handler.sendMessage(mes);
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
		
		return_btn = (Button) findViewById(R.id.button1);
		
		return_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContentActivity.this, TiebaActivity.class);
				startActivity(intent);
			}
		});
		
		share = (Button)findViewById(R.id.contentactivity_button);
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				post.setIsGood(1);
				LoginActivity.all_good_posts.add(post);
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpURLConnection connection = null;
		    			try {
		        			connection = (HttpURLConnection)((new URL(url2.toString()).openConnection()));
		        			connection.setRequestMethod("POST");
		        	        connection.setConnectTimeout(40000);
		        	        connection.setReadTimeout(40000);
		        	        
		        	        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		        	        
		        	        //out.writeBytes("mobileCode=" + pNumber.getText().toString() + "&userID=");
		        	        out.writeBytes("pid=" + PID); 
		        	        InputStream in = connection.getInputStream();
		        	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		        	        StringBuilder response = new StringBuilder();
		        	        
		        	        String line;
		        	        while ((line = reader.readLine()) != null) {
		        	        	response.append(line);
		        	        }
		        	        
		        	        
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
			}
		});
		
		//---------------------------------------------------------------------
		/////////////////////////////////////////////////////////////////////////
		
	}
	
	
	//Ϊÿһ��floor����������
	public class FloorAdapter extends ArrayAdapter<Floor>{ 
	    private int resource; 
	    public FloorAdapter(Context context, int resourceId, List<Floor> objects) { 
	        super(context, resourceId, objects); 
	        // ��¼�����Ժ�ʹ�� 
	        resource = resourceId; 
	    }

	    public View getView(int position, View convertView, ViewGroup parent) { 
	        LinearLayout FloorListView; 
	        // ��ȡ���� 
	        Floor floor = getItem(position); 
	        if(convertView == null) { 
	            FloorListView = new LinearLayout(getContext()); 
	            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	            inflater.inflate(resource, FloorListView, true);//�Ѳ��ֽ�����LinearLayout���� 
	        } else { 
	            FloorListView = (LinearLayout)convertView; 
	        }

	        // ��ȡ�ؼ�,������� 
	        floorName = (TextView)FloorListView.findViewById(R.id.floorName);
			singTime = (TextView)FloorListView.findViewById(R.id.singTime);
			mSeekBar = (SeekBar)FloorListView.findViewById(R.id.seekbar);
			play = (ImageButton)FloorListView.findViewById(R.id.btn_play);
			
			musicId = floor.getMusicID();
			floorName.setText(floor.getHostName());
			
			final Record record = new Record();
			record.init();
			//���ý�����
	    	r = new Runnable() {

				@Override
				public void run() {
					// TODO �Զ����ɵķ������
					singTime.setText(time.format(record.mediaPlayer.getCurrentPosition())+"/"+time.format(record.mediaPlayer.getDuration()));
					mSeekBar.setProgress(record.mediaPlayer.getCurrentPosition());
					mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
						
						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO �Զ����ɵķ������
							
						}
						
						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO �Զ����ɵķ������
							
						}
						
						@Override
						public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
							// TODO �Զ����ɵķ������
							if (fromUser) {
								record.mediaPlayer.seekTo(seekBar.getProgress());
							}
						}
					});
					handler.postDelayed(r, 100);
				}
				
			};
	    	/*download��������
	    	 *������Ű�ť��ʱ��
	    	 *�����
			
			
			
			
			
			*/
	    	//������Ű�ť
	        play.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO �Զ����ɵķ������
					//��ȡ·��
					
					
			        fileNamePrefix = ContentActivity.this.getExternalFilesDir(null).toString() + "/";
			        fileName = fileNamePrefix + musicId + ".3gp";
			        record.download(fileName, musicId);
			        if ( !record.isSDCardExist() ) {
			            Log.e("Record", "SD card does not exist!");
			        }
			        System.out.println("11111");
					singTime.setText("ok");
					//�����ȡ���ŵ���Ƶ
					if (record.mediaPlayer.isPlaying()) {
						play.setImageResource(R.drawable.pause);
						record.mediaPlayer.pause();
					} else {
						play.setImageResource(R.drawable.play);
						if (record.mediaPlayer != null) {
							record.mediaPlayer.start();
						} else {
							record.startPlaying(fileName);
					
						}
					}
					singTime.setText(time.format(record.mediaPlayer.getCurrentPosition())+"/"+time.format(record.mediaPlayer.getDuration()));
					mSeekBar.setProgress(record.mediaPlayer.getCurrentPosition());
					mSeekBar.setMax(record.mediaPlayer.getDuration());
					handler.post(r);
				}
				
			});
	        
	        return FloorListView; 
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
					if ("pid".equals(parser.getName())) {
						p.setPostID(Integer.parseInt(parser.nextText()));
					} else if ("PACCOUNT".equals(parser.getName())) {
						p.setPostAccount(parser.nextText());
					} else if ("PTITLE".equals(parser.getName())) {
						p.setPostTitle(parser.nextText());
					} else if ("PCONTENT".equals(parser.getName())) {
						p.setContent(parser.nextText());
					} else if ("NOF".equals(parser.getName())) {
						p.setNumOfFloor(Integer.parseInt(parser.nextText()));
					} else if ("ISGOOD".equals(parser.getName())) {
						p.setIsGood(Integer.parseInt(parser.nextText()));
					} else if ("PNAME".equals(parser.getName())) {
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
					if ("FID".equals(parser.getName())) {
						FId = Integer.parseInt(parser.nextText());
					} else if ("BTO".equals(parser.getName())) {
						BTo = Integer.parseInt(parser.nextText());
					} else if ("FCONTENT".equals(parser.getName())) {
						FContent = parser.nextText();
					} else if ("HOSTNAME".equals(parser.getName())) {
						HostName = parser.nextText();
					} else if ("MID".equals(parser.getName())) {
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
