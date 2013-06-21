package com.krl109.scheduler.db;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.krl109.scheduler.tabLayout.Schedule;

public class TimeListDatabaseHelper {
	/*private static final String TABLE_SCHEDULE = "schedule";
	private static final String TABLE_COUNT = "count";

	public static final String SCHEDULE_COLUMN_ID = "schedule_id";
	public static final String SCHEDULE_COLUMN_DATETIME = "schedule_datetime";
	public static final String SCHEDULE_COLUMN_MESSAGE = "schedule_message";
	public static final String SCHEDULE_COLUMN_STATUS = "schedule_status";
	public static final String SCHEDULE_COLUMN_SONG = "schedule_song";
	public static final String SCHEDULE_COLUMN_ALERT = "schedule_alert";
	public static final String SCHEDULE_COLUMN_TIMEMILLIS = "schedule_timemillis";
	
	public static final String TEMPLATE_COLUMN_COUNT_ID = "template_count_id";
	public static final String TEMPLATE_COLUMN_SCHEDULE_ID = "template_schedule_id";
	public static final String TEMPLATE_COLUMN_CATEGORY = "template_category";

	public static final String COUNT_COLUMN_ID = "count_id";
	public static final String COUNT_COLUMN_TEMPLATE_ID = "count_template_id";
	public static final String COUNT_COLUMN_VARIABLE = "count_variable";*/
	
	/*-----------------------------------------------------------------------------------*/
	// inisiasi yang benar, nanti yang di atas dihapus
	private static final String TABLE_MESSAGE = "message";
	private static final String TABLE_NORMAL_MESSAGE = "normal_message";
	private static final String TABLE_TYPICAL_MESSAGE = "typical_message";
	private static final String TABLE_CONTACT_NUMBER = "contact";
	private static final String TABLE_RECIPIENT = "recipient";
	private static final String TABLE_TIME = "time";
	private static final String TABLE_TEMPLATE = "template";
	private static final String TABLE_DEFINED_CHARACTER = "defined_character";
	private static final String TABLE_CATEGORY = "category";

	public static final String MESSAGE_COLUMN_ID = "message_id";
	public static final String MESSAGE_COLUMN_MESSAGE = "message_message";
	public static final String MESSAGE_COLUMN_TYPE = "message_type";
	public static final String MESSAGE_COLUMN_TIMEDATE = "message_timedate";
	public static final String MESSAGE_COLUMN_STATUS = "message_status";
	public static final String MESSAGE_COLUMN_SONG = "message_song";
	public static final String MESSAGE_COLUMN_ALERT = "message_alert";
	public static final String MESSAGE_COLUMN_TIMEMILLIS = "message_timemillis";
	public static final String MESSAGE_COLUMN_FREQUENCY = "message_frequency";
	
	public static final String NORMALMESSAGE_COLUMN_ID = "nm_id";
	public static final String NORMALMESSAGE_COLUMN_MESSAGE_ID = "nm_message_id";
	public static final String NORMALMESSAGE_COLUMN_MESSAGE = "nm_message";
	
	public static final String TYPICALMESSAGE_COLUMN_ID = "tm_id";
	public static final String TYPICALMESSAGE_COLUMN_MESSAGE_ID = "tm_message_id";
	public static final String TYPICALMESSAGE_COLUMN_MESSAGE = "tm_message";
	
	public static final String DEFINEDCHAR_COLUMN_ID = "dc_id";
	public static final String DEFINEDCHAR_COLUMN_DM_ID = "dc_dm_id";
	public static final String DEFINEDCHAR_COLUMN_VARIABLE = "dc_variable";
	public static final String DEFINEDCHAR_COLUMN_CONTENT = "dc_content";
	public static final String DEFINEDCHAR_COLUMN_POSITION = "dc_position";

	public static final String CONTACT_COLUMN_NUMBER = "contact_number";

