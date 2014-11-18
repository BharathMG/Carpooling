package com.bharathmg.carpooling;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.bharathmg.carpooling.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class OtherDriving extends Activity {
	EditText cost,seats,notes;
	private String username;
	 ProgressBar pro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_driving);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other_driving, menu);
		return true;
	}
	public void cancel(View v){
		Intent i=new Intent(this,Driveoptions.class);
		startActivity(i);
	}
	public void post(View v){
		pro=(ProgressBar)findViewById(R.id.pro);
		pro.setVisibility(ProgressBar.VISIBLE);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			username = currentUser.getUsername();
		}
		cost = (EditText) findViewById(R.id.editText1);
		seats = (EditText) findViewById(R.id.editText2);
		notes = (EditText) findViewById(R.id.editText3);
		String from=getIntent().getStringExtra("from");
		String to=getIntent().getStringExtra("to");
		String meeting = getIntent().getStringExtra("meeting");
		String depart=getIntent().getStringExtra("depart");
		String returnt=getIntent().getStringExtra("return");
		String depart_time =getIntent().getStringExtra("depart_time");
		String return_time =getIntent().getStringExtra("return_time");
		String costStr = cost.getText().toString();
		String seatsStr = seats.getText().toString();
		String notesStr = notes.getText().toString();
		ParseObject details=new ParseObject("Post");
		details.put("owner", currentUser.getObjectId());
		details.put("from", from);
		details.put("to", to);
		details.put("meeting", meeting);
		details.put("depart", depart);
		details.put("return", returnt);
		details.put("cost", costStr);
		details.put("depart_time", depart_time);
		details.put("return_time", return_time);
		details.put("totalseats",Integer.parseInt(seatsStr));
		details.put("notes", notesStr);
		List<String> users=new ArrayList<String>();
		details.put("users", users);
		details.saveInBackground(new SaveCallback(){

			@SuppressLint("ShowToast")
			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				 if (e == null) {
					 pro.setVisibility(ProgressBar.INVISIBLE);
				     Log.d("Posted", "posted"); 
					 Toast.makeText(getApplicationContext(), "Posted successfully!", Toast.LENGTH_LONG).show();
					 Intent i=new Intent(getApplicationContext(),Driveoptions.class);
					 startActivity(i);
				    } else {
				      // something went wrong
				    	
				    	Log.d("NOT Posted", e.getMessage());
				    }
				
			} });
	}

}
