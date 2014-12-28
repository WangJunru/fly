package com.fly.fragments;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.User;
import com.fly.sdk.job.UserLogout;
import com.fly.sdk.job.UserUpdate;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.activity.LoginActivity;
import com.fly.ui.activity.MainActivity;
import com.fly.ui.activity.NoticeActivity;
import com.fly.ui.activity.OrderActivity;
import com.fly.ui.activity.PaiHangBangActivity;
import com.fly.ui.activity.Version;
import com.fly.ui.dialog.ActionSheetDialog;
import com.fly.ui.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.fly.ui.dialog.ActionSheetDialog.SheetItemColor;
import com.fly.ui.dialog.AlertDialog;
import com.fly.ui.dialog.CitySelectDialog;
import com.fly.ui.dialog.LoadDialog;
import com.fly.ui.view.UserInfoItemView;
import com.fly.view.ui.utils.DataUtils;

public class UserCenterFragment extends BaseFramgment implements CropHandler{
	private TextView userNameTv;
	private TextView userMsgCount;
	private ImageView userPicIv;

	private UserInfoItemView userNameView;
	private UserInfoItemView userEmailView;

	private UserInfoItemView userSexView;
	private UserInfoItemView userAreaView;
	private UserInfoItemView userTelView;

	private UserInfoItemView userBestCjView;
	private View loginOut  ;
	private LoadDialog loadDig ;
	private CropParams mCropParams = new CropParams();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.my_center_layout, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateView(FlyApplication.getLoginUser(), FlyApplication.getLoginedUserPic());
	}

	private void initView(View rootView) {
		View backView = rootView.findViewById(R.id.back_img);
		backView.setVisibility(View.INVISIBLE);

		View shareView = rootView.findViewById(R.id.share_img);
		shareView.setVisibility(View.INVISIBLE);

		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(R.string.user_my_center);

		userNameTv = (TextView) rootView.findViewById(R.id.user_name_tx);

		userPicIv = (ImageView) rootView.findViewById(R.id.user_picture_round);
		userPicIv.setOnClickListener(this);

		userMsgCount = (TextView) rootView.findViewById(R.id.user_msg_count);

		userNameView = (UserInfoItemView) rootView
				.findViewById(R.id.user_name_info);
		userNameView.setOnClickListener(this);

		userEmailView = (UserInfoItemView) rootView
				.findViewById(R.id.user_email_info);
		userEmailView.setOnClickListener(this);

		userSexView = (UserInfoItemView) rootView
				.findViewById(R.id.user_sex_info);
		userSexView.setOnClickListener(this);

		userAreaView = (UserInfoItemView) rootView
				.findViewById(R.id.user_erea_info);
		userAreaView.setOnClickListener(this);

		userTelView = (UserInfoItemView) rootView
				.findViewById(R.id.user_tel_info);
		userTelView.setOnClickListener(this);

		userBestCjView = (UserInfoItemView) rootView
				.findViewById(R.id.user_best_cj);
		userBestCjView.setOnClickListener(this);

		View myDingDan = rootView.findViewById(R.id.user_jd_info);
		myDingDan.setOnClickListener(this);

		View securitySet = rootView.findViewById(R.id.user_safe_info);
		securitySet.setOnClickListener(this);

		View noticeView = rootView.findViewById(R.id.user_notice_info);
		noticeView.setOnClickListener(this);

		View aboutUs = rootView.findViewById(R.id.user_about_info);
		aboutUs.setOnClickListener(this);

		
	    loginOut = rootView.findViewById(R.id.user_login_out);	
		loginOut.setOnClickListener(this);
	}

	private void updateView(User user, Drawable userPic) {
		if (user != null) {
			userNameTv.setText(user.getName());
			userNameView.setValueText(user.getName());
			userEmailView.setValueText(user.getEmail());

			// if(user.getGender().equals(UserUpdate.GENDER_MALE))
			// {
			// userSexView.setValueText(R.string.male);
			// }else if(user.getGender().equals(UserUpdate.GENDER_FEMALE))
			// {
			// userSexView.setValueText(R.string.female);
			// }
			userSexView.setValueText(user.getGender());
			userAreaView.setValueText(user.getAddress());
			userTelView.setValueText(user.getCellNumber());
			userBestCjView.setValueText(getString(R.string.score_str_phb,
					user.getBestScore()));
			loginOut.setVisibility(View.VISIBLE);
		}else
		{
			loginOut.setVisibility(View.GONE);
		}

		if (userPic != null) {
			userPicIv.setImageDrawable(userPic);
		}
	}
	
	
	
	
	Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:  // 注销
			{
				if(loadDig != null)
				{
					loadDig.dismiss();
				}
				FragmentTransaction   ftr = attachedActivity.getSupportFragmentManager().beginTransaction();
				MainFragment  mainPage = new MainFragment();
				ftr.replace(R.id.main_content, mainPage, "main");
				mainPage.setFourRoundListener((MainActivity)attachedActivity);
				ftr.commit();
				DataUtils.saveLoginedUserInfo(attachedActivity,"","");
			    Intent logIntent = new Intent(attachedActivity,LoginActivity.class);
		        startActivity(logIntent);
		        ((MainActivity)attachedActivity).setBottomViewSrc(R.id.sy);
		        FlyApplication.setLogindUserPic(null);
		        FlyApplication.setLoginedUser(null);
			}break;
			case 1: // 更新
			{
				if(loadDig != null)
				{
					loadDig.dismiss();
				}
				updateView(FlyApplication.getLoginUser(), FlyApplication.getLoginedUserPic());
			}break;
			case 2: //
			{
				if (loadDig != null) {
					loadDig.dismiss();
				}
				Toast.makeText(attachedActivity, R.string.network_io_error,
						Toast.LENGTH_SHORT).show();
			}break;
			case 3:
			{
				if (loadDig != null) {
					loadDig.dismiss();
				}
				Toast.makeText(attachedActivity, R.string.request_timeout,
						Toast.LENGTH_SHORT).show();
			}break;
			default:
				break;
			}
		}
	};
	
	public void onStop() {
		super.onStop();
		
	};
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		uiHandler = null ;
	}

	public void clickView(View v) {
		switch (v.getId()) {
		case R.id.user_picture_round:
		{
			ActionSheetDialog dialog = new ActionSheetDialog(attachedActivity).builder()
					.setTitle("选择图片来源")
					.setCancelable(true)
					.setCanceledOnTouchOutside(true)
					.addSheetItem("从相册中选择", SheetItemColor.Blue, new OnSheetItemClickListener() {		
						@Override
						public void onClick(int which) {
							// TODO Auto-generated method stub
							 startActivityForResult(CropHelper.buildCropFromGalleryIntent(mCropParams), CropHelper.REQUEST_CROP); 
						}
					}).addSheetItem("拍照", SheetItemColor.Blue, new OnSheetItemClickListener() {	
						@Override
						public void onClick(int which) {
							// TODO Auto-generated method stub
							 Intent intent = CropHelper.buildCaptureIntent(mCropParams.uri);
				             startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
						}
					});
			dialog.show();
		}break;
		case R.id.user_sex_info:
		{
			ActionSheetDialog dialog = new ActionSheetDialog(attachedActivity).builder()
					.setTitle("选择性别")
					.setCancelable(true)
					.setCanceledOnTouchOutside(true)
					.addSheetItem("男", SheetItemColor.Blue, new OnSheetItemClickListener() {
						
						@Override
						public void onClick(int which) {
							// TODO Auto-generated method stub
							updateUserInfo("", "", "男", "");
						}
					}).addSheetItem("女", SheetItemColor.Blue, new OnSheetItemClickListener() {
						
						@Override
						public void onClick(int which) {
							// TODO Auto-generated method stub
							updateUserInfo("", "", "女", "");
						}
					});
			dialog.show();
		}break;
		case R.id.user_erea_info:
		{
			final CitySelectDialog  cityDlg = new CitySelectDialog(attachedActivity).builder();
			cityDlg.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					updateUserInfo("", "", "", cityDlg.getAreaString());
				}
			}).setAreaString(userAreaView.getValueText());
			
			cityDlg.show();
		}break;
		case R.id.user_best_cj:
		{
			Intent intent = new Intent(attachedActivity,PaiHangBangActivity.class);
			startActivity(intent);
		}break;
		case R.id.user_jd_info:// 订单信息
		{
			Intent intent = new Intent(attachedActivity, OrderActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.user_tel_info:
		{
		    FragmentTransaction   ftr = attachedActivity.getSupportFragmentManager().beginTransaction();
	    	BindPhoneBumberFragment  bind = new BindPhoneBumberFragment();
		    ftr.replace(R.id.main_content, bind);
		    ftr.commit();
		} break;
		case R.id.user_safe_info:// 重新设置密码
		{
			 FragmentTransaction   ftr = attachedActivity.getSupportFragmentManager().beginTransaction();
	    	 ChangePasswd  change = new ChangePasswd();
		     ftr.replace(R.id.main_content, change);
		     ftr.commit();
		}
			break;
		case R.id.user_notice_info:// 用户通知
		{
			Intent intent = new Intent(attachedActivity, NoticeActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.user_about_info:// 版本信息
		{
			Intent intent = new Intent(attachedActivity, Version.class);
			startActivity(intent);
		}
			break;
		case R.id.user_login_out:// 用户注销
		{
			AlertDialog alert = new AlertDialog(attachedActivity).builder()
					.setTitle("注销").setMsg("确认注销当前用户吗？")
					.setPositiveButton("注销", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub\
							User user = FlyApplication.getLoginUser();
							showLoadDlg("正在退出登录...");
							if (user != null) {
								final UserLogout logout = new UserLogout(user
										.getUserToken(), user.getEmail());
								taskManager.commitJob(logout,
										new ResultCallback() {

											@Override
											public void notifyResult(
													Object result) {
												// TODO Auto-generated method
												// stub
												if (result != null)
												{
													if (result instanceof Integer) {
														int retCode = (Integer) result;
														if (retCode / 100 == 2) {
															uiHandler.sendEmptyMessage(0);
														}
												    }else if(result instanceof ErrorMsg)
												    {
												    	ErrorMsg error = logout.getError();
														if (logout != null && error != null) {
															switch (error.getErrorCode()) {
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
										});
							}
						}
					}).setNegativeButton("取消", null);
			alert.show();

		}
			break;
		}
	}

	private void showLoadDlg(String str)
	{
		   if(loadDig == null )
		   {
		      loadDig = new LoadDialog(
					attachedActivity).builder();
			  loadDig.setMessage(str);
		   }else
		   {
			  loadDig.setMessage(str);
		   }
		   loadDig.show();
	}


	@Override
	public CropParams getCropParams() {
		// TODO Auto-generated method stub
		return mCropParams;
	}

	@Override
	public void onCropCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCropFailed(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void updateUserInfo(String pictureUri,String  tel, String gender, String address)
	{
		 User user = FlyApplication.getLoginUser();
		 if(user != null)
		 {
		  final UserUpdate update = new UserUpdate(user.getId(),user.getUserToken(), user.getName(),user.getEmail(), 
						"", pictureUri, tel, gender, address);
		  showLoadDlg("正在提交更新...");
		  taskManager.commitJob(update, new ResultCallback() {
					@Override
					public void notifyResult(Object result) {
						// TODO Auto-generated method stub
						if (result != null) {
							if (result instanceof User) {
								User user = (User) result;
								User before = FlyApplication.getLoginUser() ;
								user.setRank(before.getRank());
								user.setBestScore(before.getBestScore());
								user.setRole(before.getRole());
								FlyApplication.setLoginedUser(user);
								URL urls;
								try {
									urls = new URL(user.getUserPictureUri());

									URLConnection urlConn = urls.openConnection();
									Bitmap pic = BitmapFactory.decodeStream(urlConn
											.getInputStream());
									BitmapDrawable drawable = new BitmapDrawable(pic);
									FlyApplication.setLogindUserPic(drawable);	
									if (uiHandler != null)
										uiHandler.obtainMessage(1).sendToTarget();
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}else  if (result instanceof ErrorMsg)
							{
								ErrorMsg error = update.getError();
								if (update != null && error != null) {
									switch (error.getErrorCode()) {
									case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
										if (uiHandler != null)
											uiHandler.obtainMessage(2).sendToTarget();
									}
									case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
									{
										if (uiHandler != null)
											uiHandler.obtainMessage(3).sendToTarget();
									}
										break;
									}
								}
							
							}
						}
					}
				});
		 }
	}
	
	@Override
	public void onPhotoCropped(Uri arg0) {
		// TODO Auto-generated method stub
		 updateUserInfo(mCropParams.uri.getPath(), "", "", "");
//		 Bitmap bitmap = CropHelper.decodeUriAsBitmap(attachedActivity, mCropParams.uri);
//		 FlyApplication.setLogindUserPic(new BitmapDrawable(bitmap));
//		 userPicIv.setImageDrawable();
	}
	
     @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	CropHelper.handleResult(this, requestCode, resultCode, data);
    }

	@Override
	public void startActivityForCrop(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		startActivityForResult(intent, requestCode);
	}

}
