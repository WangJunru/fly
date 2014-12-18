package com.fly;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswdActivity extends BaseActivity implements OnClickListener{
	private EditText  emailEt ;
	private Button    commitBt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.forgot_layout);
    	initView();
    }
    
    private void initView()
    {
    	emailEt = (EditText)findViewById(R.id.user_name_ed);
    	
    	commitBt = (Button)findViewById(R.id.commit_bt);
    	commitBt.setOnClickListener(this);
    	
    	TextView title = (TextView)findViewById(R.id.title);
    	title.setText(R.string.forgot_passwd);
    	
    	View rightView = findViewById(R.id.share_img);
    	rightView.setVisibility(View.INVISIBLE);
    	
    	View backView = findViewById(R.id.back_img);
    	backView.setOnClickListener(this);
    }

	@Override
	public void clickView(View v) {
		// TODO Auto-generated method stub
	
		switch(v.getId())
		{
		  case R.id.back_img:
			  this.finish();
		  case R.id.commit_bt:
			 String email = emailEt.getText().toString();
			break;
		}
	}
    
    
}

