package com.fly.sdk.job;

import java.util.concurrent.Callable;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.fly.sdk.ErrorMsg;

public abstract class Job implements Callable<Object> {	
	
	protected HttpClient  httpClent ;
	protected ErrorMsg  errorMsg ;
    private boolean isComplete ;
	
	public Job(){
		httpClent = new DefaultHttpClient();
//		请求超时
		httpClent.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); 
//		读取超时
		httpClent.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
	}
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return execute();
	}
	protected abstract Object execute();
	
	public ErrorMsg  getError()
	{
		return  errorMsg ;
	}
	
	public boolean isComplete() {
		return isComplete;
	}
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
}
