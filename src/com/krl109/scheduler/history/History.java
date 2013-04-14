package com.krl109.scheduler.history;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.array;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;
import com.krl109.scheduler.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class History extends Activity implements OnItemClickListener 
{
	private ListView listMessage;
	private String[] messageList;
	
	String[] items = {"See detail"};
	String choose;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		messageList = getResources().getStringArray(R.array.history);
		listMessage = (ListView) findViewById(R.id.history);
		listMessage.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, messageList));
		listMessage.setOnItemClickListener(this);
	}
	
	/**
     * Menampilkan option dari masing-masing item di list
     */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		//Toast.makeText(this, messageList[arg2], 1000).show();	
		
		//menampilkan list dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Option");
		builder.setItems(items, new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int item) 
			{
				//Toast.makeText(getApplicationContext(), items[item],Toast.LENGTH_SHORT).show();
				choose = items[item];
				if(choose.equals("See detail"))
				{
					Intent intent = new Intent(History.this, HistoryDetail.class);
					startActivity(intent);
				}
			}
		}).show();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_history, menu);
        return true;
    }
    
    /**
     * Menampilkan menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		
    	switch (item.getItemId())
        {
        case R.id.clear_all:
            Toast.makeText(History.this, "Clear all", Toast.LENGTH_SHORT).show();
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
}