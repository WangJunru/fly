package com.fly.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.User;
import com.fly.ui.activity.NoticeActivity;
import com.fly.ui.activity.OrderActivity;
import com.fly.ui.activity.Version;
import com.fly.ui.view.UserInfoItemView;

public class UserCenterFragment extends BaseFramgment {
	private TextView    userNameTv ;
	private TextView    userMsgCount;
	private ImageView   userPicIv ;
	
	private UserInfoItemView  userNameView ;
	private UserInfoItemView  userEmailView ;
	
	private UserInfoItemView  userSexView ;
	private UserInfoItemView  userAreaView ;
	private UserInfoItemView  userTelView ;
	
	private UserInfoItemView  userBestCjView ;
	
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View view  = inflater.inflate(R.layout.my_center_layout, container, false);
		 initView(view);
		 return  view ;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		User user = FlyApplication.getLoginUser();
		if(user != null)
		{
			updateView(user,FlyApplication.getLoginedUserPic());
		}
	}
	private void  initView(View rootView)
	{
		View backView = rootView.findViewById(R.id.back_img);
		backView.setVisibility(View.INVISIBLE);
		
		View  shareView = rootView.findViewById(R.id.share_img);
		shareView.setVisibility(View.INVISIBLE);
		
		TextView title = (TextView)rootView.findViewById(R.id.title);
		title.setText(R.string.user_my_center);
		
		userNameTv = (TextView)rootView.findViewById(R.id.user_name_tx) ;
		
		userPicIv  = (ImageView)rootView.findViewById(R.id.user_picture_round);
		userPicIv.setOnClickListener(this);
		
		userMsgCount = (TextView)rootView.findViewById(R.id.user_msg_count);
		
		userNameView  = (UserInfoItemView)rootView.findViewById(R.id.user_name_info);
		userNameView.setOnClickListener(this);
		
		userEmailView =  (UserInfoItemView)rootView.findViewById(R.id.user_email_info);
		userEmailView.setOnClickListener(this);
		
		userSexView  =  (UserInfoItemView)rootView.findViewById(R.id.user_sex_info);
		userSexView.setOnClickListener(this);
		
		userAreaView = (UserInfoItemView)rootView.findViewById(R.id.user_erea_info);
		userAreaView.setOnClickListener(this);
		
		userTelView  = (UserInfoItemView)rootView.findViewById(R.id.user_tel_info);
		userTelView.setOnClickListener(this);
		
		userBestCjView = (UserInfoItemView)rootView.findViewById(R.id.user_best_cj);
		userBestCjView.setOnClickListener(this);
		
		View  myDingDan = rootView.findViewById(R.id.user_jd_info);
		myDingDan.setOnClickListener(this);
		
		View  securitySet = rootView.findViewById(R.id.user_safe_info);
		securitySet.setOnClickListener(this);
		
		View  noticeView = rootView.findViewById(R.id.user_notice_info);
		noticeView.setOnClickListener(this);
		
		View  aboutUs = rootView.findViewById(R.id.user_about_info);
		aboutUs.setOnClickListener(this);
		
		View  loginOut = rootView.findViewById(R.id.user_login_out);
		loginOut.setOnClickListener(this);
	}
	
	private void updateView(User user,Drawable userPic)
	{
		if(user != null )
		{
			userNameTv.setText(user.getName());
			userNameView.setValueText(user.getName());
			userEmailView.setValueText(user.getEmail());
			
//			if(user.getGender().equals(UserUpdate.GENDER_MALE))
//	    	{
//	    		userSexView.setValueText(R.string.male);
//	    	}else if(user.getGender().equals(UserUpdate.GENDER_FEMALE))
//	    	{
//	    		userSexView.setValueText(R.string.female);
//	    	}
			userSexView.setValueText(user.getGender());
	    	userAreaView.setValueText(user.getAddress());
	    	userTelView.setValueText(user.getCellNumber());
	    	userBestCjView.setValueText(getString(R.string.score_str_phb, user.getBestScore()));
		}
		
		if(userPic != null)
		{
			userPicIv.setImageDrawable(userPic);
		}
	}
	
	public void  clickView(View v)
	{
		switch(v.getId())
		{
		  case R.id.user_jd_info:// 订单信息
		  {
			  Intent intent = new Intent(attachedActivity,OrderActivity.class);
			  startActivity(intent);
		  }
			break;
		  case R.id.user_safe_info://重新设置密码
		  {
			  
		  }
		
			break;
		  case R.id.user_notice_info:// 用户通知
		  {
			  Intent intent = new Intent(attachedActivity,NoticeActivity.class);
			  startActivity(intent);
		  }
			break;
		  case R.id.user_about_info:// 版本信息
		  {
			  Intent intent = new Intent(attachedActivity,Version.class);
			  startActivity(intent);
		  }
			 break;
		  case R.id.user_login_out:// 用户注销
		  {
			  
			  
		  }break;
		}
	}
	
}
