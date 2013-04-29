package com.krl109.scheduler.newSchedule;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.krl109.scheduler.R;
import com.krl109.scheduler.db.TimeListDatabaseHelper;
import com.krl109.scheduler.main.MainActivity;
import com.krl109.scheduler.tabLayout.ListScheduleView;
import com.krl109.scheduler.tabLayout.Schedule;
import com.krl109.scheduler.template.Template;

public class NewSchedule extends Activity implements OnClickListener
{
	protected static final int CONTACT_PICKER_RESULT = 1001;
	private TimeListDatabaseHelper databaseHelper;
	private long timemillis;
	EditText recipient, datetime, content, freqTime;
	Button btn_save, btn_cancel;
	ImageButton btn_contact, btn_datetime, btn_template; 
	Spinner frequency;
	String[] freq = { "Once", "hourly", "daily", "weekly", "monthly", "yearly", "2 hourly", "4 hourly", "6 hourly",
				      "8 hourly", "12 hourly", "2 weekly", "3 weekly", "2 monthly", "4 monthly", "6 monthly"};

	DatePicker date_schedule;
	TimePicker time_schedule;
	String[] data;
	boolean check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_schedule);
		
		databaseHelper = new TimeListDatabaseHelper(this);
		
		recipient = (EditText) findViewById(R.id.recipient);
		datetime = (EditText) findViewById(R.id.datetime);
		content = (EditText) findViewById(R.id.content);
		freqTime = (EditText) findViewById(R.id.stopAfter);
		
		data = new String[8];
		
		//menampilkan dropdown pilihan frekuensi pengiriman
		frequency = (Spinner) findViewById(R.id.frequency);
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, freq);
		list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frequency.setAdapter(list);
		
		//Toast.makeText(New_schedule.this, freq[frequency.getSelectedItemPosition()],Toast.LENGTH_LONG).show();
		
		btn_contact = (ImageButton) findViewById(R.id.btn_contact);
		btn_contact.setOnClickListener(this);
		
		btn_datetime = (ImageButton) findViewById(R.id.btn_datetime);
		btn_datetime.setOnClickListener(this);
				
		btn_template = (ImageButton) findViewById(R.id.btn_template);
		btn_template.setOnClickListener(this);
		
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//untuk memproses penyimpanan schedule baru
				String toastMessage = 
						"Frequency 	   : " + freq[frequency.getSelectedItemPosition()] + "\n" +
						"Content       : " + content.getText().toString();
			
				Toast t = Toast.makeText(NewSchedule.this, toastMessage, Toast.LENGTH_LONG);
				t.show();
				
				data[0] = datetime.getText().toString(); //datetime
				data[1] = recipient.getText().toString(); //recipient
				data[2] = content.getText().toString(); //message
				data[3] = freq[frequency.getSelectedItemPosition()].toString(); //frequency
				data[4] = freqTime.getText().toString(); //remaining
				data[5] = "scheduled"; //status
				data[6] = "2"; //freqtimes
				
				//check the timemillis before save to database
				//1. get whether true of false timemillis in database via TimeListDatabaseHelper
				//2. if false then addTimemillis in method NewSchedule
				//3. repetition until there is no same timemillis in database
				check = databaseHelper.checkTimemillis(timemillis);
				while(check == false){
					addTimemillis(timemillis);
					check = databaseHelper.checkTimemillis(timemillis);
				}
				
				databaseHelper.saveTimeRecord(timemillis, data);
				/*for(int i = 0;i<7;i++)
				{
				Toast.makeText(getApplicationContext(), data[i], Toast.LENGTH_SHORT).show();
				}*/
				
				//Toast.makeText(getApplicationContext(), " TimeMilis" + dateTime, Toast.LENGTH_SHORT).show();
				
				Toast.makeText(getApplicationContext(), "Schedule saved", Toast.LENGTH_SHORT).show();
				Intent create_schedule = new Intent(NewSchedule.this, Schedule.class);
		    	startActivity(create_schedule);
				
			}
		});
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent cancel = new Intent(NewSchedule.this, MainActivity.class);
		    	startActivity(cancel);
			}
		});
		
	}	

	@Override
	public void onClick(View v) 
	{
		if(v.equals(btn_contact))
		{
			doLaunchContactPicker(v);
		}
		else if(v.equals(btn_datetime))
		{
			datetimeDialog();
		}
		else if(v.equals(btn_template))
		{
			Intent temp = new Intent(NewSchedule.this, Template.class);
	    	startActivity(temp);
		}
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
				Calendar calendar = Calendar.getInstance();
				calendar.set(date_schedule.getYear(), date_schedule.getMonth(), date_schedule.getDayOfMonth(), 
						time_schedule.getCurrentHour(), time_schedule.getCurrentMinute());
				timemillis = calendar.getTimeInMillis();
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
				//datetime = (EditText) findViewById(R.id.datetime);
				datetime.setText(date + " ; " + time);
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
	
	public void doLaunchContactPicker(View v)
	{
		Intent contactPicker = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		startActivityForResult(contactPicker, CONTACT_PICKER_RESULT);
	}
	
	//check to database whether get the same timemillis from database or not
	//if same, then timemillis will add one millisecond
	public long addTimemillis(long timemillis){
		timemillis = timemillis + 1;
		return timemillis;
	}
}
