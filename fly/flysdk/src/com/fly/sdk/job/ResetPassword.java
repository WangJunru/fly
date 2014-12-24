package com.fly.sdk.job;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class ResetPassword extends Job {

	private String email ;
	public ResetPassword(String email)
	{
		this.email = email;
	}
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		HttpPost  post = new HttpPost(SdkConfig.API_URI_PASSWD_RESET);
		List<BasicNameValuePair> paras = new ArrayList<BasicNameValuePair>();
		paras.add(new BasicNameValuePair("user_email",email));
		
		try
		{
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paras, SdkConfig.HTTP_ENCODING);
			post.setEntity(entity);
			
			HttpResponse response = httpClent.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			String  jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			if( (statusCode/100) == 2)
			{
				return "reset_ok";
			}else if(statusCode /100 == 4)
			{
				 errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
			}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}
			
		}catch (ClientProtocolException e) {
			// TODO: handle exception
			Log.log(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("Network io error");
			errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		} catch(Exception e)
		{
			Log.log(e.toString());
		}
		
		return null;
	}

}
