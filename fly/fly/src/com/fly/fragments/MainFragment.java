package com.fly.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.User;
import com.fly.ui.activity.LoginActivity;
import com.fly.ui.activity.QuizMainActivity;
import com.fly.util.Tools;

public class MainFragment extends BaseFramgment implements OnClickListener,OnGlobalLayoutListener{
	
	private View   mXyPlaneView ;
	private View   mGdyPlaneView ;
	private View   mThSchoolView ;
	private View   mJxkuView ;
	
	private TextView  userNameTv ;
	private ImageView userPicIv ;
	private TextView  userMsgCountTv ;
	private View   userPicContView ;
	private int  userPictureHeight ,userPictureWidth ;
	
	private FourRoundClickListener listener ;
    
	
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
	    View rootView = inflater.inflate(R.layout.first_pages, container, false);
	    initView(rootView);
		return rootView ;
	}
	
	private void  initView(View rootView)
	{
		mXyPlaneView    = rootView.findViewById(R.id.xy_plane_con);
		mGdyPlaneView   = rootView.findViewById(R.id.gdy_plane_con);
		mThSchoolView   = rootView.findViewById(R.id.plane_school_con);
		mJxkuView       = rootView.findViewById(R.id.plane_jxk_con);
		 
		mXyPlaneView.setOnClickListener(this);
		mGdyPlaneView.setOnClickListener(this);
		mThSchoolView.setOnClickListener(this);
		mJxkuView.setOnClickListener(this);
	
		
		userNameTv      = (TextView)rootView.findViewById(R.id.user_name_tx);
		userPicIv       = (ImageView)rootView.findViewById(R.id.user_picture_round);
		userPicIv.setOnClickListener(this);
		
		userMsgCountTv  = (TextView)rootView.findViewById(R.id.user_msg_count);
		userPicContView = rootView.findViewById(R.id.user_pic_con);
		userPicContView.getViewTreeObserver().addOnGlobalLayoutListener(this);
		
	}

	public void  setFourRoundListener(FourRoundClickListener listener)
	{
		this.listener = listener ;
	}
	
	
    public void onResume()
    {
    	super.onResume();
    	User user = FlyApplication.getLoginUser();
    	if(user != null)
    	{
    		updateUserView(user, FlyApplication.getLoginedUserPic());
    	}else
    	{
    	    userPicIv.setImageDrawable(Tools.getDefaultUserPic(attachedActivity));
    	}
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
	
	
	
	public void  clickView(View v)
	{
		 switch(v.getId())
		 {
		   case R.id.xy_plane_con:
		   {
			    
//			     FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction();			 
//				 QuizFragment2 f2 = new QuizFragment2();
//				 Bundle dt = new Bundle();
//				 dt.putChar("jx", 'x');
//				 f2.setArguments(dt);	 
//				 tras.replace(R.id.main_content, f2);
////				 tras.addToBackStack(null);
//				 tras.commit();
			   Intent intent = new Intent(attachedActivity,QuizMainActivity.class);
			   intent.putExtra("jx", 'x');
			   startActivity(intent);
		   }
			  break;
		   case R.id.gdy_plane_con:
		   {
//			     FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction(); 
//			     QuizFragment2 f2 = new QuizFragment2();
//				 Bundle dt = new Bundle();
//				 dt.putChar("jx", 'g');
//				 f2.setArguments(dt);	 
//				 tras.replace(R.id.main_content, f2);
////				 tras.addToBackStack(null);
//				 tras.commit();
			   Intent intent = new Intent(attachedActivity,QuizMainActivity.class);
			   intent.putExtra("jx", 'g');
			   startActivity(intent);
		  }
			  break;
		   case R.id.plane_school_con:
		   {
			   if(listener != null)
			     {
			    	 listener.clickView(v);
			     }
		   }
			   break;
		   case R.id.plane_jxk_con:
		   {
			    if(listener != null)
			     {
			    	 listener.clickView(v);
			     }
		   } break;
		   case R.id.user_picture_round:
		   {
			   checkLogin();
		   }
			   break;
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
	
	private void checkLogin()
	{
		
		if(FlyApplication.getLoginUser() == null)
		{
			Intent intent = new Intent(getActivity() ,LoginActivity.class);
			startActivityForResult(intent, 1);
		}else
		{
			showUserPic(true);
		}
	}
	private void  showUserPic(boolean show)
	{
		userNameTv.setVisibility(show?View.VISIBLE:View.INVISIBLE);
		userPicIv.setVisibility(show?View.VISIBLE:View.INVISIBLE);
		userMsgCountTv.setVisibility(show?View.INVISIBLE:View.INVISIBLE);
	}
	
	public interface  FourRoundClickListener
	{
		  public void  clickView(View v);
	}
	
	
}
