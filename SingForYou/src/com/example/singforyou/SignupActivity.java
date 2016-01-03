package com.example.singforyou;

import java.io.StringReader;

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
				/*
				 * need db
				 * post    account
				 * if exists , return Person
				 * else return empty string
				 */
				String result = "sad";
				if (result.equals("")) {
					Initialize_person(account, password, name, 8, 0);
					Intent intent = new Intent(SignupActivity.this, TiebaActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(SignupActivity.this, "账户名已存在", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void Initialize_person(String ac, String ps, String na, int st, int exv) {
		LoginActivity.person.setAccount(ac);
		LoginActivity.person.setPassword(ps);
		LoginActivity.person.setName(na);
		LoginActivity.person.setSingTime(st);
		LoginActivity.person.setExperienceValue(exv);
	}
	

}
