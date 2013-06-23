package com.krl109.scheduler.newSchedule;

import java.util.ArrayList;

import com.krl109.scheduler.db.TimeListDatabaseHelper;
import com.krl109.scheduler.tabLayout.Schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;

public class Frequency {
	String frequency;
	int frequencyTimes;
	
	public Frequency(){};
	
	/*public int frequencyTimes(){
		if (frequency == "hourly")
			return frequencyTimes = 1;
		else if (frequency == "2 hourly")
			return frequencyTimes = 2;
		else if (frequency == "4 hourly")
			return frequencyTimes = 4;
		else if (frequency == "6 hourly")
			return frequencyTimes = 6;
		else if (frequency == "8 hourly")
			return frequencyTimes = 8;
		else if (frequency == "12 hourly")
			return frequencyTimes = 12;
		else if (frequency == "daily")
			return frequencyTimes = 1;
		else if (frequency == "weekly")
			return frequencyTimes = 1;
		else if (frequency == "2 weekly")
			return frequencyTimes = 2;
		else if (frequency == "3 weekly")
			return frequencyTimes = 3;
		else if (frequency == "monthly")
			return frequencyTimes = 1;
		else if (frequency == "2 monthly")
			return frequencyTimes = 2;
		else if (frequency == "3 monthly")
			return frequencyTimes = 3;
		else if (frequency == "4 monthly")
			return frequencyTimes = 4;
		else if (frequency == "6 monthly")
			return frequencyTimes = 6;
		return frequencyTimes;
	}*/
	
	public int frequencyTimes(String frequency){
		if (frequency.contains("2")){
			frequencyTimes = 2;
		}
		else if (frequency.contains("3")){
			frequencyTimes = 3;
		}
		else if (frequency.contains("4")){
			frequencyTimes = 4;
		}
		else if (frequency.contains("6")){
			frequencyTimes = 6;
		}
		else if (frequency.contains("8")){
			frequencyTimes = 8;
		}
		else{
			frequencyTimes = 1;
		}
		return frequencyTimes;
	}
	
	/*public long repetititon(String frequency, int remaining){
		
		long newTimesent = 0;
		
		return newTimesent;
	}*/
	
	public void repetition(PendingIntent pending, AlarmManager alarm, TimeListDatabaseHelper databaseHelper, String[] data, long[] time){
		long timemillis = time[0];
		long timesent = time[1];
		//Schedule schedule = new Schedule();
		
		String frequency = data[0];
		String messageId = data[1];
		
		long interval = interval(frequency);
		
		//alarm.setRepeating(AlarmManager.RTC_WAKEUP, timesent, 5000, pending);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, timesent, interval, pending);
		for(int remaining = Integer.parseInt(data[2]); remaining > 1; remaining = remaining-1){
			timemillis = databaseHelper.addTimemillisFromTime(timemillis);
			timesent = timesent + interval;
			databaseHelper.saveScheduleToTime(timemillis, timesent, messageId);
		}
	}
	
	public long interval(String frequency){
		long interval = 0;
		
		if(frequency=="hourly"){
			interval = AlarmManager.INTERVAL_HOUR;
		}
		else if(frequency=="2 hourly"){
			interval = 2*AlarmManager.INTERVAL_HOUR;
		}
		else if(frequency=="4 hourly"){
			interval = 4*AlarmManager.INTERVAL_HOUR;
		}
		else if(frequency=="6 hourly"){
			interval = 6*AlarmManager.INTERVAL_HOUR;
		}
		else if(frequency=="8 hourly"){
			interval = 8*AlarmManager.INTERVAL_HOUR;
		}
		else if(frequency=="12 hourly"){
			interval = 12*AlarmManager.INTERVAL_HOUR;
		}
		else if(frequency=="daily"){
			interval = AlarmManager.INTERVAL_DAY;
		}
		else if(frequency=="weekly"){
			interval = 7*AlarmManager.INTERVAL_DAY;
		}
		else if(frequency=="2 weekly"){
			interval = 2*7*AlarmManager.INTERVAL_DAY;
		}
		else if(frequency=="3 weekly"){
			interval = 3*7*AlarmManager.INTERVAL_DAY;
		}
		else{//the others frequency will be set as fifteen minutes interval since the code haven't done yet
			interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
		}
		
		//"monthly", "yearly", "2 monthly", "4 monthly", "6 monthly" <- haven't been implemented
		return interval;
	}
	
	public ArrayList<Long> codeRequest(long timemillis){
		ArrayList<Long> code = new ArrayList<Long>();
		
		return code;
	}
}
