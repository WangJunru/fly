package com.fly.sdk.job;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.User;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class UserUpdate extends Job {
	
	public  enum UpdateOption
	{
		Name(0x01),Email(0x10),Password(0x100),Picture(0x1000),AllExcludePictrue(0x10000),Unknow(-1);
		private int option = -1 ;
		UpdateOption(int opt){ this.option = opt ;}
		public int getOption()
		{
		   return  option ;
		}
	}
	
	private String userToken ;
	
	private String userName ;
	private String emailAddress ;
	private String passWord ;
	private String pictureUri;
	
	private long userId ;
	
	private UpdateOption updateUpt =  UpdateOption.Unknow;
	
	/**
	 * 
	 * @param userToken
	 * @param name
	 * @param email
	 * @param password
	 * @param pictureUri
	 * @param option
	 */
	public UserUpdate(
			long   userId ,
			String userToken, 
			String name ,
			String email,
			String password,
			String pictureUri,
			UpdateOption option
	  )
	{
	   this.userId = userId ;
	   this.userToken = userToken ;
	   this.userName = name ;
	   this.emailAddress = email ;
	   this.passWord = password ;
	   this.pictureUri = pictureUri;
	   this.updateUpt = option ;
	}
	
	@Override
	public User execute() {
		// TODO Auto-generated method stub
	  try {
	    MultipartEntity entity =  new MultipartEntity();
	    entity.addPart("user_token", new StringBody(userToken));	
		entity.addPart("user_email", new StringBody(emailAddress));	
	    switch(updateUpt)
	    {
	      case Name:
	    	  entity.addPart("user[name]", new StringBody(userName, Charset.forName(SdkConfig.HTTP_ENCODING)));
	    	  break;
	      case Password:
	    	  entity.addPart("user[password]", new StringBody(passWord, Charset.forName(SdkConfig.HTTP_ENCODING)));
	    	  break;
	      case Email:
	    	  // email 地址不可更改
	    	  break;
	      case Picture:
	    	  entity.addPart("user[avatar]", new FileBody(new File(pictureUri)));
	    	  break;
	      case AllExcludePictrue:
	    	  entity.addPart("user[name]", new StringBody(userName, Charset.forName(SdkConfig.HTTP_ENCODING)));	
	    	  entity.addPart("user[password]", new StringBody(passWord, Charset.forName(SdkConfig.HTTP_ENCODING)));
	    	  break;
	      case Unknow:	
	    }
	    
	        HttpPut uriRequest = new HttpPut(SdkConfig.API_URI_UPDATE_USER+userId);
	        uriRequest.setEntity(entity);
	        
			HttpResponse response = httpClent.execute(uriRequest);
		
			int statusCode = response.getStatusLine().getStatusCode();
			
			String jsonStr = HttpUtils.readHttpBody(response.getEntity().getContent());
			
			if((statusCode/100) == 2)
			{
				return FlyJSonUtil.parseUserJsonString(jsonStr);
			}else if(statusCode /100 == 4)
			{
				errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
			}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.log(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.log(e.getMessage());
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("Network io error");
			errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
