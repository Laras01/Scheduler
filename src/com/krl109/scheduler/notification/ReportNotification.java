package com.krl109.scheduler.notification;

import com.krl109.scheduler.R;
import com.krl109.scheduler.history.*;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

/*
 * Menampilkan report notifikasi (sent atau failed), tetapi untuk sekarang, kondisinya baru untuk yang sent
 */
public class ReportNotification extends Activity
{
	private static final int NOTIF_ID = 1234; 
	//ID notifikasi, sama dengan ID pada SendingNotification agar saat ReportNotification muncul, notifikasi SendingNotification tereplace
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
    	 super.onCreate(savedInstanceState);
    	 
    	 String status = "sent";
    	 String recipient = "08997937561";
    	 String content_message = "Testing SMS Scheduler";
    	 
    	 NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
         Notification report_notif = new Notification(R.drawable.ic_launcher, "SMS Scheduler : Message " + status, System.currentTimeMillis());

         Intent history_intent = new Intent(this, History.class); //Ketika notif di klik, halaman membuka history
         history_intent.putExtra("notif_id", NOTIF_ID); //mengirimkan Notif ID
  
         PendingIntent pIntent = PendingIntent.getActivity(this, 0, history_intent, 0);
         
         report_notif.setLatestEventInfo(this, "Message " + status + " : " + recipient, content_message, pIntent);
         notifManager.notify(NOTIF_ID, report_notif);
                  
         //notifManager.cancel(NOTIF_ID);  //Menghapus notif
	}
}
