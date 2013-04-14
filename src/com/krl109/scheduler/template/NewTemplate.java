package com.krl109.scheduler.template;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewTemplate extends Activity 
{
	Button add_count;
	EditText start_at, increase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_template);
		
		add_count = (Button) findViewById(R.id.btn_data_count);
		add_count.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//untuk buka list template
				addCountDialog();
			}
		});
	}
	
	protected void addCountDialog() 
	{
		final Dialog d = new Dialog(this);
		d.setTitle("Add Data for Count");
		d.setContentView(R.layout.data_count);
				
		Button btn_ok, btn_cancel;
				
		start_at = (EditText) d.findViewById(R.id.start_at);
		increase = (EditText) d.findViewById(R.id.increase);
						
				
		btn_ok = (Button) d.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getBaseContext(),"Start at : " + start_at.getText().toString() + "\n" + "Increase : " + increase.getText().toString(),Toast.LENGTH_SHORT).show();
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

}
