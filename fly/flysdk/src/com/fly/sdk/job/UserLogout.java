package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.MyHttpDelete;
import com.fly.sdk.util.Log;

public class UserLogout extends Job {

	private String  userToken ;
	private String  email ;
	public  UserLogout(String userToken,String userEmail)
	{
		this.userToken = userToken ;
		this.email = userEmail ;
	}
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		MyHttpDelete deleteRequest = new MyHttpDelete(SdkConfig.API_URI_USER_TOKEN+userToken);
		
		List<BasicNameValuePair>  values  = new ArrayList<BasicNameValuePair>();
		values.add(new BasicNameValuePair("user_email", email));
		values.add(new BasicNameValuePair("user_token", userToken));
		
		try {
			HttpEntity entity = new UrlEncodedFormEntity(values,SdkConfig.HTTP_ENCODING);
			deleteRequest.setEntity(entity);
			
			HttpResponse response = httpClent.execute(deleteRequest);
			int statusCode = response.getStatusLine().getStatusCode() ;
			return new Integer(statusCode);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
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