	public static final String RECIPIENT_COLUMN_ID = "recipient_id";
	public static final String RECIPIENT_COLUMN_MESSAGE_ID = "recipient_message_id";
	public static final String RECIPIENT_COLUMN_NUMBER = "recipient_number";
	public static final String RECIPIENT_COLUMN_STATUS = "recipient_status";

	public static final String TIME_COLUMN_TIMEMILLIS = "time_timemillis";
	public static final String TIME_COLUMN_MESSAGE_ID = "time_message_id";
	public static final String TIME_COLUMN_STATUS = "time_status";
	public static final String TIME_COLUMN_TIMESENT = "time_timesent";

	public static final String TEMPLATE_COLUMN_ID = "template_id";
	public static final String TEMPLATE_COLUMN_CATEGORY_ID = "template_category_id";
	public static final String TEMPLATE_COLUMN_MESSAGE = "template_message";
	public static final String TEMPLATE_COLUMN_TYPE = "template_type";
	public static final String TEMPLATE_COLUMN_NAME = "template_name";

	public static final String CATEGORY_COLUMN_ID = "category_id";
	public static final String CATEGORY_COLUMN_TYPE = "category_type";

	private DatabaseOpenHelper openHelper;
	private SQLiteDatabase databaseReadable;
	private SQLiteDatabase databaseWriteable;
	//private Schedule schedule;
	
	ContentValues contentValues = new ContentValues();
	
	ArrayList<String> messageId = new ArrayList<String>();

