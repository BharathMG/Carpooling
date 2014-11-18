package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;
import com.parse.Parse;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class DriverChat extends Activity {

	private WebView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_chat);
		String name = getIntent().getStringExtra("user");
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		view = (WebView)findViewById(R.id.webView1);
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl("file:///android_asset/driver.html?my="+name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driver_chat, menu);
		return true;
	}

}
