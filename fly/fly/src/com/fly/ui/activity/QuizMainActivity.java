package com.fly.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.R;
import com.fly.fragments.BaseFramgment;
import com.fly.fragments.QuizFragment1;
import com.fly.fragments.QuizFragment2;

public class QuizMainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_holder_layout);
		
		Intent intent = getIntent();
		char jx = intent.getCharExtra("jx", 'x');	
		FragmentManager fgm = getSupportFragmentManager();
		BaseFramgment f = null ;
		if('x' == jx)
		{
			 f = new QuizFragment2();
			 Bundle dt = new Bundle();
			 dt.putBoolean("new_activity", true);
			 dt.putChar("jx", 'x');
			 f.setArguments(dt);	 
		}else if('g' == jx)
		{
			 f = new QuizFragment2();
			 Bundle dt = new Bundle();
			 dt.putBoolean("new_activity", true);
			 dt.putChar("jx", 'g');
			 f.setArguments(dt);	 
		}else
		{
			 f= new QuizFragment1();
			 Bundle bundle = new Bundle();
			 bundle.putBoolean("new_activity", true);
			 f.setArguments(bundle);
		}
		fgm.beginTransaction()
				.replace(R.id.main_content, f)
				.commit();
	}
}
