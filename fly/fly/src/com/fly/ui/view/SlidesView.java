package com.fly.ui.view;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fly.R;
import com.fly.ui.activity.ViewPagerAdapter;

public class SlidesView extends FrameLayout implements OnTouchListener {

	private ViewPager pannerViewPager;
	private LinearLayout viewGuideDotsCon;

	private ArrayList<View> spannerViews = new ArrayList<View>();

	private ArrayList<ImageView> guideDots = new ArrayList<ImageView>();

	private ViewPagerAdapter viewPagerAdapter;

	private int currentIndex = 0 ;

	private boolean isTouch = false ;
	private boolean isStoped = false ;
	
	private long touchDownTime = 0 ;
	
	
	
	public SlidesView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public SlidesView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public SlidesView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:
		{
			touchDownTime = System.currentTimeMillis();
			isTouch = true;
		}break;
		case MotionEvent.ACTION_MOVE: {
			isTouch = true;
		}break;
		case MotionEvent.ACTION_UP: {
           isTouch = false ;
           if(System.currentTimeMillis() - touchDownTime >= ViewConfiguration.getPressedStateDuration())
           {
        	   performClick();
           }
		}
			break;
		}
		return false;
	}

	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}
	
	public void onResume()
	{
		this.isStoped = false ;
	}
	public void onStop()
	{
		this.isStoped = true ;
	}
	
	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.slides_view_layout,
				this);
		this.setOnTouchListener(this);
		
		pannerViewPager = (ViewPager) this.findViewById(R.id.slides_view_pager);
		pannerViewPager.setHorizontalFadingEdgeEnabled(true);
		pannerViewPager.setFadingEdgeLength(30);
		pannerViewPager.postDelayed(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(isStoped == false && isTouch == false)
				{
					int viewItemsCount = spannerViews.size();
					if(viewItemsCount > 0)
					{
						int index = (currentIndex+1)%viewItemsCount ;
						pannerViewPager.setCurrentItem(index);
						setCurrentDot(index);
					}
				}
				pannerViewPager.postDelayed(this,5000);
			}
		},5000);
		
		viewGuideDotsCon = (LinearLayout) this
				.findViewById(R.id.slides_guide_dots);
		viewPagerAdapter = new ViewPagerAdapter(spannerViews);
		pannerViewPager.setAdapter(viewPagerAdapter);

		pannerViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	public void clearViews()
	{
		this.spannerViews.clear();
		viewPagerAdapter.notifyDataSetChanged();
		guideDots.clear();
		viewGuideDotsCon.removeAllViews();
	}
	
	public void addSlide(SpannerView slide) {
		this.spannerViews.add(slide);
		addGuidesDots();
		viewPagerAdapter.notifyDataSetChanged();

	}

	private void addGuidesDots() {
		ImageView dot = new ImageView(getContext());
		dot.setPadding(0, 0, 10, 0);
		dot.setImageResource(R.drawable.slides_dot_selector);
		dot.setClickable(true);
		guideDots.add(dot);
		viewGuideDotsCon.addView(dot);
		initDot();
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
			// 计算没个子成员的大小并放入widthMeasureSpec和heightMeasureSpec中
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			// 获得子成员的布局参数，其中x和y为左上点的坐标
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			// 得到需要设置子成员的左padding
			width = getPaddingLeft();
			// 设置，设置左上坐标
			lp.topMargin = height;
			lp.leftMargin = width;
			// child.setX(width);
			// child.setY(height);
			// child.x = width;
			// child.y = height;
			// 左上坐标+实际宽度，得到ViewGroup的大小
			width += child.getMeasuredWidth();
		}

		// 加上右padding得到总宽度
		width += getPaddingRight();
		// TopPadding+ getMeasuredHeight（子成员的实际高度）+BottomPdding得到总高度
		height += getChildAt(getChildCount() - 1).getMeasuredHeight()
				+ getPaddingBottom();
		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		final int count = getChildCount();

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			child.layout(lp.leftMargin, lp.topMargin,
					lp.leftMargin + child.getMeasuredWidth(), lp.topMargin
							+ child.getMeasuredHeight());
		}
	}

	public static int resolveSize(int size, int measureSpec) {
		int result = size;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

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

	public void initDot() {
		for (int i = 0; i < spannerViews.size(); i++) {
			guideDots.get(i).setSelected(false);
		}
		currentIndex = 0;
		guideDots.get(currentIndex).setSelected(true);
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > spannerViews.size() - 1
				|| currentIndex == position) {
			return;
		}

		guideDots.get(position).setSelected(true);
		guideDots.get(currentIndex).setSelected(false);
		currentIndex = position;
	}

}
