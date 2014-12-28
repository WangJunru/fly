package com.fly.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.R;

public class UserInfoItemView extends LinearLayout {

	private TextView labelTv ;
	private TextView  valueTv ;
	private Context context ;
	private int viewHeight = 0 ;
	
	public UserInfoItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
	}
	public UserInfoItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context ;
		initView();
		setCustomAttributes(attrs);
	}

	private void initView()
	{
	    LayoutInflater.from(context).inflate(R.layout.user_center_view_item, this);
		labelTv = (TextView)findViewById(R.id.item_lable);
		valueTv = (TextView)findViewById(R.id.item_lable_value);
		Options  opt = new Options();
		opt.inJustDecodeBounds = true ;
		BitmapFactory.decodeResource(context.getResources(), R.drawable.geren_zhuye_item_bg, opt);
		viewHeight = opt.outHeight ;
	}

	 private void setCustomAttributes(AttributeSet attrs) {  
	        TypedArray a = context.obtainStyledAttributes(attrs,  
	                R.styleable.user_info_item);  
	        
	        labelTv.setTextColor(a.getColor(R.styleable.user_info_item_label_text_color,
	        		context.getResources().getColor(R.color.user_name_label_color)));
	        labelTv.setText(a.getString(R.styleable.user_info_item_label_text));  
//	        labelTv.setTextSize(a.getDimension(R.styleable.user_info_item_label_text_size,
//	        		context.getResources().getDimension(R.dimen.pt_17)));
	        
	        
	        valueTv.setTextColor(a.getColor(R.styleable.user_info_item_value_text_color,
	        		context.getResources().getColor(R.color.user_name_color)));
	        valueTv.setText(a.getString(R.styleable.user_info_item_value_text));  
//	        valueTv.setTextSize(a.getDimension(R.styleable.user_info_item_value_text_size,
//	        		context.getResources().getDimension(R.dimen.pt_17)));
	        a.recycle();
   }
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),viewHeight);
		this.requestLayout();
	} 
	
	public void  setValueText(int resID)
	{
		valueTv.setText(resID);
	}
	public void setValueText(String text)
	{
		valueTv.setText(text);
	}
	public String getValueText()
	{
		return valueTv.getText().toString();
	}
	public void setValueTypeFace(Typeface fontFace)
	{
		labelTv.setTypeface(fontFace);
	}
	public void setValueTypeFace(Typeface fontFace,int style)
	{
		labelTv.setTypeface(fontFace,style);
	}
	
	public void  setLabelText(int resID)
	{
		labelTv.setText(resID);
	}
	public void setLabelText(String text)
	{
		labelTv.setText(text);
	
	}
	public void setLabelTypeFace(Typeface fontFace)
	{
		labelTv.setTypeface(fontFace);
	}
	public void setLabelTypeFace(Typeface fontFace,int style)
	{
		labelTv.setTypeface(fontFace,style);
	}
}
