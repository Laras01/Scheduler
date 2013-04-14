package com.krl109.scheduler.history;

import com.krl109.scheduler.R;
import com.krl109.scheduler.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryDetail extends Activity 
{
	TextView sent_at, recipient, status, content;
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_detail);
    }
}