package com.fly.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.User;
import com.fly.ui.activity.LoginActivity;

public class QuizFragment2 extends BaseFramgment {
	private char jxChar ;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		attachedActivity = (FragmentActivity)activity ;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =  inflater.inflate(R.layout.mine_quiz_2page, container, false);
		initView(view);
		return view ;
	}
	
	private  void  initView(View rootView)
	{
		View backView = rootView.findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		
		View shareView = rootView.findViewById(R.id.share_img);
		shareView.setOnClickListener(this);
		
		TextView  title = (TextView)rootView.findViewById(R.id.title);
		title.setText(R.string.quiz_title);
		
		View jxView = rootView.findViewById(R.id.xy_plane_con);
		jxView.setOnClickListener(this);
		
		ImageView jxIv = (ImageView)rootView.findViewById(R.id.jx_image);
	    TextView  jxTv = (TextView)rootView.findViewById(R.id.jx_name);
		Bundle b  = getArguments() ;
	    jxChar = b.getChar("jx");
	    
		if('x' == jxChar)
		{
			jxIv.setImageResource(R.drawable.plane_xy);
			jxTv.setText(R.string.xy_plane);
		}else if('g' == jxChar)
		{
			jxIv.setImageResource(R.drawable.plane_gdy);
			jxTv.setText(R.string.gdy_plane);
		}
		
		View szKs = rootView.findViewById(R.id.sz_quiz_bt);
		szKs.setOnClickListener(this);
		
		View szks = rootView.findViewById(R.id.bz_quiz_bt);
		szks.setOnClickListener(this);
	}

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		  case R.id.back_img:
		  {
		     FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction();
			 
			 QuizFragment1 f1 = new QuizFragment1();
			 tras.replace(R.id.main_content, f1);
//				 tras.addToBackStack(null);
			 tras.commit();
		  }break;
		  case R.id.xy_plane_con:
			  break;
		  case R.id.sz_quiz_bt:
		  {
			     User user = FlyApplication.getLoginUser();
			     
			     if(user != null)
			     {
					 FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction(); 
					 QuizFragment3 f2 = new QuizFragment3();
					 Bundle dt = new Bundle();
					 dt.putChar("jzlx", 's');
					 dt.putChar("jx", jxChar);
					 f2.setArguments(dt);	 
					 tras.replace(R.id.main_content, f2);
//					 tras.replace(R.id.main_content, f2, "ks1");
//					 tras.addToBackStack(null);
					 tras.commit();
			     }else
			     {
			    	 Intent logIntent = new Intent(attachedActivity,LoginActivity.class);
			    	 startActivity(logIntent);
			     }
		  }
			  break;
		  case R.id.bz_quiz_bt:
		  {
			     User user = FlyApplication.getLoginUser();     
			     if(user != null)
			     {
				     FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction(); 
				     QuizFragment3 f2 = new QuizFragment3();
					 Bundle dt = new Bundle();
					 dt.putChar("jzlx", 'b');
					 dt.putChar("jx", jxChar);
					 f2.setArguments(dt);	 
					 tras.replace(R.id.main_content, f2);
//					 tras.replace(R.id.main_content, f2, "ks2");
//					 tras.addToBackStack(null);
					 tras.commit();
			     }else
			     {
			    	 Intent logIntent = new Intent(attachedActivity,LoginActivity.class);
			    	 startActivity(logIntent);
			     }
		  }
			  break;
		}
	}
	
	

}
