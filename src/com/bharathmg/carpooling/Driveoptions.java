package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.parse.Parse;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Driveoptions extends Activity {
	TextView welcome;
	ParseUser currentUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driveoptions);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		welcome= (TextView) findViewById(R.id.textView1);
		currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			welcome.setText("Welcome "+currentUser.getUsername() + " to Car pooling, Now you can enjoy our service and post your ride journey");
			//Toast.makeText(getApplicationContext(), "Hi "+ currentUser.getUsername() , Toast.LENGTH_LONG).show();
		} else {
		  // show the signup or login screen
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driveoptions, menu);
		return true;
	}
	public void post(View v){
		Intent i=new Intent(this,PostRideone.class);
		startActivity(i);
	}
	public void track(View v){
		Intent i=new Intent(this,Track.class);
		startActivity(i);
	}
	
	public void chat(View v){
		Intent i=new Intent(this,DriverChat.class);
		i.putExtra("user", currentUser.getUsername());
		startActivity(i);
	}

}
