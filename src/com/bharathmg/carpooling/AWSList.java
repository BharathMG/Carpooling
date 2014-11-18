package com.bharathmg.carpooling;

import java.util.ArrayList;
import java.util.List;

import com.bharathmg.carpooling.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class AWSList extends Activity {

	private ArrayAdapter<String> adapter;
	List<String> urls=new ArrayList<String>();
	ProgressBar pro;
	ArrayList<String> listI = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_awslist);
		final ListView listview = (ListView) findViewById(R.id.listView1);
		pro = (ProgressBar) findViewById(R.id.progressBar1);
		
		adapter=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1,
	            listI);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		ParseQuery query=new ParseQuery("IMAGE");
		listview.setAdapter(adapter);
		query.findInBackground(new FindCallback(){

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null){
				for(int i=0;i<arg0.size();i++){
					String notes = arg0.get(i).getString("notes");
					String URL = arg0.get(i).getString("image");
					urls.add(URL);
					listI.add("Notes : "+notes);
					pro.setVisibility(ProgressBar.INVISIBLE);
					adapter.notifyDataSetChanged();
				}
				}
				else{
					
				}
				
			}
			
		});
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
            	Uri uri = Uri.parse(urls.get(position));
            	startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }

          });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.awslist, menu);
		return true;
	}

}
