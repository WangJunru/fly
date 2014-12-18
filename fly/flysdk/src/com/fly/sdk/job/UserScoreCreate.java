package com.fly.sdk.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.Question;
import com.fly.sdk.Score;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.http.HttpUtils;
import com.fly.sdk.util.Log;

public class UserScoreCreate extends Job {

	private String userToken ;
	private String email ;
	private long userId ;
	private double  score ;
	private ArrayList<Question>  questions ;
	
	public UserScoreCreate(
			String usertoken,
			String userEmail,
			long userID,
			double score)
	{
		this.userId = userID ;
		this.userToken = usertoken ;
		this.email = userEmail ;
		this.score = score ;	
	}
	
	public UserScoreCreate(
			String usertoken,
			String userEmail,
			long userID,
			double score,
			ArrayList<Question>  qts)
	{
		this.userId = userID ;
		this.userToken = usertoken ;
		this.email = userEmail ;
		this.score = score ;	
		this.questions = qts ;
	}
	
	@Override
	protected Object execute() {
		// TODO Auto-generated method stub
			
		
//		curl -X POST -d "user_email=wangkun029@gmail.com&
//		user_token=KW-v6_WxSsgjnyNL3nyw&score["user_id"]=8&
//		score["number"]=98" http://api.mfeiji.com/v1/scores
//		List<BasicNameValuePair> values = new ArrayList<BasicNameValuePair>();	
//		values.add(new BasicNameValuePair("user_email", email));
//		values.add(new BasicNameValuePair("user_token", userToken));
//		values.add(new BasicNameValuePair("score[user_id]", userId+""));
//		values.add(new BasicNameValuePair("score[number]", score+""));
		
		JSONStringer  jb = new JSONStringer();
	    jb.object().key("user_email").value(email).key("user_token").value(userToken).key("score")
		.object().key("user_id").value(userId).key("number").value(score);
		
		if(this.questions != null && !this.questions.isEmpty())
		{	
			jb.key("score_lines");
			jb.array();
			for(Question  qt:this.questions)
			{
			  jb.object().key("question_id").value(qt.getQuestionId()).key("correct").value(qt.isThisQtsAnwseredRight()).endObject();
			}
			jb.endArray();
//			values.add(new BasicNameValuePair("score[score_lines]", jb.toString()));
//			 Log.log(jb.toString());
		}
		jb.endObject();
		jb.endObject();
		
		try{
			StringEntity entity = new StringEntity(jb.toString(), SdkConfig.HTTP_ENCODING);
//			HttpEntity  entity = new UrlEncodedFormEntity(values, SdkConfig.HTTP_ENCODING);
	        HttpPost httpUriRequest = new HttpPost(SdkConfig.API_URI_USER_SCORE_CREATE);
//	        httpUriRequest.addHeader("Accept", "application/json");
	        httpUriRequest.addHeader("Content-type", "application/json");
//	        Content-type: application/json
	        httpUriRequest.setEntity(entity);

	    	HttpResponse  httpResponse = httpClent.execute(httpUriRequest);	
	    	int statusCode = httpResponse.getStatusLine().getStatusCode();
	    	
	    	String jsonStr = HttpUtils.readHttpBody(httpResponse.getEntity().getContent());   	
	    	if(statusCode/100 == 2)
	    	{
	    		ArrayList<Score> scores = FlyJSonUtil.parseScoreJsonString(jsonStr);
	    		for(Score sc : scores)
	    		{
	    			sc.setEmail(email);
	    			sc.setUserToken(userToken);
	    		}
	    		return scores ;
	    	}else if( statusCode/100 == 4)
	    	{
	    		errorMsg = FlyJSonUtil.parseErrorJsonString(jsonStr);
	    	}else if(statusCode/100 == 5)
	    	{
	    		errorMsg = new ErrorMsg();
	    		errorMsg.setErrorCode(ErrorMsg.ERROR_SERVER_ERROR_HAPPENED);
	    	}
	    		
		}catch(UnsupportedEncodingException e)
		{
			Log.log(e.toString());
		}catch(IOException e)
		{
			errorMsg = new ErrorMsg();
			errorMsg.addErrorMsg("Network io error");
			errorMsg.setErrorCode(ErrorMsg.ERROR_NETWORK_IO_ERROR);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
