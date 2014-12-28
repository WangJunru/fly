package com.fly.sdk;

import java.io.Serializable;

public class Notice implements Serializable{
//"id"=>1,
//"title"=>"this is a title",             # 标题
//"abstract"=>"this is a abstract",       # 摘要
//"date"=>"2014-12-25T17:55:48.040+08:00" # 发布时间
//"page":"\u003cp style=\"text-align:center\"\u003e\u003cimg alt=\"十八大后黑龙江首个省部级高官隋凤富被调查(图)\" src=\"http://img1.cache.netease.com/cnews/2014/11/28/20141128023112271a4.jpg\" style=\"border:0px; vertical-align:top\" /\u003e\u003cbr /\u003e\r\n隋凤富\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e\u003cstrong\u003e系十八大以来黑龙江首个被查省部级高官；在黑龙江农垦系统工作37年\u003c/strong\u003e\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e新京报讯 一个月前的10月28日，中央巡视组反馈黑龙江存在的问题时指出，该省农垦系统违纪违法案件频发。昨晚9：55，中纪委官网通报称，黑龙江省人大常委会副主任、省农垦总局党委书记隋凤富涉嫌严重违纪违法，目前正接受组织调查。\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e在隋凤富之前，十八大以来被调查的省部级及以上官员已达52人。但这52名高官均未涉及黑龙江。也就是说，隋凤富系黑龙江首个被调查的省部级高官。\u003c/p\u003e\r\n"                       # 详情
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1488188227173157858L;
	private long id ;
	private String title ;
	private String  abstractStr ;
	private String  dateStr ;
	private String  pageHtmlCode ;
	
	public Notice(){}
	
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
	public String getAbstractStr() {
		return abstractStr;
	}
	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getPageHtmlCode() {
		return pageHtmlCode;
	}
	public void setPageHtmlCode(String pageHtmlCode) {
		this.pageHtmlCode = pageHtmlCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null)
		{
			return false ;
		}
		if(!(obj instanceof Notice))
		{
			return false ;
		}
		
		return  this.id == ((Notice)obj).getId();
	}
	
	@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return (int)this.id;
		}
}
