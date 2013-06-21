package com.krl109.scheduler.newSchedule;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.krl109.scheduler.R;

public class AndroidContactsSelector extends Activity implements OnClickListener, TextWatcher,OnItemClickListener {
	final String 			LOG_TAG			= "PocketMagic";
	public static ArrayList<String> name = new ArrayList<String>();
	EditText				m_etSearch;
	ListView				m_lvContacts;
	
	Cursor								m_curContacts;
	SimpleCursorAdapter 				m_slvAdapter;
	
	EditText recipient;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate called.");
        setContentView(R.layout.contact);
        

		TextView cTV = (TextView) findViewById(R.id.textView1);
		cTV.setText("Select Contact");
	
		// cancel button in ibMenu, with .xml for two press states
		Button m_bCancel = (Button) findViewById(R.id.btn_cancel);
		m_bCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		Button m_bOk = (Button) findViewById(R.id.btn_ok);
		m_bOk.setText("Add Selected");
		m_bOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String getIt = new String("You selected : ");
				SparseBooleanArray checked = m_lvContacts.getCheckedItemPositions();
				recipient = (EditText) findViewById(R.id.recipient);
		        
		        for(int i = 0; i < checked.size()+1; i++){
		            if(checked.get(i)){
//		            	getIt = getIt + "," + m_lvContacts.getAdapter().getItem(i).toString();
//		            	getIt = getIt + "," + m_lvContacts.getItemAtPosition(i);
		            	Cursor cursor = (Cursor) m_lvContacts.getItemAtPosition(i);
		        		String szDisplayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//		        		getIt = getIt + "," + szDisplayName;
		        		name.add(szDisplayName);
		        		//Log.d(LOG_TAG, getIt);
		        		
		            }
		        }
		        recipient.setText((CharSequence) name);
//		        for (int i=0;i < NewSchedule.name.size();i++)
//				{
//					Log.d("recipient", NewSchedule.name.get(i));
//				}
	/*	        long[] ids = m_lvContacts.getCheckedItemIds();
		        for(long id : ids) {
		            Cursor contact = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
		                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id + "" }, null);
		            while (contact.moveToNext()) {
		                String number = contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		                String typeStr = contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
		                Log.d("number", number);
		                Log.d("TYPE", typeStr);
		            }
		        contact.close();
		            
		            // Do 
		        }
		        finish();*/
			}
		});

     	// Search
		m_etSearch = (EditText) findViewById(R.id.search);
		m_etSearch.setHint("Search");
		m_etSearch.addTextChangedListener((TextWatcher) this);
		
		// create listview
		m_lvContacts = (ListView) findViewById(R.id.listPhone);
		m_lvContacts.setOnItemClickListener((OnItemClickListener) this);
		ReadContacts("");
		//return interface
        
        
    }
    
    @SuppressWarnings("deprecation")
	void ReadContacts(String sort) {
		final Uri uri = ContactsContract.Contacts.CONTENT_URI;
        final String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //boolean mShowInvisible = false;
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'";
        String[] selectionArgs = null;
        final String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        m_curContacts = managedQuery(uri, projection, selection, selectionArgs, sortOrder);
        String[] fields = new String[] {ContactsContract.Data.DISPLAY_NAME};
        m_slvAdapter = new SimpleCursorAdapter(this, 
        		android.R.layout.simple_list_item_multiple_choice, 
        		m_curContacts,
                fields, 
                new int[] {android.R.id.text1});
        m_slvAdapter.setFilterQueryProvider(new FilterQueryProvider() {
     
			public Cursor runQuery(CharSequence constraint) {
				Log.d(LOG_TAG, "runQuery constraint:"+constraint);
				String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'" +
					" AND "+ ContactsContract.Contacts.DISPLAY_NAME + " LIKE '%"+constraint+"%'";
				String[] selectionArgs = null;//new String[]{"'1'"};//, };
				Cursor cur = managedQuery(uri, projection, selection, selectionArgs, sortOrder);
				return cur;
			}
        
        });
        m_lvContacts.setAdapter(m_slvAdapter); 
        m_lvContacts.setItemsCanFocus(false);
        m_lvContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
      //  cur.close();
		 
	}

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy called.");
        if (m_curContacts!=null) m_curContacts.close();
        m_etSearch.removeTextChangedListener(this);

	}

	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		Cursor cursor = (Cursor) m_lvContacts.getItemAtPosition(position);
		String szDisplayName = cursor.getString(cursor.getColumnIndexOrThrow( ContactsContract.Contacts.DISPLAY_NAME));
//		String szNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		String szId = cursor.getString(cursor.getColumnIndexOrThrow( ContactsContract.Contacts._ID));
		int nId = cursor.getInt(cursor.getColumnIndexOrThrow( ContactsContract.Contacts._ID));
		
		/*Log.d(LOG_TAG, "Item click:"+position+" szId:"+szId+" nId:"+nId+" Data:"+szDisplayName);
		Toast.makeText(getBaseContext(), "Item click:"+position+" szId:"+szId+" nId:"+nId+" Data:"+szDisplayName, Toast.LENGTH_SHORT).show();*/
	}
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (m_slvAdapter!=null) {
			m_slvAdapter.getFilter().filter(s);
			m_lvContacts.setAdapter(m_slvAdapter); 
		}

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}