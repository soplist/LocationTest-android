package com.example.locationtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView positionTextView;
	
	private LocationManager locationManager;
	
	private String provider;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		positionTextView = (TextView) findViewById(R.id.position_text_view);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		List<String> providerList = locationManager.getProviders(true);
		if(providerList.contains(LocationManager.GPS_PROVIDER)){
			provider = LocationManager.GPS_PROVIDER;
		}else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
			provider = LocationManager.NETWORK_PROVIDER;
		}else{
			Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
			return;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		while(location == null){
			locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
		}
		if(location != null){
			showLocation(location);
		}
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
		
		
	}
	
	protected void onDestroy(){
		super.onDestroy();
		if(locationManager != null){
			locationManager.removeUpdates(locationListener);
		}
	}
	
	LocationListener locationListener = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			showLocation(location);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private void showLocation(Location location){
		String currentPosition = "latitude is "+location.getLatitude()+"\n"
				+"longitude is "+location.getLongitude();
		positionTextView.setText(currentPosition);
	}
}
