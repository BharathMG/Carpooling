package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Passoptions extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passoptions);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passoptions, menu);
		return true;
	}
	
	public void postpic(View v){
		Intent i = new Intent (this , NotedAWS.class);
		startActivity(i);
	}
	public void gotoposts(View v){
		Intent i = new Intent (this , PassengerOne.class);
		startActivity(i);
	}

}
