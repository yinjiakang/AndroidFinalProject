package com.example.singforyou.home;

import com.example.singforyou.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MarkActivity extends Activity {

	
	private EditText edtitle, edcon;
	private Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mark_activity);
		edtitle = (EditText)findViewById(R.id.markactivity_title);
		edcon = (EditText)findViewById(R.id.markactivity_content);
		b = (Button)findViewById(R.id.markactivity_post);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
