package com.fly.sdk;

import java.io.Serializable;

public class Order implements Serializable{
//	 "id"=>1,                                  # ������id
//	 "date"=>"2014-12-01",                     # ��������
//	 "product_title"=>"this is a title",       # ������Ӧ�Ĳ�Ʒ�ı���
//	 "product_id"=>1                           # ������Ӧ�Ĳ�Ʒ��id
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7129013355617784295L;
	private int id ;
	private String date ;
	private String productTitle ;
	private String  abstractStr ;
	public String getAbstractStr() {
		return abstractStr;
	}

	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}

	private long productId ;
	
	public Order()
	{	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null)
		{
			return false ;
		}
		if(!(obj instanceof Order))
		{
			return false ;
		}
		
		return  this.id == ((Order)obj).getId();
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (int)this.id;
	}
}
