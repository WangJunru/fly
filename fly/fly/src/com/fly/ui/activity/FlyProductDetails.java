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
import android.widget.Toast;

import com.fly.R;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Notice;
import com.fly.sdk.Order;
import com.fly.sdk.Product;
import com.fly.sdk.School;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.job.GetNoticeDetail;
import com.fly.sdk.job.GetProductDetails;
import com.fly.sdk.job.GetSchoolDetails;
import com.fly.sdk.job.Job;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.sdk.util.TextUtils;
import com.fly.ui.dialog.LoadDialog;

public class FlyProductDetails extends BaseActivity {
	
	private TextView comentCountsTv ;
	private TextView moneyNumberTv ;
	private TextView userActionTipTv ;
	private TextView title ;
	
	private View  bottomView ;
	
	private WebView  mWebView ;
	
    private Object product ;
    private Job    flyTask ;
    private LoadDialog dialog ;
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
    	bottomView = findViewById(R.id.bootom_view);
    	comentCountsTv = (TextView)findViewById(R.id.show_comments);
    	moneyNumberTv  = (TextView)findViewById(R.id.money_tv);
    	userActionTipTv =  (TextView)findViewById(R.id.conn_us_tv);
    	comentCountsTv.setOnClickListener(this);
    	userActionTipTv.setOnClickListener(this);
    	View backView = findViewById(R.id.back_img);
    	backView.setOnClickListener(this);
    	View sharedView = findViewById(R.id.share_img);
    	sharedView.setOnClickListener(this);
//    	title = (TextView)findViewById(R.id.title);
    	
    	mWebView       = (WebView)findViewById(R.id.content_web_view);
    	mWebView.getSettings().setDefaultTextEncodingName(SdkConfig.HTTP_ENCODING);
    	
    	if(product != null)
    	{
    		if(product instanceof School)
    		{
    			userActionTipTv.setText(R.string.conn_us);
    			moneyNumberTv.setVisibility(View.GONE);	
    			comentCountsTv.setText(getString(R.string.coment_count,((FlyProduct)product).getCommentCount()));
    		}else if(product instanceof Product)
    		{
    			userActionTipTv.setText(R.string.order_product);
    			moneyNumberTv.setVisibility(View.VISIBLE);
    			DecimalFormat format =new DecimalFormat("0");
    			moneyNumberTv.setText(getString(R.string.money_number, 
    					format.format(((Product) product).getPrice())));
    			comentCountsTv.setText(getString(R.string.coment_count,((FlyProduct)product).getCommentCount()));
    		}else if(product instanceof Order)
    		{
    			comentCountsTv.setVisibility(View.INVISIBLE);
    			bottomView.setVisibility(View.GONE);
    		}else if(product instanceof Notice)
    		{
    			comentCountsTv.setVisibility(View.INVISIBLE);
    			bottomView.setVisibility(View.GONE);
    			sharedView.setVisibility(View.GONE);
//    			title.setText(((Notice)product).getTitle());
    		}
    		
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
	    		  	String telNumber =  ((FlyProduct)product) .getTel();
	    			if(!TextUtils.isEmpty(telNumber))
	    			{
	    				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telNumber));  
	    				startActivity(intent);
	    			}
    	    	}
    	    }
    	      break;
    	    case R.id.show_comments:
    	    {
    	    	Intent intent = new Intent(this,ComentActivity.class);  
    	    	intent.putExtra("product", (FlyProduct)product);
				startActivity(intent);
    	    }
    	    	break;
    	  }
    }
    private void loadFlyProductDetailsContent(Object product)
    {
    	if(product == null)
    		return ;
    	String htmlCode = null ;
    	if(product instanceof School)
		{
    		htmlCode  = ((School)product).getPageHtmlCode();
		}else if(product instanceof Product)
		{
			htmlCode  = ((Product)product).getPageHtmlCode();
		}else if(product instanceof Notice)
		{
			htmlCode  = ((Notice)product).getPageHtmlCode();
		}
    	
		if(!TextUtils.isEmpty(htmlCode))
		{
			mWebView.loadData("<body>"+htmlCode+"</body>", "text/html; charset=UTF-8", null);
		}else
		{
			if(product instanceof School)
    		{
				flyTask = new GetSchoolDetails(((School)product).getId());
    		}else if(product instanceof Product)
    		{
    		   flyTask = new GetProductDetails(((Product)product).getId());
    		}else if(product instanceof Order)
    		{
    			flyTask = new GetProductDetails(((Order)product).getProductId());
    		}else if(product instanceof Notice)
    		{
    			flyTask = new GetNoticeDetail(((Notice)product).getId());
    		}
			if(flyTask != null)
			{
				dialog = new LoadDialog(this).builder().setCancelable(true)
						.setCanceledOnTouchOutside(true).setMessage("���ڼ�������...");
				dialog.show();		
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
				}else if (result instanceof ErrorMsg) {
					ErrorMsg error = (ErrorMsg) result;
					switch (error.getErrorCode()) {
					case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
						uiHandler.sendEmptyMessage(1);
						break;
					case ErrorMsg.ERROR_NETWORK_IO_ERROR:
						uiHandler.sendEmptyMessage(2);
						break;
					}
				}
			}
		}
	}

    @Override
    protected void handleUIHandlerMessage(Message msg) {
    	// TODO Auto-generated method stub
        if(dialog != null)
    	     dialog.dismiss();
    	switch(msg.what)
    	{
     	  case 0:
     	  {
     		  Object obj = msg.obj;
     		  if(obj != null )
     		  {
     			  product = obj;
     			  loadFlyProductDetailsContent(product);
     		  }
     	  }break;
     	 case 1:
 			Toast.makeText(this, R.string.request_timeout, Toast.LENGTH_SHORT)
 					.show();
 			break;
 		case 2: {
 			Toast.makeText(this, R.string.network_io_error, Toast.LENGTH_SHORT)
 					.show();
 		}	
    	}
    }
    
    private void initData()
    {
    	Intent intent = getIntent();
    	product = intent.getSerializableExtra("product");
    }  
}
