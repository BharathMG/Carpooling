package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
	Button logout;
	ParseUser current_user;
	ProgressBar pro;
	TextView userName;
	public static final String TAG = Home.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		ParseAnalytics.trackAppOpened(getIntent());
		logout = (Button) findViewById(R.id.Button01);
		pro = (ProgressBar) findViewById(R.id.progressBar1);
		userName = (TextView) findViewById(R.id.textView3);
		userName.setVisibility(View.GONE);
		current_user = ParseUser.getCurrentUser();
		if (current_user != null) {
			logout.setVisibility(Button.VISIBLE);
			userName.setVisibility(View.VISIBLE);
			userName.setText("Hi! " + current_user.getUsername());

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void logout(View v) {
		ParseUser.logOut();
		current_user = null;
		Toast.makeText(getApplicationContext(), "Logged out!!", Toast.LENGTH_LONG).show();
		logout.setVisibility(Button.INVISIBLE);
		userName.setVisibility(View.INVISIBLE);

	}

	public void aboutus(View v) {
		Intent i = new Intent(this, About.class);
		startActivity(i);

	}

	public void drivepage(View v) {
		if (current_user != null) {
			Intent i = new Intent(this, Driveoptions.class);
			Toast.makeText(getApplicationContext(), "Welcome again!", Toast.LENGTH_LONG).show();
			this.startActivity(i);
		} else {
			Intent i = new Intent(this, DriveHome.class);
			this.startActivity(i);
		}
	}

	public void passenger(View v) {
		Intent i = new Intent(this, Passoptions.class);
		this.startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();
		current_user = ParseUser.getCurrentUser();
		if (current_user != null) {
			logout.setVisibility(Button.VISIBLE);
		}
	}

}
