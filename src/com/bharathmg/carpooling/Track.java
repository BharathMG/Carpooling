package com.bharathmg.carpooling;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import com.bharathmg.carpooling.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Track extends Activity {
	ArrayList<String> listI = new ArrayList<String>();
	List<ParseObject> posts;
	private ArrayAdapter<String> adapter;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pro);
		progressBar.setVisibility(View.VISIBLE);
		final ListView listview = (ListView) findViewById(R.id.list);
		 adapter=new ArrayAdapter<String>(this,
		            R.layout.text_view,
		            listI);
		 ParseQuery query = new ParseQuery("Post");
		 query.whereEqualTo("owner", ParseUser.getCurrentUser().getObjectId());
		 listview.setAdapter(adapter);
		 //query.whereEqualTo("playerName", "Dan Stemkoski");
		 query.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> scoreList, ParseException e) {
		         if (e == null) {
		        	 progressBar.setVisibility(View.INVISIBLE);
		        	// posts = scoreList;
		        	 posts=scoreList;
		        	 if(scoreList.size() == 0){
		        		 Toast.makeText(getApplicationContext(), "You have not posted any rides! Go ahead and post a ride! Good luck!", Toast.LENGTH_LONG).show();
		        	 }
		        	 for(int i=0;i<scoreList.size();i++){
		        		 listI.add("From: "+scoreList.get(i).getString("from") + "  To: "+scoreList.get(i).getString("to") + "  SEATS: "+scoreList.get(i).getNumber("totalseats"));
		        		 //progressBar.setVisibility(View.GONE);
		        		 adapter.notifyDataSetChanged();
		        	 }
		        	 
		             Log.d("Ride", "Retrieved " + scoreList.size() + " scores");
		         } else {
		        	 progressBar.setVisibility(View.INVISIBLE);
		             Log.d("score", "Error: " + e.getMessage());
		         }
		     }

			
		 });
		 
		 listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, final View view,
	                int position, long id) {
	            	//Intent i=new Intent(getApplicationContext(),PassengerTwo.class);
	            	//i.putExtra("position", position);
	            	List<String> usersreg = new ArrayList<String>();
	            	usersreg=posts.get(position).getList("users");
	            	Log.d("Size",usersreg.size()+"");
	            	final List<String> userdetails=new ArrayList<String>();
	            	//Toast.makeText(getApplicationContext(), usersreg.size()+"", Toast.LENGTH_LONG).show();
	            	for(int j=0;j<usersreg.size();j++){
	            		String user = usersreg.get(j);
	            		ParseQuery query = new ParseQuery("User");
	            		query.getInBackground(user, new GetCallback() {
	            			  public void done(ParseObject object, ParseException e) {
	            			    if (e == null) {
	            			    	userdetails.add(object.getString("username")+":"+object.getString("age")+":"+object.getString("email"));
	            			    } else {
	            			      Log.d("Wrong",e.getMessage());
	            			    }
	            			  }
	            			});
	            		
	            		
	            	}
	            	if(usersreg.size() == 0){
	            		Toast.makeText(getApplicationContext(), "No users have joined!", Toast.LENGTH_LONG).show();
	            	}
	            	else{
	            	Intent i=new Intent(getApplicationContext(),TrackUsers.class);
	            	i.putStringArrayListExtra("details", (ArrayList<String>) usersreg);
	            	
	            	startActivity(i);
	            	}
	            	/*i.putExtra("from",  posts.get(position).getString("from"));
	            	i.putExtra("owner",  posts.get(position).getString("owner"));
	            	i.putExtra("to",  posts.get(position).getString("to"));
	            	i.putExtra("depart",  posts.get(position).getString("depart"));
	            	i.putExtra("return",  posts.get(position).getString("return"));
	            	i.putExtra("seats",  posts.get(position).getNumber("totalseats"));
	            	i.putExtra("notes",  posts.get(position).getString("notes"));
	            	i.putExtra("cost",  posts.get(position).getString("cost"));
	            	i.putExtra("id",  posts.get(position).getObjectId());
	            	startActivity(i);
*/	             // final String item = (String) parent.getItemAtPosition(position);
	              //Toast.makeText(getApplicationContext(), item , Toast.LENGTH_SHORT);
	              //Log.d("Pos", "Pos: " + item);
	            }

	          });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track, menu);
		return true;
	}

}
