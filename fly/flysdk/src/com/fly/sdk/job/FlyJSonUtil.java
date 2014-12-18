package com.fly.sdk.job;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import com.fly.sdk.Comment;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Option;
import com.fly.sdk.Product;
import com.fly.sdk.ProductBanner;
import com.fly.sdk.Question;
import com.fly.sdk.QuestionAttachment;
import com.fly.sdk.School;
import com.fly.sdk.SchoolPanner;
import com.fly.sdk.Score;
import com.fly.sdk.User;
import com.fly.sdk.util.TextUtils;

public class FlyJSonUtil {

	public static User parseUserJsonString(String jsonStr) {
		if (TextUtils.isEmpty(jsonStr)) {
			return null;
		}
		JSONTokener jsonTokener = new JSONTokener(jsonStr);
		try {
			while (jsonTokener.more()) {
				// {
				// "id":8,
				// "created_at":"2014-10-16T01:43:53.717Z",
				// "updated_at":"2014-10-16T01:43:53.717Z",
				// "email":"wangkun029@gmail.com",
				// "role":null,
				// "name":"foobarfoobarxxx",
				// "authentication_token":"KW-v6_WxSsgjnyNL3nyw",
				// "avatar":{
				// "url":null,
				// "thumb":{
				// "url":null
				// }
				// }
				// }

				JSONObject jobj = (JSONObject) jsonTokener.nextValue();
				if (!jobj.isNullObject()) {
					User user = new User();
					;
					if (jobj.has("id")) {
						user.setId(jobj.getLong("id"));
					}
					if (jobj.has("name")) {
						user.setName(jobj.getString("name"));
					}
					if (jobj.has("email")) {
						user.setEmail(jobj.getString("email"));
					}
					if (jobj.has("authentication_token")) {
						user.setUserToken(jobj
								.getString("authentication_token"));
					}
					if (jobj.has("role")) {
						user.setRole(jobj.getString("role"));
					}
					if (jobj.has("created_at")) {
						user.setCreateTime(jobj.getString("created_at"));
					}
					if (jobj.has("updated_at")) {
						user.setUpdateTime(jobj.getString("updated_at"));
					}
					if (jobj.has("avatar")) {
						JSONObject avatar = jobj.getJSONObject("avatar");
						if (!avatar.isNullObject()) {
							if (avatar.has("url")) {
								user.setUserPictureUri(avatar.getString("url"));
							}

							if (avatar.has("thumb")) {
								JSONObject thumb = avatar
										.getJSONObject("thumb");
								if (!thumb.isNullObject()) {
									if (thumb.has("url")) {
										user.setUserPictureUri(thumb
												.getString("url"));
									}
								}
							}
						}
					}

					if (jobj.has("cell")) {
						user.setCellNumber(jobj.getString("cell"));
					}
					if (jobj.has("gender")) {
						user.setGender(jobj.getString("gender"));
					}
					if (jobj.has("top_score")) {
						user.setBestScore(jobj.getDouble("top_score"));
					}

					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ErrorMsg parseErrorJsonString(String jsonStr) {
		if (TextUtils.isEmpty(jsonStr)) {
			return null;
		}

		JSONTokener jsonTokener = new JSONTokener(jsonStr);
		try {
			while (jsonTokener.more()) {

				JSONObject jobj = (JSONObject) jsonTokener.nextValue();
				if (!jobj.isNullObject()) {
					ErrorMsg errors = new ErrorMsg();
					if (jobj.has("name")) {
						JSONArray arrays = jobj.getJSONArray("name");
						int size = arrays.size();
						for (int i = 0; i < size; i++) {
							errors.addErrorMsg("用户名" + arrays.getString(i));
						}
					}
					if (jobj.has("email")) {
						JSONArray arrays = jobj.getJSONArray("email");
						int size = arrays.size();
						for (int i = 0; i < size; i++) {
							errors.addErrorMsg("Email" + arrays.getString(i));
						}
					}
					return errors;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Question> parseQuestionJsonString(String jsonStr) {
		if (TextUtils.isEmpty(jsonStr)) {
			return null;
		}
		JSONTokener jsonTokener = new JSONTokener(jsonStr);
		try {
			while (jsonTokener.more()) {
				JSONArray jArray = (JSONArray) jsonTokener.nextValue();
				ArrayList<Question> questions = new ArrayList<Question>();
				int size = jArray.size();
				// [
				// {
				// "id":2715,
				// "subject":"仪表飞行中克服空间定向障碍的有效措施是",
				// "created_at":"2013-10-16T08:05:26.000Z",
				// "updated_at":"2013-10-16T08:05:26.000Z",
				// "options":[
				// {
				// "id":8143,
				// "content":"正确识读和解释仪表并采取相应的行动",
				// "correct":"1",
				// "question_id":2715,
				// "created_at":"2013-10-16T08:05:26.000Z",
				// "updated_at":"2013-10-16T08:05:26.000Z"
				// },
				// ......
				// ]

				// "attachments":[
				// {
				// "id": 3385,
				// "image": {
				// "url": "/uploads/attachment/image/3385/rails.png",
				// "thumb": {
				// "url": "/uploads/attachment/image/3385/thumb_rails.png"
				// }
				// },
				// "question_id": 1047,
				// "created_at": "2014-11-27T07:20:52.927Z",
				// "updated_at": "2014-11-27T07:20:52.927Z"
				// },
				// ......
				// ]
				// },
				// ......
				// ]
				for (int i = 0; i < size; i++) {
					if (!jArray.getJSONObject(i).isNullObject()) {
						Question qst = new Question();
						questions.add(qst);

						JSONObject jObj = (JSONObject) jArray.get(i);
						if (jObj.has("id")) {
							qst.setQuestionId(jObj.getInt("id"));
						}
						if (jObj.has("subject")) {
							qst.setSubject(jObj.getString("subject"));
						}
						if (jObj.has("created_at")) {
							qst.setCreateTime(jObj.getString("created_at"));
						}
						if (jObj.has("updated_at")) {
							qst.setUpdateTime(jObj.getString("updated_at"));
						}
						if (jObj.has("options")) {
							JSONArray jOpts = jObj.getJSONArray("options");
							int jOptsSize = jOpts.size();
							for (int j = 0; j < jOptsSize; j++) {
								if (!jOpts.getJSONObject(j).isNullObject()) {
									Option opt = new Option();
									qst.addOption(opt);

									JSONObject jOpt = jOpts.getJSONObject(j);
									if (jOpt.has("id")) {
										opt.setOptionId(jOpt.getInt("id"));
									}
									if (jOpt.has("content")) {
										opt.setContent(jOpt
												.getString("content"));
									}
									if (jOpt.has("created_at")) {
										opt.setCreateTime(jOpt
												.getString("created_at"));
									}
									if (jOpt.has("updated_at")) {
										opt.setUpdateTime(jOpt
												.getString("updated_at"));
									}
									if (jOpt.has("question_id")) {
										opt.setQuestionId(jOpt
												.getInt("question_id"));
									}
									if (jOpt.has("correct")) {
										opt.setCorrect(jOpt.getInt("correct") == 1);
									}
								}
							}
						}

						if (jObj.has("attachments")) {
							JSONArray jAtcs = jObj.getJSONArray("attachments");
							int jatcSize = jAtcs.size();
							QuestionAttachment atc = null;
							for (int m = 0; m < jatcSize; m++) {
								JSONObject jAtcObj = jAtcs.getJSONObject(m);
								if (!jAtcObj.isNullObject()) {
									atc = new QuestionAttachment();
									qst.addAttach(atc);

									if (jAtcObj.has("id")) {
										atc.setId(jAtcObj.getInt("id"));
									}
									if (jAtcObj.has("question_id")) {
										atc.setQuestionID(jAtcObj
												.getInt("question_id"));
									}
									if (jAtcObj.has("created_at")) {
										atc.setCreateAt(jAtcObj
												.getString("created_at"));
									}
									if (jAtcObj.has("updated_at")) {
										atc.setUpdateAt(jAtcObj
												.getString("updated_at"));
									}

									if (jAtcObj.has("image")) {
										JSONObject imageObj = jAtcObj
												.getJSONObject("image");
										if (!imageObj.isNullObject()) {
											if (imageObj.has("url")) {
												atc.setImageUrl(imageObj
														.getString("url"));
											}
											if (imageObj.has("thumb")) {
												JSONObject thumbObj = jAtcObj
														.getJSONObject("thumb");
												if (!thumbObj.isNullObject()) {
													if (thumbObj.has("url")) {
														atc.setThumbImageUrl(thumbObj
																.getString("url"));
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				return questions;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseVersionJsonString(String jsonStr) {
		if (TextUtils.isEmpty(jsonStr)) {
			return null;
		}
		JSONTokener jsonTokener = new JSONTokener(jsonStr);
		// {
		// "version":"2.0"
		// }
		while (jsonTokener.more()) {
			JSONObject jObj = (JSONObject) jsonTokener.nextValue();
			if (!jObj.isNullObject()) {
				if (jObj.has("version")) {
					return jObj.getString("version");
				}
			}
		}
		return null;
	}

	public static ArrayList<Score> parseScoreJsonString(String jsonStr) {
		if (TextUtils.isEmpty(jsonStr)) {
			return null;
		}
		JSONTokener jsonTokener = new JSONTokener(jsonStr);

		// get user score list
		// [
		// {
		// "id":5,
		// "number":98.0,
		// "user_id":8,
		// "created_at":"2014-10-16T03:14:38.000Z",
		// "updated_at":"2014-10-16T03:14:38.000Z"
		// }
		// ]
		// get top ten
		// [
		// {
		// "name":"ken",
		// "number":99.0,
		// "time":"2014-09-27T06:04:28.000Z"
		// },
		// ......
		// ]
		// create user score
		// {
		// "id":5,
		// "number":98.0,
		// "user_id":8,
		// "created_at":"2014-10-16T03:14:38.592Z",
		// "updated_at":"2014-10-16T03:14:38.592Z"
		// }

		while (jsonTokener.more()) {
			JSONArray jArrays = null;

			Object obj = jsonTokener.nextValue();
			try {
				jArrays = (JSONArray) obj;
			} catch (ClassCastException e) {
				// TODO: handle exception
				JSONObject jobj = (JSONObject) obj;
				jArrays = new JSONArray();
				jArrays.add(jobj);
			}
			ArrayList<Score> scores = new ArrayList<Score>();
			int size = jArrays.size();
			for (int i = 0; i < size; i++) {
				if (!jArrays.getJSONObject(i).isNullObject()) {
					JSONObject jObj = jArrays.getJSONObject(i);
					Score score = new Score();
					scores.add(score);

					if (jObj.has("id")) {
						score.setId(jObj.getInt("id"));
					}

					if (jObj.has("number")) {
						score.setScore((float) jObj.getDouble("number"));
					}

					if (jObj.has("user_id")) {
						score.setUserId(jObj.getInt("number"));
					}

					if (jObj.has("created_at")) {
						score.setCreateTime(jObj.getString("created_at"));
					}

					if (jObj.has("time")) {
						score.setCreateTime(jObj.getString("time"));
					}

					if (jObj.has("updated_at")) {
						score.setUpdateTime(jObj.getString("updated_at"));
					}

					if (jObj.has("name")) {
						score.setUserName(jObj.getString("name"));
					}

					if (jObj.has("avatar")) {
						score.setUserUrlPic(jObj.getString("avatar"));
					}

				}
			}
			return scores;
		}
		return null;
	}

	public static ArrayList<Product> parseProductJsonString(String str) {
		
		if (TextUtils.isEmpty(str)) {
			return null;
		}	
		JSONTokener jsonTokener = new JSONTokener(str);
		ArrayList<Product> products = new ArrayList<Product>();
		
		while (jsonTokener.more()) {
			JSONArray jArrays = null;
			Object obj = jsonTokener.nextValue();
			try {
				jArrays = (JSONArray) obj;
			} catch (ClassCastException e) {
				// TODO: handle exception
				JSONObject jobj = (JSONObject) obj;
				jArrays = new JSONArray();
				jArrays.add(jobj);
			}
			int size = jArrays.size();
			for (int i = 0; i < size; i++) {
				JSONObject jObj = jArrays.getJSONObject(i);
				if (!jObj.isNullObject()) {
					if (jObj.has("products")) {
						JSONArray productArray = jObj.getJSONArray("products");
						int pSize = productArray.size();
						for (int n = 0; n < pSize; n++) {
							JSONObject jPro = productArray.getJSONObject(n);
							if (!jPro.isNullObject()) {
								Product product = new Product();
								products.add(product);
								if (jPro.has("id")) {
									product.setId(jPro.getInt("id"));
								}
								if (jPro.has("first_image")) {
									product.setFirstImageUrl(jPro
											.getString("first_image"));
								}
								if (jPro.has("title")) {
									product.setTitle(jPro.getString("title"));
								}
								if (jPro.has("abstract")) {
									product.setAbstractString(jPro
											.getString("abstract"));
								}
								if (jPro.has("comment_count")) {
									product.setCommentCount(jPro
											.getInt("comment_count"));
								}
								if (jObj.has("comments")) {
									JSONArray jarr = jPro
											.getJSONArray("comments");
									int comSize = jarr.size();
									JSONObject jmObj = null;
									for (int m = 0; m < comSize; m++) {
										jmObj = jarr.getJSONObject(m);
										if (!jmObj.isNullObject()) {
											Comment com = new Comment();
											if (jmObj.has("id")) {
												com.setId(jmObj.getInt("id"));
											}
											if (jmObj.has("title")) {
												com.setTitle(jmObj
														.getString("title"));
											}
											if (jmObj.has("comment")) {
												com.setComment(jmObj
														.getString("comment"));
											}
											if (jmObj.has("commentable_id")) {
												com.setCommentableId(jmObj
														.getInt("commentable_id"));
											}
											if (jmObj.has("commentable_type")) {
												com.setCommentableType(jmObj
														.getString("commentable_type"));
											}
											if (jmObj.has("user_id")) {
												com.setUserId(jmObj
														.getInt("user_id"));
											}
											if (jmObj.has("role")) {
												com.setRole(jmObj
														.getString("role"));
											}
											if (jmObj.has("created_at")) {
												com.setCreateAt(jmObj
														.getString("created_at"));
											}
											if (jmObj.has("updated_at")) {
												com.setUpdateAt(jmObj
														.getString("updated_at"));
											}
											product.addComment(com);
										}
									}
								}
							}
						}
					}
					
				    if(jObj.has("images"))
				    {
				    	JSONArray  productArray = jObj.getJSONArray("images");
				    	
				    	if(productArray != null)
				    	{
				    		int jImgSize = productArray.size();
				    		for(int iIndex = 0 ; iIndex < jImgSize ; iIndex++)
				    		{	
				    			JSONObject jimgObject = productArray.getJSONObject(iIndex);
				    			if(!jimgObject.isNullObject())
				    			{
				    				ProductBanner  proBanner = new ProductBanner();
				    				products.add(proBanner);
				    				if(jimgObject.has("id"))
				    				{
				    					proBanner.setId(jimgObject.getLong("id"));
				    				}
				    				if(jimgObject.has("image"))
				    				{
				    					proBanner.setFirstImageUrl(jimgObject.getString("image"));
				    				}
				    		        if(jimgObject.has("title"))
				    		        {
				    		        	proBanner.setTitle(jimgObject.getString("title"));
				    		        }
				    			}
				    		}
				    		
				    	}
				    }
				}
			}
		}
		return products;
	}

	public static ArrayList<School> parseSchoolJsonString(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		JSONTokener jsonTokener = new JSONTokener(json);
		ArrayList<School> schools = new ArrayList<School>();
		while (jsonTokener.more()) {
			JSONArray jArrays = null;
			Object obj = jsonTokener.nextValue();
			try {
				jArrays = (JSONArray) obj;
			} catch (ClassCastException e) {
				// TODO: handle exception
				JSONObject jobj = (JSONObject) obj;
				jArrays = new JSONArray();
				jArrays.add(jobj);
			}
			int size = jArrays.size();
			for (int i = 0; i < size; i++) {
				JSONObject jObj = jArrays.getJSONObject(i);
				if (!jObj.isNullObject()) {

					if (jObj.has("schools")) {
						JSONArray jschAray = jObj.getJSONArray("schools");
						int jsSize = jschAray.size();
						for (int n = 0; n < jsSize; n++) {
							JSONObject jSchool = jschAray.getJSONObject(n);
							if (!jSchool.isNullObject()) {
								School school = new School();
								schools.add(school);
								if (jSchool.has("id")) {
									school.setId(jSchool.getInt("id"));
								}
								if (jSchool.has("title")) {
									school.setTitle(jSchool.getString("title"));
								}

								if (jSchool.has("abstract")) {
									school.setAbstractString(jSchool
											.getString("abstract"));
								}

								if (jSchool.has("first_image")) {
									school.setFirstImageUrl(jSchool
											.getString("first_image"));
								}

								if (jSchool.has("comment_count")) {
									school.setCommentCount(jSchool
											.getInt("comment_count"));
								}
								if (jSchool.has("comments")) {
									JSONArray jarr = jSchool
											.getJSONArray("comments");
									int comSize = jarr.size();
									JSONObject jmObj = null;
									for (int m = 0; m < comSize; m++) {
										jmObj = jarr.getJSONObject(m);
										if (!jmObj.isNullObject()) {
											Comment com = new Comment();
											if (jmObj.has("id")) {
												com.setId(jmObj.getInt("id"));
											}
											if (jmObj.has("title")) {
												com.setTitle(jmObj
														.getString("title"));
											}
											if (jmObj.has("comment")) {
												com.setComment(jmObj
														.getString("comment"));
											}
											if (jmObj.has("commentable_id")) {
												com.setCommentableId(jmObj
														.getInt("commentable_id"));
											}
											if (jmObj.has("commentable_type")) {
												com.setCommentableType(jmObj
														.getString("commentable_type"));
											}
											if (jmObj.has("user_id")) {
												com.setUserId(jmObj
														.getInt("user_id"));
											}
											if (jmObj.has("role")) {
												com.setRole(jmObj
														.getString("role"));
											}
											if (jmObj.has("created_at")) {
												com.setCreateAt(jmObj
														.getString("created_at"));
											}
											if (jmObj.has("updated_at")) {
												com.setUpdateAt(jmObj
														.getString("updated_at"));
											}
											school.addComment(com);
										}
									}
								}
							}
						}
					}
					
					if(jObj.has("images"))
					{
						JSONArray  jSchArray = jObj.getJSONArray("images");
						if(jSchArray != null)
						{
							int jSize = jSchArray.size();
							for(int jCIndex = 0 ; jCIndex < jSize ; jCIndex++)
							{
								JSONObject  jSchpanner = jSchArray.getJSONObject(jCIndex);
								if(!jSchpanner.isNullObject())
								{
									SchoolPanner schoolPanner = new SchoolPanner();
									schools.add(schoolPanner);
									if(jSchpanner.has("id"))
				    				{
										schoolPanner.setId(jSchpanner.getLong("id"));
				    				}
				    				if(jSchpanner.has("image"))
				    				{
				    					schoolPanner.setFirstImageUrl(jSchpanner.getString("image"));
				    				}
				    		        if(jSchpanner.has("title"))
				    		        {
				    		        	schoolPanner.setTitle(jSchpanner.getString("title"));
				    		        }
								}
							}
						}
					}
				}
			}
		}
		return schools;
	}

}
