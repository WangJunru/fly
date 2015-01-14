package com.fly.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Product;
import com.fly.sdk.ProductBanner;
import com.fly.sdk.School;
import com.fly.sdk.SchoolPanner;
import com.fly.sdk.job.GetProductList;
import com.fly.sdk.job.GetSchoolList;
import com.fly.sdk.job.Job;
import com.fly.sdk.threading.FlyTaskManager;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.activity.FlyProductDetails;
import com.fly.ui.dialog.LoadDialog;
import com.fly.ui.view.NetImageView;
import com.fly.ui.view.SlidesView;
import com.fly.ui.view.SpannerView;
import com.fly.util.DataCatcheTools;
import com.fly.util.Debug;

public class ProductListFragment extends BaseFramgment implements OnItemClickListener, OnRefreshListener, OnScrollListener{
	
	
	private View fView ;
    private FragmentActivity attachedActivity ;
	private SwipeRefreshLayout mSwipeLayout;
	
    private SlidesView    slidesView ;
	private ListView      productsList ;
	
	private ArrayList<Product>  products = new ArrayList<Product>();
	private ArrayList<ProductBanner> productspanner = new ArrayList<ProductBanner>();
	
	private int spannViewWidth , spannViewHeight ;
	private int productViewWidth ,productViewHeight ;
	private Job taskJob ;
	private  int  currentPage = 1 ;
	
	private LoadDialog loadDig ;
	
	public ProductListFragment(ArrayList<Product>  products,ArrayList<ProductBanner> panners)
	{
		if(products != null && !products.isEmpty())
		{
			this.products.addAll(products);
		}
		
		if(panners != null &&!panners.isEmpty())
		{
			this.productspanner.addAll(panners);
		}
		
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		attachedActivity = (FragmentActivity)activity;
		loadLocalData();
	}
	private void loadLocalData()
	{
		ArrayList<Product> data = DataCatcheTools.loadProductData(attachedActivity);
		if(data == null || data.isEmpty())
			return ;
		spliteData(data);
	}
	
	private void spliteData(List<Product> data) {
		if(data == null || data.isEmpty())
			return ;
		for(Product shObj : data)
        {
    	  if(shObj instanceof ProductBanner)
    	  {
    		  if(productspanner.contains(shObj))
    		  {
    			  productspanner.remove(shObj);
    			  productspanner.add((ProductBanner)shObj);
    		  }else
    		  {
    			  productspanner.add((ProductBanner)shObj);
    		  }
    	  }else
    	  {
    		  if(products.contains(shObj))
    		  {
    			  products.remove(shObj);
    			  products.add(shObj);
    		  }else
    		  {
    			  products.add(shObj);
    		  }
    		 
    	  }
       }
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.slidesView.onResume();
		loadNetData();
		updateViewData();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.slidesView.onStop();
	    if(taskJob != null)
	    {
	    	if(!taskJob.isComplete())
	    	   taskManager.cancelTask(taskJob);
	    }
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
    	initView(fView);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	fView = inflater.inflate(R.layout.school_list_layout, container, false);
    	return fView;
    } 
    
    private void initView(View rootView)
    {
    	Options opt = new Options();
    	opt.inJustDecodeBounds  = false ;
    	Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.product_slide_0, opt);
    	this.spannViewWidth = b.getWidth() ;
    	this.spannViewHeight = b.getHeight();
    	b.recycle();
    	
    	this.spannViewWidth = getResources().getDisplayMetrics().widthPixels;
    	this.spannViewHeight = (int) (((float)getResources().getDisplayMetrics().widthPixels / (float)opt.outWidth)*opt.outHeight);
    	
    	opt.inJustDecodeBounds  = false ;
        b = BitmapFactory.decodeResource(getResources(), R.drawable.product_default_image, opt);
    	this.productViewWidth  =  b.getWidth();
    	this.productViewHeight =  b.getHeight();
    	b.recycle();
    	
    	Bundle bundle = getArguments();
    	boolean activity = bundle != null? bundle.getBoolean("new_activity"):false;
    	View backView = rootView.findViewById(R.id.back_img);
    	if(activity)
    	{
    	  backView.setVisibility(View.VISIBLE);
    	  backView.setOnClickListener(this);
    	}else
    	{
    	   backView.setVisibility(View.INVISIBLE);
    	}
    	
    	View shareView = rootView.findViewById(R.id.share_img);
    	shareView.setVisibility(View.INVISIBLE);
    	
    	TextView  title = (TextView)rootView.findViewById(R.id.title);
    	title.setText(R.string.fxzl_title);
    	
//    	productSlides = (ImageSwitcher)rootView.findViewById(R.id.top_zhanshi_iv);
//    	slideTitle    = (TextSwitcher)rootView.findViewById(R.id.first_news_info_tv);
    	mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.id_swipe_ly);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.holo_green_dark,R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);
		
    	slidesView =    (SlidesView)rootView.findViewById(R.id.slide_view);
