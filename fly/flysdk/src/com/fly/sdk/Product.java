package com.fly.sdk;


public class Product  extends FlyProduct {
/**
	 * 
	 */
	private static final long serialVersionUID = -8268033148731833173L;
//	"id"=>1, 
//            "title"=>"this is a title",         # ����
//            "abstract"=>"this is a abstract",   # ժҪ 
//            "first_image"=>"boat.gif",          # ��һ��ͼƬ
//            "price": "1000",                    # �۸�
//            "comment_count": "10"  
	
//  "id":3,
//  "title": "this is the title",         # ����
//  "abstract": "this is a abstract"      # ժҪ��appӦ�ò���Ҫ��ʾ��飩
//  "page":"\u003cp style=\"text-align:center\"\u003e\u003cimg alt=\"ʮ�˴��������׸�ʡ�����߹���︻������(ͼ)\" src=\"http://img1.cache.netease.com/cnews/2014/11/28/20141128023112271a4.jpg\" style=\"border:0px; vertical-align:top\" /\u003e\u003cbr /\u003e\r\n��︻\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e\u003cstrong\u003eϵʮ�˴������������׸�����ʡ�����߹٣��ں�����ũ��ϵͳ����37��\u003c/strong\u003e\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e�¾���Ѷ һ����ǰ��10��28�գ�����Ѳ���鷴�����������ڵ�����ʱָ������ʡũ��ϵͳΥ��Υ������Ƶ��������9��55���м�ί����ͨ���ƣ�������ʡ�˴�ί�ḱ���Ρ�ʡũ���ֵܾ�ί�����︻��������Υ��Υ����Ŀǰ��������֯���顣\u003c/p\u003e\r\n\r\n\u003cp style=\"text-align:justify\"\u003e����︻֮ǰ��ʮ�˴������������ʡ���������Ϲ�Ա�Ѵ�52�ˡ�����52���߹پ�δ�漰��������Ҳ����˵����︻ϵ�������׸��������ʡ�����߹١�\u003c/p\u003e\r\n",    ����������
//  "comment_count": "10"                 # ������
//  "price":"1000",                       # �۸�
//  "comments":[                          # ����
//    {
//      "id"=>1, 
//      "title"=>"",                      # ���۵ı���
//      "comment"=>"this is a comment",   # ���۵�����
//      "commentable_id"=>1,              # ���۵Ĳ�Ʒ��id
//      "commentable_type"=>"Product",    # ���۵Ķ�����Product
//      "user_id"=>1,                     # �����ߵ�id
//      "role"=>"comments",               # ���ù�����ֶ�
//      "created_at"=>"2014-11-29T00:36:58.896+08:00", 
//      "updated_at"=>"2014-11-29T00:36:58.896+08:00"
//    },
	
    private double price ;
    public double getPrice() {
 		return price;
 	}
 	public void setPrice(double price) {
 		this.price = price;
 	}
 	
 	
     
     
}
