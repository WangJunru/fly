package com.fly.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.fly.app.FlyApplication;
import com.fly.sdk.threading.FlyTaskManager;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class BaseActivity extends FragmentActivity implements OnClickListener {

	protected Handler uiHandler = new Handler()
	{
	   @Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
		    handleUIHandlerMessage(msg);
			super.handleMessage(msg);	
		}
	};

	protected FlyTaskManager taskManager ;
	
	private void animateClickView(final View v)
	{
	   float factor = 0.9f;  
	   ViewPropertyAnimator.animate(v).scaleX(factor).scaleY(factor).alpha(0.3f).setListener(new AnimatorListenerAdapter() {  
        @Override  
        public void onAnimationEnd(Animator animation) {  
            ViewHelper.setScaleX(v, 1);  
            ViewHelper.setScaleY(v, 1);  
            ViewHelper.setAlpha(v, 1);  
         
            super.onAnimationEnd(animation);  
        }  
      });  
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		taskManager = FlyApplication.getFlyTaskManager();
	}

	protected void  handleUIHandlerMessage(Message msg){}

	
    protected void clickView (View v) {};
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		animateClickView(v);
//		switch(v.getId())
//		{
//		  case R.id.back_img:
//			  this.finish();
//			break;
//		  case R.id.share_img:
//		  {
//			  
//		  }
//			break;
//		}
		clickView(v);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(uiHandler != null)
		{
			uiHandler = null ;
		}
	}

}
