package com.fly;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.User;
import com.fly.sdk.job.UserLogin;
import com.fly.sdk.threading.FlyTaskManager;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;

public class LoginDialog extends Dialog implements OnClickListener, OnGlobalLayoutListener {
	
	 private View  closeBt ;
	 private EditText   userNameEd ;
	 private EditText   passwdEd ;
	 
	 private View  loginBt ;
	 private View    forgotPasswd ;
	 private View    wantedRegist ;
	 
	 private View    loginWithWeiXin ;
	 private View    loginWithWeibo ;
	 
	 private FlyTaskManager  taskManager ;
	 private UserLogin       userLoginTask ;
	 
	 private RelativeLayout  relaGroupView;
	
     protected LoginDialog(Context context) {
		super(context);
		setCancelable(true);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().setBackgroundDrawable(null);
		setContentView(R.layout.login);
		
		taskManager = FlyApplication.getFlyTaskManager();
		
		initView();
	}
     
    private Handler handler = new Handler()
    {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			loginBt.setEnabled(true);
			switch(msg.what)
			{
			case 0:  // login success
				Toast.makeText(LoginDialog.this.getContext(),"登陆成功" , Toast.LENGTH_SHORT).show();
			    dismiss();
				break;
			case 1:  // user name or passwd  wrong 
				Toast.makeText(LoginDialog.this.getContext(),"用户名或者密码错误" , Toast.LENGTH_SHORT).show();
				break;
			case 2:  // network error
				Toast.makeText(LoginDialog.this.getContext(),"网络异常" , Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
   	
    };
    
    private final class  LoginResultCapture  implements  ResultCallback
    {
    	
		@Override
		public void notifyResult(Object result) {
			// TODO Auto-generated method stub
			if(result != null)
			{
				if(result instanceof User)
				{
				   FlyApplication.setLoginedUser((User)result);
				   Message.obtain(handler, 0, result).sendToTarget();
				}else if(result instanceof ErrorMsg)
				{
					ErrorMsg error = userLoginTask.getError() ;
					if(userLoginTask != null && error != null)
					{					
						switch(error.getErrorCode())
						{
					  	  case  ErrorMsg.ERROR_USER_NAME_PASSWD_FAIL:
					  	  {
					  		handler.obtainMessage(1).sendToTarget();
					  	  }
							break;
					  	  case  ErrorMsg.ERROR_NETWORK_IO_ERROR:
					  	  {
					  		handler.obtainMessage(2).sendToTarget();
					  	  }
					  		break;
						}
					}
				}
			}
		}
    }

    private  void  initView()
    {
    	closeBt =  findViewById(R.id.dlg_close);
    	
    	userNameEd = (EditText)findViewById(R.id.user_name_ed);
    	userNameEd.requestFocus();
    	
    	passwdEd   = (EditText)findViewById(R.id.user_passwd_ed);
    	
    	loginBt    =   findViewById(R.id.login_bt);
    	forgotPasswd =  findViewById(R.id.forgot_passwd);
    	wantedRegist =  findViewById(R.id.login_regist);
    	
    	loginWithWeiXin = findViewById(R.id.weixin_login_bt);
    	loginWithWeibo  = findViewById(R.id.weibo_login_bt);
    	
    	closeBt.setOnClickListener(this);
    	loginBt.setOnClickListener(this);
    	forgotPasswd.setOnClickListener(this);
    	loginWithWeiXin.setOnClickListener(this);
    	loginWithWeibo.setOnClickListener(this);
    	
        relaGroupView = (RelativeLayout)findViewById(R.id.app_root_view);
        relaGroupView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		  case R.id.dlg_close:
		  {
			  
		  }
			   cancel();
			break;
		  case R.id.login_bt:
		  {
			  String name = userNameEd.getText().toString();
			  if(TextUtils.isEmpty(name))
			  {
				  userNameEd.requestFocus();
				  Toast.makeText(this.getContext(), "用户名忘记输入了", Toast.LENGTH_SHORT).show();
				  return ;
			  }
			  
			  String  passwd = passwdEd.getText().toString();
			  if(taskManager != null)
			  {
				 userLoginTask = new UserLogin(name, name, passwd);
				 
				 taskManager.commitJob(userLoginTask, new LoginResultCapture());
				 loginBt.setEnabled(false);
			  }
		  }
			break;
		  case R.id.forgot_passwd:
			break;
		  case R.id.login_regist:
			break;
		  case R.id.weixin_login_bt:
			break;
		  case R.id.weibo_login_bt:
			break;
		}
	}

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		RelativeLayout.LayoutParams  lapara =  (LayoutParams) forgotPasswd.getLayoutParams();
		lapara.leftMargin = loginBt.getRight() - forgotPasswd.getWidth();
		forgotPasswd.setLayoutParams(lapara);
		
		RelativeLayout.LayoutParams  registLogin = (LayoutParams) wantedRegist.getLayoutParams();
		registLogin.topMargin = (loginWithWeiXin.getTop() - forgotPasswd.getBottom() - wantedRegist.getHeight())/2 - wantedRegist.getPaddingTop() ;
		wantedRegist.setLayoutParams(registLogin);
		
		loginBt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		handler = null ;
	}
}
