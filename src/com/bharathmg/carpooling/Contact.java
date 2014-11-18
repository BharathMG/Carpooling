package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Contact extends Activity {

	private String email;
	private String username;
	private String username_owner;
	EditText mailView;
	TextView nameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		username_owner = getIntent().getStringExtra("owner");
		nameView = (TextView)findViewById(R.id.editText1);
		mailView = (EditText)findViewById(R.id.editText2);
		ParseQuery query=ParseUser.getQuery();
		query.getInBackground(username_owner, new GetCallback(){
			
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				if(arg1 == null){
					username = arg0.getString("username");
					email = arg0.getString("email");
					nameView.setText(username);
					mailView.setText(email);
					Log.d("User name", arg0.getString("username") + " email : "+arg0.getString("email"));
				}
				else{
					Log.d("Exception :",arg1.getMessage());
				}
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}
	
	public void send(View v){
		EditText sub = (EditText)findViewById(R.id.editText3);
		String subject = sub.getText().toString();
		EditText body = (EditText)findViewById(R.id.editText4);
		String bodyStr = body.getText().toString();
		/*Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/html");
		intent.putExtra(Intent.EXTRA_EMAIL, email);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, bodyStr);

		startActivity(Intent.createChooser(intent, "Send Email to "+username)); */
		Intent send = new Intent(Intent.ACTION_SENDTO);
		String uriText = "mailto:" + Uri.encode(email) + 
		          "?subject=" + Uri.encode(subject) + 
		          "&body=" + Uri.encode(bodyStr);
		Uri uri = Uri.parse(uriText);

		send.setData(uri);
		startActivity(Intent.createChooser(send, "Send mail..."));

	}
	public void dummy(){
		
	}
	public void cancel(View v){
		super.onBackPressed();
	}

}
