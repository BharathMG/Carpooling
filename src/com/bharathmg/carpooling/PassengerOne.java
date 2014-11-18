package com.bharathmg.carpooling;

import java.util.ArrayList;
import java.util.List;

import com.bharathmg.carpooling.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PassengerOne extends Activity {
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayList<String> listI = new ArrayList<String>();
	List<ParseObject> posts;

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
    int clickCounter=0;
    
	@SuppressLint({ "NewApi", "ShowToast" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passenger_one);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pro);
		progressBar.setVisibility(View.VISIBLE);
		final ListView listview = (ListView) findViewById(R.id.list);
		 adapter=new ArrayAdapter<String>(this,
		            android.R.layout.simple_list_item_1,
		            listI);
		 Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		 ParseQuery query = new ParseQuery("Post");
		 listview.setAdapter(adapter);
		 //query.whereEqualTo("playerName", "Dan Stemkoski");
		 query.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> scoreList, ParseException e) {
		         if (e == null) {
		        	 posts = scoreList;
		        	 for(int i=0;i<scoreList.size();i++){
		        		 listI.add("From: "+scoreList.get(i).getString("from") + "  To: "+scoreList.get(i).getString("to") + "  SEATS: "+scoreList.get(i).getNumber("totalseats"));
		        		 progressBar.setVisibility(View.GONE);
		        		 adapter.notifyDataSetChanged();
		        	 }
		        	 
		             Log.d("Ride", "Retrieved " + scoreList.size() + " scores");
		         } else {
		             Log.d("score", "Error: " + e.getMessage());
		         }
		     }

			
		 });
//		        listview.setAdapter(adapter);
//		        for (int i = 0; i < values.length; ++i) {
//		            listItems.add(values[i]);
//		          }
//		        adapter.notifyDataSetChanged();
		        
		        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> parent, final View view,
		                int position, long id) {
		            	Intent i=new Intent(getApplicationContext(),PassengerTwo.class);
		            	i.putExtra("position", position);
		            	i.putExtra("from",  posts.get(position).getString("from"));
		            	i.putExtra("owner",  posts.get(position).getString("owner"));
		            	i.putExtra("to",  posts.get(position).getString("to"));
		            	i.putExtra("depart",  posts.get(position).getString("depart"));
		            	i.putExtra("return",  posts.get(position).getString("return"));
		            	i.putExtra("seats",  posts.get(position).getNumber("totalseats"));
		            	i.putExtra("notes",  posts.get(position).getString("notes"));
		            	i.putExtra("cost",  posts.get(position).getString("cost"));
		            	i.putExtra("departtime", posts.get(position).getString("depart_time"));
		            	i.putExtra("returntime", posts.get(position).getString("return_time"));
		            	i.putExtra("meeting", posts.get(position).getString("meeting"));
		            	i.putExtra("id",  posts.get(position).getObjectId());
		            	startActivity(i);
		              final String item = (String) parent.getItemAtPosition(position);
		              //Toast.makeText(getApplicationContext(), item , Toast.LENGTH_SHORT);
		              Log.d("Pos", "Pos: " + item);
		            }

		          });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passenger_one, menu);
		return true;
	}
	public void addItems(View v) {
        
        
    }

}
