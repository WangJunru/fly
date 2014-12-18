package com.fly.sdk;

public class QuestionAttachment {
//	"attachments":[
//	               {
//	                 "id": 3385,
//	                 "image": {
//	                   "url": "/uploads/attachment/image/3385/rails.png",
//	                   "thumb": {
//	                     "url": "/uploads/attachment/image/3385/thumb_rails.png"
//	                   }
//	                 },
//	                 "question_id": 1047,
//	                 "created_at": "2014-11-27T07:20:52.927Z",
//	                 "updated_at": "2014-11-27T07:20:52.927Z"
//	               },
//	               ......
//	             ]
	
	private  int id ;
	private  String imageUrl ;
	private  String thumbImageUrl ;
	private  int questionID ;
	
	private  String createAt ;
	private  String updateAt ;
	
	public QuestionAttachment(){}
	public QuestionAttachment(int id)
	{
		this.id = id ;
	}
	public QuestionAttachment(int id , String imageUrl ,String thumbImageUrl)
	{
		this.id = id ;
		this.imageUrl = imageUrl ;
		this.thumbImageUrl = thumbImageUrl ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbImageUrl() {
		return thumbImageUrl;
	}

	public void setThumbImageUrl(String thumbImageUrl) {
		this.thumbImageUrl = thumbImageUrl;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
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
    
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+id+":"+imageUrl+":"+thumbImageUrl+"}\r\n";
	}
	
}
