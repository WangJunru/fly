package com.fly.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fly.R;

public class NetImageView extends ImageView {

	private String netImageUri ;
	
	public NetImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public NetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public NetImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setNetIamgeUrl(String url)
	{
		this.netImageUri  = url ;
		if(getDrawable() == null)
		{
			ImageManager2.from(getContext()).displayImage(this, this.netImageUri, R.drawable.product_slide_0);
		}
	}
	
	public String  getNetImageUrl()
	{
		return this.netImageUri;
	}
	

}
