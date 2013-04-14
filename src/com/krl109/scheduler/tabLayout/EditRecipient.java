package com.krl109.scheduler.tabLayout;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.id;
import com.krl109.scheduler.R.layout;
import com.krl109.scheduler.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

public class EditRecipient extends Activity implements OnClickListener
{
	protected static final int CONTACT_PICKER_RESULT = 1001;
	EditText recipient;
	Button btn_save, btn_cancel;
	ImageButton btn_contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_recipient);
		
		recipient = (EditText) findViewById(R.id.recipient);
		
		btn_contact = (ImageButton) findViewById(R.id.btn_contact);
		btn_contact.setOnClickListener(this);
		
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				//untuk memproses penyimpanan schedule baru
				
				Toast.makeText(EditRecipient.this, "Simpan Jadwal",Toast.LENGTH_LONG).show();
			}
		});
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new Button.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent cancel = new Intent(EditRecipient.this, MainActivity.class);
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
	}
	
	public void doLaunchContactPicker(View v)
	{
		Intent contactPicker = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		startActivityForResult(contactPicker, CONTACT_PICKER_RESULT);
	}
}
