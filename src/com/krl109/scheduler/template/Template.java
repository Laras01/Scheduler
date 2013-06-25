package com.krl109.scheduler.template;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.krl109.scheduler.R;
import com.krl109.scheduler.db.TemplateDatabaseHelper;
import com.krl109.scheduler.newSchedule.NewSchedule;

public class Template extends Activity 
{
	private TemplateDatabaseHelper helper;
	ArrayAdapter<String> adapter = null;
	//	String[] template = {"temp1", "temp2", "temp3", "temp4", "temp5"};
	Button add;
	EditText content;
	TextView tv;
	String message, type, category;
	private ArrayList<String> 	templatesNameArray = new ArrayList<String>();
	private ArrayList<String> 	templatesMessageArray = new ArrayList<String>();
	private ArrayList<String> 	templatesTypeArray = new ArrayList<String>();
	private ArrayList<Integer>	templatesIdArray = new ArrayList<Integer>();
	private ArrayList<Integer>	templatesCategoryIdArray = new ArrayList<Integer>();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		helper = new TemplateDatabaseHelper(this);
		Cursor cursor = helper.getTemplateList();
		if (cursor.moveToFirst())
		{
			do{
				templatesNameArray.add(cursor.getString(cursor.getColumnIndex(TemplateDatabaseHelper.TEMPLATE_COLUMN_NAME)));
				templatesMessageArray.add(cursor.getString(cursor.getColumnIndex(TemplateDatabaseHelper.TEMPLATE_COLUMN_MESSAGE)));
				templatesTypeArray.add(cursor.getString(cursor.getColumnIndex(TemplateDatabaseHelper.TEMPLATE_COLUMN_TYPE)));
				templatesIdArray.add(cursor.getInt(cursor.getColumnIndex(TemplateDatabaseHelper.TEMPLATE_COLUMN_ID)));
				templatesCategoryIdArray.add(cursor.getInt(cursor.getColumnIndex(TemplateDatabaseHelper.TEMPLATE_COLUMN_CATEGORY_ID)));
			}while(cursor.moveToNext());
		}else{
			tv = (TextView) findViewById(R.id.noTemplate);
			tv.setText("No Template(s) Yet.");

		}

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, templatesNameArray);

		final ListView lv = (ListView) findViewById(R.id.list_template);

		View footer = getLayoutInflater().inflate(R.layout.footer_template, null);
		lv.addFooterView(footer);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("clicked", lv.getItemAtPosition(position).toString());
				Log.d("id", ""+templatesIdArray.get(position));
				//				message = helper.getMessageTemplate(lv.getItemAtPosition(position).toString());
				message = helper.getMessageTemplate(templatesIdArray.get(position));
				type = templatesTypeArray.get(position);
				category = helper.getCategory(templatesCategoryIdArray.get(position));
				Intent i = new Intent(Template.this, NewSchedule.class);
				i.putExtra("message", message);
				i.putExtra("type", type);
				i.putExtra("category", category);
				startActivity(i);
			}

		});
		registerForContextMenu(lv);
		add = (Button) findViewById(R.id.add_template);
		add.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent add_template = new Intent(Template.this, NewTemplate.class);
				startActivity(add_template);
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Option");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_template, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		String itemName = templatesNameArray.get(info.position);
		int itemId = templatesIdArray.get(info.position);
		Log.d("item", itemName);
		Log.d("item_id", ""+itemId);
		if(helper.removeTemplate(itemId)){
			Template.this.finish();
		    startActivity(getIntent());
		}
		//		  helper.removeTemplate(itemName);
		return true;

	}
}
