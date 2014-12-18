package com.fly.sdk.test;

import java.io.File;
import java.util.List;

import com.fly.sdk.User;
import com.fly.sdk.job.QuestionDownload;
import com.fly.sdk.job.UserLogin;
import com.fly.sdk.job.UserScoreCreate;
import com.fly.sdk.job.UserScoreListGet;
import com.fly.sdk.job.UserUpdate;
import com.fly.sdk.job.UserUpdate.UpdateOption;
import com.fly.sdk.job.VersionInfo;
import com.fly.sdk.threading.FlyTaskManager;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;


public class SdkTest {
	  
	  private static User loginedUser ;
      public static void  main(String[]  args)
      {
          final FlyTaskManager taskManager = FlyTaskManager.getInstance();          
          // Regest user 
//          UserRegist registUserJob = new UserRegist("joegana","supersky1986@126.com","31415926535");
//          taskManager.commitJob(registUserJob, new ResultCallback() {
//			@Override
//			public void notifyResult(Object result) {
//				// TODO Auto-generated method stub
//				if(result != null)
//				{
//					System.out.println(result);
//				}
//			}
//		});
          
        // Login
          UserLogin userLoginJob = new UserLogin("joegana","supersky1986@126.com","123456");
          taskManager.commitJob(userLoginJob, new ResultCallback() {
  			@Override
  			public void notifyResult(Object result) {
  				// TODO Auto-generated method stub
  				if(result != null)
  				{
  					if(result instanceof User)
  					{
  						loginedUser  = (User)result ;
  						
  						// update user picture 
  						 UserUpdate userUpdateJob = new UserUpdate(
  								 loginedUser.getId(),
  								 loginedUser.getUserToken(),
  				        		 loginedUser.getName(), 
  				        		 loginedUser.getEmail(),
  				        		  "",
  				        		  new File("D:\\picExample.jpg").getAbsolutePath(), 
  				        		  UpdateOption.Picture);
  				    	  taskManager.commitJob(userUpdateJob, new ResultCallback() {
  				  			@Override
  				  			public void notifyResult(Object result) {
  				  				// TODO Auto-generated method stub
  				  				if(result != null)
  				  				{
  				  					if(result instanceof User)
  				  						loginedUser  = (User)result ;
  				  					
  				  					System.out.println(result);
  				  				}
  				  			}
  				  		});
  				    	  
  				    	// update user name 
   						 UserUpdate userUpdateName = new UserUpdate(
   								 loginedUser.getId(),
   								 loginedUser.getUserToken(),
   				        		 "joegana", 
   				        		 loginedUser.getEmail(),
   				        		  "",
   				        		  new File("D:\\picExample.jpg").getAbsolutePath(), 
   				        		  UpdateOption.Name);
   				    	  taskManager.commitJob(userUpdateName, new ResultCallback() {
   				  			@Override
   				  			public void notifyResult(Object result) {
   				  				// TODO Auto-generated method stub
   				  				if(result != null)
   				  				{
   				  					if(result instanceof User)
   				  						loginedUser  = (User)result ;
   				  					
   				  					System.out.println(result);
   				  				}
   				  			}
   				  		});
   				    // update password 
   				    	UserUpdate userUpdatePassword = new UserUpdate(
  								 loginedUser.getId(),
  								 loginedUser.getUserToken(),
  				        		 "joegana", 
  				        		 loginedUser.getEmail(),
  				        		  "123456",
  				        		  new File("D:\\picExample.jpg").getAbsolutePath(), 
  				        		  UpdateOption.Password);
   				    	
  				    	  taskManager.commitJob(userUpdatePassword, new ResultCallback() {
  				  			@Override
  				  			public void notifyResult(Object result) {
  				  				// TODO Auto-generated method stub
  				  				if(result != null)
  				  				{
  				  					if(result instanceof User)
  				  						loginedUser  = (User)result ;
  				  					
  				  					System.out.println(result);
  				  				}
  				  			}
  				  		});
  					}
  					
  					// create score 
  					UserScoreCreate  createUserScoreJob = new UserScoreCreate(
  							loginedUser.getUserToken(),
  							loginedUser.getEmail(), loginedUser.getId(), 89.9);
  					 taskManager.commitJob(createUserScoreJob, new ResultCallback() {
				  			@Override
				  			public void notifyResult(Object result) {
				  				// TODO Auto-generated method stub
				  				if(result != null)
				  				{
				  					if(result instanceof List)
				  						System.out.println(result);			  					
				  				}
				  			}
				  		});
  					// get user score list 
  					 UserScoreListGet  getUserScoreListJob = new UserScoreListGet(
  							loginedUser.getUserToken(),
  							loginedUser.getEmail()
  							);
  					 taskManager.commitJob(getUserScoreListJob, new ResultCallback() {
				  			@Override
				  			public void notifyResult(Object result) {
				  				// TODO Auto-generated method stub
				  				if(result != null)
				  				{
				  					if(result instanceof List)
				  						System.out.println(result);			  					
				  				}
				  			}
				  		});
  					 
  					 
  					System.out.println(result);
  					
  				   //  down load questions 
  			         QuestionDownload  questionDownJob = new QuestionDownload(loginedUser.getEmail(),
  			        		 loginedUser.getUserToken(),
  			        		 QuestionDownload.QUESTION_LAG_ZH,
  			        		 "pending","rotor_craft_personal");
  			         taskManager.commitJob(questionDownJob, new ResultCallback() {

  						@Override
  						public void notifyResult(Object result) {
  							// TODO Auto-generated method stub
  							if(result != null &&(result instanceof List))
  							{
  								System.out.println(result.toString());
  							}
  						}
  			        	 
  			         });
  			         
  				}
  			}
  		});
    	  
       
         // get version info 
         VersionInfo  versionInfoJob = new VersionInfo();
         taskManager.commitJob(versionInfoJob, new ResultCallback() {

			@Override
			public void notifyResult(Object result) {
				// TODO Auto-generated method stub
				if(result != null &&(result instanceof String))
				{
					System.out.println(result);
				}
			}
        	 
         });
         
         
         
//        try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        //update user 
//         UserUpdate userUpdateJob = new UserUpdate(loginedUser.getUserToken(),
//        		  loginedUser.getName(), 
//        		  loginedUser.getEmail(),
//        		  "",
//        		  new File("D:\\’≈æ≤‘∑ .png").getAbsolutePath(), 
//        		  UpdateOption.Name);
//    	  taskManager.commitJob(userUpdateJob, new ResultCallback() {
//  			@Override
//  			public void notifyResult(Object result) {
//  				// TODO Auto-generated method stub
//  				if(result != null)
//  				{
//  					if(result instanceof User)
//  						loginedUser  = (User)result ;
//  					
//  					System.out.println(result);
//  				}
//  			}
//  		});
    	  
    	//taskManager.shutDownTask();
//          int a = 201 ;
//    	  System.out.println(a/100);
      }
}
