package com.fly.sdk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.Score;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;

public class UserScoreListGet extends Job {
	
	private String email ;
	private String userToken ;
	
	public UserScoreListGet(String userToken ,String email)
	{
		this.email = email ;
		this.userToken = userToken ;
	}
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		
		HttpPost  uriRequest = new HttpPost(SdkConfig.API_URI_GET_USER_SCORE_LIST);
		
		List<BasicNameValuePair>  values  = new ArrayList<BasicNameValuePair>();
		values.add(new BasicNameValuePair("user_email", email));
		values.add(new BasicNameValuePair("user_token", userToken));
		
	  try{
		HttpEntity entity = new UrlEncodedFormEntity(values,SdkConfig.HTTP_ENCODING);
		uriRequest.setEntity(entity);
		
		HttpResponse response = httpClent.execute(uriRequest);
		int statusCode = response.getStatusLine().getStatusCode();
		
		String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
		
		if( (statusCode/100) == 2)
		{
			 ArrayList<Score> scores = FlyJSonUtil.parseScoreJsonString(jsonStr);
    		 for(Score sc : scores)
    		 {
    			sc.setEmail(email);
    			sc.setUserToken(userToken);
    		 }
    		return scores ;
		}else if(statusCode /100 == 4)
		{
			 errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
		}else if(statusCode/100 == 5)
    	{
    		errorMsg = new ErrorMsg();
    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
    	}
	 }catch (IOException e) {
		// TODO: handle exception
		 errorMsg = new ErrorMsg();
	     errorMsg.addErrorMsg("Network io error");
	     errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
	 } catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
	 }
		return null;
	}
    
}
