package com.fly.sdk.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.http.MyHttpGet;
import com.fly.sdk.util.Log;

public class QuestionDownload extends Job {
   
	public static final String   QUESTION_LAG_ENG = "en" ;
	public static final String   QUESTION_LAG_ZH = "zh-CN" ;
	public static final String   QUESTION_GROUP_PENDING = "pending";
	public static final String   QUESTION_GROUP_WRONG = "wrong";
	
	public static final String   PLANE_TYPE_ROTOR_PERSONAL = "rotor_craft_personal";
	public static final String   PLANE_TYPE_ROTOR_COMMERCIAL = "rotor_craft_commercial";
	public static final String   PLANE_TYPE_FIX_WING_PERSONAL = "fixed_wing_jet_personal";
	public static final String   PLANE_TYPE_FIX_WING_COMMERCIAL = "fixed_wing_jet_commercial";
	
//	private  int   category_id = -1 ;
	private String mUserEmail ;
	
	private String mUserToken ;
	
	/**
	 * en,zh-CN
	 */
	private String mLang  = QUESTION_LAG_ENG;
	/**
	 * pending,wrong
	 */
	private String mGroup = "";
	
	private String mPlaneType = "" ;
	
	public QuestionDownload(String userEmail,String userToken,String lan , String group , String planeType)
	{
		this.mUserEmail = userEmail ;
		this.mUserToken = userToken ;
		this.mLang = lan ;
		this.mGroup = group ;
		this.mPlaneType = planeType ;
	}

	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
		MyHttpGet  httpRequest = new MyHttpGet(SdkConfig.API_URI_QUESTION_DOWNLOAD);
		try {
		     
		    List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>();
		    paraList.add(new BasicNameValuePair("user_email", mUserEmail));
		    paraList.add(new BasicNameValuePair("user_token", mUserToken));
		    paraList.add(new BasicNameValuePair("question[lang]", mLang));
		    paraList.add(new BasicNameValuePair("question[group]", mGroup));
		    paraList.add(new BasicNameValuePair("question[plane_type]", mPlaneType));
		    
		    HttpEntity entity = new UrlEncodedFormEntity(paraList,SdkConfig.HTTP_ENCODING);
//		    String url =SdkConfig.API_URI_QUESTION_DOWNLOAD+"?user_email=nihao@126.com&user_token=ZLtRxhTV8fYxNsQf4ytb&question[lang]=zh-CN&question[group]=" ;
		    httpRequest.setEntity(entity);
		    
			HttpResponse httpResponse = httpClent.execute(httpRequest);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			String jsonStr = HttpUtils.readHttpBody(httpResponse.getEntity().getContent());
			
			if(statusCode /100 == 2)
			{
			    return FlyJSonUtil.parseQuestionJsonString(jsonStr);
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
			Log.log(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.log(e.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
