package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PassengerTwo extends Activity {
	TextView details,fromview,toview,seatsview,notesview,costview,journeytime,journeymins,meetingview;
	private String owner;
	String username,email,from,to;

	private int seats;
	private String username_owner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passenger_two);
		int position = getIntent().getIntExtra("position", 10);
		from = getIntent().getStringExtra("from");
		owner=getIntent().getStringExtra("id");
		username_owner = getIntent().getStringExtra("owner");
		to = getIntent().getStringExtra("to");
		String depart = getIntent().getStringExtra("depart");
		String returns = getIntent().getStringExtra("return");
		String departtime = getIntent().getStringExtra("departtime");
		String returntime = getIntent().getStringExtra("returntime");
		seats= getIntent().getIntExtra("seats",0);
		String notes = getIntent().getStringExtra("notes");
		String cost = getIntent().getStringExtra("cost");
		String meeting = getIntent().getStringExtra("meeting");
		Log.d("Pos", position+" "+" from "+from+" to "+to+" depart "+depart+" return "+returns+" cos "+cost+" seats "+seats+" notes "+notes );
		details = (TextView) findViewById(R.id.textView1);
		fromview = (TextView) findViewById(R.id.TextView02);
		toview = (TextView) findViewById(R.id.TextView01);
		seatsview = (TextView) findViewById(R.id.TextView03);
		notesview = (TextView) findViewById(R.id.TextView04);
		costview = (TextView) findViewById(R.id.TextView05);
		journeytime = (TextView) findViewById(R.id.TextView06);
		journeymins = (TextView) findViewById(R.id.TextView07);
		meetingview = (TextView) findViewById(R.id.TextView08);
		meetingview.setText("Meeting point :"+meeting);
		fromview.setText("From : "+from);
		toview.setText("To : "+to);
		String hours = departtime.substring(0, departtime.indexOf(":"));
		String mins = departtime.substring(departtime.indexOf(":")+1);
		Log.d("TIME", mins);
		int hrs = Integer.parseInt(hours);
		int startmins = Integer.parseInt(mins);
		String endhrs = returntime.substring(0, returntime.indexOf(":"));
		String endmins = returntime.substring(returntime.indexOf(":")+1);
		Log.d("TIME", endmins);
		
		int ends = Integer.parseInt(endhrs);
		int tempmin= 60 - startmins;
		
		int endminsin = Integer.parseInt(endmins);
		int tempend = tempmin + endminsin;
		Log.d("Mins:", tempend+"");
		int total = ends-hrs;
		int rem = (tempend % 60);
		total+= tempend / 60;
		Log.d("TIME", total+"");
		details.setText("Depart Date-"+depart+" Return Date-"+returns);
		journeymins.setText("Depart Time: "+departtime+"\n Return Time: "+returntime);
		notesview.setText("Notes : "+notes);
		costview.setText("Cost : "+cost);
		seatsview.setText("Seats available : "+seats+"");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passenger_two, menu);
		return true;
	}
	public void seatsshow(View v){
		
		/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);*/
 
			// set title
			/*alertDialogBuilder.setTitle("Seats available");
 
			// set dialog message
			alertDialogBuilder
				.setMessage(seats)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						
					}
				  });
				
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();*/
		Toast.makeText(getApplicationContext(), "Total available seats : "+seats, Toast.LENGTH_LONG).show();
	}
	public void chat(View v){
		Intent i=new Intent(this,Chat.class);
		i.putExtra("user_object", username_owner);
		startActivity(i);
		}
	public void joinuser(View v){
		if(seats > 0){
		Intent i=new Intent(this,Joinuser.class);
		i.putExtra("owner", owner);
		startActivity(i);
		}
		else{
			Toast.makeText(getApplicationContext(), "Sorry. All the seats are filled already. Try other posts", Toast.LENGTH_LONG).show();
			Log.d("Pass","pass");
		}
	}
	public void contact(View v){
		Intent i=new Intent(this,Contact.class);
		i.putExtra("owner", username_owner); 
		startActivity(i);
			}
}
