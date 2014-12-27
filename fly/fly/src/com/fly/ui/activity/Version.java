package com.fly.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.job.Job;
import com.fly.sdk.job.VersionInfo;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;

public class Version extends BaseActivity {
	
	private TextView versionInfo ;
	private ImageView mfjIv ;
	private String versonInfo = "2.0";
	
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.about);
    	initView();
    	updateVersonInfo();
    }
     
    private void initView()
    {
    	versionInfo = (TextView)findViewById(R.id.version_info);
    	mfjIv       = (ImageView)findViewById(R.id.mfj_icon);
    	mfjIv.setOnClickListener(this);
    	
    	TextView title = (TextView)findViewById(R.id.title);
    	title.setText(R.string.about_us);
    	View  back = findViewById(R.id.back_img);
    	back.setOnClickListener(this);
    	View shareView = findViewById(R.id.share_img);
    	shareView.setVisibility(View.INVISIBLE);
    	versionInfo.setText(getString(R.string.about_us_info,versonInfo));
    	updateVersonInfo();
    }
    
    public void handleUIHandlerMessage(Message msg)
    {
    	switch(msg.what)
    	{
    	  case 0:
    	  {
    		  versionInfo.setText(getString(R.string.about_us_info,versonInfo));
    	  }break;
    		
    	}
    }
    
    private void updateVersonInfo()
    {
    	Job job = new VersionInfo();
    	taskManager.commitJob(job, new ResultCallback() {
			
			@Override
			public void notifyResult(Object result) {
				// TODO Auto-generated method stub
				if(result != null)
				{
					if(result instanceof String)
					{
						versonInfo  = (String)result;
						uiHandler.sendEmptyMessage(0);
					}
				}
			}
		});
    }
    
    public void clickView(View v)
    {
    	 switch(v.getId())
    	 {
    	   case R.id.back_img:
    		   finish();
    		 break;
    	 }
    }
    
}
