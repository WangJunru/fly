package com.fly.sdk;

import java.io.Serializable;

public class Notice implements Serializable{
//"id"=>1,
//"title"=>"this is a title",             # ����
//"abstract"=>"this is a abstract",       # ժҪ
//"date"=>"2014-12-25T17:55:48.040+08:00" # ����ʱ��
//"page":"\u003cp style=\"text-align:center\"\u003e\u003cimg alt=\"ʮ�˴��������׸�ʡ�����߹���︻������(ͼ)\" src=\"http://img1.cache.netease.com/cnews/2014/11/28/20141128023112271a4.jpg\" style=\"border:0px; vertical-align:top\" /\u003e\u003cbr /\u003e\r\n��︻\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e\u003cstrong\u003eϵʮ�˴������������׸�����ʡ�����߹٣��ں�����ũ��ϵͳ����37��\u003c/strong\u003e\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e�¾���Ѷ һ����ǰ��10��28�գ�����Ѳ���鷴�����������ڵ�����ʱָ������ʡũ��ϵͳΥ��Υ������Ƶ��������9��55���м�ί����ͨ���ƣ�������ʡ�˴�ί�ḱ���Ρ�ʡũ���ֵܾ�ί�����︻��������Υ��Υ����Ŀǰ��������֯���顣\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e����︻֮ǰ��ʮ�˴������������ʡ���������Ϲ�Ա�Ѵ�52�ˡ�����52���߹پ�δ�漰��������Ҳ����˵����︻ϵ�������׸��������ʡ�����߹١�\u003c/p\u003e\r\n"                       # ����
	 
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
