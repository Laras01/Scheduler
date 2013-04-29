package com.krl109.scheduler.template;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.krl109.scheduler.R;
import com.krl109.scheduler.main.MainActivity;



public class NewTemplate extends Activity implements OnClickListener 
{
	Button add_count, btn_save, btn_cancel;
	RadioButton radioDataButton;
	RadioGroup radioDataGroup;
	EditText tmp_message, tmp_date;
	ImageButton btn_datetime;
	StringBuilder sb;
	Spinner category;
	String[] var = {"Age", "Date", "Month", "Year"};
	String[] cat ={
		"Birthday", "Anniversary", "Other"	
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_template);
		//tmp_message = (EditText) findViewById(R.id.tmp_message);
		
		category = (Spinner) findViewById(R.id.freq_spinner);
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cat);
		list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(list);
		
		btn_datetime = (ImageButton) findViewById(R.id.btn_datetime);
		btn_datetime.setOnClickListener(this);

		add_count = (Button) findViewById(R.id.btn_data_count);
		add_count.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//untuk buka list template
				addCountDialog();
			}
		});
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		
	}

	protected void addCountDialog() 
	{
		final Dialog d = new Dialog(this);
		d.setTitle("Add Data for Count");
		d.setContentView(R.layout.data_count);
				
		Button btn_ok, btn_cancel;
		radioDataGroup = (RadioGroup) findViewById(R.id.radioDataCount);		
				
		btn_ok = (Button) d.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				 // get selected radio button from radioGroup
				int selectedId = radioDataGroup.getCheckedRadioButtonId();
	 
				// find the radiobutton by returned id
			        radioDataButton = (RadioButton) findViewById(selectedId);
	 
				Toast.makeText(NewTemplate.this,
					radioDataButton.getText(), Toast.LENGTH_SHORT).show();
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
		
		//d.unregisterForContextMenu(btn_ok);
		d.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btn_save))
		{
			//save
		}
		else if(v.equals(btn_datetime))
		{
			//retrieve from contact
			/*
			 * if cat equals birthday
			 * then find data.birthday contact 
			 * 		if null then
			 * 			show alert
			 * 		endif
			 * endif*/
		}
		else if(v.equals(btn_cancel))
		{
			Intent temp = new Intent(NewTemplate.this, MainActivity.class);
	    	startActivity(temp);
		}
	}

}
