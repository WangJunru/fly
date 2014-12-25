package com.fly.sdk.threading;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.job.Job;
import com.fly.sdk.util.Log;

public class FlyTaskManager {
      private static FlyTaskManager instance = null ;
     
      private  HashMap<Job, Future<Object>> taskBool = new HashMap<Job, Future<Object>>();
      private ExecutorService  excutors ;
      private FlyTaskManager(){
    	  excutors = Executors.newFixedThreadPool(3);
      }
      
      public static FlyTaskManager  getInstance()
      {
    	  if(instance == null)
    	  {
    		  synchronized(FlyTaskManager.class)
    		  {
    			  if(instance == null)
    			  {
    				  instance = new FlyTaskManager(); 
    			  }
    		  }
    	  }
    	  return instance ;
      }
      
      public void commitJob(Job job,ResultCallback callback)
      {
    	 
    	 Future<Object> futures =  excutors.submit(job); 
    	 taskBool.put(job, futures);
    	 excutors.execute(new CaptureResultJob(job,futures,callback));
      }
      
      public void shutDownTask()
      {
    	  excutors.shutdownNow() ;
      }
      
      public void cancelTask(Job job)
      {
    	 if(job == null)
    		 return ;
    	 
    	 Future<Object>  task = taskBool.get(job);
    	 if(task != null)
    	 {
    		 task.cancel(true);
    	 }
    	 taskBool.remove(job);
    	 job.setComplete(true);
      }
      
      private class CaptureResultJob implements Runnable
      {
    	  private ResultCallback callBack ;
    	  private Future<Object> taskResult;
    	  private Job  job ;
    	  public CaptureResultJob(Job job ,Future<Object> task,ResultCallback callback)
    	  {
    		  this.job = job ;
    		  this.callBack = callback ;
    		  this.taskResult = task ;
    	  }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Object result  = null ;
			try {
				// µÈ´ý 30 Ãë
			    result = taskResult.get(30, TimeUnit.SECONDS);
			    result = (result == null) ?job.getError():result;
			    if(callBack != null)
			    {
			       callBack.notifyResult(result);
			       this.job.setComplete(true);
			       taskBool.remove(this.job);
			    }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				Log.log(e.toString());
				ErrorMsg errorMsg = job.getError();
				errorMsg.setErrorCode(ErrorMsg.ERROR_EXECUTE_TIMEOUT);
			    if(callBack != null)
			    {
			       callBack.notifyResult(errorMsg);
			    }
			}catch (Exception e) {
				// TODO: handle exception
				Log.log(e.toString());
			}
		}
      }
      
      public interface ResultCallback
      {
    	  void notifyResult(Object result);
      } 
}
