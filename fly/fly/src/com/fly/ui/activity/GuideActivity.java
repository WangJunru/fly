package com.fly.ui.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.User;
import com.fly.sdk.job.UserLogin;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.sdk.util.TextUtils;
import com.fly.util.Tools;

public class GuideActivity extends BaseActivity {

	private ViewPager guideViewPager;

	private ViewPagerAdapter guideViewAdapter;

	private ArrayList<View> mViews;

	private final int images[] = { R.drawable.guide1_02, R.drawable.guide2_02,
			R.drawable.guide3_02 };

	private ImageView[] guideDots;

	private int currentIndex;

	private ImageButton startBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SharedPreferences sharepref = getPreferences(Context.MODE_PRIVATE);
		boolean firstRun = sharepref.getBoolean("first_run", true);
		SharedPreferences shareedPref = getSharedPreferences("user.bin", Context.MODE_PRIVATE);
		String userName = shareedPref.getString("user_name", "");
		String userPasswd = shareedPref.getString("user_passwd", "");

		if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPasswd)) {
			userLogin(userName,userPasswd);
		}

		if (firstRun == true) {
			setContentView(R.layout.gude_activity_layout);

			initView();

			initDot();
			Editor edit = sharepref.edit();
			edit.putBoolean("first_run", false);
			edit.commit();
		} else {
			ImageView view = new ImageView(this);
			view.setBackgroundResource(R.drawable.guide4_02);
			setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			view.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(GuideActivity.this,
							MainActivity.class);
					startActivity(intent);
					GuideActivity.this.finish();
				}
			}, 2000);
		}

	}

	private void userLogin(String userName, String userPasswd) {
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPasswd)) {
			return;
		}
		UserLogin userLogin = new UserLogin(userName, userName, userPasswd);
		taskManager.commitJob(userLogin, new ResultCallback() {
			@Override
			public void notifyResult(Object result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result instanceof User) {
						User user = (User) result;
						FlyApplication.setLoginedUser(user);
						URL urls;
						try {
							String url  =  user.getUserPictureUri();
							Bitmap pic  = null ;
							if(url != null)
							{
								try {
									urls = new URL(url);
									
									URLConnection urlConn = urls.openConnection();
								    pic = BitmapFactory.decodeStream(urlConn
											.getInputStream());
								} catch (Exception e) {
									// TODO: handle exception
								}
					      	}
							if(pic != null)
							{
							   BitmapDrawable drawable = new BitmapDrawable(pic);
							   FlyApplication.setLogindUserPic(drawable);	
							}else
							{
							   FlyApplication.setLogindUserPic(Tools.getDefaultUserPic(GuideActivity.this));	
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			}
		});

	}

	public void clickView(View v) {
		switch (v.getId()) {
		case R.id.start_bt: {
			Intent intent = new Intent(GuideActivity.this, MainActivity.class);
			startActivity(intent);
			GuideActivity.this.finish();
		}
			break;
		}
	}

	// ≥ı ºªØ“≥√Ê
	private void initView() {
		guideViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
		mViews = new ArrayList<View>();

		for (int i = 0; i < images.length; i++) {

			ImageView iv = new ImageView(GuideActivity.this);
			iv.setBackgroundResource(images[i]);

			mViews.add(iv);
		}

		View view = LayoutInflater.from(GuideActivity.this).inflate(
				R.layout.guide4, null);
		mViews.add(view);

		startBtn = (ImageButton) view.findViewById(R.id.start_bt);

		guideViewAdapter = new ViewPagerAdapter(mViews);

		guideViewPager.setAdapter(guideViewAdapter);

		guideViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		startBtn.setOnClickListener(this);
	}

	private void initDot() {

		LinearLayout layout = (LinearLayout) findViewById(R.id.guide_dots);

		guideDots = new ImageView[mViews.size()];

		for (int i = 0; i < mViews.size(); i++) {
			guideDots[i] = (ImageView) layout.getChildAt(i);
			guideDots[i].setSelected(false);
		}

		currentIndex = 0;
		guideDots[currentIndex].setSelected(true);
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > mViews.size() - 1
				|| currentIndex == position) {
			return;
		}

		guideDots[position].setSelected(true);
		guideDots[currentIndex].setSelected(false);

		currentIndex = position;
	}
}
