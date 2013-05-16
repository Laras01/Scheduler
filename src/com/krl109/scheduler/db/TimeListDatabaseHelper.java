package com.krl109.scheduler.db;

import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.krl109.scheduler.tabLayout.Schedule;

public class TimeListDatabaseHelper {
	private static final String TABLE_SCHEDULE = "schedule";
	private static final String TABLE_CONTACT_NUMBER = "contact";
	private static final String TABLE_RECIPIENT = "recipient";
	private static final String TABLE_TIME = "time";
	private static final String TABLE_TEMPLATE = "template";
	private static final String TABLE_COUNT = "count";
	private static final String TABLE_CATEGORY = "category";

	public static final String SCHEDULE_COLUMN_ID = "schedule_id";
	public static final String SCHEDULE_COLUMN_DATETIME = "schedule_datetime";
	public static final String SCHEDULE_COLUMN_MESSAGE = "schedule_message";
	public static final String SCHEDULE_COLUMN_STATUS = "schedule_status";
	public static final String SCHEDULE_COLUMN_SONG = "schedule_song";
	public static final String SCHEDULE_COLUMN_ALERT = "schedule_alert";
	public static final String SCHEDULE_COLUMN_TIMEMILLIS = "schedule_timemillis";

	public static final String CONTACT_COLUMN_NUMBER = "contact_number";

	public static final String RECIPIENT_COLUMN_ID = "recipient_id";
	public static final String RECIPIENT_COLUMN_NUMBER = "recipient_number";
	public static final String RECIPIENT_COLUMN_TIMEMILLIS = "recipient_timemillis";
	public static final String RECIPIENT_COLUMN_SCHEDULE_ID = "recipient_schedule_id";
	public static final String RECIPIENT_COLUMN_STATUS = "recipient_status";

	public static final String TIME_COLUMN_TIMEMILLIS = "time_timemillis";
	public static final String TIME_COLUMN_SCHEDULE_ID = "time_schedule_id";
	public static final String TIME_COLUMN_STATUS = "time_status";

	public static final String TEMPLATE_COLUMN_ID = "template_id";
	public static final String TEMPLATE_COLUMN_COUNT_ID = "template_count_id";
	public static final String TEMPLATE_COLUMN_CATEGORY_ID = "template_category_id";
	public static final String TEMPLATE_COLUMN_SCHEDULE_ID = "template_schedule_id";
	public static final String TEMPLATE_COLUMN_MESSAGE = "template_message";
	public static final String TEMPLATE_COLUMN_NAME = "template_name";
	public static final String TEMPLATE_COLUMN_CATEGORY = "template_category";

	public static final String COUNT_COLUMN_ID = "count_id";
	public static final String COUNT_COLUMN_TEMPLATE_ID = "count_template_id";
	public static final String COUNT_COLUMN_VARIABLE = "count_variable";

	public static final String CATEGORY_COLUMN_ID = "category_id";
	public static final String CATEGORY_COLUMN_TYPE = "category_type";

	private DatabaseOpenHelper openHelper;
	private SQLiteDatabase databaseReadable;
	private SQLiteDatabase databaseWriteable;
	//private Schedule schedule;
	
	ContentValues contentValues = new ContentValues();

	public TimeListDatabaseHelper(Context context) {
		openHelper = new DatabaseOpenHelper(context);
		databaseReadable = openHelper.getReadableDatabase();
		databaseWriteable = openHelper.getWritableDatabase();
	}

	/*public void saveTimeRecord(long timeMilist, String[] data) {
		database = openHelper.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();

		contentValues.put(SCHEDULE_COLUMN_TIMEMILLIS, timeMilist);
		contentValues.put(SCHEDULE_COLUMN_DATETIME, data[0]);
		contentValues.put(SCHEDULE_COLUMN_RECIPIENT, data[1]);
		contentValues.put(SCHEDULE_COLUMN_MESSAGE, data[2]);
		contentValues.put(SCHEDULE_COLUMN_FREQUENCY, data[3]);
		contentValues.put(SCHEDULE_COLUMN_REMAINING, data[4]);
		contentValues.put(SCHEDULE_COLUMN_STATUS, data[5]);
		contentValues.put(SCHEDULE_COLUMN_FT, data[6]);
		database.insert(TABLE_SCHEDULE, null, contentValues);
	}*/
	
	public void saveScheduleToSchedule(long timemilis, String[] data)
	{
		databaseWriteable = openHelper.getWritableDatabase();

		contentValues.put(SCHEDULE_COLUMN_DATETIME, data[0]);//datetime
		contentValues.put(SCHEDULE_COLUMN_MESSAGE, data[2]);//message
		contentValues.put(SCHEDULE_COLUMN_STATUS, data[5]);//status
		contentValues.put(SCHEDULE_COLUMN_SONG, "not defined yet");//song
		contentValues.put(SCHEDULE_COLUMN_ALERT, "not defined yet");//alert
		contentValues.put(SCHEDULE_COLUMN_TIMEMILLIS, timemilis);
		databaseWriteable.insert(TABLE_SCHEDULE, null, contentValues);
		contentValues.clear();
	}
	
