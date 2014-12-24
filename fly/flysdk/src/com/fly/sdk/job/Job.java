package com.fly.sdk.job;

import java.util.concurrent.Callable;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fly.sdk.ErrorMsg;

public abstract class Job implements Callable<Object> {	
	
	protected HttpClient  httpClent ;
	protected ErrorMsg  errorMsg ;
	public Job(){
		httpClent = new DefaultHttpClient();
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
	
}
