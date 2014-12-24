package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.http.MyHttpGet;
import com.fly.sdk.util.Log;

public class GetNoticeDetail extends Job {
	
	/**
	 * get notice with id 获取通知详情
	 */
    private long  noticeId ;
    
    public GetNoticeDetail(long noticeId)
    {
    	this.noticeId = noticeId ;
    }
    
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		try {

			MyHttpGet get = new MyHttpGet(String.format(SdkConfig.API_URL_GET_NOTICE_DETAIL, noticeId));
			HttpResponse response = httpClent.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseNoticeJsonString(jsonStr);
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
