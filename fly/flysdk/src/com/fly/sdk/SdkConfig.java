package com.fly.sdk;

public class SdkConfig {
     
	public static String  HTTP_ENCODING = "UTF-8";
	/**
	 * create_user                           </br>
	 * {                                      </br>
	 *    <strong>
     *     "user":{                           </br>
     *       name: "foobarfoobarxxx",         </br>
     *       email: "wangkun029@gmail.com"    </br>
     *      }  
     *     </strong>                          </br>
     *  }                                     </br>    
        example:   </br> <strong>
        curl -X POST -d "user[name]=ken&user[email]=wangkun028@gmail.com&user[password]=11111111" http://api.mfeiji.com/v1/users
        </strong>
	 */
	public static String  API_URI_CREATE_USER = "http://api.mfeiji.com/v1/users" ;
	
	
	
	
	/**
	 *  user_update  </br>
	 *  {            </br>
	 *    <strong>
            "user_token": "KW-v6_WxSsgjnyNL3nyw", </br>
            "user_email": "wangkun029@gmail.com"  </br>
            "avatar": "@/home/ken/Pictures/download.jpg" </br>
          </strong>
        } </br>
        
        example: </br>
         <strong>
           curl -X PUT -d "user_token=rxLEMXBNP41D6Jprgs3x&user_email=wangkun026@gmail.com&user[name]=wong" http://api.mfeiji.com/v1/users/8  </br>
           curl -X PUT -F "user_token=KW-v6_WxSsgjnyNL3nyw" -F "user_email=wangkun029@gmail.com" -F "user[avatar]=@/home/ken/Pictures/download.jpg" http://api.mfeiji.com/v1/users/8 </br>
           use post for updating avatar  </br>
           curl -X POST -F "user_token=KW-v6_WxSsgjnyNL3nyw" -F "user_email=wangkun029@gmail.com" -F "user[avatar]=@/home/ken/Pictures/download.jpg" http://api.mfeiji.com/v1/users/8/updateavatar </br>
	     </strong>
	 */
	public static String  API_URI_UPDATE_USER = "http://api.mfeiji.com/v1/users/" ;
	
	
	/**
	 * params: {
          "user_email": "wangkun025@gmail.com"
        }
	 */
	public static String  API_URI_PASSWD_RESET = "http://api.mfeiji.com/v1/users/reset_password" ;
	
	
	/**
	 * params: {
          "user":{
            current_password: "11111111",           ＃ 原密码
            password: "22222222",                   ＃ 新密码
            password_confirmation: "22222222"       ＃ 确认新密码
          }
        }
	 */
	public static String API_URI_PASSWD_UPDATE = "http://api.mfeiji.com/v1/users/"+"%1$d"+"/update_password";
	
	/**
	 *  delete
	 *  {       
	 *     <strong>                                     </br>               
          "user_email": user.email,                     </br>
          "user_token": user.token                      </br>
          </strong>            
        }</br>
        example : </br><strong>
           curl -X DELETE -d "user_email=wangkun025@gmail.com&user_token=nMGYfd_EU8CGCkS5Fq6Z" http://api.mfeiji.com/v1/users/2
        </strong>
	 */
	public static String  API_URI_DELETE_USER = "http://api.mfeiji.com/v1/users/" ;
	
	
	/**
	 * params: {
          "user_email": user.email,   ＃ 用户的邮箱
          "user_token": user.token    ＃ 用户的token，两者用于验证用户身份
        }
	 */
	public static String API_URI_USER_DETAILS = "http://api.mfeiji.com/v1/users/%1$d" ;
	

