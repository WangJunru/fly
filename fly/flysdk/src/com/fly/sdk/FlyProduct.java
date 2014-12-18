package com.fly.sdk;

import java.util.ArrayList;

public abstract class FlyProduct {
	
     private long id; 
     private String title ;
     private String abstractString ;
     private String firstImageUrl ;
     private int   commentCount ;
     private ArrayList<Comment>  comments = new ArrayList<Comment>();
     private String pageHtmlCode ;
     
     public String getPageHtmlCode() {
 		return pageHtmlCode;
 	}
 	public void setPageHtmlCode(String pageHtmlCode) {
 		this.pageHtmlCode = pageHtmlCode;
 	}
 	
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
 	public String getAbstractString() {
 		return abstractString;
 	}
 	public void setAbstractString(String abstractString) {
 		this.abstractString = abstractString;
 	}
 	public String getFirstImageUrl() {
 		return firstImageUrl;
 	}
 	public void setFirstImageUrl(String firstImageUrl) {
 		this.firstImageUrl = firstImageUrl;
 	}
 	public int getCommentCount() {
 		return commentCount;
 	}
 	public void setCommentCount(int commentCount) {
 		this.commentCount = commentCount;
 	}
      
 	public void addComment(Comment com)
 	{
 		this.comments.add(com);
 	}
 	
 	public void delCommnet(Comment com)
 	{
 		this.comments.remove(com);
 	}
 	
 	public ArrayList<Comment>  getComments()
 	{
 		return comments ;
 	}
 	
 	public void setComments(ArrayList<Comment> coms)
 	{
 		if(coms != null && !coms.isEmpty())
 		{
 	    	this.comments.addAll(coms);
 		}
 	}
}
