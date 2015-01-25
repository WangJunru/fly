package com.fly.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Score;
import com.fly.sdk.User;
import com.fly.sdk.job.Job;
import com.fly.sdk.job.TopTenScoreListGet;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.dialog.LoadDialog;
import com.fly.ui.view.ScorePaiHangItemView;
import com.fly.ui.view.ScorePaiHangItemView.OnUserPicClickListener;
import com.fly.view.ui.utils.DialogUtils;

public class PaiHangBangActivity extends BaseActivity implements OnUserPicClickListener{
	private LinearLayout phConItem ;
	private TextView  myRankTv ;
	private ArrayList<Score>  scores = new ArrayList<Score>();
	private Job flyTask ;
	private LoadDialog  dlg ;
	private  LinearLayout.LayoutParams itemPara;
	private  String userScoreStr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.paihangbang_layout);
    	initView();
    	initScoreData();
    }
    
    private void initView()
    {
    	phConItem = (LinearLayout)findViewById(R.id.phb_con);
    	TextView  title = (TextView)findViewById(R.id.title);
    	myRankTv = (TextView)findViewById(R.id.my_phb);
    	User user =  FlyApplication.getLoginUser() ;
    	if(user != null)
    	{
    	   userScoreStr  = getString(R.string.my_pm, user.getRank());
    	   myRankTv.setText(userScoreStr);
    	}
    	title.setText(R.string.phb_title);
    	View backView = findViewById(R.id.back_img);
    	backView.setOnClickListener(this);
    	
    	View shareView = findViewById(R.id.share_img);
    	shareView.setOnClickListener(this);
    }
    
    private void initScoreData()
    {
    	if(scores.isEmpty())
    	{
    		flyTask = new TopTenScoreListGet();
    		dlg = new LoadDialog(this).builder();
    		dlg.setMessage("正在请求...");
    		dlg.show();
    		taskManager.commitJob(flyTask, new ResultCallback() {		
				@Override
				public void notifyResult(Object result) {
					// TODO Auto-generated method stub
					if(result != null)
					{
						if(result instanceof List)
						{
							scores.addAll((ArrayList<Score>)result);
							uiHandler.obtainMessage(0).sendToTarget();
						}
					}
				}
			});
    	}else
    	{
    		if(dlg != null)
    		{
    			dlg.dismiss();
    			dlg = null ;
    		}
    		 if(itemPara == null)
 		    {
 		    	itemPara =  new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
 			    		LayoutParams.WRAP_CONTENT);
 				itemPara.topMargin = 40 ; 
 				itemPara.gravity = Gravity.CENTER_HORIZONTAL;
 		    }
    		for(Score score:this.scores)
    		{
    			ScorePaiHangItemView view = new ScorePaiHangItemView(this);
    			view.setUserPicClickListener(this);
    			view.setScore(score);
    			phConItem.addView(view,itemPara);
    		}
    	}
    }
    
    @Override
    protected void handleUIHandlerMessage(Message msg) {
    	// TODO Auto-generated method stub
    	switch(msg.what)
    	{
       	case 0:
       		initScoreData();
    		break;
    	}
    }
    @Override
    protected void clickView(View v) {
    	// TODO Auto-generated method stub
    	switch (v.getId()) {
		case R.id.back_img:
		{
			finish();
		}break;
		case R.id.share_img:
		{
			showShare();
		}break;
		default:
			break;
		}
    }
    private void showShare() {
		Context context = getBaseContext();
		final OnekeyShare oks = new OnekeyShare();
		oks.setTitle(getString(R.string.app_name));
		oks.setTitleUrl("http://www.mfeiji.com/");
	    oks.setText(userScoreStr);

		oks.setViewToShare(phConItem);
	 
		oks.setUrl("http://www.mfeiji.com/");
		oks.setSite(context.getString(R.string.app_name));
		oks.setSiteUrl("http://www.mfeiji.com/");
		oks.setSilent(true);
		oks.setTheme(OnekeyShareTheme.CLASSIC);
    	oks.setDialogMode();
		// 为EditPage设置一个背景的View
		oks.setEditPageBackground(phConItem);
		oks.setInstallUrl("http://www.mfeiji.com/");
		oks.show(context);
	}
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	if(flyTask != null)
 	    {
 	    	if(!flyTask.isComplete())
 	    	   taskManager.cancelTask(flyTask);
 	    }
    }
	@Override
	public void clickUserPic(View v, Score score) {
		// TODO Auto-generated method stub
		
	}
}
