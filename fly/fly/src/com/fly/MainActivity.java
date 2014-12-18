package com.fly;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fly.fragments.MainFragment;
import com.fly.fragments.MainFragment.FourRoundClickListener;
import com.fly.fragments.ProductListFragment;
import com.fly.fragments.QuizFragment1;
import com.fly.fragments.SchoolListFragment;
import com.fly.fragments.UserCenterFragment;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.Product;
import com.fly.sdk.ProductBanner;
import com.fly.sdk.School;
import com.fly.sdk.SchoolPanner;
import com.fly.sdk.User;
import com.fly.sdk.job.GetProductList;
import com.fly.sdk.job.GetSchoolList;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;

public class MainActivity extends BaseActivity implements FourRoundClickListener{

	private final int  REQUEST_USER_LOGIN =  1 ;
	private int backKeyPressedCount = 0 ;

	private FragmentManager  fragmentManager ;
	
	private ImageView firstPages;
	private ImageView quiz;
	private ImageView minePages;
	private ImageView kxPages;
	private ImageView fxGarage;

	private ArrayList<Product>  products = new ArrayList<Product>();
	private ArrayList<ProductBanner>  productPanners = new ArrayList<ProductBanner>();
	
	private ArrayList<School>   schools  = new ArrayList<School>();
	private ArrayList<SchoolPanner> schoolPanners  =  new ArrayList<SchoolPanner>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentManager = getSupportFragmentManager();
		initView();
		initBottomView();
		loadNetworkData();
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
	    case R.id.gdy_plane_con:
		case R.id.xy_plane_con:
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
		case R.id.plane_school_con:
		case R.id.hx:
		{
			firstPages.setImageResource(R.drawable.sy_0);
			quiz.setImageResource(R.drawable.ks_0);
			minePages.setImageResource(R.drawable.wd_0);
			kxPages.setImageResource(R.drawable.hx_1);
			fxGarage.setImageResource(R.drawable.fx_0);
		}break;
		case R.id.plane_jxk_con:
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
		case R.id.hx:
		{
			FragmentTransaction   ftr = fragmentManager.beginTransaction();
			SchoolListFragment  schoolInfo = new SchoolListFragment(this.schools);
			ftr.replace(R.id.main_content, schoolInfo);
//				ftr.replace(R.id.main_content, ks, "ks");
//				ftr.addToBackStack(null);
			ftr.commit();
	    	backKeyPressedCount = 0 ;
		}break;
		case R.id.plane_jxk_con:
		case R.id.fx:
		{
			FragmentTransaction   ftr = fragmentManager.beginTransaction();
			ProductListFragment  productInfo = new ProductListFragment(this.products);
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
    
    
    private void loadNetworkData()
    {
         GetProductList   getProductList  =  new GetProductList();
         GetSchoolList    getSchoolList   =  new GetSchoolList();
         if(taskManager != null)
         {
        	 ResultCallback resultCallback = new ProAndSchoolDataCallResult();
        	 taskManager.commitJob(getProductList,resultCallback);
        	 taskManager.commitJob(getSchoolList, resultCallback);
         }
    }
    
    
    public void handleUIHandlerMessage(Message msg)
    {
     	 Object obj = msg.obj ;
     	
    	 switch(msg.what)
    	 {
    	   case 0:// shcool list
    	   {
    		   this.schoolPanners.clear();
    		   this.schools.clear();
    		   if(obj != null)
    		   {
    		      ArrayList<School> schools = (ArrayList<School>)obj;
    		      for(School shObj : schools)
    		      {
    		    	  if(shObj instanceof SchoolPanner)
    		    	  {
    		    		  this.schoolPanners.add((SchoolPanner)shObj);
    		    	  }else
    		    	  {
    		    		  this.schools.add(shObj);
    		    	  }
    		      }
    		    
    		   }
    	   }   
    		 break;
    	   case 1:// product list
    	   {
    		   this.productPanners.clear();
    		   this.products.clear();
    		   if(obj != null)
    		   {
    		      ArrayList<Product> products = (ArrayList<Product>)obj;
    		      for(Product pro : products)
    		      {
    		    	  if(pro instanceof ProductBanner)
    		    	  {
    		    		  this.productPanners.add((ProductBanner)pro);
    		    	  }else
    		    	  {
    		    		  this.products.add(pro);
    		    	  }
    		      }
    		   }
    	   }
    		  break;
    	   case 2:// error info
    	   {
    		   ErrorMsg errorMsg = (ErrorMsg)msg.obj;
    		   if(errorMsg != null)
    		   {
    			   switch(errorMsg.getErrorCode())
    			   {
    			     case ErrorMsg.ERROR_NETWORK_IO_ERROR:
    			     {
    			    	 Toast.makeText(this, R.string.network_io_error, Toast.LENGTH_SHORT).show();
    			     } break;
    			     case ErrorMsg.ERROR_SERVER_ERROR_HAPPENED:
    			     {
    			    	 Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show();
    			     }break;   
    			   }
    		   }
    	   }
    		  break;
    	 }
    }
    
    private class ProAndSchoolDataCallResult implements ResultCallback
    {
    	@Override
    	public void notifyResult(Object result) {
    		// TODO Auto-generated method stub
    		if(result != null)
    		{
    			if(result instanceof List<?>)
    			{
    				
    			  Object obj =  null ;
    			  int size = ((List)result).size() ;
    			  if(size > 0)
    			  {
    				  obj = ((List)result).get(0);
    			  }else
    			  {
    				  return ;
    			  } 
    			  if(obj instanceof Product)
    			  {
    				  uiHandler.obtainMessage(1, result).sendToTarget();
    			  }else if(obj instanceof School)
    			  {
    				  uiHandler.obtainMessage(0, result).sendToTarget();
    			  }
    			}else if(result instanceof ErrorMsg)
    			{
    				 uiHandler.obtainMessage(2, result).sendToTarget();
    			}
    		}
    	}
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
