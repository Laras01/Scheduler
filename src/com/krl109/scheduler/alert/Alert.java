package com.krl109.scheduler.alert;

import com.krl109.scheduler.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class Alert extends Activity
{	
	String content_message = "Testing SMS Scheduler";
    String recipient = "08997937561";
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
                              
        // alert dialog
        AlertDialog.Builder alert = new Builder(this);
        alert.setTitle("Continue Schedule");
        alert.setIcon(R.drawable.alert);
        alert.setMessage(content_message + " to " + recipient);
        
        //Action untuk Button Yes
        alert.setPositiveButton("Yes", null);
        
        //Action untuk Button See Detail
        alert.setNeutralButton("See Detail", new DialogInterface.OnClickListener() 
        {
          @Override
          public void onClick(DialogInterface dialog, int which) 
          {
        	  Intent detail_schedule = new Intent(Alert.this, DetailSchedule.class);
        	  //mengirimkan data content message dan recipient ke detail schedule
              detail_schedule.putExtra("content_message", content_message);
              detail_schedule.putExtra("recipient", recipient);
              startActivity(detail_schedule);     
          }
        });
        
        //Action untuk Button No
        alert.setNegativeButton("No", null);
        
        alert.create().show();
	}
}
