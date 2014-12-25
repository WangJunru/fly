package com.fly.sdk;

import java.util.ArrayList;

public class ErrorMsg {
	public final static  int  ERROR_USER_NAME_PASSWD_FAIL = 0 ;
	public final static  int  ERROR_NETWORK_IO_ERROR = 1 ;
	public final static  int  ERROR_USER_NAME_EXEIST = 2 ;
	public final static  int  ERROR_EXECUTE_TIMEOUT  = 3 ;
	
	public final static  int  ERROR_SERVER_ERROR_HAPPENED = 255 ;
	
    private  ArrayList<String> errorInfos ;
    private  int errorCode ;
    
    public ErrorMsg()
    {
    	this.errorInfos  = new ArrayList<String>() ;
    }
    public void addErrorMsg(String error)
    {
    	errorInfos.add(error);
    } 
    
    public  int   getErrorCode()
    {
    	return errorCode ;
    }
    public  void  setErrorCode(int errorCode)
    {
    	this.errorCode = errorCode ;
    }    
    
    public String toString()
    {
    	String ret = "" ;
    	for(String str:errorInfos)
    	{
    		ret += str+"\n\r";
    	}
    	return ret ;
    }
}
