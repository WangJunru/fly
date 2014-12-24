package com.fly.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.Score;
import com.fly.sdk.User;
import com.fly.sdk.job.Job;
import com.fly.sdk.job.TopTenScoreListGet;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.view.ScorePaiHangItemView;
import com.fly.ui.view.ScorePaiHangItemView.OnUserPicClickListener;

public class PaiHangBangActivity extends BaseActivity implements OnUserPicClickListener{
	private LinearLayout phConItem ;
	private TextView  myRankTv ;
	private ArrayList<Score>  scores = new ArrayList<Score>();
	private Job flyTask ;
	
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
    	   myRankTv.setText(getString(R.string.my_pm, user.getRank()));
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
    		for(Score score:this.scores)
    		{
    			ScorePaiHangItemView view = new ScorePaiHangItemView(this);
    			view.setUserPicClickListener(this);
    		    LinearLayout.LayoutParams para =  new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
    		    		LayoutParams.WRAP_CONTENT);
    		    para.topMargin = 40 ; 
    		    para.gravity = Gravity.CENTER_HORIZONTAL;
    			view.setScore(score);
    			phConItem.addView(view,para);
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
			
		}break;
		default:
			break;
		}
    }

	@Override
	public void clickUserPic(View v, Score score) {
		// TODO Auto-generated method stub
		
	}
}
