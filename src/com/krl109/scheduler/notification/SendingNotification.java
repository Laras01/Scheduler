package com.krl109.scheduler.notification;

import com.krl109.scheduler.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class SendingNotification extends Activity
{
	private static final int NOTIF_ID = 1234; //ID notifikasi
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
    	 super.onCreate(savedInstanceState);
         
    	 NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
         
         Intent reportNotif_intent = new Intent(this, ReportNotification.class);
         PendingIntent pIntent = PendingIntent.getActivity(this, 0, reportNotif_intent, 0);
         
         Notification send_notif = new Notification(R.drawable.ic_launcher, "SMS Scheduler : Message Sending", System.currentTimeMillis());
         send_notif.setLatestEventInfo(this, "SMS Scheduler", "Message Sending....", pIntent);
         
         notifManager.notify(NOTIF_ID, send_notif);
         
         //notifManager.cancel(NOTIF_ID);
	}
	
}
