package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.parse.Parse;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class DriveHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drive_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drive_home, menu);
		return true;
	}
	public void login(View v){
		Intent i = new Intent(this,Login.class);
		startActivity(i);
	}
	public void register(View v){
		Intent i = new Intent(this,DrivingDetails.class);
		startActivity(i);
	}

}
