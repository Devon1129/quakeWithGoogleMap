package com.example.maptest_exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView lvList;
	
	List<String> latlonList;
	List<LatLon> posClassList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			buildViews();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
        //buildViews();
	}
	
	@SuppressLint("SimpleDateFormat")
	private void buildViews() throws JSONException
	{
		lvList = (ListView)findViewById(R.id.lvList);
		
		// Simple way(very very simple) to resolve the exception: android.os.NetworkOnMainThreadException
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		
		//String fullJson = (new GetHttpTest()).getHTML("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
		String fullJson = (new GetHttpTest()).getHTML("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson");
		
		// Parse Json
		JSONObject obj = new JSONObject(fullJson);
//		JSONArray features = obj.getJSONArray("features");
//		JSONObject features = obj.getJSONArray("features").getJSONObject(0).getJSONObject("properties");
//		JSONObject properties = obj.getJSONArray("features").getJSONObject(0).getJSONObject("properties");
//		JSONObject geometry = obj.getJSONArray("features").getJSONObject(0).getJSONObject("geometry"); 
		
//		String feat = features.getString("place") + "\r\n" +
//				properties.getString("time") + "\r\n" +
//				geometry.getJSONArray("coordinates") + "\r\n";
		
		JSONArray features = obj.getJSONArray("features");
		
		
		List<String> quakeList = new ArrayList<String>();		
		latlonList = new ArrayList<String>();
		posClassList = new ArrayList<LatLon>();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String tmp = "";
		String tmpLatLon = "";
		for(int i = 0; i < features.length(); i++){
			
			//quakeList.add(features.getJSONObject(i).getJSONObject("properties").getString("place"));
			
			
			long unformattedTime = features.getJSONObject(i).getJSONObject("properties").getLong("time");
			Date aDate = new Date(unformattedTime);
			
			//quakeList.add("Time:" + sdfDate.format(aDate));
			
			JSONArray cordinates = features.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");

			tmp = features.getJSONObject(i).getJSONObject("properties").getString("place") + "\r\n"
				+("Time:" + sdfDate.format(aDate)) + "\r\n";
			
			LatLon  aLatLon = new LatLon();
			for(int j = 0; j < cordinates.length(); j++ ){
				//quakeList.add(String.valueOf(cordinates.getDouble(j)));
								
				tmp += (String.valueOf(cordinates.getDouble(j))) + "\r\n";
				
				// 另外存 LatLon
				if(j != 2) // j==2 是地震深度
				{
					tmpLatLon += String.valueOf(cordinates.getDouble(j)) + ", ";
				}
				
				
				// 以 LatLon 存資料的方式
				if(j == 0)
				{
					aLatLon.lon = cordinates.getDouble(j);					
				}
				else if(j == 1)
				{
					aLatLon.lat = cordinates.getDouble(j);					
				}	
			}
	
			quakeList.add(tmp);
			latlonList.add(tmpLatLon);
			posClassList.add(aLatLon);
			
			tmp = "";
			tmpLatLon = "";
		}

		String[] quakeArr = new String[quakeList.size()];	
		quakeArr = quakeList.toArray(quakeArr);
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quakeArr);
		lvList.setAdapter(adapter);
		
		lvList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lvList.getItemAtPosition(position);
				
				String itemSelected = ((TextView) view).getText().toString();
				//Toast.makeText(getApplicationContext(), "msg" + (position + 1) + ":" + itemSelected, Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), "msg" + (position + 1) + ":" + getLatLon(position), Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "msg" + (position + 1) + ":" + latlonList.get(position), Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), 
						"msg" + (position + 1) + ":  " 
								//+ String.valueOf(posClassList.get(position).lon)
								+ posClassList.get(position).lon
								+ " | " 
								//+ String.valueOf(posClassList.get(position).lat),
								+ posClassList.get(position).lat,
								Toast.LENGTH_LONG).show();
				
			
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailsActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putDouble("lon", posClassList.get(position).lon);
				bundle.putDouble("lat", posClassList.get(position).lat);
				
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
			
		});
	}
	
	private String getLatLon(int position)
	{
		return latlonList.get(position);
	}
	
	private void buildViews1() throws JSONException{
		lvList = (ListView)findViewById(R.id.lvList);
	
		//練習列出ListView
//		String[] items = {"Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream"};
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//		lvList.setAdapter(adapter);
		
		//練習2
//		String[] items = {
//				"1:\r\n"
//				+"Place:4km SSE of Anza, California\r\n"
//				+"Time:2015-03-31 23:02:57\r\n"
//				+"-116.6533333\r\n"
//				+"33.5253333\r\n"
//				+"10.33\r",
//				"2:\r\n"
//				+"Place:17km N of Yucca Valley, California\r\n"
//				+"Time:2015-03-31 22:47:06\r\n"
//				+"-116.4458333\r\n"
//				+"34.2698333\r\n"
//				+"8.96\r",
//				"3:\r\n"
//				+"Place:45km ESE of Beatty, Nevada\r\n"
//				+"Time:2015-03-31 22:34:18\r\n"
//				+"-116.2967\r\n"
//				+"36.7307\r\n"
//				+"10.39\r",
//				"4:\r\n"
//				+"Place:13km SE of Anza, California\r\n"
//				+"Time:2015-03-31 22:33:07\r\n"
//				+"-116.576\r\n"
//				+"33.4713333\r\n"
//				+"9.1\r",
//				"5:\r\n"
//				+"Place:95km NNW of Talkeetna, Alaska\r\n"
//				+"Time:2015-03-31 22:31:10\r\n"
//				+"-150.8032\r\n"
//				+"63.1226\r\n"
//				+"117.6\r"};
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//		lvList.setAdapter(adapter);
		
		//練習JSONObject與ListView結合使用
		String tmp = "{\"Data\":{\"place\":\"M 2.1 - 4km E of Cabazon, California\",\"time\":\"1427208592120\",\"laction\":[-116.749,33.9211667], \"depth\":\"19.63 KM\"}}";
		JSONObject obj = new JSONObject(tmp);
		
		String feat = obj.getJSONObject("Data").getString("place") + "\r\n" +   
				obj.getJSONObject("Data").getString("time") + "\r\n" +
				obj.getJSONObject("Data").getString("laction") + "\r\n" +
				obj.getJSONObject("Data").getString("depth");
		
		String[] items = {feat, ""};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvList.setAdapter(adapter);
		
	}
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
