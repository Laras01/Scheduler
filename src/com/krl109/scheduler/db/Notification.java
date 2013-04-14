package com.krl109.scheduler.db;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Notification extends BroadcastReceiver{
	NotificationManager nm;
	String phoneNumberSucceed;
	String phoneNumberFailed;	
	
	public Notification (String phoneNumberSucceed, String phoneNumberFailed){
		this.phoneNumberSucceed = phoneNumberSucceed;
		this.phoneNumberFailed = phoneNumberFailed;
	}
	
	public String getPhoneNumberSucceed() { return phoneNumberSucceed; }

	public void setPhoneNumberSucceed(String phoneNumberSucceed) { this.phoneNumberSucceed = phoneNumberSucceed; }

	public String getPhoneNumberFailed() { return phoneNumberFailed; }

	public void setPhoneNumberFailed(String phoneNumberFailed) { this.phoneNumberFailed = phoneNumberFailed; }

	@Override
	public void onReceive(Context context, Intent intent) {
		nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		//CharSequence title = "SMS Sent";
		
		
	}

}
