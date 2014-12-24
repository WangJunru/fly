package com.fly.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.Product;
import com.fly.sdk.ProductBanner;
import com.fly.sdk.SchoolPanner;
import com.fly.ui.activity.FlyProductDetails;
import com.fly.ui.view.NetImageView;
import com.fly.ui.view.SlidesView;
import com.fly.ui.view.SpannerView;
import com.fly.util.debug.Debug;

public class ProductListFragment extends BaseFramgment implements OnItemClickListener{
	
	
	private View fView ;
    private FragmentActivity attachedActivity ;
	
    private SlidesView    slidesView ;
	private ListView      productsList ;
	
	private ArrayList<Product>  products = new ArrayList<Product>();
	private ArrayList<ProductBanner> productspanner = new ArrayList<ProductBanner>();
	
	private int spannViewWidth , spannViewHeight ;
	private int productViewWidth ,productViewHeight ;
	
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
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.slidesView.onResume();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.slidesView.onStop();
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
    	opt.inJustDecodeBounds  = true ;
    	BitmapFactory.decodeResource(getResources(), R.drawable.product_slide_0, opt);
    	this.spannViewWidth = opt.outWidth ;
    	this.spannViewHeight = opt.outHeight;
    	
    	opt.inJustDecodeBounds  = true ;
    	BitmapFactory.decodeResource(getResources(), R.drawable.product_default_image, opt);
    	this.productViewWidth  =  opt.outWidth;
    	this.productViewHeight =  opt.outHeight;
    	
    	View backView = rootView.findViewById(R.id.back_img);
    	backView.setVisibility(View.INVISIBLE);
    	
    	View shareView = rootView.findViewById(R.id.share_img);
    	shareView.setVisibility(View.INVISIBLE);
    	
    	TextView  title = (TextView)rootView.findViewById(R.id.title);
    	title.setText(R.string.fxzl_title);
    	
//    	productSlides = (ImageSwitcher)rootView.findViewById(R.id.top_zhanshi_iv);
//    	slideTitle    = (TextSwitcher)rootView.findViewById(R.id.first_news_info_tv);
    	
    	slidesView =    (SlidesView)rootView.findViewById(R.id.slide_view);
    	for(ProductBanner panner : productspanner)
    	{
    		SpannerView sView = new SpannerView(getActivity());
    		sView.setTitleAndImageUrl(panner.getTitle(), panner.getFirstImageUrl(), spannViewWidth, spannViewHeight);
    		slidesView.addSlide(sView);
    	}
    	
    	//    	slideTitle.setText(getText(R.string.product_default_info));
    	productsList  = (ListView)rootView.findViewById(R.id.school_lists_infos);
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
						R.drawable.product_default_image,
						productViewWidth,productViewHeight);
				viewHolder.productTitle.setText(product.getTitle());
				viewHolder.productDetails.setText(product.getAbstractString());
				Debug.log.i("product", product.getTitle());
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent  intent = new Intent(attachedActivity,FlyProductDetails.class);
		intent.putExtra("product", products.get(arg2));
		startActivity(intent);
	}
}
