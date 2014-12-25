package com.fly.view.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataUtils {
	public static void saveLoginedUserInfo(Context context ,String userName ,String passwd)
	{
		if(context == null)
		{
			return  ;
		}
		if(userName == null  ||passwd == null)
		{
			return ;
		}
		SharedPreferences shareedPref = context.getSharedPreferences("user.bin", Context.MODE_PRIVATE);
		Editor edit = shareedPref.edit();
		edit.putString("user_name", userName);
		edit.putString("user_passwd", passwd);
		edit.commit();
	}

}
