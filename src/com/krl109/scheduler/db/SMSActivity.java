package com.krl109.scheduler.db;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSActivity extends Activity {
//	String phoneNumberSucceed;
//	String phoneNumberFailed;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
		//	Intent intentToNotification = new Intent(SMSActivity.this, Notification.class);

		// get phoneNumber and message from previous activity
		Intent intent = getIntent();
		String phoneNumber = intent.getStringExtra("phoneNumber");
		String message = intent.getStringExtra("message");

		// call function checkPhoneNumber
		splitPhoneNumber(phoneNumber, message);
		//startActivity(intentToNotification);
	}

	private void splitPhoneNumber(String phoneNumber, String message) {
		StringTokenizer st = new StringTokenizer(phoneNumber, ";");
		while (st.hasMoreElements()) {
			String tempPhoneNumber = (String) st.nextElement();

			// call function sendSMS
			sendSMS(tempPhoneNumber, message);
		}
	}

	public void sendSMS(final String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
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
