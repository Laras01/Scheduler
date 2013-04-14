package com.krl109.scheduler.tabLayout;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;
import com.krl109.scheduler.main.MainActivity;
import com.krl109.scheduler.template.Template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ImageButton;

public class EditContent extends Activity implements OnClickListener
{
	EditText content;
	Button btn_save, btn_cancel;
	ImageButton btn_template;
	Spinner frequency;
	String[] freq = { "Once", "hourly", "daily", "weekly", "monthly", "yearly", "2 hourly", "4 hourly", "6 hourly",
				      "8 hourly", "12 hourly", "2 weekly", "3 weekly", "2 monthly", "4 monthly", "6 monthly"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_content);
		
		content = (EditText) findViewById(R.id.content);
		
		btn_template = (ImageButton) findViewById(R.id.btn_template);
		btn_template.setOnClickListener(this);
		
		//menampilkan dropdown pilihan frekuensi pengiriman
		frequency = (Spinner) findViewById(R.id.frequency);
		ArrayAdapter<String> list = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, freq);
		list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frequency.setAdapter(list);
		
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//untuk memproses penyimpanan schedule baru
				
				//Toast.makeText(EditContent.this, freq[frequency.getSelectedItemPosition()],Toast.LENGTH_LONG).show();
				
				String toastMessage = 
						"Frequency 	   : " + freq[frequency.getSelectedItemPosition()] + "\n" +
						"Content       : " + content.getText().toString();
			
				Toast t = Toast.makeText(EditContent.this, toastMessage, Toast.LENGTH_LONG);
				t.show();
			}
		});
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent cancel = new Intent(EditContent.this, MainActivity.class);
		    	startActivity(cancel);
			}
		});
	}

	@Override
	public void onClick(View v) 
	{
		if(v.equals(btn_template))
		{
			Intent tmp = new Intent(EditContent.this, Template.class);
	    	startActivity(tmp);
		}
		
	}

}
