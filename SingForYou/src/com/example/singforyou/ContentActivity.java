package com.example.singforyou;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.singforyou.home.LikeActivity.MessageAdapter.messagelistview;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ContentActivity extends Activity {
	private ListView mListView;
	public static FloorAdapter mFadapter;
	public static Floor newFloor = new Floor();
	public static List<Floor> floor_list = new ArrayList<Floor>();
	private TextView Host_name;
	private TextView Host_content;
	private ImageButton play;
	private String fileName;
	private ImageButton reply;
	ProgressDialog progressDialog;
	
	/////////////////////////////////////////////
	private static final String url = "http://115.28.70.78/querypost";
	private static final String url1 = "http://115.28.70.78/queryfloor";
	private static final String url2 = "http://115.28.70.78/addgood";
	private static final String url3 = "http://115.28.70.78/addfloor";
	public static Posts post = new Posts();
	private Button share, return_btn;
	
	private String fileNamePrefix;
	/////////////////////////////////////////////
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {   
            switch (msg.what) {   
                 case 1:   
                	 Host_name.setText(post.getPostName());
                     Host_content.setText(post.getContent());
                     if (/*floor_list.size() != 0*/true) {
             			//mFloorAdapter = new FloorAdapter(ContentActivity.this,R.layout.content_item, floor_list);
                    	 Log.w("111111111111", floor_list.size()+"");
                    	mFadapter = new FloorAdapter(ContentActivity.this, floor_list);
             			mListView.setAdapter(mFadapter);
             			Log.w("ccccccc", "ccccccc");
             			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            				@Override
            				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            					// TODO Auto-generated method stub
            					Log.w("22222222222", "hhhhhhhhh");
            					String MID = floor_list.get(arg2).getMusicID();
            					Log.w("aaaaaaaaaaaaaaa", MID);
            				}
            			});
             			Log.w("cccccc11111c", "ccccccc");
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
				Log.w("-------------", newfloorId+"");
				Log.w("===============", newBelongto+"");

				bundle.putInt("floor", newfloorId);
				bundle.putInt("post", newBelongto);
				
				System.out.println("postId"+newBelongto);
				System.out.println("floorId"+newfloorId);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
				
				
				new Thread(new Runnable() {

					    int floorid = post.getNumOfFloor() + 1;
					    String mid = String.valueOf(post.getPostID()) + '_' + String.valueOf((post.getNumOfFloor() + 1));
					    
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
			        	        Log.w("midddddddddddddddddd", mid);
			        	        Log.w("11111111", post.getPostID()+"");
			        	        Log.w("2222222", floorid+"");
			        	        Log.w("33333333", LoginActivity.person.getName());
			        	        out.writeBytes("fid=" + floorid + "&bto=" + post.getPostID() + "&fcontent=" + "" + "&hostname=" + URLEncoder.encode(LoginActivity.person.getName()) + "&mid=" + mid); 
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
        	        Log.w("h1", PID);
        	        out.writeBytes("pid=" + PID); 
        	        InputStream in = connection.getInputStream();
        	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        	        StringBuilder response = new StringBuilder();
        	        
        	        String line;
        	        while ((line = reader.readLine()) != null) {
        	        	response.append(line);
        	        }
        	        Log.w("hhhhhhhbbbbbbbb", response.toString());
        	        post = parsePostWithPull(response.toString());
        	        
        	        
        	        //以下为对楼层进行xml获取
        	        connection = null;
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
        	        Log.w("hhhhhhhaaaaaaaaa", response1.toString());
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
				/*Intent intent = new Intent(ContentActivity.this, TiebaActivity.class);
				startActivity(intent);*/
				finish();
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
	
	
	public class FloorAdapter extends BaseAdapter {

		private List<Floor> mFloor = new ArrayList<Floor>();
		private Context context;
		private LayoutInflater layoutInflater;
		public class Floorlistview {
			public TextView t1;
		}
		public FloorAdapter(Context context, List<Floor> objects) {
			this.context = context;
			layoutInflater = LayoutInflater.from(context);
			mFloor = objects;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFloor.size();
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
			Floorlistview flistview = null;
			if (convertView == null) {
    			flistview = new Floorlistview();
    			convertView = layoutInflater.inflate(R.layout.content_item, null);
    			flistview.t1 = (TextView)convertView.findViewById(R.id.floorName);
    			convertView.setTag(flistview);
    		} else {
    			flistview = (Floorlistview)convertView.getTag();
    		}
			flistview.t1.setText(mFloor.get(position).getHostName());
			
			play = (ImageButton)convertView.findViewById(R.id.btn_play);
			play.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final String MID = mFloor.get(position).getMusicID();
					Log.w("aaaaaaaaaaaaaaa", MID);
					
					
					fileNamePrefix = ContentActivity.this.getFilesDir() + "/";
					fileName = fileNamePrefix + MID + ".3gp";
			        Thread thread = new Thread(new Runnable() {
			            @Override
			            public void run() {
			                try {
			                    //Log.e("before", "before");
			                	Record record = new Record();
			                	record.init();
			                	
			                    record.download(fileName, MID);
			                    record.startPlaying(fileName);
			                    
			                    ContentActivity.this.runOnUiThread(new Runnable() {
			                        @Override
			                        public void run() {
			                            progressDialog.dismiss();
			                        }
			                    });
			                    record.startPlaying(fileName);
			                    //Log.e("after", "after");
			                } catch (Exception e) {
			                    e.printStackTrace();
			                    ContentActivity.this.runOnUiThread(new Runnable() {
			                        @Override
			                        public void run() {
			                            progressDialog.dismiss();
			                        }
			                    });
			                }
			            }
			        });
			        thread.start();

			        progressDialog = ProgressDialog.show(ContentActivity.this, "Downloading", "Please wait...");
				}
			});
			return convertView;
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
