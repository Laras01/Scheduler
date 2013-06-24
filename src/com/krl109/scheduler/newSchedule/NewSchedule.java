package com.krl109.scheduler.newSchedule;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
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
import com.krl109.scheduler.db.AlarmManagerActivity;
import com.krl109.scheduler.db.SMSActivity;
import com.krl109.scheduler.db.TimeListDatabaseHelper;
import com.krl109.scheduler.main.MainActivity;
import com.krl109.scheduler.tabLayout.ListScheduleView;
import com.krl109.scheduler.tabLayout.Schedule;
import com.krl109.scheduler.template.Template;

public class NewSchedule extends Activity implements OnClickListener
{
	protected static final int CONTACT_PICKER_RESULT = 1001;
	private TimeListDatabaseHelper databaseHelper;
	private long timemillis, timesent;
	public static EditText recipient, datetime, content, freqTime;
	Button btn_save, btn_cancel, btn_data;
	ImageButton btn_contact, btn_datetime, btn_template; 
	Spinner frequency;
	String[] freq = { "Once", "hourly", "daily", "weekly", "monthly", "yearly", "2 hourly", "4 hourly", "6 hourly",
				      "8 hourly", "12 hourly", "2 weekly", "3 weekly", "2 monthly", "4 monthly", "6 monthly"};
	String[] def_ch={" %%AGE%%", " %%DATE%%", " %%MONTH%%",
	" %%YEAR%%"};
	String message;
	DatePicker date_schedule;
	TimePicker time_schedule;
	String[] data, dataRepetition;
	long[] time;
	boolean check;
	Frequency freqtimes = new Frequency();
	Schedule schedule;
	
	AlarmManager alarm;
	
	//ArrayList<Long> messageId = new ArrayList<Long>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_schedule);
		alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = getIntent();
		message = intent.getStringExtra("message");
		databaseHelper = new TimeListDatabaseHelper(this);
		
		recipient = (EditText) findViewById(R.id.recipient);
		datetime = (EditText) findViewById(R.id.datetime);
		content = (EditText) findViewById(R.id.content);
		freqTime = (EditText) findViewById(R.id.stopAfter);
		
		content.setText(message);
		
		data = new String[9];
		time = new long[2];
		dataRepetition = new String[3];
		
