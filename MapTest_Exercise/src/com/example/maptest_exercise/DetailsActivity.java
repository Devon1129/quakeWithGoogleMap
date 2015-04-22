package com.example.maptest_exercise;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsActivity extends Activity{
    //static LatLng NKUT = new LatLng(0, 0);    
    private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		buildViews();

		//LatLng pos = new LatLng(25.033611, 121.565000);
		
		//取得地圖物件
//		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        //Marker nkut = map.addMarker(new MarkerOptions().position(NKUT).title("南開科技大學").snippet("數位生活創意系"));
        
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
		
	}
	
	private void buildViews(){
		TextView text = (TextView)findViewById(R.id.tvMap);
		
		Bundle bundle = this.getIntent().getExtras();
		double lon = bundle.getDouble("lon");
		double lat = bundle.getDouble("lat");
		
		Toast.makeText(DetailsActivity.this, lon + "|" +  lat, Toast.LENGTH_SHORT).show();
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		Marker nkut = map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 8));
		
//		MarkerOptions markerOpt = new MarkerOptions();
//		markerOpt.position(new LatLng(lon, lat));
		
		
	}
	
//	private String readFromAss(int index){
//		String text = null;
//		String fileNa = "news" + index + ".txt";
//		try{
//			InputStream inpSt = getAssets().open(fileNa);
//			int size = inpSt.available();
//			byte[] buffer = new byte[size];
//			inpSt.read(buffer);
//			inpSt.close();
//			text = new String(buffer);
//		}catch(IOException e){
//			Log.e("DetailActivity", e.toString());
//		}
//		return text;
//		
//	}
}