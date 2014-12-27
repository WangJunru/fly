package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.User;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;
import com.fly.sdk.util.TextUtils;

public class UserInfo  extends Job{
	
	private String userName ;
	private String passWord ;
	private String emailAdress ;
	
	private boolean isUserNameLogin = false;
	
	public UserInfo(String name,String email,String passWord)
	{
	   this.userName = name ;
	   this.passWord = passWord ;
	   this.emailAdress = email ;
	   
	   if(TextUtils.isEmpty(emailAdress) || !emailAdress.contains("@"))
	   {
		   isUserNameLogin = true ;
	   }else
	   {
		   isUserNameLogin = false ;
	   }
	}
	
	@Override
	protected User execute() {
		// TODO Auto-generated method stub	
		HttpPost  uriRequest = new HttpPost(SdkConfig.API_URI_USER_TOKEN);
		List<BasicNameValuePair>  values  = new ArrayList<BasicNameValuePair>();
		if(isUserNameLogin)
		{
			values.add(new BasicNameValuePair("login", userName));
		}else
		{
			values.add(new BasicNameValuePair("login", emailAdress));
		}
		values.add(new BasicNameValuePair("password", passWord));
		
		try
		{
		   HttpEntity entity = new UrlEncodedFormEntity(values,SdkConfig.HTTP_ENCODING);
		   uriRequest.setEntity(entity);
		   
		   HttpResponse  response = httpClent.execute(uriRequest);
		   int statusCode = response.getStatusLine().getStatusCode();
		   
		   String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
		   if((statusCode/100) == 2)
		   {
				return FlyJSonUtil.parseUserJsonString(jsonStr);
			}else if((statusCode/100) == 4)
			{
				errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
				errorMsg.setErrorCode(ErrorMsg.ERROR_USER_NAME_PASSWD_FAIL);
			}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}	
		   
		}catch(UnsupportedEncodingException e)
		{
		     Log.log(e.toString());
		}catch (IOException e) {
			// TODO: handle exception
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("Network io error");
			errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null; 
	}
	
}
