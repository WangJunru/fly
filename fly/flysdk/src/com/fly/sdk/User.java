package com.fly.sdk;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8663405214643026037L;
	private String  userToken ;
	private long  id ;
	
	/**
	 * global unique 
	 */
    private String  name ;
    
    /**
     * global unique  
     */
    private String  email ;
    private String  role ;
    
    public static String GENDER_MALE = "male" ;
    public static String GENDER_FEMALE = "female" ;
    
    private String gender;
    private String cell ;
    private double bestScore ;
    
    
    private String createTime ;
    private String updateTime ;
    
    private String userPictureUri ;
    private String userPictureThumbUri ;
    
    private String userInfoUpdateUri ;
    
    private String address ;
    
    private int   rank ;
    
    public User(){}
    public User(String name ,String email, String userToken)
    {
      this.name = name ;
      this.email = email ;
      this.userToken = userToken ;
    }
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getUserPictureUri() {
		return userPictureUri;
	}
	public void setUserPictureUri(String userPictureUri) {
		this.userPictureUri = userPictureUri;
	}
	public String getUserPictureThumbUri() {
		return userPictureThumbUri;
	}
	public void setUserPictureThumbUri(String userPictureThumbUri) {
		this.userPictureThumbUri = userPictureThumbUri;
	}
	public String getUserInfoUpdateUri() {
		return userInfoUpdateUri;
	}
	public void setUserInfoUpdateUri(String userInfoUpdateUri) {
		this.userInfoUpdateUri = userInfoUpdateUri;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender ;
	}
	public String getGender()
	{
		return this.gender ;
	}
	
	public void setCellNumber(String number)
	{
		this.cell = number ;
	}
	public String getCellNumber()
	{
	    return cell ;
	}
	
	public void setBestScore(double bestScore)
	{
	    this.bestScore  = bestScore;	
	}
	public double getBestScore()
	{
		return bestScore ;
	}
	
	public int getRank()
	{
		return rank ;
	}
	public void setRank(int rank)
	{
		this.rank = rank ;
	}
	
	public String getAddress()
	{
		return address ;
	}
	public void  setAddress(String address)
	{
		this.address = address ;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
		{
			return false ;
		}		
		if(!(o instanceof User))
		{
			return false;
		}
		
		User     that = (User)o;
		return   (that.getName() == name || that.getEmail() == email) ;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return  this.name.hashCode() ;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "{"+name+":"+email+":"+userToken+":"+role+"}";
	}
	
}
