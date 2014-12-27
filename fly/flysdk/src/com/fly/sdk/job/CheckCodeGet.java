package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

public class CheckCodeGet extends Job {

	private String cell ;
	private long    userId ;
	public CheckCodeGet(String cell,long userId)
	{
		this.cell = cell ;
		this.userId = userId ;
	}
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		HttpPost post = new HttpPost(String.format(SdkConfig.API_URL_SEND_CHECK_CODE, userId));
		List<BasicNameValuePair> para = new ArrayList<BasicNameValuePair>();
		para.add(new BasicNameValuePair("cell", cell));
		
		try {
			UrlEncodedFormEntity entuty = new UrlEncodedFormEntity(para, SdkConfig.HTTP_ENCODING);
			post.setEntity(entuty);
			HttpResponse response =  httpClent.execute(post);
		    int statusCode = response.getStatusLine().getStatusCode() ;
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseCheckCodeJsonString(jsonStr,false);
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
			e.printStackTrace();
			Log.log(e.toString());
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("network io error");
    		errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null ;
	}

}