	public TimeListDatabaseHelper(Context context) {
		openHelper = new DatabaseOpenHelper(context);
		/*databaseReadable = openHelper.getReadableDatabase();
		databaseWriteable = openHelper.getWritableDatabase();*/
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
	
	public void saveScheduletoMessage(long timemillis, String[] data){
		
		databaseWriteable = openHelper.getWritableDatabase();
		
		contentValues.put(MESSAGE_COLUMN_TIMEDATE, data[0]);//datetime
		contentValues.put(MESSAGE_COLUMN_MESSAGE, data[2]);//message
		contentValues.put(MESSAGE_COLUMN_STATUS, data[5]);//status
		contentValues.put(MESSAGE_COLUMN_SONG, "not defined yet");//song
		contentValues.put(MESSAGE_COLUMN_ALERT, "not defined yet");//alert
		contentValues.put(MESSAGE_COLUMN_TIMEMILLIS, timemillis);
		contentValues.put(MESSAGE_COLUMN_TYPE, data[7]);
		contentValues.put(MESSAGE_COLUMN_FREQUENCY, data[3]);
		databaseWriteable.insert(TABLE_MESSAGE, null, contentValues);
		contentValues.clear();
	}
	
	public void saveScheduleToTime(long timemillis, long timesent, String messageId){
		
		databaseWriteable = openHelper.getWritableDatabase();
		//databaseReadable = openHelper.getReadableDatabase();
		
		//do fetching from table schedule to get schedule_id
		/*Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_MESSAGE + " where " + MESSAGE_COLUMN_TIMEMILLIS + "='" + timemillis +"'", null);
		if(cursor != null){
			while(cursor.moveToNext()){
				schedule.setScheduleId(cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_ID)));
			}
		}*/
		
		//do saving to table time
		contentValues.put(TIME_COLUMN_TIMEMILLIS, timemillis);
		contentValues.put(TIME_COLUMN_MESSAGE_ID, messageId);
		contentValues.put(TIME_COLUMN_STATUS, "not defined yet");
		contentValues.put(TIME_COLUMN_TIMESENT, timesent);
		databaseWriteable.insert(TABLE_TIME, null, contentValues);
		contentValues.clear();
	}
	
	public void saveScheduleToContact(String phoneNumbers){
		
		databaseReadable = openHelper.getReadableDatabase();
		databaseWriteable = openHelper.getWritableDatabase();
		
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
	
	public void saveScheduleToRecipient(String recipients, String messageId){
		
		databaseWriteable = openHelper.getWritableDatabase();
		
		StringTokenizer st = new StringTokenizer(recipients, ";");
		while (st.hasMoreElements()) {
			String tempPhoneNumber = (String) st.nextElement();
			contentValues.put(RECIPIENT_COLUMN_NUMBER, tempPhoneNumber);
			contentValues.put(RECIPIENT_COLUMN_MESSAGE_ID, messageId);
			contentValues.put(RECIPIENT_COLUMN_STATUS, "not defined yet");
			databaseWriteable.insert(TABLE_RECIPIENT, null, contentValues);
			contentValues.clear();
		}
		contentValues.clear();
	}
	
	public void saveScheduleToType(String type, String content, String messageId){
		
		databaseWriteable = openHelper.getWritableDatabase();
		
		if(type == "normal"){
			contentValues.put(NORMALMESSAGE_COLUMN_MESSAGE, content);
			contentValues.put(NORMALMESSAGE_COLUMN_MESSAGE_ID, messageId);
			databaseWriteable.insert(TABLE_NORMAL_MESSAGE, null, contentValues);
		}
		else{
			contentValues.put(TYPICALMESSAGE_COLUMN_MESSAGE, content);
			contentValues.put(TYPICALMESSAGE_COLUMN_MESSAGE_ID, messageId);
			databaseWriteable.insert(TABLE_TYPICAL_MESSAGE, null, contentValues);
		}
		contentValues.clear();
	}
	
	//lihat lagi function ini, takutnya ada bentrok di timemillisnya, contohnya kalau ada schedule dihapus.
	/*public long addTimemillis(long timemillis){
		
		databaseReadable = openHelper.getReadableDatabase();
		
		Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_MESSAGE + " where " + MESSAGE_COLUMN_TIMEMILLIS + "='" + timemillis +"'", null);
		
		if(cursor.moveToNext() == true){
			timemillis = timemillis + 1;
		}
		
		return timemillis;
	}*/
	
	public long addTimemillisFromTime(long timemillis){
		
		databaseReadable = openHelper.getReadableDatabase();
		
		Cursor cursor = databaseReadable.rawQuery("select " + TIME_COLUMN_TIMEMILLIS + " from " + TABLE_TIME, null);
		
		while(cursor.moveToNext() == true){
			if(cursor.getLong(cursor.getColumnIndex(TIME_COLUMN_TIMEMILLIS)) == timemillis){
				timemillis = timemillis + 1;
			}
		}
		
		return timemillis;
	}
	
	public String getMessageFromMessage(String messageId){
		
		databaseReadable = openHelper.getReadableDatabase();
		
		String message = null;
		Cursor cursor = databaseReadable.rawQuery("select "+ MESSAGE_COLUMN_MESSAGE +" from " + TABLE_MESSAGE + " where " + MESSAGE_COLUMN_ID + "='" + messageId + "'", null);
		if(cursor.moveToNext() == true){
			message = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_MESSAGE));
		}
		return message;
	}
	
	public ArrayList<String> getMessageIdFromTime(long timemillis){
		
		databaseReadable = openHelper.getReadableDatabase();
		
		Cursor cursor = databaseReadable.rawQuery("select "+ TIME_COLUMN_MESSAGE_ID +" from " + TABLE_TIME + " where " + TIME_COLUMN_TIMESENT + "='" + timemillis + "'", null);
		while(cursor.moveToNext()){
			messageId.add(cursor.getString(cursor.getColumnIndex(TIME_COLUMN_MESSAGE_ID)));
		}
		return messageId;
	}
	
	public Cursor getScheduleList(){
		
		databaseReadable = openHelper.getReadableDatabase();
		return databaseReadable.rawQuery("select * from " + TABLE_MESSAGE, null);
	}
	
	public Cursor getRecipientsFromRecipient(String messageId){
		
		databaseReadable = openHelper.getReadableDatabase();
		return databaseReadable.rawQuery("select " + RECIPIENT_COLUMN_NUMBER + " from " + TABLE_RECIPIENT + " where " + RECIPIENT_COLUMN_MESSAGE_ID + "='" + messageId + "'", null);	
	}
	