//    	for(ProductBanner panner : productspanner)
//    	{
//    		SpannerView sView = new SpannerView(getActivity());
//    		sView.setTitleAndImageUrl(panner.getTitle(), panner.getFirstImageUrl(), spannViewWidth, spannViewHeight);
//    		slidesView.addSlide(sView);
//    	}
    	
    	//    	slideTitle.setText(getText(R.string.product_default_info));
    	productsList  = (ListView)rootView.findViewById(R.id.school_lists_infos);
    	productsList.setOnScrollListener(this);
    	productsList.setOnItemClickListener(this);
    	productsList.setAdapter(new BaseAdapter() {
			
    		final class ViewHolder
    		{
    			NetImageView productIv ;
    			TextView  productTitle ;
    			TextView  productDetails ;
    		}
    		LayoutInflater layFlater =LayoutInflater.from(attachedActivity);
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ViewHolder viewHolder ;
				if(convertView == null)
				{
					viewHolder = new ViewHolder();
					convertView = layFlater.inflate(R.layout.product_list_item, parent,false);
					viewHolder.productIv = (NetImageView)convertView.findViewById(R.id.product_pic_iv);
					viewHolder.productTitle = (TextView)convertView.findViewById(R.id.product_title_tv);
					viewHolder.productDetails = (TextView)convertView.findViewById(R.id.product_details_tv);
					convertView.setTag(viewHolder);
				}else
				{
					viewHolder = (ViewHolder)convertView.getTag();
				}
				Product product = products.get(position);
				viewHolder.productIv.setNetIamgeUrl(product.getFirstImageUrl(),
						R.drawable.product_default_0,
						productViewWidth,productViewHeight);
				viewHolder.productTitle.setText(product.getTitle());
				viewHolder.productDetails.setText(product.getAbstractString());
//				Debug.log.i("product", product.getTitle());
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return products.get(position);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return products.size();
			}
		});
    }

    private Handler uiHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			 if(loadDig != null)
			 {
				 loadDig.dismiss();
			 }
			 Object obj = msg.obj ;
			 switch(msg.what)
			 {
			   case 1:
			   {
//	    		   if(obj != null)
	    		   {
	    		      updateViewData();
	    		      mSwipeLayout.setRefreshing(false);
	    		   }
			   } break;
			   case 2:
					Toast.makeText(attachedActivity, R.string.request_timeout, Toast.LENGTH_SHORT)
							.show();
					mSwipeLayout.setRefreshing(false);
					break;
				case 3: {
					Toast.makeText(attachedActivity, R.string.network_io_error, Toast.LENGTH_SHORT)
							.show();
					mSwipeLayout.setRefreshing(false);
				}
					break;
				default:
					mSwipeLayout.setRefreshing(false);
			 }
		}
	};
    private void loadNetData()
	{
	    taskJob = new GetProductList(currentPage);
		FlyTaskManager  taskMng = FlyApplication.getFlyTaskManager();
		if(taskMng != null)
		{
			 if(loadDig == null)
			 {
			    loadDig = new LoadDialog(attachedActivity).builder().setMessage("ÕýÔÚ¼ÓÔØ..."); 
			 }
			 mSwipeLayout.setRefreshing(true);
//			 loadDig.show();	 
			taskMng.commitJob(taskJob, new ResultCallback() {					
				@Override
				public void notifyResult(Object result) {
					// TODO Auto-generated method stub
					if(result != null)
		    		{
		    			if(result instanceof List<?>)
		    			{	
		    			  Object obj =  null ;
		    			  int size = ((List)result).size() ;
		    			  if(size > 0)
		    			  {
		    				  obj = ((List)result).get(0);
		    				  currentPage ++ ;
		    			  }else
		    			  {
		    				  return ;
		    			  } 
		    			  if(obj instanceof Product)
		    			  {
		    				  ArrayList<Product> data = new ArrayList<Product>();
		    				  data.addAll((List)result);
		    				  data.addAll(products);
		    				  data.addAll(productspanner);
		    				  DataCatcheTools.catcheProductData(attachedActivity, data);
		    				  spliteData((List)result);
		    				  uiHandler.obtainMessage(1).sendToTarget();
		    			  }
		    			}else if(result instanceof ErrorMsg)
		    			{
		    				ErrorMsg error = (ErrorMsg) result;
							switch (error.getErrorCode()) {
							case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
								uiHandler.sendEmptyMessage(2);
								break;
							case ErrorMsg.ERROR_NETWORK_IO_ERROR:
								uiHandler.sendEmptyMessage(3);
								break;
							}
		    			}
		    		}
				}
			});
		}
	}
    
    private void updateViewData()
	{
		if(!this.products.isEmpty() && !this.productspanner.isEmpty())
		{
		 
			slidesView.clearViews();
			for(ProductBanner panner : productspanner)
	    	{
	    		SpannerView sView = new SpannerView(getActivity());
	    		FlyProduct slidesPro  = getSlideProduct(panner);
	    		if(slidesPro != null)
	    		{
	    			sView.setTag(slidesPro);
	    		}
	    		sView.setTitleAndImageUrl(panner.getTitle(), panner.getFirstImageUrl(), spannViewWidth, spannViewHeight);
	    		slidesView.addSlide(sView);
	    	}
			((BaseAdapter)productsList.getAdapter()).notifyDataSetChanged();
		}
	}
    
	private FlyProduct  getSlideProduct(ProductBanner  banner)
	{
		if(banner == null)
			return null;
		for(FlyProduct pro:products)
		{
			if(pro.getId() == banner.getId())
				return pro ;
		}
		return null ;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent  intent = new Intent(attachedActivity,FlyProductDetails.class);
		intent.putExtra("product", products.get(arg2));
		startActivity(intent);
	}
	
	public void clickView(View v)
	{
		  switch(v.getId())
		  {
		    case R.id.back_img:
		    	attachedActivity.finish();
			  break;
		  }
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		uiHandler = null ;
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		loadNetData();
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
}
