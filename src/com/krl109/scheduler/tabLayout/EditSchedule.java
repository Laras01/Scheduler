package com.krl109.scheduler.tabLayout;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;
import com.krl109.scheduler.main.MainActivity;
import com.krl109.scheduler.template.Template;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.provider.ContactsContract.Contacts;

public class EditSchedule extends Activity implements OnClickListener
{
	protected static final int CONTACT_PICKER_RESULT = 1001;
	EditText recipient, datetime, content;
	Button btn_save, btn_cancel;
	ImageButton btn_contact, btn_datetime, btn_template; 
	Spinner frequency;
	String[] freq = { "Once", "hourly", "daily", "weekly", "monthly", "yearly", "2 hourly", "4 hourly", "6 hourly",
				      "8 hourly", "12 hourly", "2 weekly", "3 weekly", "2 monthly", "4 monthly", "6 monthly"};

	DatePicker date_schedule;
	TimePicker time_schedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_schedule);
		
		recipient = (EditText) findViewById(R.id.recipient);
		datetime = (EditText) findViewById(R.id.datetime);
		content = (EditText) findViewById(R.id.content);
		
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
				
				//Toast.makeText(New_schedule.this, freq[frequency.getSelectedItemPosition()],Toast.LENGTH_LONG).show();
				String toastMessage = 
						"Frequency 	   : " + freq[frequency.getSelectedItemPosition()] + "\n" +
						"Content       : " + content.getText().toString();
			
				Toast t = Toast.makeText(EditSchedule.this, toastMessage, Toast.LENGTH_LONG);
				t.show();
			}
		});
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent cancel = new Intent(EditSchedule.this, MainActivity.class);
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
			Intent temp = new Intent(EditSchedule.this, Template.class);
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
}