package com.fly.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.User;
import com.fly.sdk.job.UpdatePassword;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.activity.LoginActivity;
import com.fly.ui.dialog.LoadDialog;

public class ChangePasswd extends BaseFramgment {
	
	private EditText oldPassword  ;
	private EditText newPasswd ;
	private Button   changePasswd ;
	private String oldPasswd ;
	
	private LoadDialog loadDlg ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	attachedActivity = (FragmentActivity)activity ;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	return inflater.inflate(R.layout.change_passwd_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	initView(view);
    }
    
    private void  initView(View v)
	{
		TextView title = (TextView)v.findViewById(R.id.title);
		title.setText(R.string.change_passwd_title);
		
	    View backView = v.findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		
		View sharedView = v.findViewById(R.id.share_img);
		sharedView.setVisibility(View.INVISIBLE);
		
		oldPassword = (EditText)v.findViewById(R.id.phone_number_et);
		oldPassword.requestFocus();
		
		newPasswd   = (EditText)v.findViewById(R.id.check_code_ed);

		changePasswd   = (Button)v.findViewById(R.id.phone_number_bind);
		changePasswd.setOnClickListener(this);
		
		SharedPreferences sharedPasswd = attachedActivity.getSharedPreferences("user.bin", Context.MODE_PRIVATE);
		oldPasswd = sharedPasswd.getString("user_passwd", "");
	}
    
    @Override
    protected void clickView(View v) {
    	// TODO Auto-generated method stub
    	switch(v.getId())
    	{
    	  case R.id.back_img:
    	  {
    	    FragmentTransaction   ftr = attachedActivity.getSupportFragmentManager().beginTransaction();
  			UserCenterFragment  userInfo = new UserCenterFragment();
  			ftr.replace(R.id.main_content, userInfo);
  			ftr.commit();
    	  }
    		break;
    	  case R.id.phone_number_bind:
    	  {
    		  String oldpasswd = oldPassword.getText().toString();
    		  String newpasswd = newPasswd.getText().toString();
    		  
    		  if(!oldpasswd.equals(this.oldPasswd))
    		  {
    			  Toast.makeText(attachedActivity, R.string.old_passwd_wrong_tip, Toast.LENGTH_SHORT).show();
    			  return ;
    		  }
    		  if(newpasswd.length() < 6)
    		  {
    			  Toast.makeText(attachedActivity, R.string.passwd_too_short,
  						Toast.LENGTH_SHORT).show();
  				return;
    		  }
    		  loadDlg = new LoadDialog(attachedActivity).builder().setCancelable(false)
    				  .setCanceledOnTouchOutside(false)
    				  .setMessage("ÇëÉÔºó...");
    		  loadDlg.show();	  
    		  doChangePasswd(oldpasswd,newpasswd);  
    	  }
    		  break;
    	}
    }
    
    private Handler uiHandler = new Handler()
    {
    	public void handleMessage(Message msg) {
    		switch(msg.what)
    		{
    		  case 0: // success
    		  {
    			  if (loadDlg != null) {
  				    loadDlg.dismiss();
  				 }
    			  
  				 Toast.makeText(attachedActivity, R.string.change_success,
  						Toast.LENGTH_SHORT).show();
  				 
  				 FragmentTransaction   ftr = attachedActivity.getSupportFragmentManager().beginTransaction();
  				 UserCenterFragment  userInfo = new UserCenterFragment();
  				 ftr.replace(R.id.main_content, userInfo);
  				 ftr.commit();
  				 
  				 Intent intent = new Intent(attachedActivity,LoginActivity.class);
  				 startActivity(intent);
    		  }
    			break;
    		  case 2:
    		  {
			     if (loadDlg != null) {
				    loadDlg.dismiss();
				 }
				 Toast.makeText(attachedActivity, R.string.network_io_error,
						Toast.LENGTH_SHORT).show();
    		  }
    			  break;
    		  case 3:
				   if (loadDlg != null) {
					  loadDlg.dismiss();
					}
					Toast.makeText(attachedActivity, R.string.network_io_error,
							Toast.LENGTH_SHORT).show();
    		     break;
    		}
    	};
    };
    private void doChangePasswd(String oldPass,String newPass)
    {
    	User user = FlyApplication.getLoginUser();
    	if(user != null)
    	{
    		 final UpdatePassword changePas = new UpdatePassword(user.getId(), oldPass, newPass, newPass);
    		 taskManager.commitJob(changePas, new ResultCallback() {	
				@Override
				public void notifyResult(Object result) {
					// TODO Auto-generated method stub

					if (result instanceof User) {
						FlyApplication.setLogindUserPic(null);
						FlyApplication.setLoginedUser(null);
					    Message.obtain(uiHandler, 0, result).sendToTarget();
					} else if (result instanceof ErrorMsg) {
						ErrorMsg error = changePas.getError();
						if (changePas != null && error != null) {
							switch (error.getErrorCode()) {
							case ErrorMsg.ERROR_USER_NAME_PASSWD_FAIL: {
								if (uiHandler != null)
									uiHandler.obtainMessage(1).sendToTarget();
							}
								break;
							case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
								if (uiHandler != null)
									uiHandler.obtainMessage(2).sendToTarget();
							}
								break;
							case  ErrorMsg.ERROR_EXECUTE_TIMEOUT:
							{
								if (uiHandler != null)
									uiHandler.obtainMessage(4).sendToTarget();
							}break;
							}
						}
					}
				
				}
			});
    	}
    }
    
	
}
