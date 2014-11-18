package com.bharathmg.carpooling;

import java.util.List;

import com.bharathmg.carpooling.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TrackDetails extends Activity {
	TextView ageview,nameview,emailview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_details);
		String age = getIntent().getStringExtra("age");
		String email = getIntent().getStringExtra("email");
		String name = getIntent().getStringExtra("name");
		nameview = (TextView)findViewById(R.id.textView1);
		ageview = (TextView)findViewById(R.id.TextView01);
		emailview = (TextView)findViewById(R.id.TextView02);
		nameview.setText("Name : "+name);
		ageview.setText(" Age: "+age);
		emailview.setText(" email: "+email);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track_details, menu);
		return true;
	}
	
	public void back(View v){
		Intent i=new Intent(this,Track.class);
		startActivity(i);
	}

}
