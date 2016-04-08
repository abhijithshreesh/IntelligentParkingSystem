package com.example.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
 private String jsonResult;
 private String url = "http://192.168.157.1/parking/retreive.php";
 private ListView listView;
 Button refresh,nearest,booking;
 String slots[];
 List<Map<String, String>> employeeList;
 TextView tv;
String  nearest_spot;
 int count=0;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_home);
 // listView = (ListView) findViewById(R.id.listView1);
  tv =(TextView)findViewById(R.id.emp_tv);
  refresh=(Button)findViewById(R.id.refresh);
  nearest=(Button)findViewById(R.id.nearest);
  booking=(Button)findViewById(R.id.booking);
  count=0;
  //accessWebService();
  CountDownTimer t = new CountDownTimer( Long.MAX_VALUE , 2000) {

      // This is called every interval. (Every 2 seconds in this example)
      public void onTick(long millisUntilFinished) {
         // Log.d("test","Timer tick");
    	  count=0;
    	  accessWebService();
      }

      public void onFinish() {
         // Log.d("test","Timer last tick");            
          start();
      }
   }.start();
  nearest.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
			 
	        // Setting Dialog Title
			alertDialog.setTitle("CONFIRM PARKING SPOT");
			 
	        // Setting Dialog Message
	        alertDialog.setMessage("Parking Fare\n\n1hr : Rs 30\n2hr : Rs 50\n3hr : Rs 70\nabove 3hrs : Rs 100");
	 
	        // Setting Icon to Dialog
	       // alertDialog.setIcon(R.drawable.delete);
	 
	        // Setting Positive "Yes" Button
	        alertDialog.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	            	count=0;
	    			accessWebService();
	    			Intent i = new Intent(getApplicationContext(),Map_nav.class);
	    			i.putExtra("nearest_spot", nearest_spot);
	    			startActivity(i);
	            }
	        });
	 
	        // Setting Negative "NO" Button
	        alertDialog.setNegativeButton("DECLINE", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            // Write your code here to invoke NO event
	            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
	            dialog.cancel();
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	
		}
	});
  refresh.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			count=0;
			 accessWebService();
		}
	});
  booking.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent k = new Intent(getApplicationContext(),Booking.class);
			//k.putExtra("nearest_spot", nearest_spot);
			startActivity(k);
		}
	});
 
 }

 

 // Async Task to access the web
 private class JsonReadTask extends AsyncTask<String, Void, String> {
  @Override
  protected String doInBackground(String... params) {
   HttpClient httpclient = new DefaultHttpClient();
   HttpPost httppost = new HttpPost(params[0]);
   try {
    HttpResponse response = httpclient.execute(httppost);
    jsonResult = inputStreamToString(
      response.getEntity().getContent()).toString();
   }

   catch (ClientProtocolException e) {
    e.printStackTrace();
   } catch (IOException e) {
    e.printStackTrace();
   }
   return null;
  }

  private StringBuilder inputStreamToString(InputStream is) {
   String rLine = "";
   StringBuilder answer = new StringBuilder();
   BufferedReader rd = new BufferedReader(new InputStreamReader(is));

   try {
    while ((rLine = rd.readLine()) != null) {
     answer.append(rLine);
    }
   }

   catch (IOException e) {
    // e.printStackTrace();
    Toast.makeText(getApplicationContext(),
      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
   }
   return answer;
  }

  @Override
  protected void onPostExecute(String result) {
   ListDrwaer();
  String s1 = employeeList.get(0).toString();
  String s2 = employeeList.get(1).toString();
  String s3 = employeeList.get(2).toString();
  String s4 = employeeList.get(3).toString();
  String s5 = employeeList.get(4).toString();
  String s6 = employeeList.get(5).toString();
  if(s6.charAt(19)=='9') {
	  count++;
	  nearest_spot="6";
  }
 
  if(s5.charAt(19)=='9') {
	  count++;
	  nearest_spot="5";
  }

  if(s4.charAt(19)=='9'){
	  count++;
	  nearest_spot="4";
  }
  if(s2.charAt(19)=='9'){
	  count++;
	  nearest_spot="2";
  }

  if(s3.charAt(19)=='9'){
	  count++;
	  nearest_spot="3";
  }

  if(s1.charAt(19)=='9') {
	  count++;
	  nearest_spot="1";
  }
  tv.setText("Empty : "+count);
  
  
  }
 }// end async task

 public void accessWebService() {
  JsonReadTask task = new JsonReadTask();
  // passes values for the urls string array
  task.execute(new String[] { url });
 }

 // build hash set for list view
 public void ListDrwaer() {
   employeeList = new ArrayList<Map<String, String>>();

  try {
   JSONObject jsonResponse = new JSONObject(jsonResult);
   JSONArray jsonMainNode = jsonResponse.optJSONArray("sen_info");

   for (int i = 0; i < jsonMainNode.length(); i++) {
    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
    String name = jsonChildNode.optString("sensor_name");
    String number = jsonChildNode.optString("sensor_data");
   //slots[i] = number;
    String outPut = name + "-" + number;
    employeeList.add(createEmployee("employees", outPut));
   }
  } catch (JSONException e) {
   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
     Toast.LENGTH_SHORT).show();
  }

 /* SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList,
    android.R.layout.simple_list_item_1,
    new String[] { "employees" }, new int[] { android.R.id.text1 });
  listView.setAdapter(simpleAdapter);*/
 }

 private HashMap<String, String> createEmployee(String name, String number) {
  HashMap<String, String> employeeNameNo = new HashMap<String, String>();
  employeeNameNo.put(name, number);
  return employeeNameNo;
 }
}
