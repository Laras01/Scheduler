package com.krl109.scheduler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TimeListDatabaseHelper {
	private static final String TABLE_SCHEDULE = "schedule";

	public static final String SCHEDULE_COLUMN_ID = "id";
	public static final String SCHEDULE_COLUMN_TIMEMILLIST = "timemillist";
	public static final String SCHEDULE_COLUMN_DATETIME = "datetime";
	public static final String SCHEDULE_COLUMN_RECIPIENT = "recipient";
	public static final String SCHEDULE_COLUMN_MESSAGE = "message";
	public static final String SCHEDULE_COLUMN_FREQUENCY = "frequency";
	public static final String SCHEDULE_COLUMN_REMAINING = "remaining";
	public static final String SCHEDULE_COLUMN_STATUS = "status";
	public static final String SCHEDULE_COLUMN_FT = "freqtimes";

	private DatabaseOpenHelper openHelper;
	private SQLiteDatabase database;
	//private Schedule schedule;

	public TimeListDatabaseHelper(Context context) {
		openHelper = new DatabaseOpenHelper(context);
	}

	public void saveTimeRecord(long timeMilist, String[] data) {
		database = openHelper.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();

		contentValues.put(SCHEDULE_COLUMN_TIMEMILLIST, timeMilist);
		contentValues.put(SCHEDULE_COLUMN_DATETIME, data[0]);
		contentValues.put(SCHEDULE_COLUMN_RECIPIENT, data[1]);
		contentValues.put(SCHEDULE_COLUMN_MESSAGE, data[2]);
		contentValues.put(SCHEDULE_COLUMN_FREQUENCY, data[3]);
		contentValues.put(SCHEDULE_COLUMN_REMAINING, data[4]);
		contentValues.put(SCHEDULE_COLUMN_STATUS, data[5]);
		contentValues.put(SCHEDULE_COLUMN_FT, data[6]);
		database.insert(TABLE_SCHEDULE, null, contentValues);
	}
	
	public Cursor getAllTimeRecords(){
		// select all data in database -> timerecords
		return database.rawQuery("select * from " + TABLE_SCHEDULE, null);
	}
	
	public Cursor getScheduleList()
	{
		database = openHelper.getReadableDatabase();
		return database.rawQuery("select * from " + TABLE_SCHEDULE, null);
	}
	
	Schedule getOneSchedule(long timemillis){		
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_SCHEDULE + " where schedule_timemillis='" + timemillis +"'", null);
		if(cursor != null)
			cursor.moveToFirst();
		Schedule schedule = new Schedule(cursor.getLong(1), cursor.getString(2), cursor.getString(3));
		Log.e("TimeListDatabaseHelper", cursor.getString(2));
		return schedule;
	}
	
	//this method is for
	//1. check whether there is same timemillis in database or not
	//2. if same, then timemillis will add one millisecond
	//3. if not, then timemillis return with the same value (value at the first come)
	public long addTimemillis(long timemillis){
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_SCHEDULE + " where schedule_timemillis='" + timemillis +"'", null);
		
		if(cursor != null){
			while(cursor.moveToNext()){
				long millisecond = cursor.getLong(cursor.getColumnIndex("timemillist"));
				//checking process here
				if(millisecond == timemillis){
					timemillis = timemillis + 1;
				}
			}
		}		
		
		return timemillis;
	}
}