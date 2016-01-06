package com.example.singforyou;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static Person person = new Person();
	public static ArrayList<Posts> all_posts = new ArrayList<Posts>();
	static ArrayList<Posts> all_good_posts = new ArrayList<Posts>();
	static ArrayList<Posts> all_my_posts = new ArrayList<Posts>();
	HttpURLConnection connection = null;
	DataOutputStream out;
	String result;
	Handler handler;
	Thread thread;
	InputStream in;
	private Button Login_TurnToRegister, Login_LoginBtn;
	private EditText Login_accounts, Login_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		Login_TurnToRegister = (Button) findViewById(R.id.regist);
		Login_LoginBtn = (Button) findViewById(R.id.login);
		Login_accounts = (EditText) findViewById(R.id.accounts);
		Login_password = (EditText) findViewById(R.id.password);
		
		handler = new Handler() {
			public void handleMessage(Message message) {
				switch (message.what) {
				case 1:
					
					result = message.obj.toString();
					//thread.interrupt();
					if (result.equals("")) {
						Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
					} else {
						Parser_person(result);
		
						// 得到所有帖子以及精品贴
						LoadAllAndGoodPosts();
						// 得到我的所有帖子
						LoadMyPosts();
						Intent intent = new Intent(LoginActivity.this, TiebaActivity.class);
						startActivity(intent);
					}
				case 2:
					result = message.obj.toString();
					//thread.interrupt();
					Parser_allposts(result, 0);
					Log.v("size all_posts", all_posts.size() + "");
					Log.v("size all_good_posts", all_good_posts.size() + "");
				case 3:
					result = message.obj.toString();
					//thread.interrupt();
					Parser_allposts(result, 1);
					Log.v("size all_posts",all_my_posts.size() + "");
				default:
					break;
				}
			}
		};
		
		Login_LoginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String account = Login_accounts.getText().toString();
				String password = Login_password.getText().toString();
				
				String currenturl = "http://115.28.70.78/login";
				String query = "account=" + account + "&password=" + password;

				result = "";
				ConnectToUrl(currenturl, query, 1);
				//thread.start();
			}
		});
		
		Login_TurnToRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void ConnectToUrl(final String url, final String content, final int mode) {
		Log.v("connect", url + " " + content + " " + mode);
		//thread = 
		new Thread() {
			public void run() {
				try {
					connection = (HttpURLConnection)((new URL(url).openConnection()));
					connection.setRequestMethod("POST");
					connection.setConnectTimeout(1000000000);
					connection.setReadTimeout(1000000000);
					
					out  = new DataOutputStream(connection.getOutputStream());
					//out.writeBytes("mobileCode="+ phone_number.getText().toString() + "&userID=");
					out.writeBytes(content);
					Log.v("mode", mode + "");
					in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					Log.v("asdsa", response.toString());
					
					Message message = new Message();
					message.what = mode;
					message.obj = response.toString();
					handler.sendMessage(message);   
				} catch (Exception e) {
					e.printStackTrace();
					Message message = new Message();
					message.what = mode;
					message.obj = "";
					handler.sendMessage(message); 
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}.start();;
	}
	
	private void Parser_person(String xml) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("ACCOUNT")) {
						person.setAccount(parser.nextText());
					}
					if (parser.getName().equals("PASSWORD")) {
						person.setPassword(parser.nextText());
					}
					if (parser.getName().equals("NAME")) {
						person.setName(parser.nextText());
					}
					if (parser.getName().equals("SINGTIME")) {
						person.setSingTime(Integer.parseInt(parser.nextText()));
					}
					if (parser.getName().equals("EXVALUE")) {
						person.setExperienceValue(Integer.parseInt(parser.nextText()));
					}
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @parameter
	 * xml: 返回的所需结果
	 * mode: 模式  mode = 0 即执行得到所有帖子及精品贴
	 * 			mode = 1 表示得到用户的所有帖子
	 */
	private void Parser_allposts(String xml, int mode) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			Posts newposts = new Posts();
			int flag = 0;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("posts")) {
						newposts = new Posts();
						flag = 0;
					}
					if (parser.getName().equals("PACCOUNT")) {
						newposts.setPostAccount(parser.nextText());
					}
					if (parser.getName().equals("PTITLE")) {
						newposts.setPostTitle(parser.nextText());
					}
					if (parser.getName().equals("PCONTENT")) {
						newposts.setContent(parser.nextText());
					}
					if (parser.getName().equals("PNAME")) {
						newposts.setPostName(parser.nextText());
					}
					if (parser.getName().equals("PID")) {
						newposts.setPostID(Integer.parseInt(parser.nextText()));
					}
					if (parser.getName().equals("NOF")) {
						newposts.setNumOfFloor(Integer.parseInt(parser.nextText()));
					}
					if (parser.getName().equals("ISGOOD")) {
						flag = Integer.parseInt(parser.nextText());
						newposts.setIsGood(flag);
						if (mode == 0) {
							all_posts.add(newposts);
							if (flag == 1) {
								all_good_posts.add(newposts);
							}
						} else if (mode == 1) {
							all_my_posts.add(newposts)
;						}
					}
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("posts")) {
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void LoadAllAndGoodPosts() {
		all_posts.clear();
		all_good_posts.clear();
		String currenturl = "http://115.28.70.78/queryallposts";
		
		ConnectToUrl(currenturl, "getallposts", 2);
		//thread.start();
	}
	
	private void LoadMyPosts() {
		all_my_posts.clear();
		String currenturl = "http://115.28.70.78/querymyposts";
		ConnectToUrl(currenturl, "account=" + person.getAccount(), 3);
		//thread.start();
	}
}
