package com.krl109.scheduler.db;

import java.util.ArrayList;
import java.util.LinkedList;

import com.krl109.scheduler.R;
import com.krl109.scheduler.linkedlist.ListQueue;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmManagerActivity extends Activity{
	private TimeListDatabaseHelper databaseHelper = new TimeListDatabaseHelper(this);
	Schedule schedule;
	long timemillis;
	Cursor cursorRecipients;
	String message;
	
	ArrayList<SMSManager> listMessage = new ArrayList<SMSManager>();
	SMSManager sms = new SMSManager();

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
		//databaseHelper = new TimeListDatabaseHelper(this);
		
		/*Intent intent = getIntent();
		timemillis = intent.getLongExtra("timemillis", 0);
		message = databaseHelper.getMessageFromMessage(timemillis);
		cursorRecipients = databaseHelper.getRecipientsFromRecipient(timemillis);
		
		//Toast.makeText(getApplicationContext(), "Timemillist = " + message, Toast.LENGTH_SHORT).show();
		
		while(cursorRecipients.moveToNext()){
			sms.message = message;
			sms.phoneNumber = cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number"));
			listMessage.add(sms);
			sms = new SMSActivity(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			sms.sendSMS(sms.getPhoneNumber(), sms.getMessage());
			//sms.sendSMS(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			//Toast.makeText(getApplicationContext(), "Recipient = " + cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")) + "  Message = " + message, Toast.LENGTH_SHORT).show();
		}
		
		Log.e("AlarmManagerActivity", "");*/
		
		// get the timemillist passed by other activity or class by using intent
		/*Intent intent = getIntent();
		long timemillist = intent.getLongExtra("timemillist", 0);
		schedule = databaseHelper.getOneSchedule(timemillist);
		
		Intent intent1 = new Intent(AlarmManageHelper.this, SMSActivity.class);
		intent1.putExtra("phoneNumber", schedule.recipient);
		intent1.putExtra("message", schedule.message);
		PendingIntent pending = PendingIntent.getActivity(AlarmManageHelper.this, (int) timemillist, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP, timemillist, pending);

		//Toast.makeText(getApplicationContext(), "Recipient = " + schedule.getRecipient(), Toast.LENGTH_SHORT).show();
		//Toast.makeText(getApplicationContext(), "Timemillist = " + (int) timemillist + " Recipient = " + schedule.getRecipient(), Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), "Timemillist = " + timemillist, Toast.LENGTH_SHORT).show();*/
	}
	
	/*public AlarmManageHelper (long timemillist){
		this.timemillist = timemillist;
	}*/
	
	/*public long getTimemillist() { return timemillist; }

	public void setTimemillist(long timemillist) { this.timemillist = timemillist; }*/
}