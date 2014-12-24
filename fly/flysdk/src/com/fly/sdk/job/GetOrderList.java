package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.http.MyHttpGet;
import com.fly.sdk.util.Log;

public class GetOrderList extends Job {
	
	private String userEmail;
	private String userToken;
	private int  pageId ;

	public GetOrderList(int pageId ,String userEmail, String userToken) {
		this.pageId = pageId ;
		this.userEmail = userEmail;
		this.userToken = userToken;
	}

	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("user_email", userEmail));
		params.add(new BasicNameValuePair("user_token", userToken));
		params.add(new BasicNameValuePair("page", pageId+""));
	
		try {
			MyHttpGet get = new MyHttpGet(SdkConfig.API_URL_USER_ORDERS);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,SdkConfig.HTTP_ENCODING);
			get.setEntity(entity);
			
			HttpResponse response = httpClent.execute(get);
		    int statusCode = response.getStatusLine().getStatusCode() ;
			
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseOrderJsonString(jsonStr);
			}else if(statusCode / 100 == 4)
			{
				errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
			}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("network io error");
    		errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}
		return null;
	}

}
