package com.bharathmg.carpooling;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.PathOverlay;

import com.bharathmg.carpooling.R;
import com.parse.Parse;

import android.location.Address;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class Maps extends Activity {

	private MapView map;
	private ItemizedOverlay myItemizedOverlay;
	private Object myLocationOverlay;
	private GeoPoint destinationPoint;
	private Object markerDestination;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		Parse.initialize(this, "14na9mFYIWfa5jI8lBetFNtvYx0V8ZZiP9wZS3p4", "WXU6n8Ny9ThvmAtscJ14gf5VgJZuT7MM9WkDaC09");
		map = (MapView) findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        GeoPoint startPoint = new GeoPoint(48.13, -1.63);
        MapController mapController = map.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(9);
        /*Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);
        
        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        
        myItemizedOverlay = new ItemizedOverlay(marker, resourceProxy);
        map.getOverlays().add(myItemizedOverlay);
        
        GeoPoint myPoint1 = new GeoPoint(0*1000000, 0*1000000);
        ((Object) myItemizedOverlay).addItem(myPoint1, "myPoint1", "myPoint1");
        GeoPoint myPoint2 = new GeoPoint(50*1000000, 50*1000000);
        ((Object) myItemizedOverlay).addItem(myPoint2, "myPoint2", "myPoint2");
        
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        map.getOverlays().add((Overlay) myLocationOverlay);
        ((MyLocationOverlay) myLocationOverlay).enableMyLocation(); */
       /* RoadManager roadManager = new OSRMRoadManager();
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        waypoints.add(new GeoPoint(48.4, -1.9)); //end point
        Road road = roadManager.getRoad(waypoints);
        PathOverlay roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
        map.getOverlays().add(roadOverlay);
        map.invalidate();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}
	public void handleSearchLocationButton(){
       // EditText destinationEdit = (EditText)findViewById(R.id.editDestination);
        String destinationAddress = "London";
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
                        longPressHelper(addressPosition);
                        map.getController().setCenter(addressPosition);
                }
        } catch (Exception e) {
                Toast.makeText(this, "Geocoding error", Toast.LENGTH_SHORT).show();
        }
}
	public boolean longPressHelper(IGeoPoint p) {
        //On long-press, we update the trip destination:
        destinationPoint = new GeoPoint((GeoPoint)p);
       // markerDestination = putMarkerItem(markerDestination, destinationPoint, 
       //         "Destination", R.drawable.marker_b, R.drawable.jessica);
       // getRoadAsync(startPoint, destinationPoint);
        //getPOIAsync(destinationPoint, poiTagText.getText().toString());
        return true;
}

}
