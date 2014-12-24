package com.fly.sdk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.http.MyHttpGet;
import com.fly.sdk.util.Log;

public class GetCommentLists extends Job {

	private int    pageId ;
	private int    flyProId ;
	private boolean  isSchool ;
	public GetCommentLists(int pageId , int flyProId ,boolean isSchool)
	{
		this.pageId    =   pageId ;
		this.flyProId  = flyProId ;
		this.isSchool =  isSchool ;
	}
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		List<BasicNameValuePair> params  = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("page", pageId+""));
		params.add(new BasicNameValuePair(isSchool?"school_id":"product_id", flyProId+""));
		
		try
		{
		    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,SdkConfig.HTTP_ENCODING);
		    MyHttpGet  get = new MyHttpGet(SdkConfig.API_URL_FLY_PRO_COMMENTS_LIST);
		    get.setEntity(entity);
		   
	        HttpResponse httpResponse =    httpClent.execute(get);
	        int statusCode = httpResponse.getStatusLine().getStatusCode() ;
			
			String jsonStr = HttpUtils.readHttpBody(httpResponse.getEntity().getContent());
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
