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

public class UserRegist extends Job {

	private String userName ;
	private String emailAddress ;
	private String userPassword ;
	
	public UserRegist(String name ,String email , String password)
	{
	    this.userName = name ;
	    this.emailAddress = email ;
	    this.userPassword = password ;
	}
	
	@Override
	public User execute() {
		// TODO Auto-generated method stub		
		HttpPost  uriRequest = new HttpPost(SdkConfig.API_URI_CREATE_USER);
		List<BasicNameValuePair>  values  = new ArrayList<BasicNameValuePair>();
		values.add(new BasicNameValuePair("user[name]", userName));
		values.add(new BasicNameValuePair("user[email]", emailAddress));
		values.add(new BasicNameValuePair("user[password]", userPassword));
		
		
		try {
			HttpEntity entity = new UrlEncodedFormEntity(values,SdkConfig.HTTP_ENCODING);
			uriRequest.setEntity(entity);
			
			HttpResponse response = httpClent.execute(uriRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			
			if( (statusCode/100) == 2)
			{
				return FlyJSonUtil.parseUserJsonString(jsonStr);
			}else if(statusCode /100 == 4)
			{
				errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
				errorMsg.setErrorCode(ErrorMsg.ERROR_USER_NAME_EXEIST);
			}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.log(e.getMessage());
		}catch (IOException e) {
			// TODO: handle exception
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("Network io error");
			errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
