package com.fly.sdk;

public class CheckCode {
	
    private long  userID ;
    private String  batchCode ;
    private String cellNumber ;
    
    private int errorCode = -1 ;
    public static final int  GET_CODE_FAILED = 1 ;
    public static final int  WRONG_CHEDK_CODE = 2 ;
    
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "user_id:"+userID+"batch_code:"+batchCode+"cell:"+cellNumber;
	}
	
	public void  setErrorCode(int code)
	{
		this.errorCode = code ;
	}
	public int getErrorCode()
	{
		return errorCode ;
	}
	
	
    
    
}
