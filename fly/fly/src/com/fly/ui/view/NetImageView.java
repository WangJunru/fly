package com.fly.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fly.sdk.util.TextUtils;

public class NetImageView extends ImageView {

	private String netImageUri;

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

	public void setNetIamgeUrl(String url, int defaultDrawableID,
			int defaultWidth, int defaultHeigth) {
		this.netImageUri = url;
		if (!TextUtils.isEmpty(this.netImageUri)) {
			
			ImageManager2.from(getContext()).displayImage(this,
					this.netImageUri, defaultDrawableID, defaultWidth,
					defaultHeigth,false);
		}
	}

	public String getNetImageUrl() {
		return this.netImageUri;
	}
	
	public void  setImageDrawable(Drawable d)
	{
		super.setImageDrawable(d);
		invalidate();
	}
	
	public void setImageBitmap(Bitmap b)
	{
		super.setImageBitmap(b);
		invalidate();
	}

}
