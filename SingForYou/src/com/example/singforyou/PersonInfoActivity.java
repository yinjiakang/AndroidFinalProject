package com.example.singforyou;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class PersonInfoActivity extends Activity {

	
	private TextView name, sex, tel, mail, add;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_activity);
        name = (TextView)findViewById(R.id.person_name);
        sex = (TextView)findViewById(R.id.person_sex);
        tel = (TextView)findViewById(R.id.person_tel);
        mail = (TextView)findViewById(R.id.person_mail);
        add = (TextView)findViewById(R.id.person_add);
        name.setText(LoginActivity.person.getName());
        
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user, menu);
        return true;
    }*/
}

