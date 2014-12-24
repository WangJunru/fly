package com.fly.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fly.R;
import com.fly.fragments.CheckQuizItemResult;
import com.fly.fragments.CheckQuizResult;
import com.fly.sdk.Question;
import com.fly.ui.view.CheckQuizResultView;

public class ResultActivity extends BaseActivity{
	
	private TextView ksInfoResult ;
	private String   userName ;
	private ArrayList<Question>  questions = new ArrayList<Question>();
	private double   score ;
	private long     usedTimeWithMinutes ;
	private long     usedTimeWithSeconds ;
	
	private FrameLayout totalView ;
	private FragmentManager  fragmentManager ;

	
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.result_ks);
    	 fragmentManager =  getSupportFragmentManager();
    	 initData();
    	 initView();
    }
    
    private void initData()
    {
    	Intent intent = getIntent();
    	userName = intent.getStringExtra("user_name");
    	score    = intent.getDoubleExtra("score", 0);
    	usedTimeWithMinutes = intent.getLongExtra("minutes", 0);
    	usedTimeWithSeconds = intent.getLongExtra("seconds", 0);
    	questions.addAll((ArrayList<Question>) intent.getSerializableExtra("qts"));
    	
    }
    private void initView()
    {
    	View exit = findViewById(R.id.back_img);
    	exit.setOnClickListener(this);
    	
    	View sharedView = findViewById(R.id.share_img);
    	sharedView.setOnClickListener(this);
    	
    	View chaKanKsResult = findViewById(R.id.chaKan_result_iv);
    	chaKanKsResult.setOnClickListener(this);
    	
    	View paiHangB = findViewById(R.id.paihang_iv);
    	paiHangB.setOnClickListener(this);
    	 
    	totalView = (FrameLayout)findViewById(R.id.total_view);
    	
    	ksInfoResult = (TextView)findViewById(R.id.test_result);
    	ksInfoResult.setText(getString(score > 90.0f ?R.string.ks_result_info_sccess:R.string.ks_result_info_fail, userName,score,usedTimeWithMinutes,usedTimeWithSeconds));
    	
    }
    
    public void  clickView(View v)
    {
    	
    	switch(v.getId())
    	{
    	   case R.id.back_img:
			  this.finish();
    		break;
    	  case R.id.share_img:
    		break;
    	  case R.id.chaKan_result_iv:
    	  {
    		  FragmentTransaction   ftr = fragmentManager.beginTransaction();
  			  CheckQuizResult  page = new CheckQuizResult(this.questions);
  			  ftr.replace(R.id.total_view, page);
  			  ftr.commit();
    	  }
    		break;
    	  case R.id.paihang_iv:
    	  {
		    Intent intent = new Intent(this, PaiHangBangActivity.class);
		    startActivity(intent);
    	  }
    		break;
    	}
    }
} 
