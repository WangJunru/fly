package com.fly;

import java.net.URL;
import java.net.URLConnection;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.debug.Debug.log;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.User;
import com.fly.sdk.job.UserLogin;
import com.fly.sdk.threading.FlyTaskManager;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;

public class LoginActivity extends BaseActivity {

	private ImageView backBt;
	private ImageView closeBt;
	private EditText userNameEd;
	private EditText passwdEd;

	private View loginBt;
	private View forgotPasswd;
	private View wantedRegist;

	private View loginWithWeiXin;
	private View loginWithWeibo;

	private Dialog loginMaskDialog;
	private UserLogin userLoginTask;

	private Thread loadUserPicThread;

	@Override
	public void handleUIHandlerMessage(Message msg) {
		// TODO Auto-generated method stub
		loginBt.setEnabled(true);
		switch (msg.what) {
		case 0: // login success
		{
			Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case 1: // user name or passwd wrong
		{
			if (loginMaskDialog != null) {
				loginMaskDialog.dismiss();
			}
			Toast.makeText(LoginActivity.this, R.string.name_pass_fail,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case 2: // network error
		{
			if (loginMaskDialog != null) {
				loginMaskDialog.dismiss();
			}
			Toast.makeText(LoginActivity.this, R.string.network_io_error,
					Toast.LENGTH_SHORT).show();
		}
			break;
		case 3: {
			Object obj = msg.obj;
			if (obj != null && obj instanceof Bitmap) {
				BitmapDrawable drawable = new BitmapDrawable((Bitmap) obj);
				FlyApplication.setLogindUserPic(drawable);
			}
			if (loginMaskDialog != null) {
				loginMaskDialog.dismiss();
			}
			setResult(RESULT_OK);
			LoginActivity.this.finish();
		}
			break;
		}
	}

	private final class LoginResultCapture implements ResultCallback {

		@Override
		public void notifyResult(Object result) {
			// TODO Auto-generated method stub
			if (result != null) {
				if (result instanceof User) {
					User user = (User) result;
					FlyApplication.setLoginedUser(user);
					loadUserPic(user.getUserPictureUri());
					if (uiHandler != null)
						Message.obtain(uiHandler, 0, result).sendToTarget();
				} else if (result instanceof ErrorMsg) {
					ErrorMsg error = userLoginTask.getError();
					if (userLoginTask != null && error != null) {
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
						}
					}
				}
			}
		}
	}

	private void loadUserPic(final String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		if (loadUserPicThread == null) {
			loadUserPicThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						URL urls = new URL(url);
						URLConnection urlConn = urls.openConnection();
						Bitmap pic = BitmapFactory.decodeStream(urlConn
								.getInputStream());
						Message.obtain(uiHandler, 3, pic).sendToTarget();
					} catch (Exception e) {
						// TODO: handle exception
						log.e("load_pic", e.toString());
					}
				}
			});
		}
		loadUserPicThread.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		initView();	
	}

	private void initView() {
		backBt = (ImageView) findViewById(R.id.back_img);
		backBt.setOnClickListener(this);

		closeBt = (ImageView) findViewById(R.id.share_img);
		closeBt.setOnClickListener(this);

		closeBt.setImageResource(R.drawable.top_close_selector);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.user_login);

		loginBt = findViewById(R.id.user_login);
		loginBt.setOnClickListener(this);

		userNameEd = (EditText) findViewById(R.id.user_name_ed);
		passwdEd = (EditText) findViewById(R.id.user_passwd_ed);

		forgotPasswd = findViewById(R.id.forgot_passwd);
		forgotPasswd.setOnClickListener(this);
		wantedRegist = findViewById(R.id.regist);
		wantedRegist.setOnClickListener(this);

		loginWithWeiXin = findViewById(R.id.wx_login);
		loginWithWeiXin.setOnClickListener(this);

		loginWithWeibo = findViewById(R.id.wb_login);
		loginWithWeibo.setOnClickListener(this);

	}

	private void showLoginDialog() {
		if (loginMaskDialog == null) {
			loginMaskDialog = new Dialog(this);
			loginMaskDialog.setCancelable(false);
			loginMaskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			loginMaskDialog.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			loginMaskDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			loginMaskDialog.setContentView(R.layout.login_tip_dialog_layout);
			loginMaskDialog.show();

		} else {
			loginMaskDialog.show();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_img:
		case R.id.share_img:
			finish();
			break;
		case R.id.user_login: {
			String name = userNameEd.getText().toString();
			if (TextUtils.isEmpty(name)) {
				userNameEd.requestFocus();
				Toast.makeText(this, "用户名忘记输入了", Toast.LENGTH_SHORT).show();
				return;
			}

			String passwd = passwdEd.getText().toString();
			if (taskManager != null) {
				userLoginTask = new UserLogin(name, name, passwd);

				taskManager.commitJob(userLoginTask, new LoginResultCapture());
				loginBt.setEnabled(false);
			}

			showLoginDialog();

		}
			break;
		case R.id.forgot_passwd: {
			Intent intent = new Intent(this, ForgotPasswdActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.regist: {
			Intent intent = new Intent(this, RegistActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.wx_login:
			break;
		case R.id.wb_login:
			break;

		}
	}
}
