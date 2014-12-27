package com.fly.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.R;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.job.ResetPassword;
import com.fly.sdk.test.Tools;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.dialog.AlertDialog;
import com.fly.ui.dialog.LoadDialog;

public class ForgotPasswdActivity extends BaseActivity implements OnClickListener{
	private EditText  emailEt ;
	private Button    commitBt ;
	private LoadDialog loadDlg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.forgot_layout);
    	initView();
    }
    
    private void initView()
    {
    	emailEt = (EditText)findViewById(R.id.user_name_ed);
    	
    	commitBt = (Button)findViewById(R.id.commit_bt);
    	commitBt.setOnClickListener(this);
    	
    	TextView title = (TextView)findViewById(R.id.title);
    	title.setText(R.string.forgot_passwd);
    	
    	View rightView = findViewById(R.id.share_img);
    	rightView.setVisibility(View.INVISIBLE);
    	
    	View backView = findViewById(R.id.back_img);
    	backView.setOnClickListener(this);
    }
    
    @Override
    protected void handleUIHandlerMessage(Message msg) {
    	// TODO Auto-generated method stub
    	if(loadDlg != null)
		{
			loadDlg.dismiss();
		}
    	switch(msg.what)
    	{
	    	case 0:
	    		 AlertDialog alert = new AlertDialog(this).builder()
	    		.setCancelable(false)
	    		.setCanceledOnTouchOutside(false)
	    		.setPositiveButton("确定", null)
	    		.setMsg("密码重置成功。\n请登录您的邮箱按照提示重设密码。");
	    		
	    		alert.show();
	    		break;
	    	case 1:
	    		Toast.makeText(this, R.string.request_timeout,
						Toast.LENGTH_SHORT).show();
	    		break;
	    	case 2:
	    		Toast.makeText(this, R.string.network_io_error,
						Toast.LENGTH_SHORT).show();
	    		break;
	    	case 3:
	    		Toast.makeText(this, R.string.network_io_error,
						Toast.LENGTH_SHORT).show();
	    		break;
    	}
    }
    
    

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
	
		switch(v.getId())
		{
		  case R.id.back_img:
			  this.finish();
		  case R.id.commit_bt:
			 String email = emailEt.getText().toString();
			 if(Tools.isEmail(email))
			 {
				 ResetPassword  resetppasswd = new ResetPassword(email);
				 loadDlg = new LoadDialog(this).builder()
						 .setMessage("正在处理您的请求...");
				 loadDlg.show();
				 taskManager.commitJob(resetppasswd, new ResultCallback() {
					@Override
					public void notifyResult(Object result) {
						// TODO Auto-generated method stub
						if(result != null)
						{
							if(result instanceof String)
							{
								String ret = (String)result;
								if(ret.equals("ok"))
								{
									uiHandler.sendEmptyMessage(0);
								}
							}else if(result instanceof ErrorMsg)
							{
								ErrorMsg msg = (ErrorMsg)result ;
								switch(msg.getErrorCode())
								{
								  case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
									  uiHandler.sendEmptyMessage(1);
									break;
								  case  ErrorMsg.ERROR_NETWORK_IO_ERROR:
									  uiHandler.sendEmptyMessage(2);
									  break;
								  case ErrorMsg.ERROR_SERVER_ERROR_HAPPENED:
									  uiHandler.sendEmptyMessage(3);
									  break;
								}
							}
						}
					}
				});
			 }else
			 {
				 Toast.makeText(this, R.string.email_illegal,
				 Toast.LENGTH_SHORT).show();
				 return ;
			 }
			break;
		}
	}
    
    
}

