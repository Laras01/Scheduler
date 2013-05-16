package com.krl109.scheduler.db;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmManageHelper extends Activity{
	private TimeListDatabaseHelper databaseHelper = new TimeListDatabaseHelper(this);
	SMSActivity sms = new SMSActivity();
	Schedule schedule;
	long timemillis;
	Cursor cursorRecipients;
	String message;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
		Intent intent = getIntent();
		timemillis = intent.getLongExtra("timemillis", 0);
		/*message = databaseHelper.getMessageFromSchedule(timemillis);
		cursorRecipients = databaseHelper.getRecipientsFromRecipient(timemillis);*/
		
		Toast.makeText(getApplicationContext(), "Timemillist = " + timemillis, Toast.LENGTH_SHORT).show();
		
		/*if(cursorRecipients != null){
			while(cursorRecipients.moveToNext()){
				sms.sendSMS(cursorRecipients.getColumnName(cursorRecipients.getColumnIndex("recipient_number")), message);
			}
		}*/
		
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