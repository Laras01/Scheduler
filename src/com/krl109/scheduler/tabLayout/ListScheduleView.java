package com.krl109.scheduler.tabLayout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.krl109.scheduler.R;
import com.krl109.scheduler.db.TimeListDatabaseHelper;
import com.krl109.scheduler.main.MainActivity;
import com.krl109.scheduler.newSchedule.NewSchedule;

public class ListScheduleView extends Activity implements
OnItemClickListener {
	private TimeListDatabaseHelper helper;
	Button new_schedule;
	DatePicker date_schedule;
	TimePicker time_schedule;
	String[] option_edit = {"Edit All", "Edit Recipient", "Edit Date and Time", "Edit Content"};
	String sJwban;
	
	public static final Integer[] images = { R.drawable.schedule,
		R.drawable.sent, R.drawable.paused, R.drawable.failed };

	ListView listView;
	List<Schedule> schedule;
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list_schedule);
		helper = new TimeListDatabaseHelper(this);
		Cursor cursor = helper.getScheduleList();
		schedule = new ArrayList<Schedule>();
		if (cursor.moveToFirst())
		{
//			int i = 0;
			do{
				cursor.getLong(1);
				Schedule item = new Schedule(images[0], cursor.getString(2), cursor.getString(3), cursor.getString(4));
				schedule.add(item);
//				i++;
			}while(cursor.moveToNext());
		}
		
		
		/*for (int i = 0; i < titles.length; i++) {
			RowItem item = new RowItem(images[i], titles[i], descriptions[i], descriptions2[i]);
			rowItems.add(item);
		}*/

		listView = (ListView) findViewById(R.id.list);
		CustomListViewAdapter adapter = new CustomListViewAdapter(this,
				R.layout.list_item_schedule, schedule);
		registerForContextMenu(listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		new_schedule = (Button) findViewById(R.id.btn_new_schedule);
		new_schedule.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Toast.makeText(getApplicationContext(), "new schedule",Toast.LENGTH_SHORT).show();
				Intent create_schedule = new Intent(ListScheduleView.this, NewSchedule.class);
		    	startActivity(create_schedule);
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast toast = Toast.makeText(getApplicationContext(),
				"Item " + (position + 1) + ": " + schedule.get(position),
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Option");

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_schedule, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case R.id.open:
			//Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			Intent open = new Intent(ListScheduleView.this, EditSchedule.class);
	    	startActivity(open);
			break;
		case R.id.edit:
			optionEdit();
			break;
		case R.id.forward:
			Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			break;
		case R.id.removed:
			Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			break;
		case R.id.paused:
			Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	private void optionEdit() 
	{
		int numChoiceAns = option_edit.length;
		CharSequence[] opsia = new CharSequence[numChoiceAns];
		 
		for (int i = 0; i < numChoiceAns; i++) 
		{
			opsia[i] = option_edit[i];
		}
				   
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Option Edit");
		dialog.setItems(opsia, new DialogInterface.OnClickListener() 
		{				
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				sJwban = option_edit[which];
				
				if(sJwban.equals("Edit All"))
				{ 
					Intent intent = new Intent(ListScheduleView.this, EditSchedule.class);  
				    startActivity(intent);
				}
				else if(sJwban.equals("Edit Recipient"))
				{
					Intent intent = new Intent(ListScheduleView.this, EditRecipient.class);  
				    startActivity(intent);
				}
				else if(sJwban.equals("Edit Date and Time"))
				{
					datetimeDialog();
				}
				else if(sJwban.equals("Edit Content"))
				{
					Intent intent = new Intent(ListScheduleView.this, EditContent.class);  
				    startActivity(intent);
				}
			}
		}).show();
	}
	
	protected void datetimeDialog() 
	{
		final Dialog d = new Dialog(this);
		d.setTitle("Set Date and Time");
		d.setContentView(R.layout.set_datetime);
		d.show();
		
		Button btn_done, btn_cancel;
				
		date_schedule = (DatePicker) d.findViewById(R.id.datePicker1);
		time_schedule = (TimePicker) d.findViewById(R.id.timePicker1);
		//time_schedule.setIs24HourView(true); //untuk menampilkan time picker dalam hitungan 24 jam
				
				
		btn_done = (Button) d.findViewById(R.id.btn_done);
		btn_done.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//mengambil tanggal dari datepicker
				int day = date_schedule.getDayOfMonth();
				int month = date_schedule.getMonth() + 1;
				int year = date_schedule.getYear();
				String date = day + "/" + month + "/" + year; 
						
				//mengambil waktu dari time picker
				int hour = time_schedule.getCurrentHour();
				int minute = time_schedule.getCurrentMinute();
				String time = hour + ":" + minute;
				
				d.cancel();
				Toast.makeText(getBaseContext(),"Waktu diubah menjadi " + date + ";" + time,Toast.LENGTH_LONG).show();
			}
		});
			
		btn_cancel = (Button) d.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				d.cancel();
			}
		});
	}
}
