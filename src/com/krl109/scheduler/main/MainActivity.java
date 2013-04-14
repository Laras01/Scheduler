package com.krl109.scheduler.main;

/*import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button new_schedule, show_list, history, template, setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		new_schedule = (Button) findViewById(R.id.btn_new_schedule);
		new_schedule.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent create_schedule = new Intent(MainActivity.this, NewSchedule.class);
		    	startActivity(create_schedule);
			}
		});
		
		show_list = (Button) findViewById(R.id.btn_list);
		show_list.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent list = new Intent(MainActivity.this, TabLayout.class);
		    	startActivity(list);
			}
		});
		
		history = (Button) findViewById(R.id.btn_history);
		history.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent hist = new Intent(MainActivity.this, History.class);
		    	startActivity(hist);
			}
		});
		
		template = (Button) findViewById(R.id.btn_template);
		template.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent tmp = new Intent(MainActivity.this, Template.class);
		    	startActivity(tmp);
			}
		});
		
		setting = (Button) findViewById(R.id.btn_setting);
		setting.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent set = new Intent(MainActivity.this, Setting.class);
		    	startActivity(set);
			}
		});
	}

	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_history, menu);
		//return true;
	//}

}*/

import com.krl109.scheduler.R;
import com.krl109.scheduler.carouselcontrols.Carousel;
import com.krl109.scheduler.carouselcontrols.CarouselAdapter;
import com.krl109.scheduler.carouselcontrols.CarouselItem;
import com.krl109.scheduler.carouselcontrols.CarouselAdapter.OnItemClickListener;
import com.krl109.scheduler.carouselcontrols.CarouselAdapter.OnItemSelectedListener;
import com.krl109.scheduler.history.History;
import com.krl109.scheduler.newSchedule.NewSchedule;
import com.krl109.scheduler.setting.Setting;
import com.krl109.scheduler.tabLayout.TabLayout;
import com.krl109.scheduler.template.Template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity 
{
	Carousel carousel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	//Log.d("1", "com.android.main 21");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        carousel = (Carousel)findViewById(R.id.carousel);
        carousel.setOnItemClickListener(new OnItemClickListener()
        {
			public void onItemClick(CarouselAdapter<?> parent, View view,int position, long id) 
			{	
				//Toast.makeText(MainActivity.this,String.format("%s has been clicked",((CarouselItem)parent.getChildAt(position)).getName()), Toast.LENGTH_SHORT).show();
				String menu = ((CarouselItem)parent.getChildAt(position)).getName();
				if(menu.equals("New Schedule"))
				{
					Intent create_schedule = new Intent(MainActivity.this, NewSchedule.class);
			    	startActivity(create_schedule);
				}
				else if(menu.equals("List Schedule"))
				{
					Intent list = new Intent(MainActivity.this, TabLayout.class);
			    	startActivity(list);
				}
				else if(menu.equals("Template"))
				{
					Intent tmp = new Intent(MainActivity.this, Template.class);
			    	startActivity(tmp);
				}
				else if(menu.equals("History"))
				{
					Intent hist = new Intent(MainActivity.this, History.class);
			    	startActivity(hist);
				}
				else if(menu.equals("Setting"))
				{
					Intent set = new Intent(MainActivity.this, Setting.class);
			    	startActivity(set);
				}
			}
        });

        /*carousel.setOnItemSelectedListener(new OnItemSelectedListener()
        {
			public void onItemSelected(CarouselAdapter<?> parent, View view,int position, long id) 
			{
				
		        final TextView txt = (TextView) findViewById(R.id.selected_item);
		        
				switch(position){
				case 0:
					txt.setText("List Schedule");
					break;
				case 1:
					txt.setText("About Us");
					break;
				case 2:
					txt.setText("Setting");
					break;
				case 3:
					txt.setText("History");
					break;
				case 4:
					txt.setText("Template");
					break;
				case 5:
					txt.setText("New Schedule");
					break;
				}
				
			}

			public void onNothingSelected(CarouselAdapter<?> parent) {
			}
        	
        }
        );*/
        
    }
    
}
