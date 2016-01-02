package com.example.singforyou;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		Login_TurnToRegister = (Button) findViewById(R.id.regist);
		Login_LoginBtn = (Button) findViewById(R.id.login);
		Login_accounts = (EditText) findViewById(R.id.accounts);
		Login_password = (EditText) findViewById(R.id.password);
		
		Login_TurnToRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
				startActivity(intent);
			}
		});
	}
	

}
