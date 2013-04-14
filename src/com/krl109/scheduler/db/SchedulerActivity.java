package com.krl109.scheduler.db;

import java.util.Calendar;

import com.krl109.scheduler.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class SchedulerActivity extends Activity {
    private TimeListDatabaseHelper databaseHelper;
    Schedule schedule;
	
	EditText recipient, message;
    TimePicker time;
    DatePicker date;
    Button save;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_timer);
        
        databaseHelper = new TimeListDatabaseHelper(this);

		// when button save clicked
		save = (Button) findViewById(R.id.button1);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// initiate variable date and time
				recipient = (EditText) findViewById(R.id.editText1);
				message = (EditText) findViewById(R.id.editText2);
				time = (TimePicker) findViewById(R.id.timePicker1);
				date = (DatePicker) findViewById(R.id.datePicker1);
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getCurrentHour(), time.getCurrentMinute());
				long dateTime = calendar.getTimeInMillis();

				//databaseHelper.saveTimeRecord(dateTime, recipient.getText().toString(), message.getText().toString());
				
				//Toast.makeText(getApplicationContext(), "Timemillist = " + dateTime, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "Schedule saved", Toast.LENGTH_SHORT).show();
//				AlarmManageHelper alarm = new AlarmManageHelper();
//				alarm.setTimemillist(6546);
				
				// call AlarmManager
				Intent intent = new Intent(SchedulerActivity.this, AlarmManageHelper.class);
				intent.putExtra("timemillist", dateTime);
				startActivity(intent);
			}
		});
    }
}