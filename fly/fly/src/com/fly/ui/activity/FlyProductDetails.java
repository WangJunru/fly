package com.fly.ui.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Product;
import com.fly.sdk.School;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.job.GetProductDetails;
import com.fly.sdk.job.GetSchoolDetails;
import com.fly.sdk.job.Job;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.sdk.util.TextUtils;

public class FlyProductDetails extends BaseActivity {
	
	private TextView comentCountsTv ;
	private TextView moneyNumberTv ;
	private TextView userActionTipTv ;
	
	private WebView  mWebView ;
	
    private FlyProduct product ;
    private Job    flyTask ;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.fly_product_details_layout);
        initData();
        intiView();
    }
    
     
    private void intiView()
    {
    	comentCountsTv = (TextView)findViewById(R.id.show_comments);
    	moneyNumberTv  = (TextView)findViewById(R.id.money_tv);
    	userActionTipTv =  (TextView)findViewById(R.id.conn_us_tv);
    	comentCountsTv.setOnClickListener(this);
    	userActionTipTv.setOnClickListener(this);
    	View backView = findViewById(R.id.back_img);
    	backView.setOnClickListener(this);
    	View sharedView = findViewById(R.id.share_img);
    	sharedView.setOnClickListener(this);
    	
    	mWebView       = (WebView)findViewById(R.id.content_web_view);
    	mWebView.getSettings().setDefaultTextEncodingName(SdkConfig.HTTP_ENCODING);
    	
    	if(product != null)
    	{
    		if(product instanceof School)
    		{
    			userActionTipTv.setText(R.string.conn_us);
    			moneyNumberTv.setVisibility(View.GONE);		
    		}else if(product instanceof Product)
    		{
    			userActionTipTv.setText(R.string.order_product);
    			moneyNumberTv.setVisibility(View.VISIBLE);
    			DecimalFormat format =new DecimalFormat("0");
    			moneyNumberTv.setText(getString(R.string.money_number, 
    					format.format(((Product) product).getPrice())));
    		}
    		comentCountsTv.setText(getString(R.string.coment_count,product.getCommentCount()));
    		loadFlyProductDetailsContent(product);
    	}
    }
    
    public void clickView(View v)
    {
    	  switch(v.getId())
    	  {
    	    case R.id.back_img:
    	    {
    	    	this.finish();
    	    }break;
    	    case R.id.share_img:
    	    {
    	    	
    	    }break;
    	    case R.id.conn_us_tv:
    	    {
    	    	if(product != null)
    	    	{
	    		  	String telNumber =  product.getTel();
	    			if(!TextUtils.isEmpty(telNumber))
	    			{
	    				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telNumber));  
	    				startActivity(intent);
	    			}
    	    	}
    	    }
    	      break;
    	  }
    }
    private void loadFlyProductDetailsContent(FlyProduct product)
    {
    	if(product == null)
    		return ;
    	String htmlCode = product.getPageHtmlCode();
		if(!TextUtils.isEmpty(htmlCode))
		{
			mWebView.loadData("<body>"+htmlCode+"</body>", "text/html; charset=UTF-8", null);
		}else
		{
			if(product instanceof School)
    		{
				flyTask = new GetSchoolDetails(product.getId());
    		}else if(product instanceof Product)
    		{
    		   flyTask = new GetProductDetails(product.getId());
    		}
			if(flyTask != null)
			{
				taskManager.commitJob(flyTask, new ProductDetailResultCapture());
			}
		}
    }
    
    
    private final class ProductDetailResultCapture implements ResultCallback {

		@Override
		public void notifyResult(Object result) {
			// TODO Auto-generated method stub
			if (result != null) {
				if(result instanceof List)
				{
					List list = (List)result;
					if(list.size() > 0)
					{
					  uiHandler.obtainMessage(0, list.get(0)).sendToTarget();
					}
				}
			}
		}
	}

    @Override
    protected void handleUIHandlerMessage(Message msg) {
    	// TODO Auto-generated method stub
    	switch(msg.what)
    	{
     	  case 0:
     	  {
     		  Object obj = msg.obj;
     		  if(obj != null && obj instanceof FlyProduct)
     		  {
     			  product = (FlyProduct)obj;
     			  loadFlyProductDetailsContent(product);
     		  }
     	  }break;
    		
    	}
    }
    
    private void initData()
    {
    	Intent intent = getIntent();
    	product = (FlyProduct) intent.getSerializableExtra("product");
    }  
}
