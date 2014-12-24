package com.fly.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.fragments.MainFragment;
import com.fly.fragments.MainFragment.FourRoundClickListener;
import com.fly.fragments.ProductListFragment;
import com.fly.fragments.QuizFragment1;
import com.fly.fragments.SchoolListFragment;
import com.fly.fragments.UserCenterFragment;
import com.fly.sdk.User;

public class MainActivity extends BaseActivity implements FourRoundClickListener{

	private final int  REQUEST_USER_LOGIN =  1 ;
	private int backKeyPressedCount = 0 ;

	private FragmentManager  fragmentManager ;
	
	private ImageView firstPages;
	private ImageView quiz;
	private ImageView minePages;
	private ImageView kxPages;
	private ImageView fxGarage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentManager = getSupportFragmentManager();
		initView();
		initBottomView();
	}	
	
	private void initBottomView() {
		firstPages = (ImageView) findViewById(R.id.sy);
		firstPages.setOnClickListener(this);

		quiz = (ImageView) findViewById(R.id.ks);
		quiz.setOnClickListener(this);		

		minePages = (ImageView) findViewById(R.id.wd);
		minePages.setOnClickListener(this);		

		kxPages = (ImageView) findViewById(R.id.hx);
		kxPages.setOnClickListener(this);	

		fxGarage = (ImageView) findViewById(R.id.fx);
		fxGarage.setOnClickListener(this);
	}
	
	private void setBottomViewSrc(View v)
	{
		switch (v.getId()) {
		
		case R.id.sy:
		{
			firstPages.setImageResource(R.drawable.sy_1);
			quiz.setImageResource(R.drawable.ks_0);
			minePages.setImageResource(R.drawable.wd_0);
			kxPages.setImageResource(R.drawable.hx_0);
			fxGarage.setImageResource(R.drawable.fx_0);
		}break;
//	    case R.id.gdy_plane_con:
//		case R.id.xy_plane_con:
		case R.id.ks: 
		{
			firstPages.setImageResource(R.drawable.sy_0);
			quiz.setImageResource(R.drawable.ks_1);
			minePages.setImageResource(R.drawable.wd_0);
			kxPages.setImageResource(R.drawable.hx_0);
			fxGarage.setImageResource(R.drawable.fx_0);
		}break;
		case R.id.wd:
		{
			firstPages.setImageResource(R.drawable.sy_0);
			quiz.setImageResource(R.drawable.ks_0);
			minePages.setImageResource(R.drawable.wd_1);
			kxPages.setImageResource(R.drawable.hx_0);
			fxGarage.setImageResource(R.drawable.fx_0);
		}
			break;
//		case R.id.plane_school_con:
		case R.id.hx:
		{
			firstPages.setImageResource(R.drawable.sy_0);
			quiz.setImageResource(R.drawable.ks_0);
			minePages.setImageResource(R.drawable.wd_0);
			kxPages.setImageResource(R.drawable.hx_1);
			fxGarage.setImageResource(R.drawable.fx_0);
		}break;
//		case R.id.plane_jxk_con:
		case R.id.fx:
		{
			firstPages.setImageResource(R.drawable.sy_0);
			quiz.setImageResource(R.drawable.ks_0);
			minePages.setImageResource(R.drawable.wd_0);
			kxPages.setImageResource(R.drawable.hx_0);
			fxGarage.setImageResource(R.drawable.fx_1);
		}break;
			
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
//		FlyApplication.getFlyTaskManager().shutDownTask();
	
	}
	private void initView()
	{
		FragmentTransaction   ftr = fragmentManager.beginTransaction();
		MainFragment  mainPage = new MainFragment();
		mainPage.setFourRoundListener(this);
		ftr.replace(R.id.main_content, mainPage, "main");
		ftr.commit();
	}
	
	public void clickView(View v)
	{	
		setBottomViewSrc(v);
		switch (v.getId()) {
		
		case R.id.sy:
		{		
//			Fragment f = fragmentManager.findFragmentByTag("main");
//			if(f != null)
//			{
//				FragmentTransaction fr = fragmentManager.beginTransaction() ;
////				fr.show(f);
//				fr.replace(R.id.main_content, f, "main");
//			    fr.commit();
//			}
//			else
			{
				FragmentTransaction   ftr = fragmentManager.beginTransaction();
				MainFragment  mainPage = new MainFragment();
				ftr.replace(R.id.main_content, mainPage, "main");
				mainPage.setFourRoundListener(this);
				ftr.commit();
				backKeyPressedCount = 0 ;
			}
		}
			break;
		case R.id.ks: {
//			Fragment f = fragmentManager.findFragmentByTag("ks");
//			if(f != null)
//			{
//				FragmentTransaction fr = fragmentManager.beginTransaction() ;
////				fr.show(f);
//				fr.replace(R.id.main_content, f, "ks");
////			    fr.addToBackStack(null);
//			    fr.commit();
//			}
//			else
			{
				FragmentTransaction   ftr = fragmentManager.beginTransaction();
				QuizFragment1  ks = new QuizFragment1();
				ftr.replace(R.id.main_content, ks);
//				ftr.replace(R.id.main_content, ks, "ks");
//				ftr.addToBackStack(null);
				backKeyPressedCount = 0 ;
				ftr.commit();
			}
		}
			break;
		case R.id.wd:
		{
			User user = FlyApplication.getLoginUser();
			if(user == null)
		    {
		      Intent logIntent = new Intent(this,LoginActivity.class);
		      startActivityForResult(logIntent, REQUEST_USER_LOGIN);
		    }
	    	FragmentTransaction   ftr = fragmentManager.beginTransaction();
			UserCenterFragment  userInfo = new UserCenterFragment();
			ftr.replace(R.id.main_content, userInfo);
//				ftr.replace(R.id.main_content, ks, "ks");
//				ftr.addToBackStack(null);
			ftr.commit();
	    	backKeyPressedCount = 0 ;
  	
		}break;
		case R.id.plane_school_con:
		{
			Intent intent = new Intent(this,FlyProductActivity.class);
			intent.putExtra("product_type", 's');
			startActivity(intent);
		}break;
		case R.id.hx:
		{
			FragmentTransaction   ftr = fragmentManager.beginTransaction();
			SchoolListFragment  schoolInfo = new SchoolListFragment(null,null);
			ftr.replace(R.id.main_content, schoolInfo);
//				ftr.replace(R.id.main_content, ks, "ks");
//				ftr.addToBackStack(null);
			ftr.commit();
	    	backKeyPressedCount = 0 ;
		}break;
		case R.id.plane_jxk_con:
		{
			Intent intent = new Intent(this,FlyProductActivity.class);
			intent.putExtra("product_type", 'p');
			startActivity(intent);
		}break;
		case R.id.fx:
		{
			FragmentTransaction   ftr = fragmentManager.beginTransaction();
			ProductListFragment  productInfo = new ProductListFragment(null,null);
			ftr.replace(R.id.main_content, productInfo);
//				ftr.replace(R.id.main_content, ks, "ks");
//				ftr.addToBackStack(null);
			ftr.commit();
	    	backKeyPressedCount = 0 ;
		}break;
		}
	}

    @Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
	    switch(id)
	    {
	      case 0:
	    	return new LoginDialog(this);
	    }
	    return null;
    }
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(fragmentManager != null)
		{
			fragmentManager.popBackStack();
		    int   entryCount = fragmentManager.getBackStackEntryCount();
		    if(entryCount == 0)
		    {
		    	backKeyPressedCount ++ ;
		    	if(backKeyPressedCount == 1)
		    	{
		    	   Toast.makeText(this, R.string.pressed_again_to_quit, Toast.LENGTH_SHORT).show();
		    	}else if(backKeyPressedCount ==2)
		    	{
		    		this.finish();
		    	}	
		    }else
		    {
		    	backKeyPressedCount = 0 ;
		    }
		}
	}
    
	
    
}
