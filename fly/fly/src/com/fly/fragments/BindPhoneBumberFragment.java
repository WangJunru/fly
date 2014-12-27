package com.fly.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.CheckCode;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.User;
import com.fly.sdk.job.CellNumberUpdate;
import com.fly.sdk.job.CheckCodeGet;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.sdk.util.TextUtils;
import com.fly.util.Tools;

public class BindPhoneBumberFragment extends BaseFramgment {
	
	private View backView ;
	private Button getCheckCode ;
	private Button bindButton ;
	private EditText phonenumberEd ;
	private EditText checkCodeEd ;
	
	private String   cellNumberValue ;
	private String   checkCodeNumber ;
	private String   batchCodeValue ;
	
	private CellNumberUpdate cellUpdate ;
	private CheckCodeGet getCode ;
	
	private int getCodeTimeOut = 60 ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		attachedActivity = (FragmentActivity) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view  = inflater.inflate(R.layout.binding_phone_fragment, container,false);
		initView(view);
		return view ;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void  initView(View v)
	{
		TextView title = (TextView)v.findViewById(R.id.title);
		title.setText(R.string.bind_phone_title);
		
	    backView = v.findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		
		View sharedView = v.findViewById(R.id.share_img);
		sharedView.setVisibility(View.INVISIBLE);
		
		phonenumberEd = (EditText)v.findViewById(R.id.phone_number_et);
		phonenumberEd.requestFocus();
		
		checkCodeEd   = (EditText)v.findViewById(R.id.check_code_ed);
		

		getCheckCode = (Button)v.findViewById(R.id.code_check_bt);
		getCheckCode.setOnClickListener(this);
		
		bindButton   = (Button)v.findViewById(R.id.phone_number_bind);
		bindButton.setOnClickListener(this);
	}
	
