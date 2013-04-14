package com.krl109.scheduler.tabLayout;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.array;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;
import com.krl109.scheduler.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ListPaused extends Activity 
{
	/*TextView txt = new TextView(this);
    txt.setText("Failed Folder");
    setContentView(txt);*/
	private ListView listMessage;
	private String[] messageList;
	Button new_schedule;
	DatePicker date_schedule;
	TimePicker time_schedule;
	String[] option_edit = {"Edit All", "Edit Recipient", "Edit Date and Time", "Edit Content"};
	String sJwban;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_schedule);
		
		messageList = getResources().getStringArray(R.array.history);
		
		listMessage = (ListView) findViewById(R.id.history);
		listMessage.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, messageList));
		registerForContextMenu(listMessage);
		
		new_schedule = (Button) findViewById(R.id.btn_new_schedule);
		new_schedule.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Toast.makeText(getApplicationContext(), "schedule",Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Option");

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_paused, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case R.id.resume:
			Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			break;
		case R.id.forward:
			Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			break;
		case R.id.removed:
			Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}