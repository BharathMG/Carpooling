package com.bharathmg.carpooling;

import java.util.ArrayList;
import java.util.List;

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

public class TrackUsers extends Activity {
	ListView list;
	String age,email,name;
	private ArrayAdapter<String> adapter;
	ArrayList<String> listI = new ArrayList<String>();
	private List<String> usersreg,ages,emails;
	private ArrayList<String> detailsusers=new ArrayList<String>();;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_users);
		final ProgressBar pro=(ProgressBar)findViewById(R.id.progressBar1);
		pro.setVisibility(ProgressBar.VISIBLE);
		final List<String> details=getIntent().getStringArrayListExtra("details");
		list = (ListView)findViewById(R.id.list);
		adapter=new ArrayAdapter<String>(this,
	            R.layout.text_view,
	            detailsusers);
		list.setAdapter(adapter);
		usersreg=details;
		if(usersreg.size() <= 0){
			pro.setVisibility(ProgressBar.INVISIBLE);
			Toast.makeText(getApplicationContext(), "No users have joined!!", Toast.LENGTH_LONG).show();
		}
		for(int j=0;j<usersreg.size();j++){
			
    		String user = usersreg.get(j);
    		
    		ParseQuery query = ParseUser.getQuery();
    		
    		ages=new ArrayList<String>();
    		emails=new ArrayList<String>();
    		
    		query.getInBackground(user, new GetCallback(){
    			
				@Override
				public void done(ParseObject arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					pro.setVisibility(ProgressBar.INVISIBLE);
					if (arg1 == null) {
						age = arg0.getString("age");
						ages.add(age);
						email = arg0.getString("email");
						emails.add(email);
						name = arg0.getString("username");
    			    	//Toast.makeText(getApplicationContext(),arg0.getString("age")+":"+arg0.getString("email") , Toast.LENGTH_SHORT).show();
    			    	detailsusers.add(arg0.getString("username"));
    			    	adapter.notifyDataSetChanged();
    			    	pro.setVisibility(ProgressBar.INVISIBLE);
    			    } else {
    					pro.setVisibility(ProgressBar.INVISIBLE);
    			      Log.d("Wrong",arg1.getMessage());
    			    }
					
				}
    			
    		});
    		
    	}
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
            	//Intent i=new Intent(getApplicationContext(),PassengerTwo.class);
            	//i.putExtra("position", position);
            	
            	Intent i=new Intent(getApplicationContext(),TrackDetails.class);
            	//i.putStringArrayListExtra("details", (ArrayList<String>) usersreg);
            	age = ages.get(position);
            	email = emails.get(position);
            	name = detailsusers.get(position);
            	i.putExtra("age", age);
            	i.putExtra("email", email);
            	i.putExtra("name", name);
            	startActivity(i);
            }

          });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track_users, menu);
		return true;
	}

}
