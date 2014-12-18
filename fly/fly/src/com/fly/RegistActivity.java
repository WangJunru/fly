package com.fly;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.sdk.ErrorMsg;
import com.fly.sdk.User;
import com.fly.sdk.job.UserRegist;
import com.fly.sdk.threading.FlyTaskManager;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;

public class RegistActivity extends BaseActivity implements OnClickListener {

	private EditText userNameEd;
	private EditText emailEd;
	private EditText passwdEd;

	private boolean isPropAgree;
	private UserRegist registTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist_layout);
		initView();
	}

	private void initView() {
		userNameEd = (EditText) findViewById(R.id.user_name_et);
		emailEd = (EditText) findViewById(R.id.email_et);
		passwdEd = (EditText) findViewById(R.id.passwd_et);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.user_regist);

		View view = findViewById(R.id.share_img);
		view.setVisibility(View.INVISIBLE);

		View back = findViewById(R.id.back_img);
		back.setOnClickListener(this);

		View registBt = findViewById(R.id.regist_bt);
		registBt.setOnClickListener(this);

		CheckBox checkBox = (CheckBox) findViewById(R.id.agree_check);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				isPropAgree = isChecked;
			}
		});
	}

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_img: {
			finish();
		}
			break;
		case R.id.regist_bt: {
			String userName = userNameEd.getText().toString(), email = emailEd
					.getText().toString(), passwd = passwdEd.getText()
					.toString();

			if (userName.length() < 6) {
				Toast.makeText(this, R.string.user_name_too_short,
						Toast.LENGTH_SHORT).show();
				return;
			}
			// String pattern1 =
			// "^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$";
			// Pattern pattern = Pattern.compile(pattern1);
			// Matcher mat = pattern.matcher(email);
			// if(!mat.find())
			// {
			// Toast.makeText(this, R.string.email_illegal,
			// Toast.LENGTH_SHORT).show();
			// return ;
			// }
			if (passwd.length() < 6) {
				Toast.makeText(this, R.string.passwd_too_short,
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (taskManager != null) {
				registTask = new UserRegist(userName, email, passwd);
				taskManager
						.commitJob(registTask, new UserRegistResultCapture());
			}
		}
			break;
		}
	}

	@Override
	public void handleUIHandlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case 0: {
			Toast.makeText(RegistActivity.this, R.string.regist_success,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case 1: // user name or passwd wrong
		{
			Toast.makeText(RegistActivity.this, R.string.name_exeist,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case 2: // network error
		{
			Toast.makeText(RegistActivity.this, R.string.network_io_error,
					Toast.LENGTH_SHORT).show();
		}
			break;
		}

	}

	private final class UserRegistResultCapture implements ResultCallback {

		@Override
		public void notifyResult(Object result) {
			// TODO Auto-generated method stub
			if (result != null) {
				if (result instanceof User) {
					Message.obtain(uiHandler, 0, result).sendToTarget();
				} else if (result instanceof ErrorMsg) {
					ErrorMsg error = registTask.getError();
					if (registTask != null && error != null) {
						switch (error.getErrorCode()) {
						case ErrorMsg.ERROR_USER_NAME_EXEIST: {
							uiHandler.obtainMessage(1).sendToTarget();
						}
							break;
						case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
							uiHandler.obtainMessage(2).sendToTarget();
						}
							break;
						}
					}
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
