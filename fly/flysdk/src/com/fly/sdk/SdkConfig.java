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
	public static String  API_URI_PRODUCT_LIST = "http://api.mfeiji.com/v1/products";
	
	
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
	public static String API_URL_SCHOOL_LIST = "http://api.mfeiji.com/v1/schools" ;
	
	
	/**
	 * params: 
        {
          id: 1
        }
	 */
	public static String API_URL_SCHOOL_DETAILS = "http://api.mfeiji.com/v1/schools/" ;
			
}
  