		//menampilkan dropdown pilihan frekuensi pengiriman
		//show dropdown frequency options of send
		frequency = (Spinner) findViewById(R.id.frequency);
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, freq);
		list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frequency.setAdapter(list);
		
		//Toast.makeText(New_schedule.this, freq[frequency.getSelectedItemPosition()],Toast.LENGTH_LONG).show();
		
		btn_contact = (ImageButton) findViewById(R.id.btn_contact);
		btn_contact.setOnClickListener(this);
		
		btn_data = (Button) findViewById(R.id.btn_data_msg);
		registerForContextMenu(btn_data);
		
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
				/*String toastMessage = 
						"Frequency 	   : " + freq[frequency.getSelectedItemPosition()] + "\n" +
						"Content       : " + content.getText().toString();*/
			
				/*Toast t = Toast.makeText(NewSchedule.this, toastMessage, Toast.LENGTH_LONG);
				t.show();*/
				
				data[0] = datetime.getText().toString(); //datetime
				data[1] = recipient.getText().toString(); //recipient
				data[2] = content.getText().toString(); //message
				data[3] = freq[frequency.getSelectedItemPosition()].toString(); //frequency -> once | hourly | etc
				data[4] = freqTime.getText().toString(); //remaining
				data[5] = "scheduled"; //status
				data[6] = "2";//freqtimes.frequencyTimes(frequency); //freqtimes // yang pake formula (belum dibuat)
				
				//check the timemillis before save to database via TimeListDatabaseHelper
				timemillis = databaseHelper.addTimemillisFromTime(timemillis);
				
				/*Toast.makeText(getApplicationContext(), "Schedule saved", Toast.LENGTH_SHORT).show();
				Intent create_schedule = new Intent(NewSchedule.this, AlarmManageHelper.class);
				create_schedule.putExtra("timemillist", timemillis);
		    	startActivity(create_schedule);
				
		    	//save to database via TimeListDatabaseHelper8
				databaseHelper.saveTimeRecord(timemillis, data);*/
				schedule = new Schedule(timemillis, timesent, data);
				
				//check the type of message, whether dynamic or static message -> contain %%y, etc
				schedule.setMessageType(schedule.contentMessages);
				data[7] = schedule.getType();
				
				databaseHelper.saveScheduletoMessage(schedule.getTimemillis(), data);
				databaseHelper.setMessageIdFromMessage(timemillis);
				databaseHelper.saveScheduleToType(data[7], schedule.getContentMessages(), schedule.getScheduleId());
				databaseHelper.saveScheduleToTime(schedule.getTimemillis(), timesent, schedule.getScheduleId());
				databaseHelper.saveScheduleToContact(schedule.getRecipientNumbers());
				databaseHelper.saveScheduleToRecipient(data[1], schedule.getScheduleId());
				
				/*Toast t = Toast.makeText(NewSchedule.this, "" + Integer.parseInt(data[4]), Toast.LENGTH_LONG);
				t.show();*/
				
				//messageId.add(timesent);
				
				//check whether schedule has repetition or not
				Intent intent = new Intent(NewSchedule.this, SMSActivity.class);
				PendingIntent pending = PendingIntent.getActivity(NewSchedule.this, (int) timemillis, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				if(data[3] != "Once"){
					dataRepetition[0] = data[3]; //frequency
					dataRepetition[1] = schedule.getScheduleId(); //messageId
					dataRepetition[2] = data[4]; //remaining
					
					time[0] = timemillis;
					time[1] = timesent;
					
					freqtimes.repetition(pending, alarm, databaseHelper, dataRepetition, time);
				}
				else{
					alarm.set(AlarmManager.RTC_WAKEUP, timesent, pending);
				}
				//intent.putExtra("timemillis", timesent);
				//intent.putExtra("timemillisas", messageId);
				/*PendingIntent pending = PendingIntent.getActivity(NewSchedule.this, (int) timemillis, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				alarm.set(AlarmManager.RTC_WAKEUP, timesent, pending);*/
				/*Toast t = Toast.makeText(NewSchedule.this, "" + schedule.getTimesent(), Toast.LENGTH_SHORT);
				t.show();*/
				
				/*Intent intent1 = new Intent(AlarmManageHelper.this, SMSActivity.class);
				intent1.putExtra("phoneNumber", schedule.recipient);
				intent1.putExtra("message", schedule.message);
				PendingIntent pending = PendingIntent.getActivity(AlarmManageHelper.this, (int) timemillist, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
				AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
				alarm.set(AlarmManager.RTC_WAKEUP, timemillist, pending);*/
				
				/*Toast t = Toast.makeText(NewSchedule.this, "" + schedule.getScheduleId(), Toast.LENGTH_LONG);
				t.show();*/
				Toast t = Toast.makeText(NewSchedule.this, "Scheduled", Toast.LENGTH_SHORT);
				t.show();
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Choose The define Character");
		menu.add(0, v.getId(), 0, "AGE");
		menu.add(0, v.getId(), 0, "DATE");
		menu.add(0, v.getId(), 0, "MONTH");
		menu.add(0, v.getId(), 0, "YEAR");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getTitle().equals("AGE"))
		{
			content.append(def_ch[0]);

		}else if(item.getTitle().equals("DATE"))
		{
			content.append(def_ch[1]);
		}else if(item.getTitle().equals("MONTH"))
		{
			content.append(def_ch[2]);
		}else if(item.getTitle().equals("YEAR"))
		{
			content.append(def_ch[3]);

		}else {return false;}
		return true;
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
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(date_schedule.getYear(), date_schedule.getMonth(), date_schedule.getDayOfMonth(), 
						time_schedule.getCurrentHour(), time_schedule.getCurrentMinute());
				timemillis = calendar.getTimeInMillis();
				timesent = calendar.getTimeInMillis();
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
		/*Intent contactPicker = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		startActivityForResult(contactPicker, CONTACT_PICKER_RESULT);*/
		
		Intent contactPicker = new Intent(NewSchedule.this, AndroidContactsSelector.class);
		startActivity(contactPicker);
	}
}
