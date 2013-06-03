package com.krl109.scheduler.alert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.krl109.scheduler.R;

public class DetailSchedule extends Activity 
{
	TextView sent_at, recipient, content;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_schedule);
        
        Intent alert = getIntent();
		String content_message = alert.getStringExtra("content_message");
		String recipient_number = alert.getStringExtra("recipient");
		
		recipient = (TextView) findViewById(R.id.recipient);
		content = (TextView) findViewById(R.id.sent_at);

		recipient.setText(recipient_number);
		content.setText(content_message);
    }
}