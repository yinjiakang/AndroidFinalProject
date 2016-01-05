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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	private EditText Signup_account, Signup_password, Signup_name;
	private Button Signup_register_btn, Signup_return_btn;
	HttpURLConnection connection = null;
	DataOutputStream out;
	InputStream in;
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
				String query = "account=" + account + "&password" + password + "&name=" + name
						+ "&singtime=8&exvalue=0";
				
				String result = ConnectToUrl(currenturl, query);
				
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
	
	private String ConnectToUrl(String url, String content) {
		try {
			connection = (HttpURLConnection)((new URL(url).openConnection()));
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(40000);
			connection.setReadTimeout(40000);
			
			out  = new DataOutputStream(connection.getOutputStream());
			//out.writeBytes("mobileCode="+ phone_number.getText().toString() + "&userID=");
			out.writeBytes(content);
			
			in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	private void Initialize_person(String ac, String ps, String na, int st, int exv) {
		LoginActivity.person.setAccount(ac);
		LoginActivity.person.setPassword(ps);
		LoginActivity.person.setName(na);
		LoginActivity.person.setSingTime(st);
		LoginActivity.person.setExperienceValue(exv);
	}
	

}
