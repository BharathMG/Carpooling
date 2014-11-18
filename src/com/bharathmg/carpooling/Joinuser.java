package com.bharathmg.carpooling;

import java.util.List;

import com.bharathmg.carpooling.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Joinuser extends Activity {
	EditText name,age,number,notes,email;
	private String username,owner;
	ProgressBar progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joinuser);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		name = (EditText) findViewById(R.id.editText1);
		age= (EditText) findViewById(R.id.editText2);
		number = (EditText) findViewById(R.id.editText3);
		notes = (EditText) findViewById(R.id.editText4);
		email = (EditText) findViewById(R.id.EditText01);
		ParseUser user = ParseUser.getCurrentUser();
		if(user != null){
		String id = user.getObjectId();
		ParseQuery query = ParseUser.getQuery();
		query.getInBackground(id, new GetCallback(){

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				if(arg1 == null){
					
					name.setText(arg0.getString("username"));
					age.setText(arg0.getString("age"));
					//number.setText(arg0.getString("number"))
					notes.setText(arg0.getString("notes"));
					email.setText(arg0.getString("email"));
				}
				else{
					Log.d("Error", arg1.getMessage());
					Toast.makeText(getApplicationContext(), arg1.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}
			
		});
		}
		owner = getIntent().getStringExtra("owner");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.joinuser, menu);
		return true;
	}
	
	public void joinuser(View v){
		progress=(ProgressBar)findViewById(R.id.progressBar1);
		progress.setVisibility(ProgressBar.VISIBLE);
		
		final ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			//username = currentUser.getUsername();
			ParseQuery query = new ParseQuery("Post");
			query.getInBackground(owner, new GetCallback() {
			  public void done(ParseObject object, ParseException e) {
			    if (e == null) {
			      List<String> userslist = object.getList("users");
			      if(!userslist.contains(currentUser.getObjectId())) {
			      object.add("users", currentUser.getObjectId());
			      object.increment("totalseats",-1);
			      object.saveInBackground(new SaveCallback(){

					@Override
					public void done(ParseException e) {
						if(e == null){
							Log.d("Updated","updated");
							Toast.makeText(getApplicationContext(), "You have successfully joined", Toast.LENGTH_LONG).show();
							
							progress.setVisibility(ProgressBar.GONE);
							Intent i=new Intent(getApplicationContext(),PassengerOne.class);
							startActivity(i);
						}
						else{
							Log.d("Error",e.getMessage());
							progress.setVisibility(ProgressBar.GONE);
						}
						
					}
			    	  
			      }); 
			      
			      
			      Log.d("Joined", object.toString());
			      }
			      else{
			    	  progress.setVisibility(ProgressBar.GONE);
			    	  Toast.makeText(getApplicationContext(), "You have already joined this ride!!", Toast.LENGTH_LONG).show();
			      }
			      
			      
			    } else {
			      Log.d("Error",e.getMessage());
			    }
			  }
			});
		
		} else {
		 
		 ParseUser user = new ParseUser();
			user.setUsername(name.getText().toString());
			user.setPassword(number.getText().toString());
			user.setEmail(email.getText().toString());
			
			 
			// other fields can be set just like with ParseObject
			user.put("age", age.getText().toString());
			
			user.put("notes", notes.getText().toString());
			 
			user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
				 // pro.setVisibility(ProgressBar.INVISIBLE);
			    if (e == null) {
			    	ParseQuery query = new ParseQuery("Post");
					query.getInBackground(owner, new GetCallback() {
					  public void done(final ParseObject object, ParseException e) {
					    if (e == null) {
					    	ParseUser.logInInBackground(name.getText().toString(), number.getText().toString(), new LogInCallback() {
								  public void done(ParseUser user, ParseException e) {
								    if (user != null) {
								    	//pro = (ProgressBar)findViewById(R.id.progressBar1);
								    	//pro.setVisibility(ProgressBar.GONE);
								    	object.add("users", user.getObjectId());
									      object.increment("totalseats",-1);
									      object.saveInBackground(new SaveCallback(){

											@Override
											public void done(ParseException e) {
												if(e == null){
													Log.d("Updated","updated");
													Toast.makeText(getApplicationContext(), "You have successfully joined", Toast.LENGTH_LONG).show();
													
													progress.setVisibility(ProgressBar.INVISIBLE);
													Intent i=new Intent(getApplicationContext(),PassengerOne.class);
													startActivity(i);
												}
												else{
													Log.d("Error",e.getMessage());
													progress.setVisibility(ProgressBar.INVISIBLE);
												}
												
											}
									    	  
									      });
									      Log.d("Joined", object.toString());
								     
								    } else {
								      Log.w("FAIL", e.getMessage());
								      progress.setVisibility(ProgressBar.INVISIBLE);
								      Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_LONG).show();// Signup failed. Look at the ParseException to see what happened.
								     // Toast.makeText(getApplicationContext(), "Login failed. Try again.", Toast.LENGTH_LONG).show();
								    }
								  }
								});
					      
					      
					      
					    } else {
					      Log.d("Error",e.getMessage());
					    }
					  }
					});
			    } else {
			      //Log.w("ERROR", "Sign up failed parse");
			    	ParseQuery query = new ParseQuery("Post");
					query.getInBackground(owner, new GetCallback() {
					  public void done(final ParseObject object, ParseException e) {
					    if (e == null) {
					    	ParseUser.logInInBackground(name.getText().toString(), number.getText().toString(), new LogInCallback() {
								  public void done(ParseUser user, ParseException e) {
								    if (user != null) {
								    	//pro = (ProgressBar)findViewById(R.id.progressBar1);
								    	//pro.setVisibility(ProgressBar.GONE);
								    	List<String> userslist = object.getList("users");
								    	if(!(userslist.contains(user.getObjectId()))) {
								    	object.add("users", user.getObjectId());
									      object.increment("totalseats",-1);
									      object.saveInBackground(new SaveCallback(){

											@Override
											public void done(ParseException e) {
												if(e == null){
													Log.d("Updated","updated");
													Toast.makeText(getApplicationContext(), "You have successfully joined", Toast.LENGTH_LONG).show();
													
													progress.setVisibility(ProgressBar.INVISIBLE);
													Intent i=new Intent(getApplicationContext(),PassengerOne.class);
													startActivity(i);
												}
												else{
													Log.d("Error",e.getMessage());
													progress.setVisibility(ProgressBar.INVISIBLE);
												}
												
											}
									    	  
									      });
									      Log.d("Joined", object.toString());
								    }
								    	else{
								    		Log.w("Signed", "user already signed");
								    		progress.setVisibility(ProgressBar.INVISIBLE);
								    		Toast.makeText(getApplicationContext(), "You have already joined this ride!!", Toast.LENGTH_LONG).show();
								    	}
								     
								    } else {
								      Log.w("FAIL", e.getMessage());
								      progress.setVisibility(ProgressBar.INVISIBLE);
								      Toast.makeText(getApplicationContext(), "You have signed up before. Please provide your contact number correctly", Toast.LENGTH_LONG).show();// Signup failed. Look at the ParseException to see what happened.
								     // Toast.makeText(getApplicationContext(), "Login failed. Try again.", Toast.LENGTH_LONG).show();
								    }
								  }
								});
					      
					      
					      
					    } else {
					      Log.d("Error",e.getMessage());
					    }
					  }
					});
			      //Toast.makeText(getApplicationContext(), "Failed. Please try again", Toast.LENGTH_SHORT).show();		      
			    }
			  }
			});
		}
		
		
	}

}
