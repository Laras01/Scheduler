package com.krl109.scheduler.setting;

import com.krl109.scheduler.R;
import com.krl109.scheduler.setting.SlideMenuInterface.OnSlideMenuItemClickListener;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Setting extends Activity implements OnSlideMenuItemClickListener {

	private SlideMenu slidemenu;
	//private final static int MYITEMID = 42;
	private RadioGroup snooze;
	String snooze_choose;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.setting_alert);

		/*
		 * There are two ways to add the slide menu: 
		 * From code or to inflate it from XML (then you have to declare it in the activities layout XML)
		 */
		// this is from code. no XML declaration necessary, but you won't get state restored after rotation.
//		slidemenu = new SlideMenu(this, R.menu.slide, this, 333);
		// this inflates the menu from XML. open/closed state will be restored after rotation, but you'll have to call init.
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.menu_setting, this, 333);

		// this can set the menu to initially shown instead of hidden
//		slidemenu.setAsShown(); 

		// set optional header image
		//slidemenu.setHeaderImage(getResources().getDrawable(R.drawable.ic_launcher));

		AlertMenu();
	}


	@Override
	public void onSlideMenuItemClick(int itemId) {

		switch(itemId) {
		case R.id.alert:
			AlertMenu();	
			break;
		case R.id.notification:
			NotificationMenu();
			break;
		}

	}
	
	public void AlertMenu()
	{
		setContentView(R.layout.setting_alert);
		
		Button setting = (Button) findViewById(R.id.BtnSlide);
		setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slidemenu.show();
			}
		});
		
		Button alert_snooze = (Button) findViewById(R.id.btn_alert_snooze);
		alert_snooze.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//String txt = "Alert Snooze";
				//showToast(txt);
				SnoozeDialog();
			}
		});
		
		Button alert_sound = (Button) findViewById(R.id.btn_alert_sound);
		alert_sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//akses sound picker
				String txt = "Alert Sound";
				showToast(txt);
			}
		});
	}
	
	public void SnoozeDialog()
	{
		final Dialog d = new Dialog(this);
		d.setTitle("Snooze Duration");
		d.setContentView(R.layout.alert_snooze);
		d.show();
		
		Button btn_done, btn_cancel;
				
		snooze = (RadioGroup) d.findViewById(R.id.snooze_duration);
				
				
		btn_done = (Button) d.findViewById(R.id.btn_ok);
		btn_done.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{				
				//mengambil snooze duration dari radio button 
				int alert_snooze = snooze.getCheckedRadioButtonId();
				if(alert_snooze == R.id.min5)
				{
					snooze_choose = "5 minute";
				}
				else if(alert_snooze == R.id.min10)
				{
					snooze_choose = "10 minute";
				}
				else if(alert_snooze == R.id.min15)
				{
					snooze_choose = "15 minute";
				}
				else if(alert_snooze == R.id.min20)
				{
					snooze_choose = "20 minute";
				}
				else if(alert_snooze == R.id.min25)
				{
					snooze_choose = "25 minute";
				}
				else if(alert_snooze == R.id.min30)
				{
					snooze_choose = "30 minute";
				};
				
				d.cancel();
				showToast(snooze_choose);
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
	}
	
	public void NotificationMenu()
	{
		setContentView(R.layout.setting_notification);
		
		Button setting = (Button) findViewById(R.id.BtnSlide);
		setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slidemenu.show();
			}
		});
		
		Button notif_sound = (Button) findViewById(R.id.btn_notif_sound);
		notif_sound.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String txt = "Sound Picker";
				showToast(txt);
			}
		});
	}
	
	public void showToast(String text)
	{
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}



