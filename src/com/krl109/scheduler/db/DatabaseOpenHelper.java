package com.krl109.scheduler.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseOpenHelper extends SQLiteOpenHelper {

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

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("CREATE TABLE " + TABLE_NAME + "("
				+ SCHEDULE_COLUMN_ID + " INTEGER PRIMARY KEY, "
				+ SCHEDULE_COLUMN_TIMEMILLIST + " INT, "
				+ SCHEDULE_COLUMN_DATETIME + " STRING, "
				+ SCHEDULE_COLUMN_RECIPIENT + " STRING, " 
				+ SCHEDULE_COLUMN_MESSAGE + " STRING, " 
				+ SCHEDULE_COLUMN_FREQUENCY + " STRING, " 
				+ SCHEDULE_COLUMN_REMAINING + " INT, " 
				+ SCHEDULE_COLUMN_STATUS + " STRING, " 
				+ SCHEDULE_COLUMN_FT + " INT " + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		// handle database schema upgrades
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}

}
