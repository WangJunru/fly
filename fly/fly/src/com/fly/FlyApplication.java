package com.fly;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.fly.sdk.User;
import com.fly.sdk.threading.FlyTaskManager;

public class FlyApplication extends Application {

	
	private static FlyTaskManager   flyTaskManager ;
	private static User      loginedUser ;
	private static Drawable  loginedUserPic ;
	
	public static void setLogindUserPic(Drawable pic)
	{
	   loginedUserPic = pic ;
	}
	
	public static Drawable getLoginedUserPic()
	{
		return loginedUserPic ;
	}
	
	public static void setLoginedUser(User user)
	{
		loginedUser = user;
	}
	
	public static User getLoginUser()
	{
		return loginedUser;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if(flyTaskManager == null)
		{
			flyTaskManager = FlyTaskManager.getInstance();
		}
	}
	
	public static FlyTaskManager  getFlyTaskManager()
	{
		return flyTaskManager ;
	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
	
   
}
