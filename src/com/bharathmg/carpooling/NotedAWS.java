package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NotedAWS extends Activity {
	EditText n;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noted_aws);
		n= (EditText) findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.noted_aw, menu);
		return true;
	}
	public void next(View v){
		Intent i=new Intent(this,AWS.class);
		i.putExtra("notes", n.getText().toString());
		startActivity(i);
	}

}
