package com.fly.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.fly.R;
import com.fly.sdk.util.TextUtils;

public class SpannerView extends RelativeLayout {

	private NetImageView img ;
	private TextView   title ;
	public SpannerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	public SpannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}
	public SpannerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.slides_item_view_layout, this);
		img   = (NetImageView)this.findViewById(R.id.banner_image_iv);
		title = (TextView)this.findViewById(R.id.panner_info_tv);
	}
	
	public void setTitleAndImageUrl(String title , String url)
	{
		if(!TextUtils.isEmpty(title))
		{
		   this.title.setText(title);
		}
		
		if(!TextUtils.isEmpty(url))
		{
		   this.img.setNetIamgeUrl(url);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		    int width = 0;
		     int height = getPaddingTop();
		 final int count = getChildCount();
		     
		    for (int i = 0; i < count; i++) {
		         View child = getChildAt(i);
		         //����û���ӳ�Ա�Ĵ�С������widthMeasureSpec��heightMeasureSpec��
		         measureChild(child, widthMeasureSpec, heightMeasureSpec);
		         //����ӳ�Ա�Ĳ��ֲ���������x��yΪ���ϵ������
		         LayoutParams lp = (LayoutParams) child.getLayoutParams();
		         //�õ���Ҫ�����ӳ�Ա����padding
		         width = getPaddingLeft() ;
		         //���ã�������������
		         lp.topMargin = height ;
		         lp.leftMargin = width ;
//		         child.setX(width);
//		         child.setY(height);
//		         child.x = width;
//		         child.y = height;
		         //��������+ʵ�ʿ�ȣ��õ�ViewGroup�Ĵ�С
		         width += child.getMeasuredWidth();
		    }
		    
		    //������padding�õ��ܿ��
		    width += getPaddingRight();
		    //TopPadding+ getMeasuredHeight���ӳ�Ա��ʵ�ʸ߶ȣ�+BottomPdding�õ��ܸ߶�
		    height += getChildAt(getChildCount() - 1).getMeasuredHeight()+ getPaddingBottom();
		    setMeasuredDimension(resolveSize(width, widthMeasureSpec),  resolveSize(height, heightMeasureSpec));
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    
	    final int count = getChildCount();
	    
	    for (int i = 0; i < count; i++) {
	         View child = getChildAt(i);
	         LayoutParams lp = (LayoutParams) child.getLayoutParams();
	         child.layout(lp.leftMargin ,lp.topMargin, lp.leftMargin + child.getMeasuredWidth(), lp.topMargin  + child.getMeasuredHeight());
	    }
	}
	
	public static int resolveSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);

        switch (specMode) {
        case MeasureSpec.UNSPECIFIED:
            result = size;
            break;
        case MeasureSpec.AT_MOST:
            result = Math.min(size, specSize);
            break;
        case MeasureSpec.EXACTLY:
            result = specSize;
            break;
        }
        return result;
    }

}
