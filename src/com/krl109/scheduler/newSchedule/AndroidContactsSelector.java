package com.krl109.scheduler.newSchedule;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
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
	final String LOG_TAG = "PocketMagic";
	public static ArrayList<String> name = new ArrayList<String>();
	public static ArrayList<String> idContact = new ArrayList<String>();
	EditText m_etSearch;
	ListView m_lvContacts;
	
	Cursor m_curContacts, cursor;
	SimpleCursorAdapter m_slvAdapter;
	
	Intent intent;
	StringBuilder sb;
	
	String number;

	
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
				intent = new Intent(AndroidContactsSelector.this, NewSchedule.class);
				sb = new StringBuilder();
				
				name.clear();
						        
		        for(int i = 0; i < checked.size()+1; i++){
		            if(checked.get(i)){
		            	Cursor cursor = (Cursor) m_lvContacts.getItemAtPosition(i);
		        		String szDisplayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
		        		String szIdContact = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
		        		name.add(szDisplayName);
		        		idContact.add(szIdContact);
		            }
		        }
		       
		        if(idContact.size() > 0){
		        	cursor = getContentResolver().query(Data.CONTENT_URI,
						    new String[] {Data._ID, Phone.NUMBER, Phone.TYPE, Phone.LABEL},
						    Data.CONTACT_ID + "=?" + " AND "
						    + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'",
						    new String[] {String.valueOf(idContact.get(0))}, null);
						cursor.moveToFirst();
						number = cursor.getString(1);
						sb.append(number);
//		        	sb.append(idContact.get(0));
					for(int i = 1; i < idContact.size(); i++){
						sb.append(";");
//						sb.append(idContact.get(i));
						cursor = getContentResolver().query(Data.CONTENT_URI,
							    new String[] {Data._ID, Phone.NUMBER, Phone.TYPE, Phone.LABEL},
							    Data.CONTACT_ID + "=?" + " AND "
							    + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'",
							    new String[] {String.valueOf(idContact.get(i))}, null);
							cursor.moveToFirst();
							number = cursor.getString(1);
							sb.append(number);
					}
					//intent.putExtra("recipients", sb.toString());
					NewSchedule.recipient.setText(sb.toString());
					idContact.clear();
					Log.d("recipient", sb.toString());
		        }
		        
		        /*if(name.size() > 0){
		        	sb.append(name.get(0));
					for(int i = 1; i < name.size(); i++){
						sb.append(";");
						sb.append(name.get(i));
					}
					//intent.putExtra("recipients", sb.toString());
					NewSchedule.recipient.setText(sb.toString());
		        }*/
										        
		        //intent.putStringArrayListExtra("recipients", name);
				
				//Toast.makeText(getBaseContext(), sb.toString(), Toast.LENGTH_SHORT).show();
		        
		        AndroidContactsSelector.this.finish();
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
        m_slvAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_multiple_choice, m_curContacts, fields, new int[] {android.R.id.text1});
        m_slvAdapter.setFilterQueryProvider(new FilterQueryProvider() {
     
			public Cursor runQuery(CharSequence constraint) {
				Log.d(LOG_TAG, "runQuery constraint:"+constraint);
				String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'" +
					" AND "+ ContactsContract.Contacts.DISPLAY_NAME + " LIKE '%"+constraint+"%'";
				String[] selectionArgs = null;
				Cursor cur = managedQuery(uri, projection, selection, selectionArgs, sortOrder);
				return cur;
			}
        
        });
        m_lvContacts.setAdapter(m_slvAdapter); 
        m_lvContacts.setItemsCanFocus(false);
        m_lvContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);		 
	}

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy called.");
        if (m_curContacts!=null) m_curContacts.close();
        m_etSearch.removeTextChangedListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		Cursor cursor = (Cursor) m_lvContacts.getItemAtPosition(position);
		String szDisplayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//		String szNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		String szId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
		int nId = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
		
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