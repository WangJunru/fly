package com.fly.sdk.util;

public class Log {
	
   public static boolean DEBUG  = true ;
   public static void  log(String info)  
   {
	  if(DEBUG)
		  System.out.println(info);
   }
}
