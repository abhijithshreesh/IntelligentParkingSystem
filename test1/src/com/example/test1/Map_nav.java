package com.example.test1;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Map_nav extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_nav);
		Button b[]=null;
		Button n1 = (Button)findViewById(R.id.Button09);
		Button n2 = (Button)findViewById(R.id.Button06);
		Button n3 = (Button)findViewById(R.id.Button11);
		Button n4 = (Button)findViewById(R.id.button4);
		Button n5 = (Button)findViewById(R.id.Button03);
		Button n6 = (Button)findViewById(R.id.button2);
		Button r1 = (Button)findViewById(R.id.road1);
		Button r2 = (Button)findViewById(R.id.road2);
		Button r3 = (Button)findViewById(R.id.road3);
		Button r4 = (Button)findViewById(R.id.road4);
		Button r5 = (Button)findViewById(R.id.road5);
		Button r6 = (Button)findViewById(R.id.road6);
		Button r7 = (Button)findViewById(R.id.road7);
		Button r8 = (Button)findViewById(R.id.road8);
		Button r9 = (Button)findViewById(R.id.road9);
		Button r10 = (Button)findViewById(R.id.road10);
		Button r11 = (Button)findViewById(R.id.road11);
		Button r12 = (Button)findViewById(R.id.road12);
		Button r13 = (Button)findViewById(R.id.road13);
		Button r14 = (Button)findViewById(R.id.road14);
		Button r15 = (Button)findViewById(R.id.road15);
		String spot=getIntent().getExtras().getString("nearest_spot");
	//	Toast.makeText(getApplicationContext(),"hi"+spot, 
       //         Toast.LENGTH_LONG).show();
		int num=Integer.parseInt(spot);
		//String a[]=null;
		
		
		//spot=getIntent().getExtras().getInt("nearset_spot");
		//a[1]="1";a[2]="2";a[3]="3";a[4]="9";a[5]="10";
		/*for(int i=1;i<6;i++){
			if(a[i]==spot){
				n1.setBackgroundDrawable(getResources().getDrawable(R.drawable.fr));
			}
		}*/
		switch(num){
		case 1:n1.setBackgroundDrawable(getResources().getDrawable(R.drawable.fgr));
				r1.setVisibility(View.VISIBLE);
				r2.setVisibility(View.VISIBLE);
				r3.setVisibility(View.VISIBLE);
				r6.setVisibility(View.VISIBLE);
				break;
		case 2:n3.setBackgroundDrawable(getResources().getDrawable(R.drawable.fgr));
				r1.setVisibility(View.VISIBLE);
				r2.setVisibility(View.VISIBLE);
				r3.setVisibility(View.VISIBLE);
				r4.setVisibility(View.VISIBLE);
				r5.setVisibility(View.VISIBLE);
				r8.setVisibility(View.VISIBLE);
		
				break;
		case 3:n2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fgr));
				r1.setVisibility(View.VISIBLE);
				r2.setVisibility(View.VISIBLE);
				r3.setVisibility(View.VISIBLE);
				r4.setVisibility(View.VISIBLE);
				r7.setVisibility(View.VISIBLE);
				break;
		case 4:n4.setBackgroundDrawable(getResources().getDrawable(R.drawable.fgr));
				r1.setVisibility(View.VISIBLE);
				r2.setVisibility(View.VISIBLE);
				r9.setVisibility(View.VISIBLE);
				r10.setVisibility(View.VISIBLE);
				r11.setVisibility(View.VISIBLE);
				
				break;
		case 5:n5.setBackgroundDrawable(getResources().getDrawable(R.drawable.fgr));
				r1.setVisibility(View.VISIBLE);
				r2.setVisibility(View.VISIBLE);
				r9.setVisibility(View.VISIBLE);
				r10.setVisibility(View.VISIBLE);
				r12.setVisibility(View.VISIBLE);
				r13.setVisibility(View.VISIBLE);
				break;
		case 6:n6.setBackgroundDrawable(getResources().getDrawable(R.drawable.fgr));
				r1.setVisibility(View.VISIBLE);
				r2.setVisibility(View.VISIBLE);
				r9.setVisibility(View.VISIBLE);
				r10.setVisibility(View.VISIBLE);
				r12.setVisibility(View.VISIBLE);
				r14.setVisibility(View.VISIBLE);
				r15.setVisibility(View.VISIBLE);
		break;
			
			
		}
		//n1.setText(num+"H");
		//n1.setBackgroundDrawable(getResources().getDrawable(R.drawable.fr));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map_nav, menu);
		return true;
	}

}
