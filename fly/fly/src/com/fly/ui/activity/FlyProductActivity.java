package com.fly.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.R;
import com.fly.fragments.BaseFramgment;
import com.fly.fragments.ProductListFragment;
import com.fly.fragments.SchoolListFragment;

public class FlyProductActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_holder_layout);
		FragmentManager fgm = getSupportFragmentManager();
		
		Intent intent = getIntent();
		char type = intent.getCharExtra("product_type", 's');
		
		Bundle bundle = new Bundle();
		bundle.putBoolean("new_activity", true);
		BaseFramgment f =  null ;
		
		if('s' == type)
		{
			f = new SchoolListFragment(null, null);
		}else
		{
			f = new ProductListFragment(null, null);
		}
		
		f.setArguments(bundle);
		fgm.beginTransaction().replace(R.id.main_content, f).commit();
	}

}
