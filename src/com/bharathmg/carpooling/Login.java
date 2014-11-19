package com.bharathmg.carpooling;



import com.bharathmg.carpooling.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class Login extends Activity {
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	ProgressBar pro;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		 ParseAnalytics.trackAppOpened(getIntent());
		 if(ParseUser.getCurrentUser() != null){
			 Intent i = new Intent(getApplicationContext(),Driveoptions.class);
		     Toast.makeText(getApplicationContext(), "Welcome again!", Toast.LENGTH_LONG).show();
		     startActivity(i);
		 }
		//ParseUser.logOut();

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							pro=(ProgressBar)findViewById(R.id.progressBar1);
							pro.setVisibility(ProgressBar.VISIBLE);
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		//mLoginStatusView = findViewById(R.id.login_status);
		//mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						pro = (ProgressBar)findViewById(R.id.progressBar1);
				    	pro.setVisibility(ProgressBar.VISIBLE);
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			ParseUser.logInInBackground(mEmail, mPassword, new LogInCallback() {
				  public void done(ParseUser user, ParseException e) {
				    if (user != null) {
				    	pro = (ProgressBar)findViewById(R.id.progressBar1);
				    	pro.setVisibility(ProgressBar.GONE);
				     Intent i = new Intent(getApplicationContext(),Driveoptions.class);
				     Toast.makeText(getApplicationContext(), "Welcome again!", Toast.LENGTH_LONG).show();
				     startActivity(i);
				     
				    } else {
				    	pro = (ProgressBar)findViewById(R.id.progressBar1);
				    	pro.setVisibility(ProgressBar.GONE);
				      Log.w("FAIL", e.getMessage());// Signup failed. Look at the ParseException to see what happened.
				      Toast.makeText(getApplicationContext(), "Login failed. Try again."+e.getMessage(), Toast.LENGTH_LONG).show();
				    }
				  }
				});
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			
			ParseUser.logInInBackground(mEmail, mPassword, new LogInCallback() {
				  public void done(ParseUser user, ParseException e) {
				    if (user != null) {
				    	pro.setVisibility(ProgressBar.GONE);
				     Intent i = new Intent(getApplicationContext(),Driveoptions.class);
				     startActivity(i);
				     
				    } else {
				      Log.w("FAIL", "Login failed");// Signup failed. Look at the ParseException to see what happened.
				    }
				  }
				});
			
			return true;
			// TODO: register the new account here.
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			//showProgress(false);

			if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			//showProgress(false);
		}
	}
}
