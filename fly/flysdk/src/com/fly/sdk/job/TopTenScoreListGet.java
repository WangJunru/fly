package com.fly.sdk.job;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class TopTenScoreListGet  extends Job {

	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		HttpGet versionGet =  new HttpGet(SdkConfig.API_URI_TOPTEN_SCORE_LIST);
		try {
			HttpResponse httpResponse = httpClent.execute(versionGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode() ;
			
			String jsonStr = HttpUtils.readHttpBody(httpResponse.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseScoreJsonString(jsonStr);
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
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null ;
	
	}

}
