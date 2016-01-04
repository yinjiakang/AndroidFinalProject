package com.example.singforyou;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button Login_TurnToRegister, Login_LoginBtn;
	private EditText Login_accounts, Login_password;
	static Person person = new Person();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		Login_TurnToRegister = (Button) findViewById(R.id.regist);
		Login_LoginBtn = (Button) findViewById(R.id.login);
		Login_accounts = (EditText) findViewById(R.id.accounts);
		Login_password = (EditText) findViewById(R.id.password);
		
		Login_LoginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String account = Login_accounts.getText().toString();
				String password = Login_password.getText().toString();
				/*
				 * need db
				 * post    account
				 * if exists , return Person
				 * else return empty string
				 */
				
				/* 
				 * if (person.getPassword().equals(password)) {
				 * 		Intent intent = new Intent(LoginActivity.this, TiebaActivity.class);
				 * 		startActivity(intent);
				 * } else {
				 * 		// error
				 * }
				 */
				String result = "aaa";
				if (result.equals("")) {
					Toast.makeText(LoginActivity.this, "’À∫≈≤ª¥Ê‘⁄", Toast.LENGTH_SHORT).show();
				}
				Parser_getPassword(result);
				if (person.getPassword().equals(password)) {
					Intent intent = new Intent(LoginActivity.this, TiebaActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(LoginActivity.this, "√‹¬Î¥ÌŒÛ", Toast.LENGTH_SHORT).show();
				}
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
	private void Parser_getPassword(String xml) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("account")) {
						person.setAccount(parser.nextText());
					}
					if (parser.getName().equals("password")) {
						person.setPassword(parser.nextText());
					}
					if (parser.getName().equals("name")) {
						person.setName(parser.nextText());
					}
					if (parser.getName().equals("singtime")) {
						person.setSingTime(Integer.parseInt(parser.nextText()));
					}
					if (parser.getName().equals("exvalue")) {
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
}