	public String recipients(Cursor recipient){
		String recipients = null;
		ArrayList<String> contact = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		while(recipient.moveToNext()){
			contact.add(recipient.getString(recipient.getColumnIndex(RECIPIENT_COLUMN_NUMBER)));
		}
		
		sb.append(contact.get(0));
		for(int i = 1; i < contact.size(); i++){
			sb.append(";");
			sb.append(contact.get(i));
		}
		recipients = sb.toString();
		return recipients;
	}
	
	public void setMessageIdFromMessage(long timemillis){
		
		databaseReadable = openHelper.getReadableDatabase();
		Schedule schedule = new Schedule();
		
		//do fetching from table schedule to get schedule_id
		Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_MESSAGE + " where " + MESSAGE_COLUMN_TIMEMILLIS + "='" + timemillis +"'", null);
		if(cursor != null){
			while(cursor.moveToNext()){
				schedule.setScheduleId(cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_ID)));
			}
		}
	}
	
	//get the number of row
	public int count(Cursor cursor){
		int count = cursor.getCount();
		return count;
	}
	
	/*public String getMessageFromMessage(long timemillis){
		
		databaseReadable = openHelper.getReadableDatabase();
		
		String message = null;
		Cursor cursor = databaseReadable.rawQuery("select "+ MESSAGE_COLUMN_MESSAGE +" from " + TABLE_MESSAGE + " where " + MESSAGE_COLUMN_TIMEMILLIS + "='" + timemillis + "'", null);
		if(cursor.moveToNext() == true){
			message = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_MESSAGE));
		}
		return message;
	}*/
	
	/*public Cursor getRecipientsFromRecipient(long timemillis){
		
		databaseReadable = openHelper.getReadableDatabase();
		return databaseReadable.rawQuery("select " + RECIPIENT_COLUMN_NUMBER + " from " + TABLE_RECIPIENT + " where " + RECIPIENT_COLUMN_TIMEMILLIS + "='" + timemillis + "'", null);	
	}*/
	
	/*public void saveScheduleToSchedule(long timemilis, String[] data){
		contentValues.put(SCHEDULE_COLUMN_DATETIME, data[0]);//datetime
		contentValues.put(SCHEDULE_COLUMN_MESSAGE, data[2]);//message
		contentValues.put(SCHEDULE_COLUMN_STATUS, data[5]);//status
		contentValues.put(SCHEDULE_COLUMN_SONG, "not defined yet");//song
		contentValues.put(SCHEDULE_COLUMN_ALERT, "not defined yet");//alert
		contentValues.put(SCHEDULE_COLUMN_TIMEMILLIS, timemilis);
		databaseWriteable.insert(TABLE_SCHEDULE, null, contentValues);
		contentValues.clear();
	}*/
	
	/*public void saveScheduleToTime(long timemillis){
		
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
	}*/
	
	/*public void saveScheduleToContact(String phoneNumbers){
		
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
	}*/
	
	/*public String getMessageFromSchedule(long timemillis){
		
		String message = null;
		Cursor cursor = databaseReadable.rawQuery("select "+ SCHEDULE_COLUMN_MESSAGE +" from " + TABLE_SCHEDULE + " where " + SCHEDULE_COLUMN_TIMEMILLIS + "='" + timemillis + "'", null);
		if(cursor.moveToNext() == true){
			message = cursor.getString(cursor.getColumnIndex(SCHEDULE_COLUMN_MESSAGE));
		}
		return message;
	}*/
	
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
	/*public long addTimemillis(long timemillis){
		
		Cursor cursor = databaseReadable.rawQuery("select * from " + TABLE_SCHEDULE + " where schedule_timemillis='" + timemillis +"'", null);
		
		if(cursor.moveToNext() == true){
			timemillis = timemillis + 1;
		}
		
		return timemillis;
	}*/
}