package com.fly.sdk.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fly.sdk.util.TextUtils;

public class Tools {
	
	public static boolean isMobileNO(String mobiles) {
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		if (TextUtils.isEmpty(email)) {
			return false;
		}
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
	
	public static void main(String[] args)
	{
		System.out.println(isMobileNO("12512170373"));
	}
}
