package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class PostRidetwo extends Activity {
	//EditText departText,returnText,departtime,returntime;
	EditText departdate,returndate;
	String departtext,returntext,departtimetext,returntimetext;
	TimePicker departtime, returntime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_ridetwo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_ridetwo, menu);
		return true;
	}
	public void next(View v){
		departdate = ((EditText)findViewById(R.id.depart_date));
		returndate = (EditText) findViewById(R.id.return_date);
		departtime = (TimePicker) findViewById(R.id.depart_time);
		returntime = (TimePicker) findViewById(R.id.return_time);
		Intent i=new Intent(this,OtherDriving.class);
		String from= getIntent().getStringExtra("from");
		String to= getIntent().getStringExtra("to");
		String meeting = getIntent().getStringExtra("meeting");
		departtext = departdate.getText().toString();
		returntext = returndate.getText().toString();
		departtimetext = departtime.getCurrentHour() + ":" + departtime.getCurrentMinute();
		returntimetext = returntime.getCurrentHour() + ":" + returntime.getCurrentMinute();
		
		i.putExtra("from", from);
		i.putExtra("to", to);
		i.putExtra("meeting",meeting);
		i.putExtra("depart", departtext);
		i.putExtra("return", returntext);
		i.putExtra("depart_time", departtimetext);
		i.putExtra("return_time", returntimetext );
		startActivity(i);
	}

}
