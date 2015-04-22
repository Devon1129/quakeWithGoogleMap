package com.example.maptest_exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHttpTest {
	// Reference::
	// http://stackoverflow.com/questions/1485708/how-do-i-do-a-http-get-in-java/1485730#1485730
	
	 public String getHTML(String urlToRead) {
		 URL url;
	     HttpURLConnection conn;
	     BufferedReader rd;
	     
	     String line;
	     String result = "";
	     try {
	        url = new URL(urlToRead);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        while ((line = rd.readLine()) != null) {
	           result += line;
	        }
	        rd.close();
	     } catch (IOException e) {
	        e.printStackTrace();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	     return result;
	 }
}
