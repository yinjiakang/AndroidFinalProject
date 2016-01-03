package com.example.singforyou;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private Button Login_TurnToRegister, Login_LoginBtn;
	private EditText Login_accounts, Login_password;
	protected static Person person;
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
	

}
