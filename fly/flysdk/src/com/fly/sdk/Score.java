package com.fly.sdk;

import java.io.Serializable;

public class Score implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 873952460284695374L;

	private int id;
    
    private double  score ;
    
    private int userId ;
    private String userName ;
    private String email ;
    private String userToken ;
    private String userPicUrl ;
    
    
    public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	private String createTime ;
    private String updateTime ;
    
    public Score(){ }
    public Score(int id ,int score)
    {
    	this.id = id ;
    	this.score  = score ;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
    
	public void setUserUrlPic(String urlPic)
	{
		this.userPicUrl = urlPic ;
	}
	public String getUserUrlPic()
	{
		return this.userPicUrl;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "{ id:"+id +",user_id:"+userId+
		",userName:"+userName+",Email:"+email+",score:"+score+"}";
	}
}
