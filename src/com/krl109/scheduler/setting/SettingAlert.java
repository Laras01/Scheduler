package com.krl109.scheduler.setting;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.krl109.scheduler.R;

public class SettingAlert extends Activity implements AnimationListener 
{
    View menu;
    View app;
    boolean menuOut = false;
    AnimParams animParams = new AnimParams();
    String[] listMenu = {"Alert", "Notification"};
    String[] list = {"item1", "item2", "item3", "item4", "item5"};

    class ClickListener implements OnClickListener 
    {
        @Override
        public void onClick(View v) {
            System.out.println("onClick " + new Date());
            SettingAlert me = SettingAlert.this;
//            Context context = me;
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
        
        /*Button alert = (Button) menu.findViewById(R.id.btn_alert);
        alert.setOnClickListener(new Button.OnClickListener() 
		{	
			public void onClick(View v) 
			{
				Intent set_alert = new Intent(SlideAnimationThenCallLayout.this, SettingAlert.class);
            	startActivity(set_alert);
			}
		});
        Button notif = (Button) menu.findViewById(R.id.btn_notif);*/
        
        ViewUtils.printView("menu", menu);
        ViewUtils.printView("app", app);
        //ViewUtils.printView("set_alert", set_alert);
        
        Button snooze = (Button) app.findViewById(R.id.btn_alert_snooze);
        snooze.setOnClickListener(new Button.OnClickListener() 
        {	
        	public void onClick(View v) 
            {
            	Toast.makeText(getApplicationContext(), "alert_snooze",Toast.LENGTH_SHORT).show();
            }
         });
        
        Button sound = (Button) app.findViewById(R.id.btn_alert_sound);
        sound.setOnClickListener(new Button.OnClickListener() 
        {	
        	public void onClick(View v) 
            {
            	Toast.makeText(getApplicationContext(), "alert_sound",Toast.LENGTH_SHORT).show();
            }
         });            

        app.findViewById(R.id.BtnSlide).setOnClickListener(new ClickListener());
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
}