	public void saveScheduleToTime(long timemillis){
		
		Schedule schedule = new Schedule();
		
		//do fetching from table schedule to get schedule_id
		Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_SCHEDULE + " where schedule_timemillis='" + timemillis +"'", null);
		if(cursor != null){
			while(cursor.moveToNext()){
				schedule.setScheduleId(cursor.getInt(cursor.getColumnIndex(SCHEDULE_COLUMN_ID)));
			}
		}
		
		//do saving to table time
		contentValues.put(TIME_COLUMN_TIMEMILLIS, timemillis);
		contentValues.put(TIME_COLUMN_SCHEDULE_ID, schedule.getScheduleId());
		contentValues.put(TIME_COLUMN_STATUS, "not defined yet");
		databaseWriteable.insert(TABLE_TIME, null, contentValues);
		contentValues.clear();
	}
	
	public void saveScheduleToContact(String phoneNumbers){
		
		StringTokenizer st = new StringTokenizer(phoneNumbers, ";");
		while (st.hasMoreElements()) {
			String tempPhoneNumber = (String) st.nextElement();
			Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_CONTACT_NUMBER + " where " + CONTACT_COLUMN_NUMBER + "='" + tempPhoneNumber + "'", null);
			if (cursor.moveToNext() == false){
				contentValues.put(CONTACT_COLUMN_NUMBER, tempPhoneNumber);
				databaseWriteable.insert(TABLE_CONTACT_NUMBER, null, contentValues);
			}
		}
		contentValues.clear();
	}
	
	public void saveScheduleToRecipient(Schedule schedule){
		
		StringTokenizer st = new StringTokenizer(schedule.getRecipientNumbers(), ";");
		while (st.hasMoreElements()) {
			String tempPhoneNumber = (String) st.nextElement();
			contentValues.put(RECIPIENT_COLUMN_NUMBER, tempPhoneNumber);
			contentValues.put(RECIPIENT_COLUMN_SCHEDULE_ID, schedule.getScheduleId());
			contentValues.put(RECIPIENT_COLUMN_STATUS, "not defined yet");
			contentValues.put(RECIPIENT_COLUMN_TIMEMILLIS, schedule.getTimemillis());
			databaseWriteable.insert(TABLE_RECIPIENT, null, contentValues);
			contentValues.clear();
		}
		contentValues.clear();
	}
	
	public Cursor getScheduleList()
	{
		return databaseReadable.rawQuery("select * from " + TABLE_SCHEDULE, null);
	}
	
	public String getMessageFromSchedule(long timemillis){
		
		String message = null;
		Cursor cursor = databaseReadable.rawQuery("select "+ SCHEDULE_COLUMN_MESSAGE +" from " + TABLE_SCHEDULE + " where " + SCHEDULE_COLUMN_TIMEMILLIS + "='" + timemillis + "'", null);
		if(cursor.moveToNext() == true){
			message = cursor.getString(cursor.getColumnIndex(SCHEDULE_COLUMN_MESSAGE));
		}
		return message;
	}
	
	public Cursor getRecipientsFromRecipient(long timemillis){
		return databaseReadable.rawQuery("select " + RECIPIENT_COLUMN_NUMBER + " from " + TABLE_RECIPIENT + " where " + RECIPIENT_COLUMN_TIMEMILLIS + "='" + timemillis + "'", null);	
	}
	
	/*public Cursor getAllTimeRecords(){
		// select all data in database -> timerecords
		return database.rawQuery("select * from " + TABLE_SCHEDULE, null);
	}*/
	
	/*Schedule getOneSchedule(long timemillis){		
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_SCHEDULE + " where schedule_timemillis='" + timemillis +"'", null);
		if(cursor != null)
			cursor.moveToFirst();
		Schedule schedule = new Schedule(cursor.getLong(1), cursor.getString(2), cursor.getString(3));
		Log.e("TimeListDatabaseHelper", cursor.getString(2));
		return schedule;
	}*/
	
	//this method is for
	//1. check whether there is same timemillis in database or not
	//2. if same, then timemillis will add one millisecond
	//3. if not, then timemillis return with the same value (value at the first come)
	public long addTimemillis(long timemillis){
		
		Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_SCHEDULE + " where schedule_timemillis='" + timemillis +"'", null);
		
		if(cursor.moveToNext() == true){
			timemillis = timemillis + 1;
		}
		
		return timemillis;
	}
}