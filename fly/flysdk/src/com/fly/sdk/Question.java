package com.fly.sdk;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4568568290132231322L;

	private int  questionId;
	
	private String subject ;	
	private String createTime ;
	private String updateTime ;
	
	private int   userCheckIndex ;
	
	private boolean isThisQtsAnwseredRight = false ;
	public void  setThisQtsAnwseredRight(boolean anwser)
	{
		isThisQtsAnwseredRight = anwser ;
	}
	public boolean isThisQtsAnwseredRight()
	{
		return isThisQtsAnwseredRight ;
	}
	
	private int  anwseredOptIndex = -1 ;
	public void  setAnwseredOptIndex(int index)
	{
		anwseredOptIndex = index ;
	}
	
	public int getAnwseredOptIndex()
	{
		return anwseredOptIndex ;
	}
	
	private ArrayList<Option>  options = new ArrayList<Option>();
	private ArrayList<QuestionAttachment> attachs = new ArrayList<QuestionAttachment>();
	
	public Question(){}
	public Question(int  questionID,String subject)
	{
		this.questionId = questionID ;
		this.subject =  subject ;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public ArrayList<Option> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}
	
	
	public void addOption(Option opt)
	{
		options.add(opt);
	}
	
	public Option  getOption(int optionID)
	{
		for(Option opt:options)
		{
			if(opt.getOptionId() == optionID )
				return opt;
		}
		return null ;
	}
	
	public void setAttaches(ArrayList<QuestionAttachment> attaches) {
		this.attachs = attaches;
	} 
	public ArrayList<QuestionAttachment> getAttaches()
	{
		return attachs ;
	}
	
	public void  addAttach(QuestionAttachment atc)
	{
		this.attachs.add(atc);
	}
	
	public QuestionAttachment  getAttach(int atcId)
	{
		for(QuestionAttachment atc:attachs)
		{
			if(atc.getId() == atcId )
				return atc;
		}
		return null ;
	}
	
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
		{
			return false ;
		}
		
		if(!( o instanceof Question))
		{
		  return false ;
		}
		
		Question that = (Question)o;
		return this.questionId == that.getQuestionId();	
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return  questionId*11 ;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+questionId+":"+subject+":\r\n"+options.toString()+attachs.toString()+"}\r\n";
	}
	
	
}
