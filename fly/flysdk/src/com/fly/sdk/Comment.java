package com.fly.sdk;

import java.io.Serializable;

public class Comment implements Serializable{
//	"id"=>1, 
//            "title"=>"",                      # ���۵ı���
//            "comment"=>"this is a comment",   # ���۵�����
//            "commentable_id"=>1,              # ���۵Ĳ�Ʒ��id
//            "commentable_type"=>"Product",    # ���۵Ķ�����Product
//            "user_id"=>1,                     # �����ߵ�id
//            "role"=>"comments",               # ���ù�����ֶ�
//            "created_at"=>"2014-11-29T00:36:58.896+08:00", 
//            "updated_at"=>"2014-11-29T00:36:58.896+08:00"
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4540471929989887993L;
	private long id ;
	private String title ;
	private String comment ;
	private int commentableId ;
    private String commentableType ;
    private int    userId ;
    private String role; 
    private String createAt ;
    private String updateAt ;
    private String date ;
    
    private String userPicUrl;
    private String userName ;
    
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCommentableId() {
		return commentableId;
	}
	public void setCommentableId(int commentableId) {
		this.commentableId = commentableId;
	}
	public String getCommentableType() {
		return commentableType;
	}
	public void setCommentableType(String commentableType) {
		this.commentableType = commentableType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	public void setDate(String date)
	{
		this.date = date ;
	}
	public String getDate()
	{
		return date ;
	}
	public void setUserPicUrl(String url)
	{
		this.userPicUrl = url ;
	}
	public String getUserPicUrl()
	{
		return this.userPicUrl ;
	}
	public String getUserName()
	{
		return userName ;
	}
	public void  setUserName(String userName)
	{
		this.userName = userName ;
	}
	
	
}
