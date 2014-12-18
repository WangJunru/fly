package com.fly.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.fly.FlyApplication;
import com.fly.QuizActivity;
import com.fly.R;
import com.fly.sdk.User;

public class QuizFragment3 extends BaseFramgment  implements OnGlobalLayoutListener{
	
	private TextView  userNameTv ;
	private ImageView userPicIv ;
	private TextView  userMsgCountTv ;
	private View   userPicContView ;
	private int  userPictureHeight ,userPictureWidth ;
	private char mJx ;
	private char mJzlx ;
	
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
		attachedActivity = (FragmentActivity) activity ;
	}
	
	public void  onResume()
	{
		super.onResume();
		User user = FlyApplication.getLoginUser();
    	if(user != null)
    	{
    		updateUserView(user, FlyApplication.getLoginedUserPic());
    	}
	}
	
	public  void  updateUserView(User user,Drawable userPic)
	  {
	    	
	    	if(user != null && userNameTv != null)
	    	{
	    	   userNameTv.setText(user.getName());
	    	}
	    	if(userPicIv != null && userPic != null)
	    	{
	    	  userPicIv.setImageDrawable(userPic);
	    	}
	   }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View  rootView = inflater.inflate(R.layout.mine_quiz_3page, container, false);
		initView(rootView);
		return rootView ;
	}
	
	private void initView(View rootView)
	{
		View backView  = rootView.findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		
		View sharedView  = rootView.findViewById(R.id.share_img);
		sharedView.setOnClickListener(this);
		
		TextView  titleView = (TextView)rootView.findViewById(R.id.title);
		
		Bundle  arg = getArguments() ;
		mJzlx = arg.getChar("jzlx");
	    mJx = arg.getChar("jx");
	    
		if('x' == mJx)
		{
			titleView.setText(getString(R.string.mnks_title, getString(R.string.zsj_ks)));
		}else if('g' == mJx)
		{
			titleView.setText(getString(R.string.mnks_title, getString(R.string.gdy_ks)));
		}
		
		View mnks = rootView.findViewById(R.id.moni_quiz_bt);
		mnks.setOnClickListener(this);
		
		View xkwz = rootView.findViewById(R.id.weizuo_quiz_bt);
		xkwz.setOnClickListener(this);
		
		userNameTv      = (TextView)rootView.findViewById(R.id.user_name_tx);
		userPicIv       = (ImageView)rootView.findViewById(R.id.user_picture_round);
		userMsgCountTv  = (TextView)rootView.findViewById(R.id.user_msg_count);
		userPicContView = rootView.findViewById(R.id.user_pic_con);
		userPicContView.getViewTreeObserver().addOnGlobalLayoutListener(this);
		
	}
	
	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		userPictureHeight = userPicIv.getHeight() - 3 ;
		userPictureWidth  = userPicIv.getWidth() - 3 ;
		
		LayoutParams layPara = (LayoutParams) userMsgCountTv.getLayoutParams() ;
		layPara.leftMargin = userPicIv.getRight() - userMsgCountTv.getWidth() / 2 ;
		layPara.topMargin  = userPicIv.getTop() + userMsgCountTv.getHeight() / 2 ;
		userMsgCountTv.setLayoutParams(layPara);
		
		userPicContView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}
	

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
	  	  case R.id.back_img:
	  	  {
	  		 FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction(); 
		     QuizFragment2 f2 = new QuizFragment2();
			 Bundle dt = new Bundle();
			 dt.putChar("jx", mJx);
			 f2.setArguments(dt);	 
			 tras.replace(R.id.main_content, f2);
//			 tras.replace(R.id.main_content, f2, "ks2");
//			 tras.addToBackStack(null);
			 tras.commit();
	  	  }
	  		  break;
		  case R.id.moni_quiz_bt:
		  {
			  Intent intent = new Intent(attachedActivity,QuizActivity.class);
			  intent.putExtra("ksnr", 'm');	  
			  intent.putExtra("jx", mJx);	  
			  intent.putExtra("jzlx", mJzlx);
              startActivity(intent);
		  }break;
		  case R.id.weizuo_quiz_bt:
		  {
			  Intent intent = new Intent(attachedActivity,QuizActivity.class);
			  intent.putExtra("ksnr", 'p');	  
			  intent.putExtra("jx", mJx);	  
			  intent.putExtra("jzlx", mJzlx);
		      startActivity(intent);
		  }break;
		}
	}

	
}
