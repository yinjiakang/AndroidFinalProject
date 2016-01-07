package com.example.singforyou.home;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.singforyou.*;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MarkActivity extends Activity {

	public int NUM = 1;
	private EditText edtitle, edcon;
	private Button b;
	private ProgressDialog mydialog;
	private FrameLayout mContent;
	private int isStart = 0;
	private Posts post;
	Thread t;
	
	private static final String url = "http://115.28.70.78/newpost";
	
	//Timer timer = new Timer();
	void Dialog() {
		Log.w("1", "aa");
		LayoutInflater inflater = getLayoutInflater();
		Log.w("2", "aa");
		View layout = inflater.inflate(R.layout.dialog, null);
		Log.w("3", "aa");
		AlertDialog.Builder builder = new AlertDialog.Builder(MarkActivity.this);
		builder.setView(layout);
		Log.w("4", "aa");
		builder.create().show();
		Log.w("5", "aa");
		
		isStart = 1;
	}
	
	private static final int UPDATE_CONTENT = 0;
	private Handler handler = new Handler() {
	    public void handleMessage(Message message) {
	    	t.interrupt();
	    	switch (message.what) {
	    	case UPDATE_CONTENT:
	    		//content.setText(message.obj.toString());
	    		LoginActivity.all_posts.add(post);
	    		LoginActivity.all_my_posts.add(post);
				Intent intent = new Intent(MarkActivity.this, TiebaActivity.class);
				startActivity(intent);
				finish();
	    		break;
	    	default:
	    		break;
	    	}
	    }
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mark_activity);
		edtitle = (EditText)findViewById(R.id.markactivity_title);
		edcon = (EditText)findViewById(R.id.markactivity_content);
		//mContent = (FrameLayout)findViewById(R.id.content);
		//timer.scheduleAtFixedRate(new MyTask(), 1, 5000);
		
		b = (Button)findViewById(R.id.markactivity_post);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				post = new Posts(LoginActivity.person.getAccount(), edtitle.getText().toString(), edcon.getText().toString(), LoginActivity.person.getName(), 0, 0, 0);
				//List<Posts> l;
				//l.add(post);
				//传递数值给佳鹏
				
				
				t = new Thread(new Runnable() {

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
		        	        out.writeBytes("pac=" +  LoginActivity.person.getAccount() + "&ptitle=" + edtitle.getText().toString() + "&pcontent=" + edcon.getText().toString() + "&pname=" + LoginActivity.person.getName() + "&pid=" + "0" + "&nof=" + "0" + "&isgood=" + "0");
		        	        
		        	        InputStream in = connection.getInputStream();
		        	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		        	        StringBuilder response = new StringBuilder();
		        	        
		        	        String line;
		        	        while ((line = reader.readLine()) != null) {
		        	        	response.append(line);
		        	        }
		        	        
		        	        Message message = new Message();
		        	        message.what = UPDATE_CONTENT;
		        	        Log.w("aaa", response.toString());
		        	        message.obj = response.toString();
		        	        handler.handleMessage(message);
		    			} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
		    				if (connection != null) {
		        				connection.disconnect();
		        			}
		    			}
					}
					
				});
				t.start();
				//mContent.addView(MarkActivity.this.startActivity(intent).getDecorView());
				//Dialog();
			}
		});
		
	}
	
	/*private Handler mHandler = new Handler() {
		public void handleMessage(Message m) {
			switch(m.what) {
			case 1:
				if (isStart == 1) {
					//builder.create().dismiss();
					isStart = 0;
				}
				break;
			}
		}
	};
	public class MyTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			mHandler.sendMessage(message);
		}
		
	}*/

}
