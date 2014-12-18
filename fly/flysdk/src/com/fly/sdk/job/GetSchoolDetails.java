package com.fly.sdk.job;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class GetSchoolDetails extends Job{

	private int  schoolId;
	public GetSchoolDetails(int id)
	{
		this.schoolId = id ;
	}
	
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		HttpGet versionGet =  new HttpGet(SdkConfig.API_URL_SCHOOL_DETAILS+this.schoolId);
		try {
			HttpResponse httpResponse = httpClent.execute(versionGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode() ;
			
			String jsonStr = HttpUtils.readHttpBody(httpResponse.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseSchoolJsonString(jsonStr);
			}else if(statusCode / 100 == 4)
			{
				errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
			}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}		
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
		return null ;
	}

}
