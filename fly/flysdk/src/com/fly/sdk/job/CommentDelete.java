package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.http.MyHttpDelete;
import com.fly.sdk.util.Log;

public class CommentDelete extends Job {

	private int commentId;
	
	private String userEmail;
	private String userToken;
	

	public CommentDelete(int commentId, String userEmail,
			String userToken) {
		this.commentId = commentId;
		this.userEmail = userEmail ;
		this.userToken = userToken ;
	}

	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("user_email", userEmail));
		params.add(new BasicNameValuePair("user_token",userToken));
		
		try {
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,SdkConfig.HTTP_ENCODING);
			MyHttpDelete post = new MyHttpDelete(String.format(SdkConfig.API_URL_FLY_PRO_DELETE_COMMENT,commentId));
			post.setEntity(entity);
			
			HttpResponse response = httpClent.execute(post);
		    int statusCode = response.getStatusLine().getStatusCode() ;
			
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  "delete_ok";
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
			Log.log(e.toString());
		}catch (IOException e) {
			// TODO: handle exception
			Log.log(e.toString());
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("network io error");
    		errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.log(e.toString());
		}
		return null;
	}

}
