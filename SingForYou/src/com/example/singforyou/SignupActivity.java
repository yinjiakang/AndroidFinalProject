package com.example.singforyou;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	private EditText Signup_account, Signup_password, Signup_name;
	private Button Signup_register_btn, Signup_return_btn;
	HttpURLConnection connection = null;
	Thread thread;
	DataOutputStream out;
	InputStream in;
	String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Signup_account = (EditText) findViewById(R.id.name);
		Signup_password = (EditText) findViewById(R.id.password);
		Signup_name = (EditText) findViewById(R.id.Wname);
		Signup_register_btn = (Button) findViewById(R.id.register_btn);
		Signup_return_btn = (Button) findViewById(R.id.signup_return);
		
		Signup_return_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		Signup_register_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String account = Signup_account.getText().toString();
				String password = Signup_password.getText().toString();
				String name = Signup_name.getText().toString(); 
				
				String currenturl = "http://115.28.70.78/signup";
				String query = "account=" + account + "&password=" + password + "&name=" + name
						+ "&singtime=8&exvalue=0";
				Log.v("aa", "1");
				result="";
				ConnectToUrl(currenturl, query);

				Log.v("aa", result);
				if (result.equals("success")) {
					Initialize_person(account, password, name, 8, 0);
					Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
					startActivity(intent);
				} 
				else if (result.equals("exist")) {
					Toast.makeText(SignupActivity.this, "账户名已存在", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private void ConnectToUrl(final String url, final String content) {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					connection = (HttpURLConnection)((new URL(url).openConnection()));
					connection.setRequestMethod("POST");
					connection.setConnectTimeout(40000);
					connection.setReadTimeout(40000);
					Log.w("a1", "11");
					out  = new DataOutputStream(connection.getOutputStream());
					Log.w("a1", "22");
					//out.writeBytes("mobileCode="+ phone_number.getText().toString() + "&userID=");
					out.writeBytes(content);
					Log.w("a1", "33");
					
					in = connection.getInputStream();
					Log.w("a1", "44");
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					Log.w("a1", "55");
					StringBuilder response = new StringBuilder();
					Log.w("a1", "66");
					String line;
					Log.w("a1", "77");
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					Log.w("a1", response.toString());
					result = response.toString();
					Log.w("a1", "99");
				} catch (Exception e) {
					e.printStackTrace();
					result = "";
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
			
		}.start();;
	}
	
	private void Initialize_person(String ac, String ps, String na, int st, int exv) {
		LoginActivity.person.setAccount(ac);
		LoginActivity.person.setPassword(ps);
		LoginActivity.person.setName(na);
		LoginActivity.person.setSingTime(st);
		LoginActivity.person.setExperienceValue(exv);
	}
	

}
