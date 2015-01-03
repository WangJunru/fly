package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class CommentCreate extends Job {

	private int flyProId;
	private boolean isSchool;

	private String userEmail;
	private String userToken;
	private String comment;

	public CommentCreate(int flyProId, String userEmail,
			String userToken, String comment, boolean isSchool) {
		this.flyProId = flyProId ;
		this.userEmail = userEmail ;
		this.userToken = userToken ;
		this.comment = comment ;
		this.isSchool = isSchool ;
	}

	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("user_email", userEmail));
		params.add(new BasicNameValuePair("user_token",userToken));
		params.add(new BasicNameValuePair(isSchool?"school_id":"product_id", flyProId+""));
		params.add(new BasicNameValuePair("comment[comment]", comment));
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,SdkConfig.HTTP_ENCODING);
			HttpPost post = new HttpPost(SdkConfig.API_URL_FLY_PRO_CREATE_COMMENT);
			post.setEntity(entity);
			
			HttpResponse response = httpClent.execute(post);
		    int statusCode = response.getStatusLine().getStatusCode() ;
			
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseCommentJsonString(jsonStr);
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
