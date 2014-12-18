package com.fly.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fly.R;

public class QuizFragment1 extends BaseFramgment{
	
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
		 View view  = inflater.inflate(R.layout.mine_quiz_1page, container, false);
		 initView(view);
		 return  view ;
	}
	
	private void initView(View rootView)
	{
		View backView = rootView.findViewById(R.id.back_img);
		backView.setVisibility(View.INVISIBLE);
		
		View sharedView = rootView.findViewById(R.id.share_img);
		sharedView.setVisibility(View.INVISIBLE);
		
		TextView  title = (TextView)rootView.findViewById(R.id.title);
		title.setText(R.string.quiz_title);
		
		View  xyView = rootView.findViewById(R.id.xy_plane_con);
		xyView.setOnClickListener(this);
		
		View gdyView = rootView.findViewById(R.id.gdy_plane_con);
		gdyView.setOnClickListener(this);
	}

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		 	
		  case R.id.xy_plane_con:
		  {
			 FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction();
			 
			 QuizFragment2 f2 = new QuizFragment2();
			 Bundle dt = new Bundle();
			 dt.putChar("jx", 'x');
			 f2.setArguments(dt);	 
			 tras.replace(R.id.main_content, f2);
//			 tras.addToBackStack(null);
			 tras.commit();
		  }
			break;
		  case R.id.gdy_plane_con:
		  {
			     FragmentTransaction tras =  attachedActivity.getSupportFragmentManager().beginTransaction(); 
			     QuizFragment2 f2 = new QuizFragment2();
				 Bundle dt = new Bundle();
				 dt.putChar("jx", 'g');
				 f2.setArguments(dt);	 
				 tras.replace(R.id.main_content, f2);
//				 tras.addToBackStack(null);
				 tras.commit();
		  }
			break;
		}
		
	}
	
	
}
