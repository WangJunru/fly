package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class CellNumberUpdate extends Job {

	private String batchCode ;
	private String cellNumber ;
	private String checkCode ;
	
	private long  userID ;
	
	public CellNumberUpdate(long userID ,String cell,String batchCode ,String checkCode)
	{
		this.userID = userID ;
		this.cellNumber = cell ;
		this.batchCode  = batchCode ;
		this.checkCode  = checkCode ;
	}
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		
		HttpPut put = new HttpPut(String.format(SdkConfig.API_URL_UPDATE_CELL, userID));
		List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
		param.add(new BasicNameValuePair("user[cell]", cellNumber));
		param.add(new BasicNameValuePair("user[batch_code]", batchCode));
		param.add(new BasicNameValuePair("code", checkCode));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param,SdkConfig.HTTP_ENCODING);
			put.setEntity(entity);
			
			HttpResponse response = httpClent.execute(put);
			int statusCode = response.getStatusLine().getStatusCode() ;				
		    String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
		    if(statusCode / 100 == 2)
			{
			  return  FlyJSonUtil.parseCheckCodeJsonString(jsonStr,true);
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
		return null;
	}

}
