package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Chat extends Activity {
	WebView view;
	EditText nameView;
	private String name;
	ProgressBar pro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		pro = (ProgressBar) findViewById(R.id.progressBar1);
		view = (WebView) findViewById(R.id.webView1);
		
		
	}
	public void openchat(View v){
		pro.setVisibility(ProgressBar.VISIBLE);
		nameView = (EditText) findViewById(R.id.editText1);
		name = nameView.getText().toString();
		if(name.length() == 0){
			Toast.makeText(getApplicationContext(), "Please enter your name to continue", Toast.LENGTH_LONG).show();
		}
		else{
			nameView.setVisibility(EditText.INVISIBLE);
			Button sendButton = (Button) findViewById(R.id.button1);
			sendButton.setVisibility(Button.INVISIBLE);
			view.setVisibility(WebView.VISIBLE);
			String user_object = getIntent().getStringExtra("user_object");
			ParseQuery query=ParseUser.getQuery();
			query.getInBackground(user_object, new GetCallback(){

				@Override
				public void done(ParseObject arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					if(arg1 == null){
						String username =arg0.getString("username");
						view = (WebView)findViewById(R.id.webView1);
						view.getSettings().setJavaScriptEnabled(true);
						view.loadUrl("file:///android_asset/index.html?user="+username+"&my="+name);
						pro.setVisibility(ProgressBar.INVISIBLE);
						
					
					}
					else{
						Log.d("Error", arg1.getMessage());
						Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
					}
					
				}
				
			});
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

}
