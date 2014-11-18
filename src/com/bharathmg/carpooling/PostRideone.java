package com.bharathmg.carpooling;

import java.io.BufferedInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import com.bharathmg.carpooling.R;
import com.google.android.maps.*;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




import com.urbanairship.util.Base64.InputStream;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PostRideone extends Activity {
	
	private static double longitute;
	private static double latitude;
	EditText fromText,toText,meetingText;
	ImageView photo;
	private MapView mapView;
	String from,to;
	private MyLocationNewOverlay mMyLocationOverlay;
	private MapController mMapController;
	private GeoPoint destinationPoint;
	private Object markerDestination;
	private MapView map,maptwo;
	private GeoPoint addressPosition;
	ProgressBar pro;
//	@Override
//	protected boolean isRouteDisplayed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_rideone);
		addressPosition = new GeoPoint(48.13, -1.63);
		fromText = (EditText) findViewById(R.id.editText1);
		pro= (ProgressBar)findViewById(R.id.progressBar1);
		
		toText = (EditText) findViewById(R.id.editText2);
		meetingText = (EditText) findViewById(R.id.EditText01);
		
		addressPosition = new GeoPoint(48.13, -1.63);
		
		
		//getLatitudeAndLongitudeFromGoogleMapForAddress("1600 Pennsylvania Ave NW, Washington, DC 20500, United States");
		//getLatLong(js);
		/*mapView = (MapView) findViewById(R.id.mapview);
		mapView.setUseSafeCanvas(false);
		//enable zoom controls
		mapView.setBuiltInZoomControls(true);

		//enable multitouch
		mapView.setMultiTouchControls(true);
		//GpsMyLocationProvider can be replaced by your own class. It provides the position information through GPS or Cell towers.
		GpsMyLocationProvider imlp = new GpsMyLocationProvider(this.getBaseContext());
		//minimum distance for update
		imlp.setLocationUpdateMinDistance(1000);
		//minimum time for update
		imlp.setLocationUpdateMinTime(60000);*/
		//GeoPoint gPt = new GeoPoint(51500000, -150000);
		//ResourceProxyImpl resProxyImp = new ResourceProxyImpl(this);
//		mMyLocationOverlay = new MyLocationNewOverlay(this.getBaseContext(),imlp , mapView);
//		mMyLocationOverlay.setUseSafeCanvas(false);
//		mMyLocationOverlay.setDrawAccuracyEnabled(true);
//
//		mapView.getOverlays().add(mMyLocationOverlay);
		/*mapView = (MapView) findViewById(R.id.mapview);
		mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
		mapView.setBuiltInZoomControls(true);
		mMapController = mapView.getController();
		mMapController.setZoom(13);
		
		//Centre map near to Hyde Park Corner, London
		mMapController.setCenter(gPt);
		*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_rideone, menu);
		return true;
	}
	
	public boolean getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress){

	    Geocoder coder = new Geocoder(getApplicationContext(),Locale.getDefault());
	    List<Address> address;
	    try 
	    {
	        address = coder.getFromLocationName(searchedAddress,5);
	        if (address == null) {
	            Log.d("A", "############Address not correct #########");
	        }
	        Address location = address.get(0);

	        Log.d("A", "Address Latitude : "+ location.getLatitude()+ "Address Longitude : "+ location.getLongitude());
	        return true;

	    }
	    catch(Exception e)
	    {
	        Log.d("A", "MY_ERROR : ############Address Not Found");
	        return false;
	    }
	}
	
	public void next(View v){
		fromText = (EditText) findViewById(R.id.editText1);
		toText = (EditText) findViewById(R.id.editText2);
		Intent i = new Intent(this,PostRidetwo.class);
		i.putExtra("from", fromText.getText().toString());
		i.putExtra("to", toText.getText().toString());
		i.putExtra("meeting", meetingText.getText().toString());
		startActivity(i);
	}
	
	public void loadBitmap(String url) throws IOException {
		URL newurl;
		
			newurl = new URL(url);
		
		Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
		//photo =(ImageView) findViewById(R.id.imageView1);
		//photo.setImageBitmap(mIcon_val);
	}
	private static JSONObject getLocationInfo(String address)
	{

	    StringBuilder stringBuilder = new StringBuilder();
	    try {

	    address = address.replaceAll(" ","%20");    

	    HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
	    HttpClient client = new DefaultHttpClient();
	    HttpResponse response;
	    stringBuilder = new StringBuilder();


	        response = client.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        InputStream stream = (InputStream) entity.getContent();
	        int b;
	        while ((b = stream.read()) != -1) {
	            stringBuilder.append((char) b);
	        }
	    } catch (ClientProtocolException e) {
	        Log.i("getLocationInfo ClientProtocolException", e.toString());
	    } catch (IOException e) {

	        Log.i("getLocationInfo IOException", e.toString());
	    }


	    JSONObject jsonObject = new JSONObject();
	    try {
	        jsonObject = new JSONObject(stringBuilder.toString());
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        Log.i("getLocationInfo JSONException", e.toString());
	    }
	    Log.d("Lat", jsonObject.toString());
	    return jsonObject;
	}

	private static void getLatLong(JSONObject jsonObject) 
	{

	    try {

	       longitute = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
	        Log.i("Log1", longitute+"");
	    latitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
	            .getJSONObject("geometry").getJSONObject("location")
	            .getDouble("lat");
	        Log.i("lat1", latitude+"");
	    } catch (JSONException e) {

	        longitute=0;
	        latitude = 0;
	        Log.i("getLatLong", e.toString());
	        

	    }

	    
	}
	public GeoPoint handleSearchLocationButton(String place){
	       // EditText destinationEdit = (EditText)findViewById(R.id.editText1);
	        String destinationAddress = place;
	        GeocoderNominatim geocoder = new GeocoderNominatim(this);
	        try {
	                List<Address> foundAdresses = geocoder.getFromLocationName(destinationAddress, 1);
	                if (foundAdresses.size() == 0) { //if no address found, display an error
	                        Toast.makeText(this, "Address not found.", Toast.LENGTH_SHORT).show();
	                } else {
	                        Address address = foundAdresses.get(0); //get first address
	                        addressPosition = new GeoPoint(
	                                        address.getLatitude(), 
	                                        address.getLongitude());
	                        //longPressHelper(addressPosition);
	                       // return addressPosition;
	                        //map.getController().setCenter(addressPosition);
	                }
	                
	        } catch (Exception e) {
	                Toast.makeText(this, "Geocoding error", Toast.LENGTH_SHORT).show();
	        }
	        return addressPosition;
	}
	public void handleSearchLocationButton(){
        EditText destinationEdit = (EditText)findViewById(R.id.editText1);
        String destinationAddress = destinationEdit.getText().toString();
        GeocoderNominatim geocoder = new GeocoderNominatim(this);
        try {
                List<Address> foundAdresses = geocoder.getFromLocationName(destinationAddress, 1);
                if (foundAdresses.size() == 0) { //if no address found, display an error
                        Toast.makeText(this, "Address not found.", Toast.LENGTH_SHORT).show();
                } else {
                        Address address = foundAdresses.get(0); //get first address
                        GeoPoint addressPosition = new GeoPoint(
                                        address.getLatitude(), 
                                        address.getLongitude());
                        //longPressHelper(addressPosition);
                        map.getController().setCenter(addressPosition);
                }
        } catch (Exception e) {
        		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Geocoding error", Toast.LENGTH_SHORT).show();
        }
}
	public void handleSearchLocationButtonTwo(){
		EditText destinationEdit = (EditText)findViewById(R.id.editText2);
        String destinationAddress = destinationEdit.getText().toString();
        GeocoderNominatim geocoder = new GeocoderNominatim(this);
        try {
                List<Address> foundAdresses = geocoder.getFromLocationName(destinationAddress, 1);
                if (foundAdresses.size() == 0) { //if no address found, display an error
                        Toast.makeText(this, "Address not found.", Toast.LENGTH_SHORT).show();
                } else {
                        Address address = foundAdresses.get(0); //get first address
                        GeoPoint addressPosition = new GeoPoint(
                                        address.getLatitude(), 
                                        address.getLongitude());
                        //longPressHelper(addressPosition);
                        maptwo.getController().setCenter(addressPosition);
                }
        } catch (Exception e) {
                Toast.makeText(this, "Geocoding error", Toast.LENGTH_SHORT).show();
        }
	}
	public boolean longPressHelper(IGeoPoint p) {
        //On long-press, we update the trip destination:
        destinationPoint = new GeoPoint((GeoPoint)p);
       // markerDestination = putMarkerItem(markerDestination, destinationPoint, 
             //   "Destination", R.drawable.ic_launcher, R.drawable.ic_launcher);
        //getRoadAsync(startPoint, destinationPoint);
        //getPOIAsync(destinationPoint, poiTagText.getText().toString());
        return true;
}

	

}
