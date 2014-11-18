package com.bharathmg.carpooling;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import com.bharathmg.carpooling.R;
import com.parse.Parse;





import android.location.Address;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends Activity {
	private MapView mapView;
	/*private MyLocationNewOverlay mMyLocationOverlay;
	private MapController mMapController;*/
	/*static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	  static final LatLng KIEL = new LatLng(53.551, 9.993);*/
	private MapView map;
	 GeoPoint addressPosition;
	 ProgressBar pro;
	 ProgressDialog dialog;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		pro = (ProgressBar) findViewById(R.id.progressBar2);
		pro.setVisibility(ProgressBar.VISIBLE);
		
		addressPosition = new GeoPoint(48.13, -1.63);
		String from = getIntent().getStringExtra("from");
		String to = getIntent().getStringExtra("to");
		map = (MapView) findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        GeoPoint startPoint= handleSearchLocationButton(from);
       // GeoPoint startPoint = new GeoPoint(48.13, -1.63);
        MapController mapController = map.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(9);
        RoadManager roadManager = new OSRMRoadManager();
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        GeoPoint end = handleSearchLocationButton(to);
        waypoints.add(end); //end point
        Road road = roadManager.getRoad(waypoints);
        PathOverlay roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
        map.getOverlays().add(roadOverlay);
        map.invalidate();
       // dialog.dismiss();
        pro.setVisibility(ProgressBar.INVISIBLE);
		/*map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
		        .title("Hamburg"));
		    Marker kiel = map.addMarker(new MarkerOptions()
		        .position(KIEL)
		        .title("Kiel")
		        .snippet("Kiel is cool")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.ic_launcher)));

		    // Move the camera instantly to hamburg with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);*/
		/*mapView = (MapView) findViewById(R.id.mapview);
		GeoPoint gPt = new GeoPoint(51500000, -150000);
		mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
		mapView.setBuiltInZoomControls(true);
		mMapController = mapView.getController();
		mMapController.setZoom(13);
		
		//Centre map near to Hyde Park Corner, London
		mMapController.setCenter(gPt);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	

}
