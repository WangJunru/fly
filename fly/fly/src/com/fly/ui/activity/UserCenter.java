package com.fly.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.Score;
import com.fly.sdk.User;
import com.fly.sdk.job.Job;
import com.fly.sdk.job.UserUpdate;
import com.fly.ui.view.RoundImageView;
import com.fly.ui.view.UserInfoItemView;

public class UserCenter extends BaseActivity {
	
	private TextView    userNameTv ;
//	private TextView    userMsgCount ;
	private RoundImageView   userPicIv ;
	
	private UserInfoItemView  userNameView ;
	private UserInfoItemView  userEmailView ;
	
	private UserInfoItemView  userSexView ;
	private UserInfoItemView  userAreaView ;
	private UserInfoItemView  userTelView ;
	
	private UserInfoItemView  userBestCjView ;
	
	private Score score ;
	private Job flyTask ;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.my_center_layout);
    	initData();
    	initView();
    }
       
    private void initData()
    {
    	Intent intent =  getIntent();
    	this.score = (Score)intent.getSerializableExtra("score");
    	
    }
    private void initView()
    {
    	View backView = findViewById(R.id.back_img);
    	backView.setOnClickListener(this);
		backView.setVisibility(View.VISIBLE);
		
		View  shareView = findViewById(R.id.share_img);
		shareView.setVisibility(View.INVISIBLE);
		
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.user_zhuye);
		
		userNameTv = (TextView)findViewById(R.id.user_name_tx) ;
		
		userPicIv  = (RoundImageView)findViewById(R.id.user_picture_round);
		userPicIv.setOnClickListener(this);
		
//		userMsgCount = (TextView)findViewById(R.id.user_msg_count);
		
		userNameView  = (UserInfoItemView)findViewById(R.id.user_name_info);
		userNameView.setOnClickListener(this);
		
		userEmailView =  (UserInfoItemView)findViewById(R.id.user_email_info);
		userEmailView.setOnClickListener(this);
		
		userSexView  =  (UserInfoItemView)findViewById(R.id.user_sex_info);
		userSexView.setOnClickListener(this);
		
		userAreaView = (UserInfoItemView)findViewById(R.id.user_erea_info);
		userAreaView.setOnClickListener(this);
		
		userTelView  = (UserInfoItemView)findViewById(R.id.user_tel_info);
		userTelView.setOnClickListener(this);
		
		userBestCjView = (UserInfoItemView)findViewById(R.id.user_best_cj);
		userBestCjView.setOnClickListener(this);
    }
    public void clickView(View v)
    {
    	switch(v.getId())
    	{
    	  case R.id.back_img:
    		  finish();
    		break;
    	}
    }
    private void  updateView(User user)
    {
    	if(user == null)
    	{
    		return ;
    	}
		Options opt = new Options();
		opt.inJustDecodeBounds = true ;
		
		BitmapFactory.decodeResource(getResources(),R.drawable.default_user_pic, opt);
    	userNameTv.setText(user.getName());
    	userPicIv.setNetIamgeUrl(user.getUserPictureUri(),
    			R.drawable.user_pic_bg, opt.outWidth,opt.outHeight);
    	
    	userNameView.setValueText(user.getName());
    	userEmailView.setValueText(user.getEmail());
    	if(user.getGender().equals(UserUpdate.GENDER_MALE))
    	{
    		userSexView.setValueText(R.string.male);
    	}else if(user.getGender().equals(UserUpdate.GENDER_FEMALE))
    	{
    		userSexView.setValueText(R.string.female);
    	}
    	userAreaView.setValueText(user.getAddress());
    	userTelView.setValueText(user.getCellNumber());
    	userBestCjView.setValueText(getString(R.string.score_str, user.getBestScore()));
    	
    }
    
    
}
