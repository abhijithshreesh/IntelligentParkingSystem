package com.example.test1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Booking extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);
		Button book = (Button)findViewById(R.id.book);
		final EditText vehicle = (EditText)findViewById(R.id.vehicle);
		final EditText mobile = (EditText)findViewById(R.id.mobile);
		TimePicker time = (TimePicker)findViewById(R.id.timePicker1);
		
		book.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(Booking.this);
				 
		        // Setting Dialog Title
				alertDialog.setTitle("Booking Confirmed");
				 
		        // Setting Dialog Message
		        alertDialog.setMessage("vehicle registration "+vehicle.getText().toString()+"\n"+"contact no. "+mobile.getText().toString()+"\nAllocated Slot : B2");
		 
		
		 
		        // Setting Icon to Dialog
		        //alertDialog.setIcon(R.drawable.tick);
		        
		        // Setting OK Button
		        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog closed
		                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
		                }
		        });
		 
		        // Showing Alert Message
		        alertDialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_booking, menu);
		return true;
	}

}
