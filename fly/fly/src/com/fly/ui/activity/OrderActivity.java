package com.fly.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fly.R;

public class OrderActivity extends BaseActivity {
	    protected void onCreate(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.notice_layout);
	    	initView();
	    }
	      
	      private void initView()
	      {
	      	TextView title = (TextView)findViewById(R.id.title);
	      	title.setText(R.string.my_order);
	      	View  back = findViewById(R.id.back_img);
	      	back.setOnClickListener(this);
	      	View shareView = findViewById(R.id.share_img);
	      	shareView.setVisibility(View.INVISIBLE);
	      	
	      }
}
