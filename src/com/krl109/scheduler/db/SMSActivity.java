package com.krl109.scheduler.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.krl109.scheduler.linkedlist.TimeQueue;
import com.krl109.scheduler.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SMSActivity extends Activity {
//	String phoneNumberSucceed;
//	String phoneNumberFailed;
	private TimeListDatabaseHelper databaseHelper = new TimeListDatabaseHelper(this);
	String phoneNumber;
	String message;
	
	Schedule schedule;
	ArrayList<Long> timesent = new ArrayList<Long>();
	Cursor cursorRecipients;
	//ArrayList<TimeQueue> timemillis = new ArrayList<TimeQueue>();
	ArrayList<String> messageId = new ArrayList<String>();
	String msgId;
	
	//LinkedList<SMSManager> listMessage = new LinkedList<SMSManager>();
	/*SMSManager sms = new SMSManager();
	SMSManager tempSms = new SMSManager();*/
	
	/*TimeQueue timeQueue = new TimeQueue();
	TimeQueue tempTime = new TimeQueue();*/

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_schedule);
		
		//databaseHelper = new TimeListDatabaseHelper(this);
		
		//get time now
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		timesent.add(calendar.getTimeInMillis());
		
		/*Intent intent = getIntent();
		timesent = intent.getLongExtra("timemillis", 0);*/
		while(timesent.isEmpty() == false){
			messageId = databaseHelper.getMessageIdFromTime(timesent.get(0));
			while(messageId.isEmpty() == false){
				msgId = messageId.get(0);
				message = databaseHelper.getMessageFromMessage(msgId);
				cursorRecipients = databaseHelper.getRecipientsFromRecipient(msgId);
				while(cursorRecipients.moveToNext()){
					//sendSMS(cursorRecipients.getString(0 + cursorRecipients.getColumnIndex("recipient_number")), message);
					Toast.makeText(getApplicationContext(), "p=" + cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), Toast.LENGTH_SHORT).show();
					//i++;
				}
				messageId.remove(0);
			}
			timesent.remove(0);
		}
		
		
		//int i = 1;
		
		/*timeQueue.setTimemillis(intent.getLongExtra("timemillis", 0));
		timemillis.add(timeQueue);*/
		/*message = databaseHelper.getMessageFromMessage(timemillis);
		cursorRecipients = databaseHelper.getRecipientsFromRecipient(timemillis);*/
		
		/*while(timemillis.isEmpty() == false){
			tempTime = timemillis.get(0);
			message = databaseHelper.getMessageFromMessage(tempTime.getTimemillis());
			cursorRecipients = databaseHelper.getRecipientsFromRecipient(tempTime.getTimemillis());
			while(cursorRecipients.moveToNext()){
				//sendSMS(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			}
			timemillis.remove(0);
		}*/
		
		//Toast.makeText(getApplicationContext(), "Timemillist = " + message, Toast.LENGTH_SHORT).show();
		/*while(cursorRecipients.moveToNext()){
			sms.message = message;
			sms.phoneNumber = cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number"));
			listMessage.add(sms);
			//Toast.makeText(getApplicationContext(), "while (cursor...)", Toast.LENGTH_SHORT).show();
			//Toast.makeText(getApplicationContext(), "p=" + sms.phoneNumber, Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "Recipient = " + cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")) + "  Message = " + message, Toast.LENGTH_SHORT).show();
			sms = new SMSManager(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			sms = new SMSManager(SMSActivity.this);
			
			sendSMS(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			sms = new SMSActivity(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			sms.sendSMS(sms.getPhoneNumber(), sms.getMessage());
			//sms.sendSMS(cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")), message);
			//Toast.makeText(getApplicationContext(), "Recipient = " + cursorRecipients.getString(cursorRecipients.getColumnIndex("recipient_number")) + "  Message = " + message, Toast.LENGTH_SHORT).show();
		}*/
		
		/*int i = 1;
		while(listMessage.isEmpty() == false){
			tempSms.phoneNumber = listMessage.pop().phoneNumber;
			//sendSMS(tempSms.getPhoneNumber(), tempSms.getMessage());
			Toast.makeText(getApplicationContext(), "p=" + i, Toast.LENGTH_SHORT).show();
			i++;
		}*/
	}
	
	/*public SMSActivity(){}
	
	public SMSActivity(String phoneNumber, String message){
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}*/

	/*private void splitPhoneNumber(String phoneNumber, String message) {
		StringTokenizer st = new StringTokenizer(phoneNumber, ";");
		while (st.hasMoreElements()) {
			String tempPhoneNumber = (String) st.nextElement();

			// call function sendSMS
			sendSMS(tempPhoneNumber, message);
		}
	}*/

	public void sendSMS(String phoneNumber, String message) {
		final String SENT = "SMS_SENT";
		final String DELIVERED = "SMS_DELIVERED";
		/*final String SMS_DELIVERED = "SMS Delivered";
		final String SMS_NOT_DELIVERED = "SMS Not Delivered";*/
//		final StringBuilder sb = new StringBuilder();

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		// when the SMS has been sent
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
					
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		// when the SMS has been delivered
		registerReceiver(new BroadcastReceiver() {			
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered",
							Toast.LENGTH_SHORT).show();
					
					/*if(phoneNumberSucceed == null){
						phoneNumberSucceed = phoneNumber;
					}
					else{
						sb.append(phoneNumberSucceed+"; ");
						sb.append(phoneNumber);
						phoneNumberSucceed = sb.toString();
					}*/
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					
					/*if(phoneNumberFailed == null){
						phoneNumberFailed = phoneNumber;
					}
					else{
						sb.append(phoneNumberFailed+"; ");
						sb.append(phoneNumber);
						phoneNumberFailed = sb.toString();
					}*/
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}
	
	//Notification notif = new Notification(phoneNumberSucceed, phoneNumberFailed);
}
