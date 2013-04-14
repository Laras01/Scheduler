package com.krl109.scheduler.template;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Template extends Activity 
{
	//ArrayList<String> template = new ArrayList<String>();
	ArrayAdapter adapter = null;
	String[] template = {"temp1", "temp2", "temp3", "temp4", "temp5"};
	Button add;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, template);
		
		ListView lv = (ListView) findViewById(R.id.list_template);
		
		View footer = getLayoutInflater().inflate(R.layout.footer_template, null);
		
		lv.addFooterView(footer);
		lv.setAdapter(adapter);
		
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
}