	public void clickView(View v)
	{
		switch(v.getId())
		{
		   case R.id.back_img:
	      {
		    FragmentTransaction   ftr = attachedActivity.getSupportFragmentManager().beginTransaction();
			UserCenterFragment  userInfo = new UserCenterFragment();
			ftr.replace(R.id.main_content, userInfo);
			ftr.commit();
		   } break;
		   case R.id.code_check_bt:
		   {
			   cellNumberValue = phonenumberEd.getText().toString();
			   checkCodeNumber = checkCodeEd.getText().toString();
			   if(Tools.isMobileNO(cellNumberValue))
			   {
				   doSendCodeJob(cellNumberValue);
				   
				   getCheckCode.setEnabled(false);
				   getCheckCode.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						getCodeTimeOut -- ;
						getCheckCode.setText(getString(R.string.second,getCodeTimeOut));
						if(getCodeTimeOut == 0)
						{
							getCheckCode.setEnabled(true);
							getCheckCode.setText(R.string.code_check);
						}else
						{
							getCheckCode.postDelayed(this, 1000);
						}
					}
				}, 1000);
			   }else
			   {
				   Toast.makeText(attachedActivity, R.string.invalide_number,
							Toast.LENGTH_SHORT).show();
			   }
		   }break;
		   case R.id.phone_number_bind:
		   {   
			   checkCodeNumber = checkCodeEd.getText().toString();
			   cellNumberValue = phonenumberEd.getText().toString();
			   if(!Tools.isMobileNO(cellNumberValue))
			   {
				   Toast.makeText(attachedActivity, R.string.invalide_number,
							Toast.LENGTH_SHORT).show();
				   return ;
			   }
			   if(TextUtils.isEmpty(checkCodeNumber))
			   {
				   Toast.makeText(attachedActivity, R.string.input_6_check,
							Toast.LENGTH_SHORT).show();
				   return ;
			   }
			   if(TextUtils.isEmpty(batchCodeValue))
			   {
				   Toast.makeText(attachedActivity, R.string.get_check_code,
							Toast.LENGTH_SHORT).show();
			   }
			   bindingCellNumber(cellNumberValue,checkCodeNumber,batchCodeValue);
		   }break;	
		}
	}

	private Handler uiHandler = new Handler()
	{
		  public void handleMessage(android.os.Message msg) {
			    switch(msg.what)
			    {
			       case 0:
			       {
			    	    User user =  FlyApplication.getLoginUser();
			    	    if(user != null)
			    	    {
			    	    	user.setCellNumber(cellNumberValue);
			    	    }
			    	   Toast.makeText(attachedActivity, R.string.bind_success_ok,
								Toast.LENGTH_SHORT).show();
			    	   clickView(backView);
			       }
			    	break;
			      case 1:
			    	  Toast.makeText(attachedActivity, R.string.network_io_error,
								Toast.LENGTH_SHORT).show();
			    	break;
			      case 2:
			    	  Toast.makeText(attachedActivity, R.string.request_timeout,
								Toast.LENGTH_SHORT).show();
			    	  break;
			      case 4:
			      {
			    	  getCodeTimeOut-- ;
			    	  getCheckCode.setText(getString(R.string.second,getCodeTimeOut));
			    	  if(getCodeTimeOut == 0)
			    	  {
			    		  getCheckCode.setEnabled(true);
			    		  getCheckCode.setText(R.string.code_check);
			    	  }else
			    	  {
			    		  this.sendEmptyMessageDelayed(4, 1000);
			    	  }
			      }break;
			      case 5:
			      {  
			    	  Toast.makeText(attachedActivity, R.string.get_code_fail,
								Toast.LENGTH_SHORT).show();
			      }  break;
			      case 6:
			      {
			    	  Toast.makeText(attachedActivity, R.string.check_code_fail,
								Toast.LENGTH_SHORT).show();
			      }
			    	  break;
			    }
		  }; 
	};
	
	public void onDestroyView() {
		super.onDestroyView();
		taskManager.cancelTask(getCode);
		taskManager.cancelTask(cellUpdate);
		uiHandler = null ;
	};
	
	private void doSendCodeJob(String cellNumber)
	{
		User user = FlyApplication.getLoginUser();
    	if(user != null)
    	{
    	    getCode = new CheckCodeGet(cellNumber,user.getId());
    		taskManager.commitJob(getCode, new ResultCallback() {
				@Override
				public void notifyResult(Object result) {
					// TODO Auto-generated method stub
					 if(result != null)
					 {
						 if(result instanceof CheckCode)
						 {
							 CheckCode checkCode = (CheckCode)result ;
							 String bCode =  checkCode.getBatchCode();
							 if(TextUtils.isEmpty(bCode))
							 {
								 if(checkCode.getErrorCode() == CheckCode.GET_CODE_FAILED)
								 {
									 uiHandler.sendEmptyMessage(5);
								 }else if(checkCode.getErrorCode() == CheckCode.WRONG_CHEDK_CODE)
								 {
									 uiHandler.sendEmptyMessage(6);
								 }
							 }else{
							    batchCodeValue  =  checkCode.getBatchCode();
							    cellNumberValue = checkCode.getCellNumber();
							 }
						 }else
						 {
							 if(result instanceof ErrorMsg)
							 {
								  ErrorMsg error = (ErrorMsg)result ;
								  switch(error.getErrorCode())
								  {
								     case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
								    	 uiHandler.obtainMessage(1).sendToTarget();
								    	 break;
								     case ErrorMsg.ERROR_NETWORK_IO_ERROR:
								    	 uiHandler.obtainMessage(2).sendToTarget();
								    	 break;
								  }
							 }
						 }
					 }
				}
			});
    	}
	}
    private void bindingCellNumber(String cellNumber,String code,String batchCode)
    {
    	if(TextUtils.isEmpty(cellNumber) || TextUtils.isEmpty(code) || TextUtils.isEmpty(batchCode))
    	{
    		return ;
    	}
    	
    	User user = FlyApplication.getLoginUser();
    	if(user != null)
    	{
//    		String cell,String batchCode ,String checkCode
    	    cellUpdate = new CellNumberUpdate(user.getId(),cellNumber,batchCode,code);
    		taskManager.commitJob(cellUpdate, new ResultCallback() {	
				@Override
				public void notifyResult(Object result) {
					// TODO Auto-generated method stub
					 if(result != null)
					 {
						 if(result instanceof CheckCode)
						 {
							 CheckCode checkCode = (CheckCode)result ;
							 String number =  checkCode.getCellNumber();
							 if(TextUtils.isEmpty(number))
							 {
								 if(checkCode.getErrorCode() == CheckCode.GET_CODE_FAILED)
								 {
									 uiHandler.sendEmptyMessage(5);
								 }else if(checkCode.getErrorCode() == CheckCode.WRONG_CHEDK_CODE)
								 {
									 uiHandler.sendEmptyMessage(6);
								 }
							 }else{
							    cellNumberValue = checkCode.getCellNumber();
							    uiHandler.sendEmptyMessage(0);
							 }
						 }else
						 {
							 if(result instanceof ErrorMsg)
							 {
								  ErrorMsg error = (ErrorMsg)result ;
								  switch(error.getErrorCode())
								  {
								     case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
								    	 uiHandler.obtainMessage(1).sendToTarget();
								    	 break;
								     case ErrorMsg.ERROR_NETWORK_IO_ERROR:
								    	 uiHandler.obtainMessage(2).sendToTarget();
								    	 break;
								  }
							 }
						 }
					 }
				}
			});
    		
    	}
    }
	
}