/*import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class Setting extends Activity implements AnimationListener 
{
    View menu;
    View app;
    View set_alert;
    boolean menuOut = false;
    AnimParams animParams = new AnimParams();
    String[] listMenu = {"Alert", "Notification"};
    String[] list = {"item1", "item2", "item3", "item4", "item5"};

    class ClickListener implements OnClickListener 
    {
        @Override
        public void onClick(View v) {
            System.out.println("onClick " + new Date());
            Setting me = Setting.this;
            Context context = me;
            Animation anim;

            int w = app.getMeasuredWidth();
            int h = app.getMeasuredHeight();
            int left = (int) (app.getMeasuredWidth() * 0.8);

            if (!menuOut) {
                // anim = AnimationUtils.loadAnimation(context, R.anim.push_right_out_80);
                anim = new TranslateAnimation(0, left, 0, 0);
                menu.setVisibility(View.VISIBLE);
                animParams.init(left, 0, left + w, h);
            } else {
                // anim = AnimationUtils.loadAnimation(context, R.anim.push_left_in_80);
                anim = new TranslateAnimation(0, -left, 0, 0);
                animParams.init(0, 0, w, h);
            }

            anim.setDuration(500);
            anim.setAnimationListener(me);
            //Tell the animation to stay as it ended (we are going to set the app.layout first than remove this property)
            anim.setFillAfter(true);


            // Only use fillEnabled and fillAfter if we don't call layout ourselves.
            // We need to do the layout ourselves and not use fillEnabled and fillAfter because when the anim is finished
            // although the View appears to have moved, it is actually just a drawing effect and the View hasn't moved.
            // Therefore clicking on the screen where the button appears does not work, but clicking where the View *was* does
            // work.
            // anim.setFillEnabled(true);
            // anim.setFillAfter(true);

            app.startAnimation(anim);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_alert);
        
        menu = findViewById(R.id.menu);
        app = findViewById(R.id.app);

        //set_alert = findViewById(R.id.set_alert);
        
        ViewUtils.printView("menu", menu);
        ViewUtils.printView("app", app);
        //ViewUtils.printView("set_alert", set_alert);
        
        ListView list_menu = (ListView) menu.findViewById(R.id.list_menu);
        list_menu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listMenu));
        list_menu.setOnItemClickListener(new OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {                
                if(parent.getItemAtPosition(position).equals("Alert"))
                {
                	Toast.makeText(getApplicationContext(), "alert",Toast.LENGTH_SHORT).show();
                	//Intent set_alert = new Intent(Setting.this, SettingAlert.class);
                	//startActivity(set_alert);
                	
                }
            }
        });
                

        //ListView listView = (ListView) app.findViewById(R.id.list);
        //ViewUtils.initListView(this, listView, "item", 30, android.R.layout.simple_list_item_1);

        app.findViewById(R.id.BtnSlide).setOnClickListener(new ClickListener());
        
        app.findViewById(R.id.btn_alert_snooze).setOnClickListener(new ClickListener()
        {
        	public void onClick(View v) 
			{
        		Toast.makeText(getApplicationContext(), "alert_snooze",Toast.LENGTH_SHORT).show();
			}
        });
    }

    void layoutApp(boolean menuOut) {
        System.out.println("layout [" + animParams.left + "," + animParams.top + "," + animParams.right + ","
                + animParams.bottom + "]");
        app.layout(animParams.left, animParams.top, animParams.right, animParams.bottom);
        //Now that we've set the app.layout property we can clear the animation, flicker avoided :)
        app.clearAnimation();

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        System.out.println("onAnimationEnd");
        ViewUtils.printView("menu", menu);
        ViewUtils.printView("app", app);
        //ViewUtils.printView("set_alert", set_alert);
        menuOut = !menuOut;
        if (!menuOut) {
            menu.setVisibility(View.INVISIBLE);
        }
        layoutApp(menuOut);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        System.out.println("onAnimationRepeat");
    }

    @Override
    public void onAnimationStart(Animation animation) {
        System.out.println("onAnimationStart");
    }

    static class AnimParams {
        int left, right, top, bottom;

        void init(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }
}*/

