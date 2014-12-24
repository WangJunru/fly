package com.fly.sdk;

import java.io.Serializable;

public class Option implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7743084726577279902L;
	private int optionId;
	private int questionId;

	private String content;
	private String createTime;
	private String updateTime;

	private boolean isCorrect;
	public Option() {
	}

	public Option(int optionId, int questionId, String content,
			boolean isCorrect) {
		this.optionId = optionId;
		this.questionId = questionId;
		this.content = content;
		this.isCorrect = isCorrect;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
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
		
		Question  qt  = (Question)o ; 
		return qt.getQuestionId() == this.optionId ;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return optionId * 11 ;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+optionId+":"+content+":"+isCorrect+"}\r\n";
	}

	
}
