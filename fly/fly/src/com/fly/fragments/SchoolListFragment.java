package com.fly.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.School;
import com.fly.view.NetImageView;
import com.fly.view.SlidesView;
import com.fly.view.SpannerView;

public class SchoolListFragment extends BaseFramgment  implements OnItemClickListener,OnPreDrawListener{
	
	private View fView ;
	private FragmentActivity attachedActivity ;
	
//	private ImageSwitcher productSlides ;
//	private TextSwitcher      slideTitle ;
	private SlidesView    slidesView ;
	private ListView      productsList ;
	
	private ArrayList<School>  products = new ArrayList<School>();
	
	public SchoolListFragment(ArrayList<School> items)
	{
		if(items != null && !items.isEmpty())
		    this.products.addAll(items);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		attachedActivity = (FragmentActivity)activity;
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
    	View backView = rootView.findViewById(R.id.back_img);
    	backView.setVisibility(View.INVISIBLE);
    	
    	View shareView = rootView.findViewById(R.id.share_img);
    	shareView.setVisibility(View.INVISIBLE);
    	
    	TextView  title = (TextView)rootView.findViewById(R.id.title);
    	title.setText(R.string.thxx_title);
    	
//    	productSlides = (ImageSwitcher)rootView.findViewById(R.id.top_zhanshi_iv);
//    	slideTitle    = (TextSwitcher)rootView.findViewById(R.id.first_news_info_tv);
//    	slideTitle.setText(getText(R.string.product_default_info));
    	
    	slidesView    = (SlidesView)rootView.findViewById(R.id.slide_view);
    	
    	SpannerView spanner = new SpannerView(getActivity());
    	spanner.setTitleAndImageUrl(getString(R.string.product_default_info), "http://a.hiphotos.baidu.com/image/w%3D310/sign=3c23740659b5c9ea62f305e2e538b622/b90e7bec54e736d12796ce0599504fc2d5626931.jpg");
    	slidesView.addSlide(spanner);
    	
    	SpannerView spanner1 = new SpannerView(getActivity());
    	spanner1.setTitleAndImageUrl(getString(R.string.product_default_info), "http://a.hiphotos.baidu.com/image/w%3D310/sign=3c23740659b5c9ea62f305e2e538b622/b90e7bec54e736d12796ce0599504fc2d5626931.jpg");
    	slidesView.addSlide(spanner1);
    	
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
				School product = products.get(position);
				viewHolder.productIv.setNetIamgeUrl(product.getFirstImageUrl());
				viewHolder.productTitle.setText(product.getTitle());
				viewHolder.productDetails.setText(product.getAbstractString());
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
    	
    	rootView.getViewTreeObserver().addOnPreDrawListener(this);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onPreDraw() {
		// TODO Auto-generated method stub
		int listViewHeight = productsList.getHeight();
		RelativeLayout.LayoutParams  relatice = (LayoutParams) productsList.getLayoutParams();
		relatice.height  = 400;
		fView.getViewTreeObserver().removeOnPreDrawListener(this);
		return true;
	}
    
    
}
