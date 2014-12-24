package com.fly.util.debug;

import android.util.Log;

public class Debug {
	public static boolean isDebug =  true ;
    public final static class log
    {
    	
    	 public static void i(String tag ,String msg)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg);
    	 }
    	 public static void i(String tag ,String msg,Throwable e)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg,e);
    	 }
    	 
    	 public static void d(String tag ,String msg)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg);
    	 }
    	 public static void d(String tag ,String msg,Throwable e)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg,e);
    	 }
    	 
    	 public static void e(String tag ,String msg)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg);
    	 }
    	 public static void e(String tag ,String msg,Throwable e)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg,e);
    	 }
    	 
    	 public static void v(String tag ,String msg)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg);
    	 }
    	 public static void v(String tag ,String msg,Throwable e)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg,e);
    	 }
    	 
    	 public static void w(String tag ,String msg)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg);
    	 }
    	 public static void w(String tag ,String msg,Throwable e)
    	 {
    		 if(isDebug)
    			 Log.i(tag, msg,e);
    	 }
    	 
    }
}
