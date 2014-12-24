package com.fly.sdk.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.fly.sdk.SdkConfig;
import com.fly.sdk.util.Log;

public class HttpUtils {
     public static String readHttpBody(InputStream in)
     {   	 
		try {
			 BufferedReader bufReader = new BufferedReader(
						new InputStreamReader(in,
								SdkConfig.HTTP_ENCODING));	
    		String jsonStr = "" ;
			String jsonTmp = "" ;
			while ( (jsonTmp = bufReader.readLine()) != null)
			{
				jsonStr += jsonTmp ;
			}
			bufReader.close();
		    return jsonStr ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null ;
     }
}
