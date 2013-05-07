/*package com.krl109.scheduler.db;

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
*/

package com.krl109.scheduler.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "scheduler-krl5.db";
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
	
	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("CREATE TABLE " + TABLE_SCHEDULE + "("
				+ SCHEDULE_COLUMN_ID + " INTEGER PRIMARY KEY, "
				+ SCHEDULE_COLUMN_DATETIME + " STRING, "
				+ SCHEDULE_COLUMN_MESSAGE + " STRING, " 
				+ SCHEDULE_COLUMN_STATUS + " STRING, "
				+ SCHEDULE_COLUMN_SONG + " STRING, "
				+ SCHEDULE_COLUMN_ALERT + " STRING, "
				+ SCHEDULE_COLUMN_TIMEMILLIS + " STRING)");
		
		database.execSQL("CREATE TABLE " + TABLE_CONTACT_NUMBER + "(" 
				+ CONTACT_COLUMN_NUMBER + " STRING PRIMARY KEY)");
		
		database.execSQL("CREATE TABLE " + TABLE_TIME + "(" 
				+ TIME_COLUMN_TIMEMILLIS + " STRING PRIMARY KEY, " 
				+ TIME_COLUMN_STATUS + " STRING, " 
				+ TIME_COLUMN_SCHEDULE_ID + " INTEGER, " 
				+ "FOREIGN KEY ("+ TIME_COLUMN_SCHEDULE_ID +") REFERENCES "+ TABLE_SCHEDULE +" ("+ SCHEDULE_COLUMN_ID +"))");
		
		database.execSQL("CREATE TABLE " + TABLE_RECIPIENT + "(" + RECIPIENT_COLUMN_ID + " INTEGER PRIMARY KEY, "
				   + RECIPIENT_COLUMN_STATUS + " STRING, "
				   + RECIPIENT_COLUMN_SCHEDULE_ID + " INTEGER, "
				   + RECIPIENT_COLUMN_TIMEMILLIS + " STRING, "
				   + RECIPIENT_COLUMN_NUMBER + " STRING, "
				   + "FOREIGN KEY (" + RECIPIENT_COLUMN_SCHEDULE_ID +") REFERENCES " + TABLE_SCHEDULE + " (" + SCHEDULE_COLUMN_ID + "), "
				   + "FOREIGN KEY (" + RECIPIENT_COLUMN_TIMEMILLIS +") REFERENCES " + TABLE_TIME + " (" + TIME_COLUMN_TIMEMILLIS + "), "
				   + "FOREIGN KEY (" + RECIPIENT_COLUMN_NUMBER +") REFERENCES " + TABLE_CONTACT_NUMBER + " (" + CONTACT_COLUMN_NUMBER + "))");
		
		database.execSQL("CREATE TABLE " + TABLE_TEMPLATE + " (" 
				   + TEMPLATE_COLUMN_ID + " INTEGER PRIMARY KEY, " 
				   + TEMPLATE_COLUMN_COUNT_ID + " INTEGER, " 
				   + TEMPLATE_COLUMN_CATEGORY_ID + " INTEGER, " 
				   + TEMPLATE_COLUMN_SCHEDULE_ID + " INTEGER, " 
				   + TEMPLATE_COLUMN_MESSAGE + " STRING, " 
				   + TEMPLATE_COLUMN_NAME + " STRING, " 
				   + TEMPLATE_COLUMN_CATEGORY + " STRING, " 
				   + "FOREIGN KEY (" + TEMPLATE_COLUMN_COUNT_ID + ") REFERENCES " + TABLE_COUNT + " (" + COUNT_COLUMN_ID + "), " 
				   + "FOREIGN KEY (" + TEMPLATE_COLUMN_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORY + " (" + CATEGORY_COLUMN_ID + "), " 
				   + "FOREIGN KEY (" + TEMPLATE_COLUMN_SCHEDULE_ID + ") REFERENCES " + TABLE_SCHEDULE + " (" + SCHEDULE_COLUMN_ID + "))");
		
		database.execSQL("CREATE TABLE " + TABLE_COUNT + " (" 
				   + COUNT_COLUMN_ID + " INTEGER PRIMARY KEY, " 
				   + COUNT_COLUMN_TEMPLATE_ID + " INTEGER, " 
				   + COUNT_COLUMN_VARIABLE + " INTEGER, " 
				   + "FOREIGN KEY (" + COUNT_COLUMN_TEMPLATE_ID + ") REFERENCES " + TABLE_TEMPLATE + " (" + TEMPLATE_COLUMN_ID + "))");
		
		database.execSQL("CREATE TABLE " + TABLE_CATEGORY + " (" 
				   + CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY, " 
				   + CATEGORY_COLUMN_TYPE + " STRING)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		// handle database schema upgrades
		database.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		onCreate(database);
	}

}
