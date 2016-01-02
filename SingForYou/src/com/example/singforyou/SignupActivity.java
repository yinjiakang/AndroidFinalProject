package com.example.singforyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity {
	private EditText Signup_name, Signup_password;
	private Button Signup_register_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Signup_name = (EditText) findViewById(R.id.name);
		Signup_password = (EditText) findViewById(R.id.password);;
		Signup_register_btn = (Button) findViewById(R.id.register_btn);
		
		Signup_register_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// implement needed
				
			}
		});
	}
	
	

}