	/**
	 *  login                                     </br> 
	 *  {                                         </br>
	 *        <strong>
	 *          "login": user.name or user.email, </br>
                "password": user.password         </br>
              </strong>                           
        }                                        </br> 
        example: </br><strong>
           curl -X POST -d "login=wangkun025@gmail.com&password=11111111" http://api.mfeiji.com/v1/user_tokens  </br>
              or  </br>
           curl -X POST -d "login=ken&password=11111111" http://api.mfeiji.com/v1/user_tokens  </br>
        </strong>
        logout                                   </br> 
        {                                        </br> 
           <strong>
          "user_email": user.email,              </br>
          "user_token": user.token               </br> 
          </strong>                                 
         }                                       </br>      
        example: </br><strong>
          curl -X DELETE -d "user_email=wangkun025@gmail.com&user_token=nMGYfd_EU8CGCkS5Fq6Z" http://api.mfeiji.com/v1/user_tokens/nMGYfd_EU8CGCkS5Fq6Z
          </strong>
	 *
	 */
	
	public static String  API_URI_USER_TOKEN = "http://api.mfeiji.com/v1/user_tokens/" ;
	
	
	/**
	 * question download 
	 */
	public static String  API_URI_QUESTION_DOWNLOAD = "http://api.mfeiji.com/v1/questions/question_group" ;
	
	
	/**
	 * app version information
	 */
	public static String  API_URI_VERSION_INFO = "http://api.mfeiji.com/v1/version" ;
	
	
	/**
	 * only for user logined  
	 */
	public static String  API_URI_GET_USER_SCORE_LIST = "http://api.mfeiji.com/v1/scores" ;
	
	
	/**
	 * commit score numbers
	 */
	public static String  API_URI_USER_SCORE_CREATE = "http://api.mfeiji.com/v1/scores" ;
	
	
	/**
	 * get top ten best scores 
	 */
	public static String  API_URI_TOPTEN_SCORE_LIST = "http://api.mfeiji.com/v1/scores/topten";
	
	
	/**
	 *  {
          page: 1 # paginate number       分页的页码
        }
	 */
	public static String  API_URI_PRODUCT_LIST = "http://api.mfeiji.com/v1/products?";
	
	
	/**
	 *  {
          id: 1
        }
	 */
	public static String  API_URI_PRODUCT_DETAILS = "http://api.mfeiji.com/v1/products/";
	
	
	/**
	 * params: {
          page: 1 # paginate number    ＃分页的页码
        }
	 */
	public static String API_URL_SCHOOL_LIST = "http://api.mfeiji.com/v1/schools?" ;
	
	
	/**
	 * params: 
        {
          id: 1
        }
	 */
	public static String API_URL_SCHOOL_DETAILS = "http://api.mfeiji.com/v1/schools/" ;
	
	
	/**
	 *  {
          page: 1                                             # 页码
          school_id: 1 (only: school_id or product_id)        # 只能使用school_id 或者product_id这两个参数
        }
	 */
	public static String API_URL_FLY_PRO_COMMENTS_LIST = "http://api.mfeiji.com/v1/comments" ;
	
	
	
	/**
	 * params: 
        {
          "user_email": user.email,                       # 评论者的邮箱 
          "user_token": user.authentication_token,        # 评论者的user token
          "school_id": 1,                # or product_id  # 评论的产品或者航校的id
          "comment": { 
            "comment": "this is a comment"                # 评论内容
          }
        }
	 */
	public static String API_URL_FLY_PRO_CREATE_COMMENT = "http://api.mfeiji.com/v1/comments" ;
	
	
	/**
	 * params: 
       {
         "user_email": user.email,
         "user_token": user.authentication_token,
       }
	 */
	public static String API_URL_FLY_PRO_DELETE_COMMENT = "http://api.mfeiji.com/v1/comments/%1$d";
	
	
	/**
	 * params: 
        {
          "user_email": user.email,
          "user_token": user.authentication_token,
          page: 1
        }
	 */
	public static String API_URL_USER_ORDERS = "http://api.mfeiji.com/v1/orders";
	
	
	
	/**
	 * params: {
          time: "2014-12-13 16:04:58 +0800"   # 时间戳， 只获取此时间之后的通知
        }
	 */
	public static String API_URL_GET_NOTICE  = "http://api.mfeiji.com/v1/notices" ;
	
	
	/**
	 * action: get
	 */
	public static String API_URL_GET_NOTICE_DETAIL = "http://api.mfeiji.com/v1/notices/%1$d" ;
			
}
  