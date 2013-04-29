package com.krl109.scheduler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TimeListDatabaseHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "scheduler-krl3.db";
	private static final String TABLE_NAME = "schedule";

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
		database.insert(TABLE_NAME, null, contentValues);
	}
	
	public Cursor getAllTimeRecords(){
		// select all data in database -> timerecords
		return database.rawQuery("select * from " + TABLE_NAME, null);
	}
	
	public Cursor getScheduleList()
	{
		database = openHelper.getReadableDatabase();
		return database.rawQuery("select * from " + TABLE_NAME, null);
	}
	
	Schedule getOneSchedule(long timemillis){		
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where timemillist='" + timemillis +"'", null);
		if(cursor != null)
			cursor.moveToFirst();
		Schedule schedule = new Schedule(cursor.getLong(1), cursor.getString(2), cursor.getString(3));
		Log.e("TimeListDatabaseHelper", cursor.getString(2));
		return schedule;
	}
	
	public boolean checkTimemillis(long timemillis){
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where timemillist='" + timemillis +"'", null);
		if (cursor == null){
			return true;
		}
		else{
			return false;
		}
	}
}