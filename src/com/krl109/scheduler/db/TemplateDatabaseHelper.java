package com.krl109.scheduler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TemplateDatabaseHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "scheduler-krl6.db";
	private static final String TABLE_MESSAGE = "message";
	private static final String TABLE_STATIC_MESSAGE = "static_message";
	private static final String TABLE_DYNAMIC_MESSAGE = "dynamic_message";
	private static final String TABLE_CONTACT_NUMBER = "contact";
	private static final String TABLE_RECIPIENT = "recipient";
	private static final String TABLE_TIME = "time";
	private static final String TABLE_TEMPLATE = "template";
	private static final String TABLE_DEFINED_CHARACTER = "defined_character";
	private static final String TABLE_CATEGORY = "category";

	public static final String TEMPLATE_COLUMN_ID = "template_id";
	public static final String TEMPLATE_COLUMN_CATEGORY_ID = "template_category_id";
	public static final String TEMPLATE_COLUMN_MESSAGE = "template_message";
	public static final String TEMPLATE_COLUMN_TYPE = "template_type";
	public static final String TEMPLATE_COLUMN_NAME = "template_name";

	public static final String CATEGORY_COLUMN_ID = "category_id";
	public static final String CATEGORY_COLUMN_TYPE = "category_type";

	public static final String DEFINEDCHAR_COLUMN_ID = "dc_id";
	public static final String DEFINEDCHAR_COLUMN_DM_ID = "dc_dm_id";
	public static final String DEFINEDCHAR_COLUMN_VARIABLE = "dc_variable";
	public static final String DEFINEDCHAR_COLUMN_CONTENT = "dc_content";
	public static final String DEFINEDCHAR_COLUMN_POSITION = "dc_position";

	private DatabaseOpenHelper openHelper;
	private SQLiteDatabase database;
	//private Schedule schedule;

	public static String message;

	public TemplateDatabaseHelper(Context context) {
		openHelper = new DatabaseOpenHelper(context);
	}

	public void saveTemplatetoTemplate(int categoryID, String[] data) {
		database = openHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(TEMPLATE_COLUMN_CATEGORY_ID, categoryID);
		contentValues.put(TEMPLATE_COLUMN_MESSAGE, data[0]);
		contentValues.put(TEMPLATE_COLUMN_TYPE, data[1]);
		contentValues.put(TEMPLATE_COLUMN_NAME, data[2]);
		database.insert(TABLE_TEMPLATE, null, contentValues);

		Log.e("saved", "sucess");
	}

	public void saveTemplatetoCategory(String data) {
		database = openHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(CATEGORY_COLUMN_TYPE, data);
		database.insert(TABLE_CATEGORY, null, contentValues);

		Log.e("saved", "sucess");
	}

	public boolean checkTemplate(String template) {
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select "+ TEMPLATE_COLUMN_NAME +" from " + TABLE_TEMPLATE + " where " + TEMPLATE_COLUMN_NAME +"='"+ template + "'", null);
		if(cursor.moveToFirst() ){
			return true;
		}
		else{
			return false;}
	}
	
	public boolean checkCategory(String category) {
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select "+ CATEGORY_COLUMN_TYPE +" from " + TABLE_CATEGORY + " where " + CATEGORY_COLUMN_TYPE +"='"+ category + "'", null);
		if(cursor.moveToFirst() ){
			return true;
		}
		else{
			return false;}
	}

	public int getCategoryId(String category)
	{
		database = openHelper.getReadableDatabase();
		int categoryId = 0;
		Cursor cursor = database.rawQuery("select "+ CATEGORY_COLUMN_ID +" from " + TABLE_CATEGORY + " where " + CATEGORY_COLUMN_TYPE + "='" + category + "'", null);
		if(cursor.moveToFirst()){
			categoryId = cursor.getInt(cursor.getColumnIndex(CATEGORY_COLUMN_ID));
		}
		cursor.close();
		return categoryId;
	}

	public String getCategory(int id)
	{
		database = openHelper.getReadableDatabase();
		String category = null;
		Cursor cursor = database.rawQuery("select "+ CATEGORY_COLUMN_TYPE +" from " + TABLE_CATEGORY + " where " + CATEGORY_COLUMN_ID + "='" + id + "'", null);
		if(cursor.moveToFirst()){
			category = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_TYPE));
		}
		cursor.close();
		return category;
	}
	
	public Cursor getAllTemplateRecords(){
		// select all data in database -> timerecords
		return database.rawQuery("select * from " + TABLE_TEMPLATE, null);
	}

	public Cursor getTemplateList()
	{
		database = openHelper.getReadableDatabase();
		return database.rawQuery("select * from " + TABLE_TEMPLATE, null);
	}

	public String getMessageTemplate(int id)
	{
		database = openHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_TEMPLATE + " where "+ TEMPLATE_COLUMN_ID +"='" + id +"'", null);
		if(cursor != null){
			while(cursor.moveToNext()){
				message = cursor.getString(cursor.getColumnIndex(TEMPLATE_COLUMN_MESSAGE));
			}
		}
		cursor.close();
		return message;
	}

	/**
	 * @detail removes a template
	 * @param id
	 * @return returns true if removed successfully else false;
	 */
	public boolean removeTemplate(int id){
		try{
			database.delete(TABLE_TEMPLATE, TEMPLATE_COLUMN_ID + "=" + id, null);
			Log.d("removed", "success");
			return true;
		}catch(SQLException sqe){
			Log.e("fail deleted", "failed");
			return false;
		}
	}
	//-----------------------------------------------------end of functions for template table-----
}