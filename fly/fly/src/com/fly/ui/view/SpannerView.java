package com.fly.ui.view;

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
import com.fly.util.debug.Debug;

public class SpannerView extends RelativeLayout {

	private NetImageView img;
	private TextView title;

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

	private void initView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.slides_item_view_layout, this);
		img = (NetImageView) this.findViewById(R.id.banner_image_iv);
		img.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Debug.log.i("spanner_view",
						img.getWidth() + ":" + img.getHeight());
			}
		});
		title = (TextView) this.findViewById(R.id.panner_info_tv);
	}

	public void setTitleAndImageUrl(String title, String url, int defaultWidth,
			int defaultHeigth) {
		if (!TextUtils.isEmpty(title)) {
			this.title.setText(title);
		}

		if (!TextUtils.isEmpty(url)) {
			this.img.setNetIamgeUrl(url, R.drawable.product_slide_0,
					defaultWidth, defaultHeigth);
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

}
