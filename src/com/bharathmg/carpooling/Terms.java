package com.bharathmg.carpooling;

import com.bharathmg.carpooling.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class Terms extends Activity {
	WebView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		view = (WebView)findViewById(R.id.webView1);
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl("file:///android_asset/terms.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.terms, menu);
		return true;
	}

}
