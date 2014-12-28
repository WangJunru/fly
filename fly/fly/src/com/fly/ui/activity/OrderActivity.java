package com.fly.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fly.R;
import com.fly.fragments.MyListView;
import com.fly.ui.view.BshElasticView;
import com.fly.ui.view.BshElasticView.IRefresh;

public class OrderActivity extends BaseActivity {
	
	private  BshElasticView  strechView ;
	private   MyListView   listView ;
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
	      	
	      	strechView = ( BshElasticView ) findViewById( R.id.strech_view);
	      	strechView.setFactor( 2 );
	      	strechView.setMaxElastic( 0.9f );
	      	
	      	listView = new MyListView(this);
	      	listView.setBackgroundColor(getResources().getColor(R.color.white));
	      	listView.setDividerHeight(1);
	      	listView.setDivider(new ColorDrawable(getResources().getColor(R.color.product_details_text_color)));
	      	listView.setFadingEdgeLength(0);
	      	listView.setScrollbarFadingEnabled(true);
	      	listView.setScrollingCacheEnabled(true);
	      	strechView.setScrollOverable(listView);
	      	
	      	strechView.setIRefresh(new IRefresh() {

				@Override
				public boolean refreshTop()
				{
					new Thread( new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Log.d( "bsh", "refreshing" );
								
								Thread.sleep( 3000 );

							} catch ( InterruptedException e )
							{
								e.printStackTrace();
							}
							strechView.onRefreshComplete();

						}
					} ).start();
					return false;
				}

				@Override
				public boolean refreshBtm()
				{
					new Thread( new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								Log.d( "bsh", "refreshing" );
								Thread.sleep( 3000 );
							} catch ( InterruptedException e )
							{
								e.printStackTrace();
							}
							strechView.onRefreshComplete();
						}
					} ).start();
					return false;
				}
			
			});
	      	
	      }
	      
	      public void clickView(View v)
	      {
	    	  switch(v.getId())
	    	  {
	    	    case R.id.back_img:
	    	    {
	    	    	finish();
	    	    }
	    		  break;
	    	    case R.id.share_img:
	    	    {
	    	    	
	    	    }break;
	    	  }
	      }
}
